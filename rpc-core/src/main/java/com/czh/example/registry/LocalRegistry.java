package com.czh.example.registry;



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
    private static final Map<String,Class<?>> MAP =new ConcurrentHashMap<>();

    /**
     * 注册服务
     */
    public static void register(String serviceName,Class<?> implClass){
        MAP.put(serviceName,implClass);
        System.out.println("LocalRegistry:服务提供者成功注册服务----"+"服务接口名："+serviceName
                +"---服务实现类："+implClass);
    }

    /**
     * 获取服务
     */
    public static Class<?> get(String serviceName){
        return MAP.get(serviceName);
    }

    /**
     * 删除服务
     */
    public static void remove(String serviceName){
        MAP.remove(serviceName);
    }
}
