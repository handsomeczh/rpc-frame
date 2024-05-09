package com.czh.example.loadbalancer;

import com.czh.example.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 随机负载均衡器
 *
 * @author czh
 * @version 1.0.0
 * 2024/3/24 9:33
 */
public class RandomLoadBalancer implements LoadBalancer {

    private final Random random = new Random();

    /**
     * 选择服务调用
     */
    @Override
    public ServiceMetaInfo select(Map<String, Object> requestParams, List<ServiceMetaInfo> serviceMetaInfoList) {
        if (serviceMetaInfoList == null || serviceMetaInfoList.size() == 0) {
            return null;
        }
        int size = serviceMetaInfoList.size();
        if (size == 1) {
            return serviceMetaInfoList.get(0);
        }
        return serviceMetaInfoList.get(random.nextInt(size));
    }
}
