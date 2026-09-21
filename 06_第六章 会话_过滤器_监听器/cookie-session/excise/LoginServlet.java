package excise; // TODO(0): 改成你自己工程里的包名

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 登录页（doGet）+ 登录处理（doPost）
 * 骨架可以直接跑，先启动看到登录页，再开始做 TODO
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();

        // ---------- TODO(1) 记住我：从 Cookie 回显用户名 ----------
        // 要求：
        //   1. req.getCookies() 可能为 null，先判空
        //   2. 遍历找到 name 为 "rememberUser" 的 Cookie
        //   3. 它的值当初是 URL 编码存的，要用 URLDecoder.decode 解码；找不到 Cookie 就是 ""
        String username = "";
        Cookie[] cookies = req.getCookies();
        if(null!=cookies&&cookies.length!=0){
            for (Cookie cookie : cookies) {
                if ("rememberUser".equals(cookie.getName())){
                    username = URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8);
                }
            }
        }

        // 登录失败转发回来时带过来的错误提示（TODO(4) 里 set 的，现在先不管）
        Object msg = req.getAttribute("msg");

        out.println("<!DOCTYPE html><html><head><meta charset='utf-8'><title>登录</title></head><body>");
        out.println("<h2>登录</h2>");
        if (msg != null) out.println("<p style='color:red'>" + msg + "</p>");
        out.println("<form method='post' action='" + req.getContextPath() + "/login'>");
        out.println("用户名：<input name='username' value='" + username + "'/><br/>");
        out.println("密码：<input type='password' name='password'/><br/>");
        out.println("<input type='checkbox' name='remember' value='on'/> 记住我<br/>");
        out.println("<input type='submit' value='登录'/>");
        out.println("</form></body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");

        // ---------- TODO(2) 读取参数并校验 ----------
        // 要求：
        //   1. 从 req 取 username、password、remember 三个参数（remember 只有勾选了才有值）
        //   2. 和下面的 USERS 比对，算出 boolean ok
        boolean ok = false;
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String remember = req.getParameter("remember");
        String expectedPwd = USERS.get(username);
        ok = expectedPwd != null && expectedPwd.equals(password);
        if (ok) {
            // ---------- TODO(3) 登录成功 ----------
            // 要求：
            //   1. 把用户名存入 Session，属性名叫 "loginUser"
            //   2. 勾了"记住我"：写一个 Cookie，name 为 rememberUser，值为用户名
            //      （用户名要先 URLEncoder.encode；有效期 7 天；路径设为应用根路径 "/"）
            //      没勾：写一个同名 Cookie 但 setMaxAge(0)，把浏览器里旧的清掉
            //   3. 重定向到 /index —— 浏览器地址栏要变的那种跳转
            req.getSession().setAttribute("loginUser", username);

            if (remember != null) {
                // 勾了"记住我"：写 7 天的 Cookie，名字要和 doGet 里读的对上
                Cookie cookie = new Cookie("rememberUser", URLEncoder.encode(username, StandardCharsets.UTF_8));
                cookie.setMaxAge(7 * 24 * 60 * 60);
                cookie.setPath(req.getContextPath());
                resp.addCookie(cookie);
            } else {
                // 没勾：发同名同路径的 Cookie，但存活 0 秒 = 让浏览器把旧的删掉
                Cookie cookie = new Cookie("rememberUser", "");
                cookie.setMaxAge(0);
                cookie.setPath(req.getContextPath());
                resp.addCookie(cookie);
            }

            resp.sendRedirect(req.getContextPath() + "/index");

        } else {
            // ---------- TODO(4) 登录失败 ----------
            // 要求：
            //   1. 往 req 里 set 属性 "msg"，值为 "用户名或密码错误"
            //   2. 转发回 /login —— 地址栏不变的那种跳转，正因为不变，属性才能带得过去
            req.setAttribute("msg","用户名或密码错误");
            req.getRequestDispatcher("/login").forward(req,resp);
        }
    }

    // 假装这是数据库
    private static final java.util.Map<String, String> USERS = java.util.Map.of(
            "admin", "123456",
            "user", "123456"
    );
}
