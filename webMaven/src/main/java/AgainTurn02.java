import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/againTurn02")//请求转发
public class AgainTurn02 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("===========请求转发02,是GET002请求==========");
        // 请求转发，可以先自己设置数据
        req.setAttribute("mix", "Jade");
        req.getRequestDispatcher("/againTurn03").forward(req, resp);
        // 会覆盖之前写入的网页，不过很正常，因为路径变了
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("===========请求转发02，是POST请求==========");
    }
}
