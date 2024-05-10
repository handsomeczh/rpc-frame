package com.czh.example.bootstrap;

import com.czh.example.application.RpcApplication;
import com.czh.example.config.RegistryConfig;
import com.czh.example.config.RpcConfig;
import com.czh.example.factory.RegistryFactory;
import com.czh.example.model.ServiceMetaInfo;
import com.czh.example.model.ServiceRegisterInfo;
import com.czh.example.registry.LocalRegistry;
import com.czh.example.registry.Registry;
import com.czh.example.server.tcp.VertxTcpServer;

import java.util.ArrayList;

/**
 * 服务提供者初始化
 * @author czh
 * @version 1.0.0
 * 2024/3/25 13:30
 */
public class ProviderBootStrap {

    /**
     * 初始化
     */
    public static void init(ArrayList<ServiceRegisterInfo> serviceRegisterInfoList){
        //RPC框架初始化（配置和注册中心）
        RpcApplication.init();

        //全局配置
        final RpcConfig rpcConfig = RpcApplication.getRpcConfig();

        //注册服务
        for (ServiceRegisterInfo<?> serviceRegisterInfo : serviceRegisterInfoList) {
            String serviceName = serviceRegisterInfo.getServiceName();
            //本地注册
            LocalRegistry.register(serviceName,serviceRegisterInfo.getImplClass());

            //注册服务到服务中心
            RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
            Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
            serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
            try {
                registry.register(serviceMetaInfo);
            } catch (Exception e) {
                throw new RuntimeException(serviceName+"服务注册失败",e);
            }
        }
        //启动服务器
        VertxTcpServer vertxTcpServer = new VertxTcpServer();
        vertxTcpServer.doStart(rpcConfig.getServerPort());
    }
}
