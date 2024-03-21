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
     * 注册中心类别
     */
    private String registry = "zookeeper";

    /**
     * 注册中心地址
     * zookeeper:2181
     * etcd:2379
     */
    private String address = "http://localhost:2181";

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 超时时间（单位毫秒）
     */
    private Long timeout = 10000L;


}
