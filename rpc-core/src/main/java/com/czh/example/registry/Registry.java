package com.czh.example.registry;

import com.czh.example.config.RegistryConfig;
import com.czh.example.model.ServiceMetaInfo;

import java.util.List;

/**
 * 注册中心接口
 * @author czh
 * @version 1.0.0
 * 2024/3/20 10:43
 */
public interface Registry {
    //ServiceMetaInfo：服务元信息

    /**
     * 初始化
     */
    void init(RegistryConfig registryConfig);

    /**
     * 注册服务（服务端）
     */
    void register(ServiceMetaInfo serviceMetaInfo) throws Exception;

    /**
     * 注销服务（服务端）
     */
    void unRegister(ServiceMetaInfo serviceMetaInfo);

    /**
     * 服务发现（获取某服务的所有节点，消费端）
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

    /**
     * 监听（消费端）
     */
    void watch(String serviceNodeKey);
}
