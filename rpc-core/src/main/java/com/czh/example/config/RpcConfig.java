package com.czh.example.config;

/**
 * @author czh
 * @version 1.0.0
 * 2024/3/15 17:35
 */

import com.czh.example.fault.retry.RetryStrategy;
import com.czh.example.fault.retry.RetryStrategyConstant;
import com.czh.example.fault.tolerant.TolerantStrategy;
import com.czh.example.fault.tolerant.TolerantStrategyConstant;
import com.czh.example.loadbalancer.LoadBalancer;
import com.czh.example.loadbalancer.LoadBalancerConstant;
import com.czh.example.serializer.SerializerConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * RPC 框架配置
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RpcConfig {

    /**
     * 名称
     */
    private String name = "Rpc框架-默认配置";

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

    /**
     * 序列化器
     */
    private String serializer = SerializerConstants.JSON;

    /**
     * 注册中心配置
     */
    public RegistryConfig registryConfig = new RegistryConfig();

    /**
     * 负载均衡器
     */
    private String loadBalancer = LoadBalancerConstant.ROUND_ROBIN;

    /**
     * 重试策略
     */
    private String retryStrategy = RetryStrategyConstant.FIXED_INTERVAL;

    /**
     * 容错策略
     */
    private String tolerantStrategy = TolerantStrategyConstant.FAIL_FAST;
}
