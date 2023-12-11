package com.example.javawebtest01.core.config;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;

/**
 * @author zhangshengji
 * @since 2023/12/11 16:10
 */
@Data
public class ControllerMapInfo {
    private Map<String, Object> requestURI2ObjMap = new HashMap<>();
    private Map<String, Method> requestURI2MethodMap = new HashMap<>();
}
