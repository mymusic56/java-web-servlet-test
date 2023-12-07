package com.example.javawebtest01;

import java.io.*;
import javax.servlet.ServletContext;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!-servlet";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ServletContext servletContext = request.getServletContext();
        String name = servletContext.getServletContextName();
        System.out.println("name = " + name);


        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }

    public void destroy() {
    }
}