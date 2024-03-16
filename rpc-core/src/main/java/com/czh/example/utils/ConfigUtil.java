package com.czh.example.utils;

/**
 * @author czh
 * @version 1.0.0
 * 2024/3/15 17:40
 */

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;

import java.nio.charset.StandardCharsets;

/**
 * 配置工具类
 */
public class ConfigUtil {

    /**
     * 加载配置类对象
     *
     * @param tClass
     * @param prefix
     * @param <T>
     * @return
     */
    public static <T> T loadConfig(Class<T> tClass, String prefix) {
        return loadConfig(tClass, prefix, "");
    }

    public static <T> T loadConfig(Class<T> tClass,String prefix,String environment){
//        配置文件拼接
        StringBuilder configFileBuilder = new StringBuilder("application");
        if (StrUtil.isNotBlank(environment)){
            configFileBuilder.append("-").append(environment);
        }
        configFileBuilder.append(".yaml");
//        todo 支持yaml和yml配置文件
//        todo 提供消费者读取配置文件中文乱码（properties编码问题）
        Props props = new Props(configFileBuilder.toString(), "ISO-8859-1");
//       todo 监听配置文件变更自动更新配置对象
        props.autoLoad(true);
        return props.toBean(tClass,prefix);
    }
}
