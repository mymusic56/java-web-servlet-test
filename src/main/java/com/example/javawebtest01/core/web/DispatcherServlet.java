package com.example.javawebtest01.core.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.javawebtest01.config.DBProperties;
import com.example.javawebtest01.core.config.ComponentParseUtil;
import com.example.javawebtest01.dao.WebsitesDAO;
import com.example.javawebtest01.dto.WebsitesDO;
import com.example.javawebtest01.util.DBPropertiesUtil;

import cn.hutool.json.JSONUtil;

/**
 * 注解是从 Servlet 3.0 版本开始引入的
 *
 * 这里使用通配符，匹配所有的路径，如果有明确指定的URL，会优先使用
 */
// @WebServlet(name = "DispatcherServlet", value = "/*", initParams = {})
public class DispatcherServlet extends HttpServlet {
    private List<String> uriMappingNames = new ArrayList<>();
    private Map<String, Object> uriMappingObjs = new HashMap<>();
    private Map<String, Method> uriMappingMethods = new HashMap<>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        System.out.println("DispatcherServlet init()......");
        ServletContext servletContext = getServletContext();
        String initParameter = servletContext.getInitParameter("contextConfigLocation");

        // todo 获取数据配置文件信息，放入到上下文中
        DBPropertiesUtil.init(servletContext);

        // initParameter 没有获取到值
        if (initParameter == null) {
            System.out.println("contextConfigLocation is null");

            initParameter = "/WEB-INF/mvc-servlet.xml";
        }
        // 解析自定义注解
        URL resource = null;
        try {
            resource = config.getServletContext().getResource(initParameter);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        ComponentParseUtil componentParseUtil = new ComponentParseUtil();
        componentParseUtil.parse(resource);

        this.uriMappingObjs = componentParseUtil.getUriMappingObjs();
        this.uriMappingMethods = componentParseUtil.getUriMappingMethods();
        this.uriMappingNames = componentParseUtil.getUriMappingNames();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();
        System.out.println("pathInfo = " + pathInfo);

        ServletContext servletContext = request.getServletContext();

        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType("text/html;charset=UTF-8");
        if ("/".equals(pathInfo)) {
            response.getWriter().println("Handling request for / 根目录页面");
            return;
        } else if ("/db/get".equals(pathInfo)) {
            // Handle request for /page2
            DBProperties dbProperties = (DBProperties)servletContext.getAttribute("dbProperties");
            WebsitesDAO websitesDAO = new WebsitesDAO();
            List<WebsitesDO> doList = websitesDAO.getConnection("", dbProperties);
            response.getWriter().println("查询结果：" + JSONUtil.toJsonStr(doList));
            return;
        } else {
            doMyRequest(request, response);
            return;
        }
    }

    private void doMyRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();

        if (!uriMappingNames.contains(pathInfo)) {
            response.getWriter().println("uri mapping name not found: " + pathInfo);
            return; // 如果是uri mapping，直接返回，不再处理请求了。
        }

        Method method = uriMappingMethods.get(pathInfo);
        if (method == null) {
            response.getWriter().println("not found uri: " + pathInfo);
            return;
        }

        Object obj = uriMappingObjs.get(pathInfo);
        if (obj == null) {
            response.getWriter().println("request mapping deal object error: " + pathInfo);
            return;
        }

        try {
            Object invoke = method.invoke(obj);
            if (invoke != null) {
                response.getWriter().println(invoke.toString());
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public void destroy() {}
}