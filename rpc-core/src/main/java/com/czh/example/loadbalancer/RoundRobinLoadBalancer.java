package com.czh.example.loadbalancer;

import com.czh.example.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 轮询负载均衡器
 * @author czh
 * @version 1.0.0
 * 2024/3/24 9:29
 */
public class RoundRobinLoadBalancer implements LoadBalancer{

    // todo 服务器维护该值，解决不同服务消费者线程调用时都初始化为 0
    private final AtomicInteger currentIndex = new AtomicInteger(0);

    /**
     * 选择服务调用
     */
    @Override
    public ServiceMetaInfo select(Map<String, Object> requestParams, List<ServiceMetaInfo> serviceMetaInfoList) {
        if(serviceMetaInfoList.isEmpty()){
            return null;
        }
        int size = serviceMetaInfoList.size();
        if(size == 1){
            return serviceMetaInfoList.get(0);
        }

        int index = currentIndex.getAndIncrement() % size;
        return serviceMetaInfoList.get(index);
    }
}
