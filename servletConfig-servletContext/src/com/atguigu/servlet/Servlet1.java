package com.atguigu.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Enumeration;
@WebServlet(
        urlPatterns = "/servlet1",
        initParams = {@WebInitParam(name="keya",value = "valueA"),@WebInitParam(name="keyb",value = "valueB")}
)
public class Servlet1 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("----------------ServletConfig获取参数----------------");
        ServletConfig servletConfig = getServletConfig();//获取初始配置信息

        String keya = servletConfig.getInitParameter("keya");//更具参数名获取参数值
        System.out.println("keya:"+keya);

        Enumeration<String> initParameterNames = servletConfig.getInitParameterNames();

        while (initParameterNames.hasMoreElements()){//判断有没有下一个参数，如果有返回true，没有返回false
            String s = initParameterNames.nextElement();//取出下一个元素，向下移动游标（类似JDBC)
            System.out.println(s+"="+getInitParameter(s));
        }

        System.out.println("-----------------------servletContext获取参数-----------------------");
        ServletContext servletContext = getServletContext();
        Enumeration<String> parameterNames = servletContext.getInitParameterNames();
        while (parameterNames.hasMoreElements()){
            String s = parameterNames.nextElement();
            System.out.println(s+"="+servletContext.getInitParameter(s));
        }

        servletContext.setAttribute("ka","va");
        //servletContext.setAttribute("ka","vb");//后者是object类型后面拿的时候要类型转换
       //String ka = (String) servletContext.getAttribute("ka");
       // servletContext.removeAttribute("ka");

    }
}
