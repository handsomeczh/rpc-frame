package com.czh.example;

import com.czh.example.model.User;
import com.czh.example.proxy.ServiceProxyFactory;
import com.czh.example.service.UserService;

/**
 * @author czh
 * @version 1.0.0
 * 2024/4/28 18:10
 */
public class TestConsumer {
    public static void main(String[] args) {
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
//        System.out.println(userService); 单独使用userService得到的userService对象会为空，因为反射机制需要我们传递方法名和形参。
        User user = new User();
        user.setName("张三");
        System.out.println(userService.getUser(user));
//        user.setName("李四");
//        System.out.println(userService.getUser(user));
//        user.setName("王五");
//        System.out.println(userService.getUser(user));
        System.exit(0);
    }
}
