package com.czh.example.proxy;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.czh.example.application.RpcApplication;
import com.czh.example.config.RpcConfig;
import com.czh.example.constant.RpcConstant;
import com.czh.example.factory.RegistryFactory;
import com.czh.example.model.RpcRequest;
import com.czh.example.model.RpcResponse;
import com.czh.example.model.ServiceMetaInfo;
import com.czh.example.registry.Registry;
import com.czh.example.serializer.Serializer;
import com.czh.example.factory.SerializerFactory;


import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

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
//        指定序列化器
        final Serializer serializer = SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerializer());
//        JdkSerializer serializer = new JdkSerializer();
//        使用工厂+读取配置

//        构造请求
        String serviceName = method.getDeclaringClass().getName();
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(serviceName)
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();
        try {
//        序列化
            byte[] bodyBytes = serializer.serialize(rpcRequest);

//            从注册中心获取服务提供者请求地址
            RpcConfig rpcConfig = RpcApplication.getRpcConfig();
            Registry registry = RegistryFactory.getInstance(rpcConfig.getRegistryConfig().getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceVersion(RpcConstant.DEFAULT_SEVICE_VERSION);
            List<ServiceMetaInfo> serviceMetaInfoList = registry.serviceDiscovery(serviceMetaInfo.getServiceKey());
            if(CollUtil.isEmpty(serviceMetaInfoList)){
                throw new RuntimeException("暂无服务地址");
            }
//            暂时先去第一个
//            todo 代码优化，目前仅只取了第一个
            ServiceMetaInfo selectedServiceMetaInfo = serviceMetaInfoList.get(0);


//        发送请求
//        请求的服务提供者地址被硬编码，需要使用注册中心和服务发现机制解决
            try (HttpResponse httpResponse = HttpRequest.post(selectedServiceMetaInfo.getServiceAddress())
                    .body(bodyBytes)
                    .execute()) {
                byte[] result = httpResponse.bodyBytes();
                //            反序列化
                RpcResponse rpcResponse = serializer.deserialize(result, RpcResponse.class);
                return rpcResponse.getData();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
