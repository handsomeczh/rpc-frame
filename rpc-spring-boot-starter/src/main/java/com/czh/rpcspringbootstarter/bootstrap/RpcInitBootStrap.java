package com.czh.rpcspringbootstarter.bootstrap;

import com.czh.example.application.RpcApplication;
import com.czh.example.config.RpcConfig;
import com.czh.example.server.tcp.VertxTcpServer;
import com.czh.rpcspringbootstarter.annotation.EnableRpc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * Rpc 框架启动
 *
 * @author czh
 * @version 1.0.0
 * 2024/3/25 14:38
 */
@Slf4j
public class RpcInitBootStrap implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata,
                                        BeanDefinitionRegistry registry) {
        //获取EnableRpc注解的属性值
        boolean needServer = (boolean) importingClassMetadata.getAnnotationAttributes(EnableRpc.class.getName()).get("needServer");

        //Rpc框架树池话（配置和注册中心）
        RpcApplication.init();

        //全局配置
        final RpcConfig rpcConfig = RpcApplication.getRpcConfig();

        //启动服务器
        if(needServer){
            VertxTcpServer vertxTcpServer = new VertxTcpServer();
            vertxTcpServer.doStart(rpcConfig.getServerPort());
        }else{
            log.info("不启动 server");
        }
    }
}
