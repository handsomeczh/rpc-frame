package com.czh.example.fault.retry;

import com.czh.example.model.RpcResponse;
import com.github.rholder.retry.*;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * 固定时间间隔重试策略
 * @author czh
 * @version 1.0.0
 * 2024/3/24 12:39
 */
@Slf4j
public class FixedIntervalRetryStrategy implements RetryStrategy{
    /**
     * 重试策略
     *
     * @param callable
     * @return
     * @throws Exception
     */
    @Override
    public RpcResponse doRetry(Callable<RpcResponse> callable) throws Exception {
        Retryer<RpcResponse> retryer = RetryerBuilder.<RpcResponse>newBuilder()
//                重试条件
                .retryIfExceptionOfType(Exception.class)
//                重试等待策略
                .withWaitStrategy(WaitStrategies.fixedWait(3L, TimeUnit.SECONDS))
//                重试停止策略,重试三次后失败
                .withStopStrategy(StopStrategies.stopAfterAttempt(3))
//                重试工作
                .withRetryListener(new RetryListener() {
                    @Override
                    public <V> void onRetry(Attempt<V> attempt) {
                        log.info("重试次数 {}", attempt.getAttemptNumber());
                    }
                })
                .build();
        return retryer.call(callable);
    }
}
