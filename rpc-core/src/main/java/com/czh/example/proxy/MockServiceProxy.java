package com.czh.example.proxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Mock 服务代理（JDK 动态代理）
 *
 * @author czh
 * @version 1.0.0
 * 2024/3/16 21:55
 */
@Slf4j
public class MockServiceProxy implements InvocationHandler {

    /**
     * 调用代理
     *
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//        根据方法的返回值类型，生成特定的默认值对象
        Class<?> methodReturnType = method.getReturnType();
        log.info("mock invock {}", method.getName());

        return getDefaultObject(methodReturnType);
    }

    /**
     * 生成指定类型的默认值对象
     *
     * @param type
     * @return
     */
    private Object getDefaultObject(Class<?> type) {
//        判断type是否为基本类型
        if (type.isPrimitive()) {
            if (type == boolean.class) {
                return false;
            }else if(type == int.class){
                return 0;
            }else if(type == short.class){
                return (short)0;
            }else if(type == long.class){
                return 0L;
            }
        }
        return null;
    }
}











