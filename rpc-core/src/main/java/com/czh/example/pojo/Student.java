package com.czh.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author czh
 * @version 1.0.0
 * 2024/4/28 16:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student implements Serializable {
    String name;
    Integer age;

}
