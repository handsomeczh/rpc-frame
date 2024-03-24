package com.czh.example.fault.retry;

import com.czh.example.model.RpcResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

/**
 * 不重试策略
 * @author czh
 * @version 1.0.0
 * 2024/3/24 12:37
 */
@Slf4j
public class NoRetryStrategy implements RetryStrategy{

    /**
     * 重试策略
     *
     * @param callable
     * @return
     * @throws Exception
     */
    @Override
    public RpcResponse doRetry(Callable<RpcResponse> callable) throws Exception {
        return callable.call();
    }
}
