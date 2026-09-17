package com.atguigu.servlet;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Enumeration;

@WebServlet("/servlet4")
public class Servlet4 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(req.getMethod());//获取请求方式
        System.out.println(req.getScheme());//获取请求协议
        System.out.println(req.getProtocol());//请求协议以及版本
        System.out.println(req.getRequestURI());//获取请求的uri,项目内的资源路径
        System.out.println(req.getRequestURL());//资源的完整路径

        System.out.println(req.getLocalPort());//本应用端口号，tomcat:8080
        System.out.println(req.getServerPort());//客户端发请求时使用的端口号
        System.out.println(req.getRemotePort());//客户端软件的端口号

        //根据本次请求中所有的请求头的名字
        Enumeration<String> headerNames = req.getHeaderNames();
        while (headerNames.hasMoreElements()){
            String s = headerNames.nextElement();
            System.out.println(s+":"+req.getHeader(s));
        }
    }
}
