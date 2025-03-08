package againTurn;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/againTurn01") //重定向
public class AgainTurn01 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("===========是重定向001==========");
        //resp.setStatus(302);//设置状态码，固定的
        //resp.setHeader("location","/againTurn03");//前面那个也是固定的
        // ============以下，简化方式完成重定向==================
        try {
            resp.sendRedirect("./againTurn03");
            //可以重定向到网址
            //resp.sendRedirect("https://www.baidu.com");
        } catch (IOException e) {
            System.out.println("文件异常" + e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("===========是POST请求==========");
    }
}
