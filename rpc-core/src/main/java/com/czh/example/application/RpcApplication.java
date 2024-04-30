package com.czh.example.application;

import com.czh.example.config.RpcConfig;
import com.czh.example.utils.ConfigUtil;
import lombok.extern.slf4j.Slf4j;

import static com.czh.example.constant.RpcConstant.DEFAULT_CONFIG_PREFIX;
import static com.czh.example.constant.RpcConstant.PROPERTIES_CONFIG_SUFFIX;


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
     */
    public static void init(RpcConfig newRpcConfig) {
        rpcConfig = newRpcConfig;
        log.info("rpc init, config = {}", rpcConfig.toString());
//        //注册中心初始化
//        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
//        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
//        registry.init(registryConfig);
//        log.info("registry init, config = {}",registryConfig);
//
////        创建并注册Shutdown Hook ，jvm退出时执行操作
//        Runtime.getRuntime().addShutdownHook(new Thread(registry::destroy));
    }

    public static void init() {
        RpcConfig newRpcConfig;
        try {
            newRpcConfig = ConfigUtil.loadConfig(RpcConfig.class, DEFAULT_CONFIG_PREFIX,PROPERTIES_CONFIG_SUFFIX);
        } catch (Exception e) {
            newRpcConfig = new RpcConfig();
        }

        init(newRpcConfig);
    }

    /**
     * 获取配置
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
