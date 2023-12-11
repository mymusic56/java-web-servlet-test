package com.example.javawebtest01.controller.api;

import java.util.Date;

import com.example.javawebtest01.core.annotation.RequestMapping;
import com.example.javawebtest01.core.annotation.RestController;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;

/**
 * @author zhangshengji
 * @since 2023/12/11 15:04
 */
@RestController
@RequestMapping("/api/test2")
public class Test2Controller {

    @RequestMapping("/printDate")
    public String test1() {
        return "访问接口/api/test2/printDate: " + DateUtil.format(new Date(), DatePattern.NORM_DATETIME_PATTERN);
    }

}
