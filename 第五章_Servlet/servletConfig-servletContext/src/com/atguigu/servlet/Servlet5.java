package com.atguigu.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

@WebServlet("/servlet5")
public class Servlet5 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //接收请求中键值对的参数
        String username = req.getParameter("username");//根据参数名获取参数值
        System.out.println(username);
        String userPwd = req.getParameter("userPwd");
        System.out.println(userPwd);
        //根据参数名获取多个参数值
        String[] hobbies = req.getParameterValues("hobby");
        System.out.println(Arrays.toString(hobbies));

        //获取所有的参数名
        Enumeration<String> pnames = req.getParameterNames();
        while (pnames.hasMoreElements()) {
            String pname = pnames.nextElement();
            String[] values = req.getParameterValues(pname);
            if(values.length>1){
                System.out.println(pname+":"+Arrays.toString(values));
            }else {
                System.out.println(pname+":"+values[0]);
            }
        }
        //返回所有参数的map集合
        Map<String, String[]> parameterMap = req.getParameterMap();
        Set<Map.Entry<String, String[]>> entries = parameterMap.entrySet();
        for (Map.Entry<String, String[]> entry : entries) {
            String pname = entry.getKey();
            String[] value = entry.getValue();
            if(value.length>1){
                System.out.println(pname+":"+Arrays.toString(value));
            }else {
                System.out.println(pname+"="+value[0]);
            }
        }
    }
}
