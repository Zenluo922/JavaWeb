package com.atguigu.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
@WebServlet("/servlet1")
public class Servlet1 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");

        //获取session对象
        HttpSession session = req.getSession();
        session.setMaxInactiveInterval(60);
        System.out.println(session.getId());
        System.out.println(session.isNew());

        session.setAttribute("usename",username);

        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().write("成功");
    }
}
