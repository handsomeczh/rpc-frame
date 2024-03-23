package com.czh.example.proxy;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import com.czh.example.application.RpcApplication;
import com.czh.example.config.RpcConfig;
import com.czh.example.constant.RpcConstant;
import com.czh.example.factory.RegistryFactory;
import com.czh.example.model.RpcRequest;
import com.czh.example.model.RpcResponse;
import com.czh.example.model.ServiceMetaInfo;
import com.czh.example.protocol.*;
import com.czh.example.registry.Registry;
import com.czh.example.serializer.Serializer;
import com.czh.example.factory.SerializerFactory;
import com.czh.example.server.tcp.VertxTcpClient;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetClient;


import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author czh
 * @version 1.0.0
 * 2024/3/14 14:02
 */
/**
 * JDK动态代理
 * invocation:调用
 * declare:声明
 */
public class ServiceProxy implements InvocationHandler {


    /**
     * 调用代理
     *
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//        构造请求
        String serviceName = method.getDeclaringClass().getName();
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(serviceName)
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();
        try {
//            从注册中心获取服务提供者请求地址
            RpcConfig rpcConfig = RpcApplication.getRpcConfig();
            Registry registry = RegistryFactory.getInstance(rpcConfig.getRegistryConfig().getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceVersion(RpcConstant.DEFAULT_SEVICE_VERSION);
            List<ServiceMetaInfo> serviceMetaInfoList = registry.serviceDiscovery(serviceMetaInfo.getServiceKey());
            if (CollUtil.isEmpty(serviceMetaInfoList)) {
                throw new RuntimeException("暂无服务地址");
            }
//            暂时先去第一个
//            todo 代码优化，目前仅只取了第一个
            ServiceMetaInfo selectedServiceMetaInfo = serviceMetaInfoList.get(0);

            //            发送TCP请求
            RpcResponse rpcResponse = VertxTcpClient.doRequest(rpcRequest, selectedServiceMetaInfo);
            return rpcResponse.getData();

        } catch (Exception e) {
            throw new RuntimeException("调用失败");
        }

    }
}
