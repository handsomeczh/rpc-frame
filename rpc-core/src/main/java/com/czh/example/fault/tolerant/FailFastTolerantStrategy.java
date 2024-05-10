package com.czh.example.fault.tolerant;

import com.czh.example.model.RpcResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * 快速失败 - 容错策略（立刻通知外层调用方）
 * 遇到异常后，将异常再次抛出，缴费外层处理
 *
 * @author czh
 * @version 1.0.0
 * 2024/3/25 11:11
 */
@Slf4j
public class FailFastTolerantStrategy implements TolerantStrategy {
    /**
     * 容错
     */
    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        log.info("快速失败容错机制");
        throw new RuntimeException("服务错误", e);
    }
}
