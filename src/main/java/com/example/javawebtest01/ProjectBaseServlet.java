package com.example.javawebtest01;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.javawebtest01.config.DBProperties;
import com.example.javawebtest01.dao.WebsitesDAO;
import com.example.javawebtest01.dto.WebsitesDO;
import com.example.javawebtest01.util.DBPropertiesUtil;

import cn.hutool.json.JSONUtil;

/**
 * 注解是从 Servlet 3.0 版本开始引入的
 *
 * 这里使用通配符，匹配所有的路径，如果有明确指定的URL，会优先使用
 */
@WebServlet(name = "ProjectBaseServlet", value = "/*")
public class ProjectBaseServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!-servlet-这是一个基础的Servlet!";
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();
        System.out.println("pathInfo = " + pathInfo);

        ServletContext servletContext = request.getServletContext();

        // 获取数据配置文件信息，放入到上下文中
        DBPropertiesUtil.init(servletContext);

        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType("text/html;charset=UTF-8");
        if ("/page1".equals(pathInfo)) {
            // Handle request for /page1
            response.getWriter().println("Handling request for /page1 页面1");
            return;
        } else if ("/page2".equals(pathInfo)) {
            // Handle request for /page2
            response.getWriter().println("Handling request for /page2 页面2");
            return;
        } else if ("/db/get".equals(pathInfo)) {
            // Handle request for /page2
            DBProperties dbProperties = (DBProperties)servletContext.getAttribute("dbProperties");
            WebsitesDAO websitesDAO = new WebsitesDAO();
            List<WebsitesDO> doList = websitesDAO.getConnection("", dbProperties);
            response.getWriter().println("查询结果：" + JSONUtil.toJsonStr(doList));
            return;
        }

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }

    public void destroy() {}
}