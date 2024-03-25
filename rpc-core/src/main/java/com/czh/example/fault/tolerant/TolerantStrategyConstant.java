package com.czh.example.fault.tolerant;

/**
 * @author czh
 * @version 1.0.0
 * 2024/3/25 11:22
 */
public interface TolerantStrategyConstant {

    /**
     * 故障恢复
     */
    String FAIL_BACK = "failBack";

    /**
     * 快速失败
     */
    String FAIL_FAST = "failFast";

    /**
     * 故障转移
     */
    String FAIL_OVER = "failOver";

    /**
     * 静默处理
     */
    String FAIL_SAFE = "failSafe";
}
