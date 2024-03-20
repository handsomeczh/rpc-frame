package com.czh.example;


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
        RpcConfig rpc = ConfigUtil.loadConfig(RpcConfig.class, "rpc");
        System.out.println(rpc);
//      todo userService为空 使用json序列化器报错 同kryo
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        System.out.println(userService);
        User user = new User();
        user.setName("czh");
        //todo newUser为空 使用json序列化器报错
        User newUser = userService.getUser(user);
        if (newUser != null) {
            System.out.println(newUser.getName());
        } else {
            System.out.println("new == null");
        }
//        todo 使用hessian报错，class java.lang.Integer cannot be cast to class java.lang.Short
        short number = userService.getNumber();
        System.out.println(number);
    }
}
