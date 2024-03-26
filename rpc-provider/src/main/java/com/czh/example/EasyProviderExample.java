package com.czh.example;

/**
 * @author czh
 * @version 1.0.0
 * 2024/3/13 23:21
 */

import com.czh.example.registry.LocalRegistry;
import com.czh.example.server.impl.VertxHttpService;
import com.czh.example.service.UserService;
import com.czh.example.service.impl.UserServiceImpl;

/**
 * 简易服务提供者示例
 */
public class EasyProviderExample {
    public static void main(String[] args) {
//        注册服务    注册需要的是实例化对象，不要传递接口对象
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);

//      启动web服务
        VertxHttpService httpService = new VertxHttpService();
        httpService.doStart(8080);
    }
}
