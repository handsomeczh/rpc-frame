package com.czh.example.model;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

import java.sql.Struct;

/**
 * 服务元信息（注册信息）
 *
 * @author czh
 * @version 1.0.0
 * 2024/3/20 10:18
 */
@Data
public class ServiceMetaInfo {

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 服务版本号
     */
    private String serviceVersion = "1.0";

    /**
     * 服务域名
     */
    private String serviceHost;

    /**
     * 服务端口号
     */
    private Integer servicePort;

    /**
     * 服务地址
     */
    private String serviceAddress;

    /**
     * todo 服务分组（未实现）
     */
    private String serviceGroup = "default";

    /**
     * 获取服务键名
     *
     * @return
     */
    public String getServiceKey() {
        //后续可扩展服务分组
//        return String.format("%s:%s:%s",serviceName,serviceVersion,serviceGroup);
        return String.format("%s:%s", serviceName, serviceVersion);
    }

    /**
     * 获取服务注册节点键名
     *
     * @return
     */
    public String getServiceNodeKey() {
        return String.format("%s/%s:%s", getServiceKey(), serviceHost,servicePort);
    }

    /**
     * 获取完整服务地址
     * 服务消费者调用
     *
     * @return
     */
    public String getServiceAddress() {
        if(!StrUtil.contains(serviceHost,"http")){
            return String.format("http://%s:%s",serviceHost,servicePort);
        }
        return String.format("%s:%s",serviceHost,servicePort);
    }
}
