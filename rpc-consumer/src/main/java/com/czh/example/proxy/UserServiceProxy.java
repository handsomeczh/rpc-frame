package com.czh.example.proxy;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.czh.example.model.RpcRequest;
import com.czh.example.model.RpcResponse;
import com.czh.example.model.User;
import com.czh.example.serializer.impl.JdkSerializer;
import com.czh.example.service.UserService;

import java.io.IOException;

/**
 * @author czh
 * @version 1.0.0
 * 2024/3/14 12:29
 */

/**
 * 静态代理
 */
public class UserServiceProxy implements UserService {
    @Override
    public User getUser(User user) {
//        指定序列化器
        JdkSerializer serializer = new JdkSerializer();

//        发请求
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(UserService.class.getName())
                .methodName("getUser")
                .parameterTypes(new Class[]{User.class})
                .args(new Object[]{user})
                .build();

        try {
//            序列化（Java对象=> 字节数组）
            byte[] bodyBytes = serializer.serialize(rpcRequest);
            byte[] result;
            try (HttpResponse httpResponse = HttpRequest.post("http://localhost:8080")
                    .body(bodyBytes)
                    .execute()) {
                result = httpResponse.bodyBytes();
            }
            RpcResponse rpcResponse = serializer.deserialize(result, RpcResponse.class);
            return (User) rpcResponse.getData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}













