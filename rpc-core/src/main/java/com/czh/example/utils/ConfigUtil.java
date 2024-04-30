package com.czh.example.utils;

/**
 * @author czh
 * @version 1.0.0
 * 2024/3/15 17:40
 */


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.Setting;
import cn.hutool.setting.dialect.Props;
import com.czh.example.config.RpcConfig;
import com.czh.example.constant.RpcConstant;
import com.sun.tools.javac.Main;
import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.*;
import java.util.Map;

import static com.czh.example.constant.RpcConstant.*;


/**
 * 配置工具类
 */
@Slf4j
public class ConfigUtil {

    private static final String DEFAULT_FILE_PATH = "\\src\\main\\resources\\";

    /**
     * 加载配置类对象
     */
    public static <T> T loadConfig(Class<T> tClass, String prefix, String suffix) {
        return loadConfig(tClass, prefix, suffix, "");
    }

    public static <T> T loadConfig(Class<T> tClass, String prefix, String suffix, String environment) {
//        配置文件拼接
        StringBuilder configFileBuilder = new StringBuilder("application");
        if (StrUtil.isNotBlank(environment)) {
            configFileBuilder.append("-").append(environment);
        }
        configFileBuilder.append(suffix);
        String configFile = configFileBuilder.toString();

        // 加载配置
        T config = loadConfigFromFile(tClass, prefix, configFile, suffix);

        // 监听配置文件变化
//        try {
//            watchConfigFile(config, tClass, prefix, configFile, suffix);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        return config;
    }

    private static <T> T loadConfigFromFile(Class<T> tClass, String prefix, String configFile, String suffix) {
        if (suffix.equals(RpcConstant.PROPERTIES_CONFIG_SUFFIX)) {
            Props props = new Props(configFile);
            return props.toBean(tClass, prefix);
        } else if(suffix.equals(SETTING_CONFIG_SUFFIX)){
            Setting setting = new Setting(configFile);
//            setting.autoLoad(true);
            return setting.toBean(prefix, tClass);
        }else {
            InputStream inputStream = null;
            try {
                inputStream = new FileInputStream("src/main/resources/"+configFile);
                Yaml yaml = new Yaml();
                Map<String, Object> data = yaml.load(inputStream);
                return BeanUtil.toBean(data, tClass);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }


    private static <T> void watchConfigFile(T config,
                                            Class<T> tClass, String prefix,
                                            String configFile, String suffix) throws IOException {
        // 监听的配置文件所在目录
        // todo 无法解决服务提供者和服务消费者路径读取问题
        Path directory = Paths.get(DEFAULT_FILE_PATH);
//        Path directory = Paths.get(moduleName+DEFAULT_FILE_PATH);
        WatchService watchService = FileSystems.getDefault().newWatchService();
        directory.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

        new Thread(() -> {
            try {
                while (true) {
                    WatchKey key = watchService.take();

                    for (WatchEvent<?> event : key.pollEvents()) {
                        WatchEvent.Kind<?> kind = event.kind();

                        if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
                            Path modifiedFile = (Path) event.context();
                            if (modifiedFile.endsWith(configFile)) {
                                System.out.println("Config file modified: " + modifiedFile);
                                // 重新加载配置 todo 可以监听到变化但不能读取新的内容（yaml除外）
                                T reloadedConfig = loadConfigFromFile(tClass, prefix, configFile, suffix);
                                System.out.println("reloadedConfig:"+reloadedConfig);
                                // 更新原始配置
                                BeanUtil.copyProperties(reloadedConfig, config);
                            }
                        }
                    }

                    boolean valid = key.reset();
                    if (!valid) {
                        break;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}


// 未使用动态加载配置文件版本
//public class ConfigUtil {
//
//    /**
//     * 加载配置类对象
//     *
//     * @param tClass 需要返回的配置对象
//     * @param prefix 配置文件前缀
//     * @param <T>
//     * @return
//     */
//    public static <T> T loadConfig(Class<T> tClass, String prefix, String suffix) {
//        return loadConfig(tClass, prefix, suffix,"");
//    }
//
//    /**
//     *
//     * @param tClass
//     * @param prefix
//     * @param environment 环境：test或prod
//     * @return
//     * @param <T>
//     */
//    public static <T> T loadConfig(Class<T> tClass,String prefix,String suffix,String environment){
////        配置文件拼接
//        StringBuilder configFileBuilder = new StringBuilder("application");
//        if (StrUtil.isNotBlank(environment)){
//            configFileBuilder.append("-").append(environment);
//        }
//        configFileBuilder.append(suffix);
//        if (suffix.equals(RpcConstant.PROPERTIES_CONFIG_SUFFIX)) {
//            Props props = new Props(configFileBuilder.toString());
//            return props.toBean(tClass,prefix);
//        }else{
//            Setting setting = new Setting(configFileBuilder.toString());
//            setting.autoLoad(true);
//            return setting.toBean(prefix,tClass);
//        }
//    }
//}
