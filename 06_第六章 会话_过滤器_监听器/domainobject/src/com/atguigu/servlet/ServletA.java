package com.atguigu.servlet;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
@WebServlet("/servletA")
public class ServletA extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //向请求域存放数据
        req.setAttribute("request","requestMessage");

        //向会话域存放数据
        HttpSession session = req.getSession();
        session.setAttribute("session","sessionMessage");

        //向应用域存放数据
        ServletContext application = getServletContext();
        application.setAttribute("application","applicationMessage");

        String reqMessage = (String) req.getAttribute("request");
        System.out.println("请求域："+reqMessage);

        req.getRequestDispatcher("servletB").forward(req,resp);//响应调度器
        //把最后一行换成重定向：
        //  resp.sendRedirect("servletB");
        //  再去 ServletB 里取：请求域取到的是 null，session 和 application 照样能取到。
    }
}
