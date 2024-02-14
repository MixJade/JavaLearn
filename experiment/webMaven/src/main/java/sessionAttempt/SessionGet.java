package sessionAttempt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/sessionGet")//从服务器获取session
public class SessionGet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("Try to get session\n");
        HttpSession session = req.getSession();
        Object message = session.getAttribute("Message");
        System.out.println(message);
        if (message != null) {
            resp.getWriter().write("I get the session\nand i remove it");
            // 根据value删除session
            session.removeAttribute("Message");
        } else {
            resp.getWriter().write("I didn't find the session i want");
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
