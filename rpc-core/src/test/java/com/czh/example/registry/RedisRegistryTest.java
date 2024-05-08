package com.czh.example.registry;


import com.czh.example.config.RegistryConfig;
import com.czh.example.model.ServiceMetaInfo;
import com.czh.example.service.UserService;
import org.junit.Test;

/**
 * @author czh
 * @version 1.0.0
 * 2024/5/8 9:46
 */
public class RedisRegistryTest {

    @Test
    public void testInit() throws Exception {
        RedisRegistry redisRegistry = new RedisRegistry();
        RegistryConfig config = new RegistryConfig();
        redisRegistry.init(config);
        ServiceMetaInfo info = new ServiceMetaInfo();
        info.setServiceName("com.czh.example.service.UserService");
        info.setServiceHost("localhost");
        info.setServicePort(8080);
        redisRegistry.register(info);
        redisRegistry.serviceDiscovery("com.czh.example.service.UserService:1.0");
        redisRegistry.serviceDiscovery("com.czh.example.service.UserService:1.0");
//        redisRegistry.unRegister(info);
        Thread.sleep(100000L);
    }



}