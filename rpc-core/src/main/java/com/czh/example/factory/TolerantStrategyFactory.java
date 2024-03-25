package com.czh.example.factory;

import com.czh.example.fault.tolerant.FailFastTolerantStrategy;
import com.czh.example.fault.tolerant.TolerantStrategy;
import com.czh.example.spi.SpiLoader;

/**
 * 容错策略工厂（工厂模式，用于获取容错策略对象）
 *
 * @author czh
 * @version 1.0.0
 * 2024/3/25 11:25
 */
public class TolerantStrategyFactory {

    static {
        SpiLoader.load(TolerantStrategy.class);
    }

    /**
     * 默认容错策略
     */
    private static final TolerantStrategy default_tolerant_strategy = new FailFastTolerantStrategy();

    public static TolerantStrategy getInstance(String key){
        return SpiLoader.getInstance(TolerantStrategy.class,key);
    }

}
