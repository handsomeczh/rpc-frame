package com.example.examplespringbootconsumer;

import com.czh.example.model.User;
import com.czh.example.service.UserService;
import com.czh.rpcspringbootstarter.annotation.RpcReference;
import org.springframework.stereotype.Service;

/**
 * @author czh
 * @version 1.0.0
 * 2024/3/25 15:36
 */
@Service
public class ExampleServiceImpl {

    @RpcReference
    private UserService userService;

    public void test(){
        User user = new User();
        user.setName("spring服务消费者");
        User newUser = userService.getUser(user);
        System.out.println(newUser.getName());
    }

}
