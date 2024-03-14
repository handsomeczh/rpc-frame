package com.czh.example.example;

/**
 * @author czh
 * @version 1.0.0
 * 2024/3/13 23:21
 */

import com.czh.example.registry.LocalRegistry;
import com.czh.example.impl.VertxHttpService;
import com.czh.example.service.UserService;

/**
 * 简易服务提供者示例
 */
public class EasyProviderExample {
    public static void main(String[] args) {
//        注册服务
        LocalRegistry.register(UserService.class.getName(),UserService.class);

//      启动web服务
        VertxHttpService httpService = new VertxHttpService();
        httpService.doStart(8080);
    }
}
