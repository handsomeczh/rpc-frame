package com.czh.example.registry;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ConcurrentHashSet;
import cn.hutool.cron.CronUtil;
import cn.hutool.cron.task.Task;
import cn.hutool.json.JSONUtil;
import com.czh.example.cache.RegistryServiceCache;
import com.czh.example.config.RegistryConfig;
import com.czh.example.model.ServiceMetaInfo;
import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.KeyValue;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.ScanArgs;
import io.lettuce.core.ScanIterator;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.pubsub.RedisPubSubAdapter;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.sync.RedisPubSubCommands;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Redis注册中心接口
 *
 * @author czh
 * @version 1.0.0
 * 2024/5/7 21:07
 */
public class RedisRegistry implements Registry {

    //redis客户端
    private RedisClient redisClient;

    //redis连接
    private StatefulRedisConnection<String, String> connection;

    // 创建同步命令
    private RedisCommands<String, String> syncCommands;

    //本机注册的节点key集合（用于维护续期）
    private final Set<String> localRegisterNodeKeySet = new HashSet<>();

    // 注册中心服务缓存
    private final RegistryServiceCache serviceCache = new RegistryServiceCache();

    // 服务节点生存时间
    private Long REDIS_TTL = 30L;

    //正在监听的key的集合
    private final Set<String> watchKeys = new ConcurrentHashSet<>();

    /**
     * 初始化
     * 读取注册中心配置并初始化客户端对象
     */
    @Override
    public void init(RegistryConfig registryConfig) {
        // 创建RedisURI实例并设置主机名、端口和密码
        RedisURI redisURI = RedisURI.builder()
                .withHost(registryConfig.getHost())
                .withPort(registryConfig.getPort())
                .withPassword(registryConfig.getPassword().toCharArray())
                .build();
        redisClient = RedisClient.create(redisURI);
        connection = redisClient.connect();
        syncCommands = connection.sync();
        System.out.println(syncCommands.get("helloKey"));
        //心跳检测
        heartBeat();
    }

    /**
     * 服务提供者注册服务
     */
    @Override
    public void register(ServiceMetaInfo serviceMetaInfo) throws Exception {
        //创建key和value key = "%s:%s:%s:%s",serviceName,serviceVersion,serviceHost,servicePort
        String key = serviceMetaInfo.getRedisServiceKey();
        String value = JSONUtil.toJsonStr(serviceMetaInfo);
        syncCommands.set(key, value);
        //设置生存时间
        syncCommands.expire(key, REDIS_TTL);
        // 将已经创建的服务的节点键名存进localRegisterNodeKeySet，用于后续维护续期
        localRegisterNodeKeySet.add(key);
        System.out.println("服务提供者-注册服务成功");
    }

    /**
     * 注销服务（服务端）,删除key
     */
    @Override
    public void unRegister(ServiceMetaInfo serviceMetaInfo) {
        String key = serviceMetaInfo.getRedisServiceKey();
        syncCommands.del(key);
        localRegisterNodeKeySet.remove(key);
        System.out.println("服务提供者-注销服务成功");
    }

    /**
     * 服务发现（获取某服务的所有节点，消费端）
     */
    @Override
    public List<ServiceMetaInfo> serviceDiscovery(String serviceKey) {
        //优先从缓存获取服务
        List<ServiceMetaInfo> cacheServiceMetaInfoList = serviceCache.readCache();
        if (cacheServiceMetaInfoList != null && cacheServiceMetaInfoList.size() != 0) {
            System.out.println("RPC框架-从服务消费者本地缓存获取服务对象");
            return cacheServiceMetaInfoList;
        }
        System.out.println("RPC框架-从Redis注册中心获取服务对象");
        //从注册中心读取 serviceKey = serviceMetaInfo.getServiceKey() = "%s:%s", serviceName, serviceVersion
        // key = "%s:%s:%s:%s",serviceName,serviceVersion,serviceHost,servicePort
        //搜索得到所有符合的键
        List<ServiceMetaInfo> list = new ArrayList<>();
        // 使用 SCAN 命令迭代搜索带有指定前缀的键
        io.lettuce.core.ScanIterator<String> iterator = ScanIterator.scan(syncCommands, ScanArgs.Builder.matches(serviceKey + "*").limit(1000));
        while (iterator.hasNext()) {
            //对搜索到的键获取value,处理value；
            String key = iterator.next();
            String value = syncCommands.get(key);
            list.add(JSONUtil.toBean(value, ServiceMetaInfo.class));
            //监听key的变化
            watch(key);
        }
        // 写入RPC框架本地缓存
        serviceCache.writeCache(list);
        System.out.println("RPC服务提供者-从注册中心服务发现");
        return list;
    }

    /**
     * 服务销毁,用于项目关闭后释放资源
     */
    @Override
    public void destroy() {
        System.out.println("当前服务提供者节点下线");
        //遍历本节点所有的key
        for (String key : localRegisterNodeKeySet) {
            syncCommands.del(key);
        }
        //释放资源
        if (redisClient != null) {
            redisClient.close();
        }
        if (connection != null) {
            connection.close();
        }
    }

    @Override
    public void heartBeat() {
//        每10秒续签一次
        CronUtil.schedule("*/10 * * * * *", (Task) () -> {
            //遍历本节点所有的key
            for (String key : localRegisterNodeKeySet) {
                //key = "%s:%s:%s:%s",serviceName,serviceVersion,serviceHost,servicePort
                String value = syncCommands.get(key);
                if(value == null){
                    //节点已过期，需要重启节点才能重新注册
                    continue;
                }
                // 续签，即重新注册
                syncCommands.expire(key,REDIS_TTL);
            }
        });

        //支持秒级别定时任务
        CronUtil.setMatchSecond(true);
        CronUtil.start();
    }

    @Override
    public void watch(String key) {
        boolean newKey = watchKeys.add(key);
        if (newKey) {
            //监听键事件
            StatefulRedisPubSubConnection<String, String> pubSubConnection = redisClient.connectPubSub();
            RedisPubSubCommands<String, String> sync = pubSubConnection.sync();
            //监听key的过期和删除事件，发生时回发送message
            sync.psubscribe("__keyevent@0__:expired", "__keyevent@0__:del");
            //处理监听到的事件
            pubSubConnection.addListener(new RedisPubSubAdapter<String, String>() {
                @Override
                public void message(String channel, String message) {
                    // 监听到特定键删除或者过期,清空缓存即可
                    if (message.equals(key)) {
                        System.out.println("RPC框架-服务提供者的服务节点下线或者删除，清空本地缓存（消费端）");
                        serviceCache.clearCache();
                    }
                }
            });
        }
    }


}
