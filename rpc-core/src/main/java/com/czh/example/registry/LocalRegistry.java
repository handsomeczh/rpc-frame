package com.czh.example.registry;

/**
 * @author czh
 * @version 1.0.0
 * 2024/3/14 0:01
 */

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 本地注册中心
 * 之后就可以根据要调用的服务名称获取到对应的实现类，然后通过反射进行方法调用了。
 * 理解：将提供者注册，以便服务者调用
 */
public class LocalRegistry {

    /**
     * 注册信息存储
     * key ：服务名称
     * value ：服务的实现类
     */
    private static final Map<String,Class<?>> map =new ConcurrentHashMap<>();

    /**
     * 注册服务
     * @param serviceName
     * @param implClass
     */
    public static void register(String serviceName,Class<?> implClass){
        map.put(serviceName,implClass);
    }

    /**
     * 获取服务
     * @param serviceName
     * @return
     */
    public static Class<?> get(String serviceName){
        return map.get(serviceName);
    }

    /**
     * 删除服务
     * @param serviceName
     */
    public static void remove(String serviceName){
        map.remove(serviceName);
    }
}
