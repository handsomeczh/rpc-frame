package com.czh.example.fault.retry;

/**
 * 重试策略键名常量
 * @author czh
 * @version 1.0.0
 * 2024/3/24 12:59
 */
public interface RetryStrategyConstant {

    /**
     * 不重试
     */
    String NO = "no";

    /**
     * 固定时间间隔
     */
    String FIXED_INTERVAL = "fixedInterval";

    // todo 可扩展其他重试策略
}
