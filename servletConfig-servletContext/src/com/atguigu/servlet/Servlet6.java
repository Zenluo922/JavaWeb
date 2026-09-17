package com.atguigu.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/servlet6")
public class Servlet6 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //响应行相关
        resp.setStatus(404);

        String info = "<h1>hello</h1>";
        //响应头相关
        //resp.setHeader("Content-Type","text/html");
        resp.setContentType("text/html");
        resp.setContentLength(info.getBytes().length);

        //设置响应体内容
        //获得一个向响应体中输入文本字符输入流
        PrintWriter writer = resp.getWriter();
        writer.write(info);
    }
}
