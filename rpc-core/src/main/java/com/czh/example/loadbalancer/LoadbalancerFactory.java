package com.czh.example.loadbalancer;

import com.czh.example.spi.SpiLoader;

/**
 * 负载均衡器工厂（工厂模式，用于获取负载均衡器对象）
 *
 * @author czh
 * @version 1.0.0
 * 2024/3/24 9:57
 */
public class LoadbalancerFactory {

    static {
        SpiLoader.load(LoadBalancer.class);
    }

    /**
     * 默认负载均衡器
     */
    private static final LoadBalancer DEFAULT_LOAD_BALANCER = new RoundRobinLoadBalancer();

    /**
     * 获取实例
     */
    public static LoadBalancer getInstance(String key) {
        System.out.println("使用了"+key+"负载均衡器");
        return SpiLoader.getInstance(LoadBalancer.class, key);
    }

}
