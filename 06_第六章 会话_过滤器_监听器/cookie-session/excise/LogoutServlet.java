package excise; // TODO(0): 改成你自己工程里的包名

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

/**
 * 退出登录
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // ---------- TODO(6) 退出 ----------
        // 要求：
        //   1. 让当前 Session 立刻失效（有一个方法专门干这个）
        //   2. 顺手把 rememberUser 这个 Cookie 也清掉
        //      （怎么"删"一个 Cookie？提示：同名 + setMaxAge(0)，路径要和当初写时一致）
        //   3. 重定向回 /login

        HttpSession session = req.getSession(false);
        if(session!=null){
            session.invalidate();
        }
        Cookie cookie = new Cookie("rememberUser", "");
        cookie.setMaxAge(0);
        cookie.setPath(req.getContextPath());
        resp.addCookie(cookie);
        resp.sendRedirect(req.getContextPath()+"/login");
    }
}
