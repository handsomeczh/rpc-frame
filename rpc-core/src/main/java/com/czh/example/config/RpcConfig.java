package com.czh.example.config;

/**
 * @author czh
 * @version 1.0.0
 * 2024/3/15 17:35
 */

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * RPC 框架配置
 */
@Data
public class RpcConfig {

    /**
     * 名称
     */
    private String name = "陈泽瀚";

    /**
     * 版本号
     */
    private String version = "1.0";

    /**
     * 服务器主机名
     */
    private String serverHost = "localhost";

    /**
     * 服务器端口号
     */
    private Integer serverPort = 8080;

    /**
     * 默认调用
     */
    private boolean mock = false;
}
