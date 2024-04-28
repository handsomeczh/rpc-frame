package com.czh.example.proxy;


import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.czh.example.model.RpcRequest;
import com.czh.example.model.RpcResponse;
import com.czh.example.serializer.impl.JdkSerializer;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * JDK动态代理
 * invocation:调用
 * declare:声明
 */
public class ServiceProxy implements InvocationHandler {


    /**
     * 调用代理
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        //指定序列化器
        JdkSerializer serializer = new JdkSerializer();

//        构造请求
        String serviceName = method.getDeclaringClass().getName();
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(serviceName)
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();
        try {
            // 序列化
            byte[] bodyBytes = serializer.serialize(rpcRequest);
            //发送请求
            try (HttpResponse httpResponse = HttpRequest.post("http://localhost:8080")
                    .body(bodyBytes)
                    .execute()) {
                byte[] result = httpResponse.bodyBytes();
                //反序列化
                RpcResponse rpcResponse = serializer.deserialize(result, RpcResponse.class);
                return rpcResponse.getData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}


//public class ServiceProxy implements InvocationHandler {
//
//
//    /**
//     * 调用代理
//     *
//     * @return
//     * @throws Throwable
//     */
//    @Override
//    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
////        构造请求
//        String serviceName = method.getDeclaringClass().getName();
//        RpcRequest rpcRequest = RpcRequest.builder()
//                .serviceName(serviceName)
//                .methodName(method.getName())
//                .parameterTypes(method.getParameterTypes())
//                .args(args)
//                .build();
//        try {
////            从注册中心获取服务提供者请求地址
//            RpcConfig rpcConfig = RpcApplication.getRpcConfig();
//            Registry registry = RegistryFactory.getInstance(rpcConfig.getRegistryConfig().getRegistry());
//            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
//            serviceMetaInfo.setServiceName(serviceName);
//            serviceMetaInfo.setServiceVersion(RpcConstant.DEFAULT_SERVICE_VERSION);
//            List<ServiceMetaInfo> serviceMetaInfoList = registry.serviceDiscovery(serviceMetaInfo.getServiceKey());
//            if (CollUtil.isEmpty(serviceMetaInfoList)) {
//                throw new RuntimeException("暂无服务地址");
//            }
//
////            负载均衡
//            LoadBalancer loadBalancer = LoadbalancerFactory.getInstance(rpcConfig.getLoadBalancer());
////            将调用方法名（请求路径）作为负载均衡参数，调用相同方法总会请求到同一个服务器节点上
////            todo 自定义一致性hash算法中的hash算法：根据客户端ip地址来计算hash值，保证同ip的请求发送给相同服务提供者
//            HashMap<String, Object> requestParams = new HashMap<>();
//            requestParams.put("methodName", rpcRequest.getMethodName());
//            ServiceMetaInfo selectServiceMetaInfo = loadBalancer.select(requestParams, serviceMetaInfoList);
//
//
//            //            发送TCP请求
////            重试策略
//            RpcResponse rpcResponse = null;
//            try {
//                RetryStrategy retryStrategy = RetryStrategyFactory.getInstance(rpcConfig.getRetryStrategy());
//                rpcResponse = retryStrategy.doRetry(() ->
//                        VertxTcpClient.doRequest(rpcRequest, selectServiceMetaInfo)
//                );
//            } catch (Exception e) {
//                //多次重试失败后触发容错机制
//                TolerantStrategy tolerantStrategy = TolerantStrategyFactory.getInstance(rpcConfig.getTolerantStrategy());
//                rpcResponse = tolerantStrategy.doTolerant(null,e);
//            }
//            return rpcResponse.getData();
//
//        } catch (Exception e) {
//            throw new RuntimeException("调用失败");
//        }
//
//    }
//}
