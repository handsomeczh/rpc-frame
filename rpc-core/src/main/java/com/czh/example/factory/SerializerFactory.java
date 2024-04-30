package com.czh.example.factory;


import com.czh.example.serializer.Serializer;
import com.czh.example.serializer.impl.HessianSerializer;
import com.czh.example.serializer.impl.JdkSerializer;
import com.czh.example.serializer.impl.JsonSerializer;
import com.czh.example.serializer.impl.KryoSerializer;
import com.czh.example.spi.SpiLoader;

import java.util.HashMap;
import java.util.Map;

/**
 * 序列化器工厂（用于获取序列化器对象），实现序列化器对象复用
 * @author czh
 * @version 1.0.0
 * 2024/3/18 14:48
 */
public class SerializerFactory {
    /**
     * 用静态代码在首次使用时加载序列化器接口的所有实现类
     */
    static {
        SpiLoader.load(Serializer.class);
    }

    /**
     * 默认序列化器
     */
    private static final Serializer DEFAULT_SERIALIZER = new JdkSerializer();

    /**
     * 获取实例
     */
    public static Serializer getInstance(String key){
        Serializer serializer = SpiLoader.getInstance(Serializer.class, key);
        if(serializer == null){
            System.out.println("SerializerFactory:配置文件为空，使用默认配置");
            return DEFAULT_SERIALIZER;
        }
        return serializer;
    }
}


//public class SerializerFactory {
//    /**
//     * 用静态代码在首次使用时加载序列化器接口的所有实现类
//     */
//    private static final Map<String,Serializer> KEY_SERIALIZER_MAP = new HashMap<>(){{
//        put("jdk",new JdkSerializer());
//        put("json",new JsonSerializer());
//        put("kryo", new KryoSerializer());
//        put("hessian",new HessianSerializer());
//    }};
//
//    /**
//     * 默认序列化器
//     */
//    private static final Serializer DEFAULT_SERIALIZER = new JdkSerializer();
//
//    /**
//     * 获取实例
//     */
//    public static Serializer getInstance(String key){
//        return KEY_SERIALIZER_MAP.getOrDefault(key,DEFAULT_SERIALIZER);
//    }
//}



