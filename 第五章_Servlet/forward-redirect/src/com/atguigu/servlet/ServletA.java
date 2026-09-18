package com.atguigu.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
@WebServlet("/servletA")
public class ServletA extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("servletA执行了");

        String money = req.getParameter("money");
        System.out.println("servletA获得参数：money="+money);
        //请求转发给servletB
        //获得请求转发器req.getRequestDispatcher
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("servletB");
        //请求器做出转发动作，过程是sA传sB
        requestDispatcher.forward(req,resp);
    }
}
