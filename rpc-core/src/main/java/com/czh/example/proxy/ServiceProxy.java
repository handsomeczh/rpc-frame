package com.czh.example.proxy;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.czh.example.application.RpcApplication;
import com.czh.example.config.RpcConfig;
import com.czh.example.constant.RpcConstant;
import com.czh.example.factory.RegistryFactory;
import com.czh.example.factory.RetryStrategyFactory;
import com.czh.example.factory.SerializerFactory;
import com.czh.example.factory.TolerantStrategyFactory;
import com.czh.example.fault.retry.RetryStrategy;
import com.czh.example.fault.tolerant.TolerantStrategy;
import com.czh.example.loadbalancer.LoadBalancer;
import com.czh.example.loadbalancer.LoadbalancerFactory;
import com.czh.example.model.RpcRequest;
import com.czh.example.model.RpcResponse;
import com.czh.example.model.ServiceMetaInfo;
import com.czh.example.protocol.*;
import com.czh.example.registry.Registry;
import com.czh.example.serializer.Serializer;
import com.czh.example.server.tcp.VertxTcpClient;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetClient;


import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * JDK动态代理
 * invocation:调用
 * declare:声明
 *
 * @author czh
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
            serviceMetaInfo.setServiceVersion(RpcConstant.DEFAULT_SERVICE_VERSION);
            List<ServiceMetaInfo> serviceMetaInfoList = registry.serviceDiscovery(serviceMetaInfo.getServiceKey());
            if (CollUtil.isEmpty(serviceMetaInfoList)) {
                throw new RuntimeException("暂无服务地址");
            }

//            负载均衡
            LoadBalancer loadBalancer = LoadbalancerFactory.getInstance(rpcConfig.getLoadBalancer());
//            将调用方法名（请求路径）作为负载均衡参数，调用相同方法总会请求到同一个服务器节点上
            HashMap<String, Object> requestParams = new HashMap<>();
            requestParams.put("methodName", rpcRequest.getMethodName());
            ServiceMetaInfo selectServiceMetaInfo = loadBalancer.select(requestParams, serviceMetaInfoList);


            //            发送TCP请求
//            重试策略
            RpcResponse rpcResponse = null;
            try {
                RetryStrategy retryStrategy = RetryStrategyFactory.getInstance(rpcConfig.getRetryStrategy());
                rpcResponse = retryStrategy.doRetry(() ->
                        VertxTcpClient.doRequest(rpcRequest, selectServiceMetaInfo)
                );
            } catch (Exception e) {
                //多次重试失败后触发容错机制
                TolerantStrategy tolerantStrategy = TolerantStrategyFactory.getInstance(rpcConfig.getTolerantStrategy());
                rpcResponse = tolerantStrategy.doTolerant(null,e);
            }
            return rpcResponse.getData();

        } catch (Exception e) {
            throw new RuntimeException("调用失败");
        }

    }
}
