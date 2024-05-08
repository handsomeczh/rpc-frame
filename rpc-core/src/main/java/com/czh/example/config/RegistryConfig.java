package com.czh.example.config;

import lombok.Data;

/**
 * RPC 框架注册中心配置
 *
 * @author czh
 * @version 1.0.0
 * 2024/3/20 10:31
 */
@Data
public class RegistryConfig {

    /**
     * 注册中心类别 etcd/redis/zookeeper
     */
//    private String registry = "redis";
    private String registry = "etcd";

    /**
     * 注册中IP地址
     */
    private String host = "127.0.0.1";
//    private String host = "192.168.88.128";

    /**
     * 注册中心端口
     */
    private Integer port = 6379;

    /**
     * 注册中心地址
     * Etcd :2379
     * Redis : 192.168.88.128:2379
     */
//    private String address = "http://192.168.88.128:6379";
    private String address = "http://localhost:2379";

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password = "123456";

    /**
     * 超时时间（单位毫秒）
     */
    private Long timeout = 10000L;

}
