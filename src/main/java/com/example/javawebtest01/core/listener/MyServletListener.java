package com.example.javawebtest01.core.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author zhangshengji
 * @since 2023/12/12 9:12
 */
public class MyServletListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContextListener.super.contextInitialized(sce);

        System.out.println("MyServletListener executed......");
    }

}
