package com.czh.example.proxy;

/**
 * @author czh
 * @version 1.0.0
 * 2024/3/14 14:13
 */

import com.czh.example.application.RpcApplication;

import java.lang.reflect.Proxy;

/**
 * 服务代理工厂（用于创建代理对象）
 */
public class ServiceProxyFactory {

    /**
     * 根据服务类获取代理对象
     * @param serviceClass
     * @return
     * @param <T>
     */
    public static <T> T getProxy(Class<T> serviceClass){
//        如果mock值为true代表不使用远程调用
        if(RpcApplication.getRpcConfig().isMock()){
            return getMockProxy(serviceClass);
        }

        return (T) Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class[]{serviceClass},
                new ServiceProxy()
        );
    }

    private static <T> T getMockProxy(Class<T> serviceClass) {
        return (T) Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class[]{serviceClass},
                new MockServiceProxy()
        );
    }
}
