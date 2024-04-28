package com.czh.example.serializer.impl;


import cn.hutool.core.util.ObjectUtil;
import com.czh.example.pojo.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;

/**
 * @author czh
 * @version 1.0.0
 * 2024/4/28 15:56
 */
public class SerializerTest {

    @Test
    public void jdkSerializerTest() throws IOException {
        System.out.println("jdk序列化器");
        Student stu = new Student("张三", 18);
        JdkSerializer serializer = new JdkSerializer();
        byte[] bytes = serializer.serialize(stu);
        System.out.println(Arrays.toString(bytes));

        Student student = serializer.deserialize(bytes, Student.class);
        System.out.println(student);
    }
    /*
    jdk序列化器
    [-84, -19, 0, 5, 115, 114, 0, 28, 99, 111, 109, 46, 99, 122, 104, 46, 101, 120, 97, 109, 112, 108, 101, 46, 112, 111, 106, 111, 46, 83, 116, 117, 100, 101, 110, 116, 82, -83, 11, 70, -88, -72, -73, 113, 2, 0, 2, 76, 0, 3, 97, 103, 101, 116, 0, 19, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 73, 110, 116, 101, 103, 101, 114, 59, 76, 0, 4, 110, 97, 109, 101, 116, 0, 18, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 59, 120, 112, 115, 114, 0, 17, 106, 97, 118, 97, 46, 108, 97, 110, 103, 46, 73, 110, 116, 101, 103, 101, 114, 18, -30, -96, -92, -9, -127, -121, 56, 2, 0, 1, 73, 0, 5, 118, 97, 108, 117, 101, 120, 114, 0, 16, 106, 97, 118, 97, 46, 108, 97, 110, 103, 46, 78, 117, 109, 98, 101, 114, -122, -84, -107, 29, 11, -108, -32, -117, 2, 0, 0, 120, 112, 0, 0, 0, 18, 116, 0, 6, -27, -68, -96, -28, -72, -119]
    Student(name=张三, age=18)
     */

    @Test
    public void jsonSerializerTest() throws IOException {
        System.out.println("json序列化器");
        Student stu = new Student("张三", 18);
        JsonSerializer jsonSerializer = new JsonSerializer();
        byte[] bytes = jsonSerializer.serialize(stu);
        System.out.println(Arrays.toString(bytes));

        Student student = jsonSerializer.deserialize(bytes,Student.class);
        System.out.println(student);
    }
    /*
    json序列化器
    [123, 34, 110, 97, 109, 101, 34, 58, 34, -27, -68, -96, -28, -72, -119, 34, 44, 34, 97, 103, 101, 34, 58, 49, 56, 125]
    Student(name=张三, age=18)
     */

    @Test
    public void hessionSerializerTest() throws IOException {
        System.out.println("hession序列化器");
        Student stu = new Student("张三", 18);
        HessianSerializer serializer = new HessianSerializer();
        byte[] bytes = serializer.serialize(stu);
        System.out.println(Arrays.toString(bytes));

        Student student = serializer.deserialize(bytes, Student.class);
        System.out.println(student);
    }
    /*
    hession序列化器
    [77, 116, 0, 28, 99, 111, 109, 46, 99, 122, 104, 46, 101, 120, 97, 109, 112, 108, 101, 46, 112, 111, 106, 111, 46, 83, 116, 117, 100, 101, 110, 116, 83, 0, 4, 110, 97, 109, 101, 83, 0, 2, -27, -68, -96, -28, -72, -119, 83, 0, 3, 97, 103, 101, 73, 0, 0, 0, 18, 122]
    Student(name=张三, age=18)
     */

    @Test
    public void kryoSerializerTest() throws IOException {
        System.out.println("kryo序列化器");
        Student stu = new Student("张三", 18);
        KryoSerializer serializer = new KryoSerializer();
        byte[] bytes = serializer.serialize(stu);
        System.out.println(Arrays.toString(bytes));

        Student student = serializer.deserialize(bytes, Student.class);
        System.out.println(student);
    }
    /*
    kryo序列化器
    [1, 36, -125, -27, -68, -96, -28, -72, -119]
    Student(name=张三, age=18)
     */
}