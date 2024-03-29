package againTurn;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/againTurn03")// 请求转发与重定向所指向的Servlet
public class AgainTurn03 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("===========是请求转发、重定向03请求==========");
        Object mix = req.getAttribute("mix");
        System.out.println(mix);
        // 会覆盖之前写入的网页，不过很正常，因为路径变了
        resp.setHeader("content-type", "text/html;charset=UTF-8");
        if (mix==null){
            resp.getWriter().write("<h2>是重定向吧</h2>通过响应进行跳转，可以看到地址变啦");
        }else {
            resp.getWriter().write("<h2>是请求转发吧,"+mix+",我收到参数了</h2>通过响应进行跳转，可以看到地址没变");
            resp.getWriter().write("<p>你可以看到，请求转发，不会改变网址，但网页被覆盖了</p>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("===========请求转发03，是POST请求==========");
    }
}
