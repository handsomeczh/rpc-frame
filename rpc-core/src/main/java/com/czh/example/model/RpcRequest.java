package com.czh.example.model;

import com.czh.example.constant.RpcConstant;
import com.czh.example.serializer.Serializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author czh
 * @version 1.0.0
 * 2024/3/14 11:41
 */

/**
 * rpc请求
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RpcRequest implements Serializable {

//    服务名称
    private String serviceName;

//    方法名称
    private String methodName;

//    服务版本
    private String serviceVersion = RpcConstant.DEFAULT_CONFIG_PREFIX;

//    参数类型列表
    private Class<?>[] parameterTypes;

//    参数列表
    private  Object[] args;
}
