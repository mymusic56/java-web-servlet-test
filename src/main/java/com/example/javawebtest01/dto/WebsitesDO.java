package com.example.javawebtest01.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 
 * @since 2023/12/7 15:41
 */
@Data
public class WebsitesDO implements Serializable {

    private Integer id;
    /**
     * 站点名称
     */
    private String name;
    private String url;
    /**
     * Alexa 排名
     */
    private Integer alexa;
    private String country;
    private Date createdAt;
    private Date updatedAt;

}
