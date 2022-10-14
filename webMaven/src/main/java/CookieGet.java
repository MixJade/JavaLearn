import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/cookieGet")
public class CookieGet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("======是获取cookie======");
        Cookie[] cookies = req.getCookies();
        for (Cookie cookie : cookies) {
            String name = cookie.getName();
            String value = cookie.getValue();
            System.out.println(name + " : " + value);
            //写入网页，因为里面有中文，所有要以html的形式，不然中文变问号
            resp.setHeader("content-type", "text/html;charset=UTF-8");
            PrintWriter writer = resp.getWriter();
            writer.write("<br>"+name + "  " + value );
            if ("username".equals(name)) {
                writer.write("<br>I get the cookie<br>");
                writer.write("这就是散玉" + value);
                break;
            } else {
                writer.write("<br>No!!You have no cookie!!!\n<br>");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        this.doGet(req, resp);
    }
}
