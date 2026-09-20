package com.atguigu.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
@WebServlet("/servletA")
public class ServletA extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie cookie1 = new Cookie("keya", "valuea");
        Cookie cookie2 = new Cookie("keyb", "valueb");
        //cookie1.setMaxAge(60*2);
        cookie1.setPath("/cookie/servletB");
        resp.addCookie(cookie1);
        resp.addCookie(cookie2);
    }
}
