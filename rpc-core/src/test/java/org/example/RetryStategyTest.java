package org.example;

import com.czh.example.fault.retry.FixedIntervalRetryStrategy;
import com.czh.example.fault.retry.NoRetryStrategy;
import com.czh.example.fault.retry.RetryStrategy;
import org.junit.Test;

/**
 * @author czh
 * @version 1.0.0
 * 2024/3/24 12:47
 */
public class RetryStategyTest {

    RetryStrategy retryStrategy = new FixedIntervalRetryStrategy();

    @Test
    public void doRetry(){
        try {
            retryStrategy.doRetry(()->{
                System.out.println("测试重试");
                throw new RuntimeException("模拟重试失败");
            });
        }catch (Exception e){
            System.out.println("重试多次失败");
            e.printStackTrace();
        }
    }

}
