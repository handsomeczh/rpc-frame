package com.czh.example.serializer;


import com.czh.example.serializer.impl.JdkSerializer;
import com.czh.example.spi.SpiLoader;

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
     * @param key
     * @return
     */
    public static Serializer getInstance(String key){
        return SpiLoader.getInstance(Serializer.class,key);
    }
}
