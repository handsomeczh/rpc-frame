package com.czh.example.spi;


import com.czh.example.serializer.Serializer;
import org.junit.Test;

/**
 * @author czh
 * @version 1.0.0
 * 2024/4/30 11:21
 */
public class SpiLoaderTest{

    @Test
    public void testSpiLoader(){
        SpiLoader.loadAll();
        Serializer serializer = SpiLoader.getInstance(Serializer.class, "json");
    }

}