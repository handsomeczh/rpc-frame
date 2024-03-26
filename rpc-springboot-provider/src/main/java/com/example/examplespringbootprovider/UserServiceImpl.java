package com.example.examplespringbootprovider;

import com.czh.example.model.User;
import com.czh.example.service.UserService;
import com.czh.rpcspringbootstarter.annotation.RpcService;
import org.springframework.stereotype.Service;

/**
 * @author czh
 * @version 1.0.0
 * 2024/3/25 15:36
 */
@Service
@RpcService
public class UserServiceImpl implements UserService {
    @Override
    public User getUser(User user) {
        System.out.println("spring服务提供者 用户名：" + user.getName());
        return user;
    }
}
