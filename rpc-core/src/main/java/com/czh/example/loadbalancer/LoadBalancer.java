package com.czh.example.loadbalancer;

import com.czh.example.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;

/**
 * 负载均衡器（服务端使用）
 * 负载均衡器通用接口，提供一个选择服务方法（轮询、随机、一致性Hash）
 *
 * @author czh
 * @version 1.0.0
 * 2024/3/24 9:25
 */
public interface LoadBalancer {
    /**
     * 选择服务调用
     *
     * @param requestParams 请求参数
     * @param serviceMetaInfoList 可用服务列表
     * @return
     */
    ServiceMetaInfo select(Map<String, Object> requestParams, List<ServiceMetaInfo> serviceMetaInfoList);
}
