package com.czh.example.registry;

import cn.hutool.core.collection.ConcurrentHashSet;
import com.czh.example.cache.RegistryServiceCache;
import com.czh.example.config.RegistryConfig;
import com.czh.example.model.ServiceMetaInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.CuratorCache;
import org.apache.curator.framework.recipes.cache.CuratorCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * zookeeper 注册中心
 *
 * @author czh
 * @version 1.0.0
 * 2024/3/21 10:15
 */
@Slf4j
public class ZookeeperRegistry implements Registry {

    private CuratorFramework client;

    private ServiceDiscovery<ServiceMetaInfo> serviceDiscovery;

    /**
     * 本机注册的节点key集合（用于维护续期）
     */
    private final Set<String> localRegistryNodeKeySet = new HashSet<>();

    /**
     * 注册中心服务缓存
     */
    private final RegistryServiceCache registryServiceCache = new RegistryServiceCache();

    /**
     * 正在监听的key集合 ConcurrentHashSet：并发哈希集
     */
    private final Set<String> watchKeySet = new ConcurrentHashSet<>();

    /**
     * 根节点
     */
    private static final String ZK_ROOT_PATH = "/rpc/zk";

    /**
     * 初始化
     *
     * @param registryConfig
     */
    @Override
    public void init(RegistryConfig registryConfig) {
        //构建client实例 retryPolicy: 重试策略 ExponentialBackoffRetry: 指数回退重试
        client = CuratorFrameworkFactory
                .builder()
                .connectString(registryConfig.getAddress())
                .retryPolicy(new ExponentialBackoffRetry(Math.toIntExact(registryConfig.getTimeout()), 3))
                .build();

//        构建serviceDiscovery实例
        serviceDiscovery = ServiceDiscoveryBuilder
                .builder(ServiceMetaInfo.class)
                .client(client)
                .basePath(ZK_ROOT_PATH)
                .serializer(new JsonInstanceSerializer<>(ServiceMetaInfo.class))
                .build();

        try {
            //启动client和serviceDiscovery
            client.start();
            serviceDiscovery.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 注册服务（服务端）
     *
     * @param serviceMetaInfo
     * @throws Exception
     */
    @Override
    public void register(ServiceMetaInfo serviceMetaInfo) throws Exception {
        //注册到zk里
        serviceDiscovery.registerService(buildServiceInstance(serviceMetaInfo));

//        添加节点信息到本地缓存
        String registryKey = ZK_ROOT_PATH + "/" + serviceMetaInfo.getServiceNodeKey();
        localRegistryNodeKeySet.add(registryKey);
    }

    private ServiceInstance<ServiceMetaInfo> buildServiceInstance(ServiceMetaInfo serviceMetaInfo) {
        String serviceAddress = serviceMetaInfo.getServiceHost() + ":" + serviceMetaInfo.getServicePort();
//        String serviceAddress = serviceMetaInfo.getServiceAddress();
        try {
            return ServiceInstance
                    .<ServiceMetaInfo>builder()
                    .id(serviceAddress)
                    .name(serviceMetaInfo.getServiceKey())
                    .address(serviceAddress)
                    .payload(serviceMetaInfo)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    /**
     * 注销服务（服务端）
     *
     * @param serviceMetaInfo
     */
    @Override
    public void unRegister(ServiceMetaInfo serviceMetaInfo) {
        try {
            serviceDiscovery.unregisterService(buildServiceInstance(serviceMetaInfo));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //从本地缓存移除
        String registerKey = ZK_ROOT_PATH + "/" + serviceMetaInfo.getServiceNodeKey();
        localRegistryNodeKeySet.remove(registerKey);

    }

    /**
     * 服务发现（获取某服务的所有节点，消费端）
     *
     * @param serviceKey 服务键名
     * @return
     */
    @Override
    public List<ServiceMetaInfo> serviceDiscovery(String serviceKey) {
        //优先从缓存获取服务
        List<ServiceMetaInfo> cacheServiceMetaInfoList = registryServiceCache.readCache();
        if (cacheServiceMetaInfoList != null) {
            return cacheServiceMetaInfoList;
        }
        try {
            //查询服务信息
            Collection<ServiceInstance<ServiceMetaInfo>> serviceInstanceList = serviceDiscovery.queryForInstances(serviceKey);
            //解析服务信息
            List<ServiceMetaInfo> serviceMetaInfoList = serviceInstanceList.stream()
                    .map(ServiceInstance::getPayload)
                    .collect(Collectors.toList());
            //写入服务缓存
            registryServiceCache.writeCache(serviceMetaInfoList);
            return serviceMetaInfoList;
        } catch (Exception e) {
            throw new RuntimeException("获取服务列表失败", e);
        }
    }

    /**
     * 服务销毁
     */
    @Override
    public void destroy() {
        log.info("当前节点下线");
        //下线节点（可不做，因为都是临时节点，服务下线自然删除）
        for (String key : localRegistryNodeKeySet) {
            try {
                client.delete().guaranteed().forPath(key);
            }catch (Exception e){
                throw new RuntimeException(key+"节点下线失败");
            }
        }
        //释放资源
        if(client != null){
            client.close();
        }

    }

    /**
     * 心跳检测（服务端）
     */
    @Override
    public void heartBeat() {
        //不需要心跳机制，建立了临时节点，如果服务器故障，则临时节点直接丢失
    }

    /**
     * 监听（消费端）
     *
     * @param serviceNodeKey 服务节点key
     */
    @Override
    public void watch(String serviceNodeKey) {
        String watchKey = ZK_ROOT_PATH + "/" + serviceNodeKey;
        boolean newWatch = watchKeySet.add(watchKey);
        if (newWatch) {
            CuratorCache curatorCache = CuratorCache.build(client, watchKey);
            curatorCache.start();
            curatorCache.listenable().addListener(
                    CuratorCacheListener
                            .builder()
                            .forDeletes(childData -> registryServiceCache.clearCache())
                            .forChanges(((oldNode, node) -> registryServiceCache.clearCache()))
                            .build()
            );
        }
    }
}
