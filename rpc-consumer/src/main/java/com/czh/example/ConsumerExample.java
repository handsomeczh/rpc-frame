package com.czh.example;


import com.czh.example.bootstrap.ConsumerBootstrap;
import com.czh.example.model.User;
import com.czh.example.proxy.ServiceProxyFactory;
import com.czh.example.service.UserService;


/**
 * 简易服务消费者示例
 *
 * @author czh
 * @version 1.0.0
 * 2024/3/15 21:04
 */
public class ConsumerExample {
    public static void main(String[] args) {
        //服务提供者初始化
        ConsumerBootstrap.init();

        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("张三");
        System.out.println(userService.getUser(user));
    }
}
