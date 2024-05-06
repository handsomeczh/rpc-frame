package com.czh.example;

/**
 * @author czh
 * @version 1.0.0
 * 2024/3/13 23:21
 */


import com.czh.example.application.RpcApplication;
import com.czh.example.config.RegistryConfig;
import com.czh.example.config.RpcConfig;
import com.czh.example.factory.RegistryFactory;
import com.czh.example.model.ServiceMetaInfo;
import com.czh.example.registry.LocalRegistry;
import com.czh.example.registry.Registry;
import com.czh.example.server.impl.VertxHttpService;
import com.czh.example.service.UserService;
import com.czh.example.service.impl.UserServiceImpl;

/**
 * 简易服务提供者示例
 * @author czh
 */
public class EasyProviderExample {
    public static void main(String[] args) {
        // RPC框架初始化
        RpcApplication.init();
        System.out.println(RpcApplication.getRpcConfig());

//        注册服务    注册需要的是实例化对象，不要传递接口对象
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);

        // 注册服务到注册中心
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName(UserService.class.getName());
        serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
        serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
        try {
            registry.register(serviceMetaInfo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

//      启动web服务
        VertxHttpService httpService = new VertxHttpService();
        httpService.doStart(8080);
    }
}




























