package com.czh.example.cache;

import com.czh.example.model.ServiceMetaInfo;

import java.util.List;

/**
 * 注册中心本地缓存
 * @author czh
 * @version 1.0.0
 * 2024/3/21 9:03
 */
public class RegistryServiceCache {
    /**
     * 服务缓存
     */
    public List<ServiceMetaInfo> serviceCache;

    /**
     * 写缓存
     * @param newServiceCache
     */
    public void writeCache(List<ServiceMetaInfo> newServiceCache){
        this.serviceCache = newServiceCache;
    }

    /**
     * 读缓存
     * @return
     */
    public List<ServiceMetaInfo> readCache(){
        return this.serviceCache;
    }

    /**
     * 清空缓存
     */
    public void clearCache(){
        this.serviceCache = null;
    }
}
