package com.czh.example.fault.tolerant;

import com.czh.example.model.RpcResponse;

import java.util.Map;

/**
 * 快速失败 - 容错策略（立刻通知外层调用方）
 * 遇到异常后，将异常再次抛出，缴费外层处理
 *
 * @author czh
 * @version 1.0.0
 * 2024/3/25 11:11
 */
public class FailFastTolerantStrategy implements TolerantStrategy {
    /**
     * 容错
     *
     * @param context 上下文用于传递数据
     * @param e       异常
     * @return
     */
    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        throw new RuntimeException("服务错误", e);
    }
}
