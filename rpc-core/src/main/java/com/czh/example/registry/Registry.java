package com.czh.example.registry;

import com.czh.example.config.RegistryConfig;
import com.czh.example.model.ServiceMetaInfo;

import java.util.List;

/**
 * 注册中心接口
 * todo 后续可以实现多种不同注册中心，并且和序列化器一样，可以使用SPI机制动态加载
 * @author czh
 * @version 1.0.0
 * 2024/3/20 10:43
 */
public interface Registry {
    //ServiceMetaInfo：服务元信息

    /**
     * 初始化
     * @param registryConfig
     */
    void init(RegistryConfig registryConfig);

    /**
     * 注册服务（服务端）
     * @param serviceMetaInfo
     * @throws Exception
     */
    void register(ServiceMetaInfo serviceMetaInfo) throws Exception;

    /**
     * 注销服务（服务端）
     * @param serviceMetaInfo
     */
    void unRegister(ServiceMetaInfo serviceMetaInfo);

    /**
     * 服务发现（获取某服务的所有节点，消费端）
     * @param serviceKey 服务键名
     * @return
     */
    List<ServiceMetaInfo> serviceDiscovery(String serviceKey);

    /**
     * 服务销毁
     */
    void destroy();

    /**
     * 心跳检测（服务端）
     */
    void heartBeat();
}
