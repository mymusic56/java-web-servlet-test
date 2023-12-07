package com.example.javawebtest01.config;

import java.io.Serializable;

import lombok.Data;

/**
 * @since 2023/12/7 16:28
 */
@Data
public class DBProperties implements Serializable {

    private String url;
    private String driver;
    private String username;
    private String password;
}
