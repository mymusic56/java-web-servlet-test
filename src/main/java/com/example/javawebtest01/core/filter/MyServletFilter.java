package com.example.javawebtest01.core.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhangshengji
 * @since 2023/12/12 9:15
 */
public class MyServletFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
        throws IOException, ServletException {

        System.out.println("MyServletFilter execute before ..." + req.getPathInfo());
        super.doFilter(req, res, chain);
        System.out.println("MyServletFilter execute after ...");
    }
}
