package com.czh.example.service.impl;

import com.czh.example.model.User;
import com.czh.example.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author czh
 * @version 1.0.0
 * 2024/3/13 23:20
 */

/**
 * 用户服务实现类
 */
@Data
public class UserServiceImpl implements UserService {
    @Override
    public User getUser(User user) {
        System.out.println("UserServiceImpl：用户名："+user.getName());
        return user;
    }
}
