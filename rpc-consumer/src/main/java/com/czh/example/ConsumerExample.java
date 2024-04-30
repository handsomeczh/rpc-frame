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
//        加载配置文件
//        ConfigUtil.loadConfig(RpcConfig.class, "rpc");

        //服务提供者初始化
        ConsumerBootstrap.init();

        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("czh");
        System.out.println("第一次调用");
        User newUser = userService.getUser(user);
        if (newUser != null) {
            System.out.println(newUser.getName());
        } else {
            System.out.println("new == null");
        }
        System.out.println(userService.getNumber());
    }
}
