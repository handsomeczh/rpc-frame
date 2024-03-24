package com.czh.example.loadbalancer;

/**
 * 负载均衡键名常量
 *
 * @author czh
 * @version 1.0.0
 * 2024/3/24 9:55
 */
public interface LoadBalancerConstant {

    String ROUND_ROBIN = "roundRobin";

    String RANDOM = "random";

    String CONSISTENT_HASH = "consistentHash";

//    todo 实现更多不同算法的负载均衡器：选择最少活跃数负载均衡器

}
