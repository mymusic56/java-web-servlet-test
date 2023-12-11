package com.example.javawebtest01.controller;

import java.util.Date;

import com.example.javawebtest01.core.annotation.RequestMapping;
import com.example.javawebtest01.core.annotation.RestController;
import com.example.javawebtest01.dto.WebsitesDO;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;

/**
 * @author zhangshengji
 * @since 2023/12/11 15:04
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/test")
    public String test() {
        return "访问接口/test/test: " + DateUtil.format(new Date(), DatePattern.NORM_DATETIME_PATTERN);
    }

    @RequestMapping("/test1")
    public String test1(WebsitesDO websitesDO) {
        return "访问接口/test/test1: " + JSONUtil.toJsonStr(websitesDO);
    }

    @RequestMapping("/printDate")
    public void printDate() {
        System.out.println("printDate = " + DateUtil.format(new Date(), DatePattern.NORM_DATETIME_PATTERN));;
    }
}
