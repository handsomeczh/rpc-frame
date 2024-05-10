package com.czh.example.fault.retry;

import com.czh.example.model.RpcResponse;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * @author czh
 * @version 1.0.0
 * 2024/5/10 14:29
 */
public class RetryStrategyTest{

    RetryStrategy retryStrategy = new NoRetryStrategy();

    @Test
    public void doRetry() {
        try {
            RpcResponse rpcResponse = retryStrategy.doRetry(() -> {
                System.out.println("测试重试");
                throw new RuntimeException("模拟重试失败");
            });
            System.out.println(rpcResponse);
        } catch (Exception e) {
            System.out.println("重试多次失败");
            e.printStackTrace();
        }
    }
}