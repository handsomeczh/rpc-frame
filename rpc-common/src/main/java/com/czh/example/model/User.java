package com.czh.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author czh
 * @version 1.0.0
 * 2024/3/13 22:58
 */

/**
 * 用户
 */
@Data
public class User implements Serializable {

    private String name;

}
