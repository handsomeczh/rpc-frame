package com.czh.example.fault.tolerant;

import com.czh.example.model.RpcResponse;

import java.util.Map;

/**
 * 容错策略通用接口
 *
 * @author czh
 * @version 1.0.0
 * 2024/3/25 11:06
 */
public interface TolerantStrategy {
    /**
     * 容错
     *
     * @param context 上下文用于传递数据
     * @param e 异常
     * @return
     */
    RpcResponse doTolerant(Map<String,Object> context,Exception e);
}
