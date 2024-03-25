package com.czh.example.fault.tolerant;

import com.czh.example.model.RpcResponse;

import java.util.Map;

/**
 * 故障转移：一次调用失败后，切换一个其他节点再次进行调用。
 * @author czh
 * @version 1.0.0
 * 2024/3/25 11:21
 */
public class FailOverTolerantStrategy implements TolerantStrategy{
    /**
     * 容错
     *
     * @param context 上下文用于传递数据
     * @param e       异常
     * @return
     */
    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
//        todo 后期扩展
        return null;
    }
}
