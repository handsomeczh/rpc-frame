package com.czh.example.fault.tolerant;

import com.czh.example.model.RpcResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * 静默处理异常 - 容错策略
 * 遇到异常后，记录一条日志，然后正常返回一个响应对象，就好像没有发生所
 *
 * @author czh
 * @version 1.0.0
 * 2024/3/25 11:13
 */
@Slf4j
public class FailsafeTolerantStrategy implements TolerantStrategy {
    /**
     * 容错
     */
    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        log.info("静默处理异常", e);
        return new RpcResponse();
    }
}
