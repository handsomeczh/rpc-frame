package org.example;

import com.czh.example.application.RpcApplication;
import com.czh.example.config.RpcConfig;

import com.czh.example.utils.ConfigUtil;
import org.checkerframework.checker.units.qual.Current;
import org.junit.Test;

import static com.czh.example.constant.RpcConstant.*;

/**
 * @author czh
 * @version 1.0.0
 * 2024/4/27 14:44
 */
public class ConfigTest {


    @Test
    /**
     * 测试properties配置文件加载
     */
    public void loadPropertiesToConfig() {
        RpcConfig rpcConfig = ConfigUtil.loadConfig(RpcConfig.class, DEFAULT_CONFIG_PREFIX, DEFAULT_TEST_ENVIRONMENT);
        System.out.println(rpcConfig);
    }

    /**
     * 测试全局配置对象
     */
    @Test
    public void testRpcApplication(){
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        System.out.println(rpcConfig);

        // 传入自定义配置文件
        RpcApplication.init(new RpcConfig("自定义配置文件","1.0","localhost",8888,false,"json",null,null,null,null));
    }

    /**
     * 测试加载setting配置文件
     */
    @Test
    public void testSettingConfig() throws InterruptedException {
//        RpcConfig rpcConfig = ConfigUtil.loadConfig(RpcConfig.class, DEFAULT_CONFIG_PREFIX, PROPERTIES_CONFIG_SUFFIX,DEFAULT_TEST_ENVIRONMENT);
        RpcConfig rpcConfig = ConfigUtil.loadConfig(RpcConfig.class, DEFAULT_CONFIG_PREFIX, SETTING_CONFIG_SUFFIX);
        System.out.println(rpcConfig);
        Thread.currentThread().sleep(100000);
        System.out.println(rpcConfig);
    }
}
