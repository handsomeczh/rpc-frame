package com.czh.example.service;

/**
 * @author czh
 * @version 1.0.0
 * 2024/3/13 22:58
 */

import com.czh.example.model.User;

/**
 * 用户服务
 */
public interface UserService {

    /**
     * 获取用户
     *
     * @param user
     * @return
     */
    User getUser(User user);
}
