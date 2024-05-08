package com.czh.example.registry;

import com.czh.example.config.RegistryConfig;
import com.czh.example.model.ServiceMetaInfo;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * @author czh
 * @version 1.0.0
 * 2024/5/8 13:56
 */
public class EtcdRegistryTest{

    @Test
    public void test() throws Exception {
        EtcdRegistry redisRegistry = new EtcdRegistry();
        RegistryConfig config = new RegistryConfig();
        redisRegistry.init(config);
        ServiceMetaInfo info = new ServiceMetaInfo();
        info.setServiceName("com.czh.example.service.UserService");
        info.setServiceHost("localhost");
        info.setServicePort(8080);
        redisRegistry.register(info);
        redisRegistry.serviceDiscovery("com.czh.example.service.UserService:1.0");
//        Thread.sleep(11000);
        Thread.sleep(1100000);
        redisRegistry.serviceDiscovery("com.czh.example.service.UserService:1.0");
    }

}