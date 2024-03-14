package com.czh.example;

import com.czh.example.model.User;
import com.czh.example.proxy.ServiceProxyFactory;
import com.czh.example.proxy.UserServiceProxy;
import com.czh.example.service.UserService;

/**
 * @author czh
 * @version 1.0.0
 * 2024/3/13 23:23
 */
public class EasyConsumerExample {
    public static void main(String[] args) {
//       需要获取 UserService 的实现类对象

//        todo 静态代理，无法实例化异常未解决
//        UserServiceProxy userService = new UserServiceProxy();

//        动态代理
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);

//      通过RPC框架，快速得到一个支持远程调用服务提供者的代理对象，如同调用本地方法
//        UserService userService = null;
        User user = new User();
        user.setName("czh");
//        调用
        User newUser = userService.getUser(user);
        if (newUser != null){
            System.out.println(newUser.getName());
        }else{
            System.out.println("user == null");
        }
    }

//    public static void main(String[] args) {
//        //静态代理
//        UserServiceProxy userService = new UserServiceProxy();
//    }
}
