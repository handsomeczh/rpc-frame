package com.example.examplespringbootconsumer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author czh
 * @version 1.0.0
 * 2024/3/25 15:44
 */
@SpringBootTest
public class ExampleServiceImplTest {

    @Resource
    private ExampleServiceImpl exampleService;

    @Test
    void testOne(){
        exampleService.test();
    }
}
