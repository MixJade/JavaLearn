import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet("/paramGet")//请求转发不会导致地址的改变，同时会调用目标的资源
public class ParamGet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");//设置读取的字符编码
        // 获取请求数据
        String method = req.getMethod();
        String contextPath = req.getContextPath();
        StringBuffer requestURL = req.getRequestURL();
        String requestURI = req.getRequestURI();

        String str01 = "请求方法：" + method +
                "<br>虚拟目录：" + contextPath +
                "<br>获取URL" + requestURL +
                "<br>获取URI：" + requestURI;
        // 获取请求头
        String str02=req.getHeader("user-agent")+"<br>";
        //关于请求体中参数，
        StringBuilder str03 = new StringBuilder(str01);
        str03.append(str02);
        Map<String, String[]> map = req.getParameterMap();
        for (String key : map.keySet()) {
            str03.append(key).append(": ");
            for (String s : map.get(key)) {
                str03.append(s).append(" ");
            }
            str03.append("<br>");
        }
        //设置响应数据
        String username = req.getParameter("username");
        resp.setHeader("content-type", "text/html;charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        writer.write("<h1>" + username + "你好鸭</h1>");
        if ("GET".equals(method)){
            writer.write("<h2>是GET方法！</h2>");
            writer.write("<p>中文名字是乱码吗？</p>");
            writer.write("<p>get方法的中文是通过16进制存储"+req.getQueryString()+"</p>");
        }
        writer.write("<p>" + str03 + "</p>");
    }

}
