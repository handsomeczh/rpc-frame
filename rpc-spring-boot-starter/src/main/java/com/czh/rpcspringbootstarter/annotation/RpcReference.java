package com.czh.rpcspringbootstarter.annotation;

import com.czh.example.constant.RpcConstant;
import com.czh.example.fault.retry.RetryStrategyConstant;
import com.czh.example.fault.tolerant.TolerantStrategyConstant;
import com.czh.example.loadbalancer.LoadBalancerConstant;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 服务消费者注解（用于注入服务）
 *
 * @author czh
 * @version 1.0.0
 * 2024/3/25 14:17
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface RpcReference {

    /**
     * 服务接口类
     */
    Class<?> interfaceClass() default void.class;

    /**
     * 版本
     */
    String serviceVersion() default RpcConstant.DEFAULT_SERVICE_VERSION;

    /**
     * 负载均衡器
     */
    String loadBalancer() default LoadBalancerConstant.ROUND_ROBIN;

    /**
     * 重试策略
     */
    String retryStrategy() default RetryStrategyConstant.NO;

    /**
     * 容错策略
     */
    String tolerantStrategy() default TolerantStrategyConstant.FAIL_FAST;

    /**
     * 模拟调用
     */
    boolean mock() default false;
}
