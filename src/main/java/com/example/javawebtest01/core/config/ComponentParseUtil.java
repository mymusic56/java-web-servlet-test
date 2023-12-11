package com.example.javawebtest01.core.config;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.javawebtest01.core.annotation.RequestMapping;
import com.example.javawebtest01.core.annotation.RestController;

import lombok.Data;

/**
 * @author zhangshengji
 * @since 2023/12/11 16:09
 */
@Data
public class ComponentParseUtil {

    private List<String> packageNames;

    private List<String> controllerNames;

    private Map<String, Object> controllerObjs = new HashMap<>();

    private Map<String, Class<?>> controllerClasses = new HashMap<>();

    private List<String> uriMappingNames = new ArrayList<>();
    private Map<String, Object> uriMappingObjs = new HashMap<>();
    private Map<String, Method> uriMappingMethods = new HashMap<>();

    // 获取需要扫描的包

    // 将包名 映射成 class - 包名通过反射创建对象

    // 根据指定的注解，初始化注解对应的 URI和对象映射关系

    public void parse(URL xmlPath) {

        this.packageNames = XmlScanComponentHelper.getNodeValues(xmlPath);

        refresh();
    }

    private void refresh() {
        initController();
        initMapping();
    }

    public void initController() {
        this.controllerNames = scanPackages(this.packageNames);

        for (String controllerName : this.controllerNames) {

            Class<?> aClass = null;
            try {
                aClass = Class.forName(controllerName);
                controllerClasses.put(controllerName, aClass);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            Object o = null;
            try {
                o = aClass.newInstance();
                this.controllerObjs.put(controllerName, o);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void initMapping() {

        for (String controllerName : this.controllerNames) {
            Class<?> aClass = this.controllerClasses.get(controllerName);
            Object o = this.controllerObjs.get(controllerName);
            Method[] declaredMethods = aClass.getDeclaredMethods();
            RestController annotationController = aClass.getAnnotation(RestController.class);
            if (annotationController == null) {
                continue;
            }
            RequestMapping annotationRM = aClass.getAnnotation(RequestMapping.class);

            String pathPrefix = "";
            if (annotationRM != null && annotationRM.value() != null && !annotationRM.value().equals("")) {
                pathPrefix = this.getPath(annotationRM.value());
            }
            String methodPath = "";
            for (Method declaredMethod : declaredMethods) {
                RequestMapping annotationTemp = declaredMethod.getAnnotation(RequestMapping.class);

                if (annotationTemp != null && annotationTemp.value() != null && !annotationTemp.value().equals("")) {
                    methodPath = this.getPath(annotationTemp.value());

                    uriMappingNames.add(pathPrefix + methodPath);
                    uriMappingObjs.put(pathPrefix + methodPath, o);
                    uriMappingMethods.put(pathPrefix + methodPath, declaredMethod);
                }
            }
        }
    }

    /**
     * 返回格式 "/xx/xxx" 或者 "/" 或者 ""
     * 
     * @param path
     * @return
     */
    private String getPath(String path) {

        if (path.length() > 1 && path.charAt(path.length() - 1) == '/') {
            path = path.substring(1);
        }

        char c = path.charAt(0);
        if (c == '/') {
            return path;
        } else {
            return "/" + path;
        }
    }

    public List<String> scanPackages(List<String> packages) {
        List<String> tempControllerNames = new ArrayList<>();
        for (String aPackage : packages) {
            tempControllerNames.addAll(scanPackage(aPackage));
        }

        return tempControllerNames;
    }

    public List<String> scanPackage(String packageName) {
        List<String> tempControllerNames = new ArrayList<>();

        String resourceName = "/" + packageName.replaceAll("\\.", "/");
        URL resource = this.getClass().getClassLoader().getResource(resourceName);
        if (resource == null) {
            throw new RuntimeException("packageName resource get failed:" + resourceName);
        }
        File dir = new File(resource.getFile());
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                tempControllerNames.addAll(scanPackage(packageName + "." + file.getName()));
            } else {
                tempControllerNames.add(packageName + "." + file.getName().replace(".class", ""));
            }

        }
        return tempControllerNames;
    }
}
