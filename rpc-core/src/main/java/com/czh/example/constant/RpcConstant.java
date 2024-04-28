package com.czh.example.constant;

/**
 * RPC 相关常量
 *
 * @author czh
 * @version 1.0.0
 * 2024/3/15 17:47
 */
public class RpcConstant {

    /**
     * 默认配置文件加载前缀
     * 可以读取到类似的配置：rpc.name=czh rpc.version=1.0 rpc.XXX=XXX
     */
    public static final String DEFAULT_CONFIG_PREFIX = "rpc";

    /**
     * 配置文件后缀
     */
    public static final String PROPERTIES_CONFIG_SUFFIX = ".properties";

    public static final String SETTING_CONFIG_SUFFIX = ".setting";

    public static final String YAML_CONFIG_SUFFIX = ".yaml";

    /**
     * 默认服务版本号
     */
    public static final String DEFAULT_SERVICE_VERSION = "1.0";

    /**
     * 配置文件环境参数
     */
    public static final String DEFAULT_TEST_ENVIRONMENT = "test";

    public static final String DEFAULT_PROD_ENVIRONMENT = "prod";
}
