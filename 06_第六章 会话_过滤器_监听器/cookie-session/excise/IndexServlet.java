package excise; // TODO(0): 改成你自己工程里的包名

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * 登录墙：没登录的人访问这里，直接踢回 /login
 */
@WebServlet("/index")
public class IndexServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");

        // ---------- TODO(5) 登录检查 ----------
        // 要求：
        //   1. 从 Session 里取 "loginUser"
        //   2. 取不到（没登录 / Session 已过期）→ 重定向回 /login，后面的输出不执行
        //   3. 取到了 → 输出欢迎页："欢迎你，xxx"（xxx 是用户名），
        //      再输出一个退出链接：<a href='logout'>退出登录</a>
        //
        // 提示：req.getSession() 没有也会"顺手创建"一个新 Session（浪费），
        //       想只查不建应该用哪个重载？这就是本题真正想让你记住的点
        HttpSession session = req.getSession(false);
        String loginUser = null;
        if (session != null){
            loginUser = (String) session.getAttribute("loginUser");
        }
        if(loginUser == null){
            resp.sendRedirect(req.getContextPath()+"/login");
            return;
        }
        PrintWriter writer = resp.getWriter();
        writer.println("欢迎你"+loginUser);
        writer.println("<a href='logout'>退出登录</a>");


    }
}
