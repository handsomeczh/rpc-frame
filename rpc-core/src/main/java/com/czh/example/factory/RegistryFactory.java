package com.czh.example.factory;

import com.czh.example.registry.EtcdRegistry;
import com.czh.example.registry.Registry;
import com.czh.example.spi.SpiLoader;

/**
 * 注册中心工厂（用于获取注册中心对象）
 *
 * @author czh
 * @version 1.0.0
 * 2024/3/20 11:20
 */
public class RegistryFactory {

    static {
        SpiLoader.load(Registry.class);
    }

    /**
     * 默认序列化器
     */
    private static final Registry DEFAULT_REGISTRY = new EtcdRegistry();

    /**
     * 获取实例
     */
    public static Registry getInstance(String key) {
        return SpiLoader.getInstance(Registry.class, key);
    }


}
