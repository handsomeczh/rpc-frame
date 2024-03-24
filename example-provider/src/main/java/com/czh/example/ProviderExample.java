package com.czh.example;

import com.czh.example.application.RpcApplication;
import com.czh.example.config.RpcConfig;
import com.czh.example.factory.RegistryFactory;
import com.czh.example.model.ServiceMetaInfo;
import com.czh.example.registry.LocalRegistry;
import com.czh.example.registry.Registry;
import com.czh.example.config.RegistryConfig;
import com.czh.example.server.impl.VertxHttpService;
import com.czh.example.server.tcp.VertxTcpServer;
import com.czh.example.service.UserService;
import com.czh.example.service.impl.UserServiceImpl;


/**
 * 服务提供者示例
 *
 * @author czh
 * @version 1.0.0
 * 2024/3/15 21:07
 */
public class ProviderExample {
    public static void main(String[] args) {
//        rpc框架初始化
        RpcApplication.init();

//        注册服务
        String serviceName = UserService.class.getName();
        LocalRegistry.register(serviceName, UserServiceImpl.class);

//        注册服务到注册中心
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName(serviceName);
        serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
        serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
        serviceMetaInfo.setServiceAddress(rpcConfig.getServerHost()+":"+rpcConfig.getServerPort());
        try {
            registry.register(serviceMetaInfo);
        }catch (Exception e){
            throw new RuntimeException(e);
        }

//        启动TCP服务
        VertxTcpServer vertxTcpServer = new VertxTcpServer();
        vertxTcpServer.doStart(8080);

//        启动web服务
//        VertxHttpService httpService = new VertxHttpService();
//        httpService.doStart(RpcApplication.getRpcConfig().getServerPort());
    }
}
