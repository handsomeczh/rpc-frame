package com.czh.example.application;

import com.czh.example.config.RpcConfig;
import com.czh.example.constant.RpcConstant;
import com.czh.example.factory.RegistryFactory;
import com.czh.example.registry.Registry;
import com.czh.example.config.RegistryConfig;
import com.czh.example.utils.ConfigUtil;
import lombok.extern.slf4j.Slf4j;



/**
 * RPC 框架应用
 * 相当于holder，存放了项目全局用到的变量。双检锁单例模式实现
 *
 * @author czh
 * @version 1.0.0
 * 2024/3/15 19:00
 */
@Slf4j
public class RpcApplication {

    /**
     * volatile
     * 1. 线程的可见性：当一个线程修改一个共享变量时，另外一个线程能读到这个修改的值。
     * <p>
     * 2. 顺序一致性：禁止指令重排序。
     */
    private static volatile RpcConfig rpcConfig;

    /**
     * 框架初始化，支持自定义配置
     *
     * @param newRpcConfig
     */
    public static void init(RpcConfig newRpcConfig) {
        rpcConfig = newRpcConfig;
        log.info("rpc init, config = {}", newRpcConfig.toString());
        //注册中心初始化
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
        registry.init(registryConfig);
        log.info("registry init, config = {}",registryConfig);

//        创建并注册Shutdown Hook ，jvm退出时执行操作
        Runtime.getRuntime().addShutdownHook(new Thread(registry::destroy));
    }

    public static void init() {
        RpcConfig newRpcConfig;
        try {
            newRpcConfig = ConfigUtil.loadConfig(RpcConfig.class, RpcConstant.DEFAULT_CONFIG_PREFIX);
        } catch (Exception e) {
            newRpcConfig = new RpcConfig();
        }

        init(newRpcConfig);
    }

    /**
     * 获取配置
     *
     * @return
     */
    public static RpcConfig getRpcConfig() {
        if (rpcConfig == null) {
            synchronized (RpcApplication.class) {
                if (rpcConfig == null) {
                    init();
                }
            }
        }
        return rpcConfig;
    }
}
