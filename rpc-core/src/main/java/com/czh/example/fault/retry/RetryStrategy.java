package com.czh.example.fault.retry;

import com.czh.example.model.RpcResponse;

import java.util.concurrent.Callable;

/**
 * 重试策略接口
 * @author czh
 * @version 1.0.0
 * 2024/3/24 12:35
 */
public interface RetryStrategy {

    /**
     * 重试策略
     */
    RpcResponse doRetry(Callable<RpcResponse> callable) throws Exception;
}
