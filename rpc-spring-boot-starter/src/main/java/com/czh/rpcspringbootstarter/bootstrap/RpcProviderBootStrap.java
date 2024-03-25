package com.czh.rpcspringbootstarter.bootstrap;

import com.czh.example.application.RpcApplication;
import com.czh.example.config.RegistryConfig;
import com.czh.example.config.RpcConfig;
import com.czh.example.factory.RegistryFactory;
import com.czh.example.model.ServiceMetaInfo;
import com.czh.example.registry.LocalRegistry;
import com.czh.example.registry.Registry;
import com.czh.rpcspringbootstarter.annotation.RpcService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * Rpc 服务提供者启动类
 *
 * @author czh
 * @version 1.0.0
 * 2024/3/25 14:38
 */
@Slf4j
public class RpcProviderBootStrap implements BeanPostProcessor {

    /**
     * bean初始化后执行，注册服务
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> aClass = bean.getClass();
        RpcService rpcService = aClass.getAnnotation(RpcService.class);
        if (rpcService != null) {
            //需要注册服务
            //1.获取服务基本信息
            Class<?> interfaceClass = rpcService.interfaceClass();
            //默认值处理
            if (interfaceClass == void.class) {
                interfaceClass = aClass.getInterfaces()[0];
            }
            String serviceName = interfaceClass.getName();
            String serviceVersion = rpcService.serviceVersion();
            //2. 注册服务
            //本地注册
            LocalRegistry.register(serviceName, aClass);

            //全局配置
            final RpcConfig rpcConfig = RpcApplication.getRpcConfig();

            //注册服务到注册中心
            RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
            Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceVersion(serviceVersion);
            serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
            serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
            try {
                registry.register(serviceMetaInfo);
            } catch (Exception e) {
                throw new RuntimeException(serviceName + "服务注册失败", e);
            }
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean,beanName);
    }
}
