package com.czh.example.proxy;


import java.lang.reflect.Proxy;

/**
 * 服务代理工厂（用于创建代理对象）
 */
public class ServiceProxyFactory {

    /**
     * 根据服务类获取代理对象
     * @param serviceClass 需要代理的服务
     * @return 返回服务对象
     * @param <T> 泛型
     */
    public static <T> T getProxy(Class<T> serviceClass){
        return (T) Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class[]{serviceClass},
                new ServiceProxy()
        );
    }
}


//public class ServiceProxyFactory {
//
//    /**
//     * 根据服务类获取代理对象
//     * @param serviceClass
//     * @return
//     * @param <T>
//     */
//    public static <T> T getProxy(Class<T> serviceClass){
////        如果mock值为true代表不使用远程调用
//        if(RpcApplication.getRpcConfig().isMock()){
//            return getMockProxy(serviceClass);
//        }
//
//        return (T) Proxy.newProxyInstance(
//                serviceClass.getClassLoader(),
//                new Class[]{serviceClass},
//                new ServiceProxy()
//        );
//    }
//
//    private static <T> T getMockProxy(Class<T> serviceClass) {
//        return (T) Proxy.newProxyInstance(
//                serviceClass.getClassLoader(),
//                new Class[]{serviceClass},
//                new MockServiceProxy()
//        );
//    }
//}
