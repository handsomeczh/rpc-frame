package com.czh.example;


import com.czh.example.bootstrap.ConsumerBootstrap;
import com.czh.example.config.RpcConfig;
import com.czh.example.model.User;
import com.czh.example.proxy.ServiceProxyFactory;
import com.czh.example.service.UserService;
import com.czh.example.utils.ConfigUtil;

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

//      todo userService为空
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
//        System.out.println(userService);   为什么无法输出，会报错
        User user = new User();
        user.setName("czh");
        //todo 使用json序列化器报错
        System.out.println("第一次调用");
        User newUser = userService.getUser(user);
//        System.out.println("第二次调用");
//        User newUser1 = userService.getUser(user);
        if (newUser != null) {
            System.out.println(newUser.getName());
        } else {
            System.out.println("new == null");
        }
//        todo 使用hessian报错，class java.lang.Integer cannot be cast to class java.lang.Short
        System.out.println(userService.getNumber());
    }
}
