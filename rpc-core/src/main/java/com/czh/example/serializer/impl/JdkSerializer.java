package com.czh.example.serializer.impl;

import cn.hutool.core.util.ObjectUtil;
import com.czh.example.serializer.Serializer;

import java.io.*;

/**
 * JDK 序列化器
 */
//public class JdkSerializer implements Serializer {
//    /**
//     * 序列化
//     */
//    @Override
//    public <T> byte[] serialize(T object) throws IOException {
//        return ObjectUtil.serialize(object);
//    }
//
//    /**
//     * 反序列化
//     */
//    @Override
//    public <T> T deserialize(byte[] bytes, Class<T> type) throws IOException {
//        return ObjectUtil.deserialize(bytes,type);
//    }
//}


public class JdkSerializer implements Serializer {
    /**
     * 序列化
     *
     * @param object
     * @param <T>
     * @return
     * @throws IOException
     */
    @Override
    public <T> byte[] serialize(T object) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(object);
        objectOutputStream.close();
        return outputStream.toByteArray();
    }

    /**
     * 反序列化
     *
     * @param bytes
     * @param type
     * @param <T>
     * @return
     * @throws IOException
     */
    @Override
    public <T> T deserialize(byte[] bytes, Class<T> type) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        try {
            return (T) objectInputStream.readObject();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            objectInputStream.close();
        }
    }
}
