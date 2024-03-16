package com.czh.example;

import com.czh.example.application.RpcApplication;
import com.czh.example.config.RpcConfig;
import com.czh.example.registry.LocalRegistry;
import com.czh.example.server.impl.VertxHttpService;
import com.czh.example.service.UserService;
import com.czh.example.service.impl.UserServiceImpl;

import javax.sound.midi.Soundbank;
import java.sql.SQLOutput;

/**
 * 简易服务提供者示例
 *
 * @author czh
 * @version 1.0.0
 * 2024/3/15 21:07
 */
public class ProviderExample {
    public static void main(String[] args) {
//        rpc框架初始化
        RpcApplication.init();

//        注册服务
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);

//        启动web服务
        VertxHttpService httpService = new VertxHttpService();
        httpService.doStart(RpcApplication.getRpcConfig().getServerPort());
    }
}
