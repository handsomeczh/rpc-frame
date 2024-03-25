package com.czh.example.bootstrap;

import com.czh.example.application.RpcApplication;

/**
 * 服务消费者启动类（初始化）
 *
 * @author czh
 * @version 1.0.0
 * 2024/3/25 13:56
 */
public class ConsumerBootstrap {

    /**
     * 初始化
     */
    public static void init(){
        //RPC 框架初始化（配置和注册中心）
        RpcApplication.init();
    }
}
