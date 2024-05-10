package com.czh.rpcspringbootstarter.annotation;

/**
 * 用于全局标识项目需要引入RPC框架，执行初始化方法
 * 服务消费者和服务提供者初始化的模块不同，我们需要再EnableRpc注解中，
 * 指定是否需要启动服务器属性
 *
 * @author czh
 * @version 1.0.0
 * 2024/3/25 14:17
 */

import com.czh.rpcspringbootstarter.bootstrap.RpcConsumerBootstrap;
import com.czh.rpcspringbootstarter.bootstrap.RpcInitBootStrap;
import com.czh.rpcspringbootstarter.bootstrap.RpcProviderBootStrap;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 启用Rpc注解
 * Retention: 保留
 * @author Lenovo
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({RpcInitBootStrap.class, RpcProviderBootStrap.class, RpcConsumerBootstrap.class})
public @interface EnableRpc {

    /**
     * 需要启动server
     */
    boolean needServer() default true;
}
