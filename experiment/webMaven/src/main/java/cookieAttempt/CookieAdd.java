package cookieAttempt;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cookieAdd")//向浏览器添加cookie
public class CookieAdd extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("======是添加cookie======");
        resp.getWriter().write("I add cookie");
        Cookie cookie=new Cookie("username","MixJade");
        cookie.setMaxAge(60*60);//存活一个小时,如果不设置，该cookie会在浏览器关闭后销毁
        resp.addCookie(cookie);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        this.doGet(req,resp);
    }
}
