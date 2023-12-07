package com.example.javawebtest01.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContext;

import com.example.javawebtest01.config.DBProperties;

/**
 * @since 2023/12/7 15:48
 */
public class DBPropertiesUtil {

    public static void init(ServletContext context) {
        // 读取配置文件
        Properties properties = new Properties();
        try (InputStream input = context.getResourceAsStream("/WEB-INF/classes/application.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            // 处理异常
        }

        // 获取配置项的值
        String databaseUrl = properties.getProperty("database.url");
        String databaseDriver = properties.getProperty("database.driver");
        String databaseUsername = properties.getProperty("database.username");
        String databasePassword = properties.getProperty("database.password");

        DBProperties dbProperties = new DBProperties();
        dbProperties.setUrl(databaseUrl);
        dbProperties.setDriver(databaseDriver);
        dbProperties.setUsername(databaseUsername);
        dbProperties.setPassword(databasePassword);

        context.setAttribute("dbProperties", dbProperties);
    }

}
