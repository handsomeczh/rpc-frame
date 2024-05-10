package com.czh.example.factory;

import com.czh.example.fault.retry.NoRetryStrategy;
import com.czh.example.fault.retry.RetryStrategy;
import com.czh.example.spi.SpiLoader;

/**
 * @author czh
 * @version 1.0.0
 * 2024/3/24 13:02
 */
public class RetryStrategyFactory {

    static {
        SpiLoader.load(RetryStrategy.class);
    }

    /**
     * 默认重试策略
     */
    private static final RetryStrategy DEFAULT_RETRY_STRATEGY = new NoRetryStrategy();


    public static RetryStrategy getInstance(String key) {
        return SpiLoader.getInstance(RetryStrategy.class, key);
    }

}
