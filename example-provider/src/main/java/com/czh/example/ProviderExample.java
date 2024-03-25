package com.czh.example;


import com.czh.example.bootstrap.ProviderBootStrap;
import com.czh.example.model.ServiceRegisterInfo;
import com.czh.example.service.UserService;
import com.czh.example.service.impl.UserServiceImpl;

import java.util.ArrayList;


/**
 * 服务提供者示例
 *
 * @author czh
 * @version 1.0.0
 * 2024/3/15 21:07
 */
public class ProviderExample {
    public static void main(String[] args) {
        //需要注册的服务
        ArrayList<ServiceRegisterInfo> serviceRegisterInfoList = new ArrayList<>();
        ServiceRegisterInfo serviceRegisterInfo = new ServiceRegisterInfo(UserService.class.getName(), UserServiceImpl.class);
        serviceRegisterInfoList.add(serviceRegisterInfo);

        //服务提供者初始化
        ProviderBootStrap.init(serviceRegisterInfoList);
    }
}
