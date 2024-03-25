package com.czh.example.fault.tolerant;

import com.czh.example.model.RpcResponse;

import java.util.Map;

/**
 * 降级到其他服务 - 容错策略
 * 失败自动恢复：系统某个功能出现调用失败或错误时，通过其他的方法，
 * 恢复该功能的正常。如重试、调用其他服务等
 *
 * @author czh
 * @version 1.0.0
 * 2024/3/25 11:17
 */
public class FailBackTolerantStrategy implements TolerantStrategy {
    /**
     * 容错
     *
     * @param context 上下文用于传递数据
     * @param e       异常
     * @return
     */
    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
//        todo 自行实现
        return null;

    }
}
