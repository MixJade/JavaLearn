import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Map;

@WebServlet("/chaosSolution")
public class ChaosSolution extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        System.out.println("\n===========是GET请求==========");
        String username = req.getParameter("username");
        /*
            get方法中文乱码原因是，以utf-8的方式从网页读取，以ISO_8859_1方式在tomcat读出
            但是如果通过tomcat启动而非maven插件，下面这个操作，反而会乱码
            byte[] bytes = username.getBytes(StandardCharsets.ISO_8859_1);// 先对从tomcat传出来的数据编码
            username = new String(bytes, StandardCharsets.UTF_8); // 再对解码的数组进行重新解码
         */
        resp.setHeader("content-type", "text/html;charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        writer.write("<h1>" + username + "</h1>你好<br>");
        String queryString = req.getQueryString();
        queryString = URLDecoder.decode(queryString,"utf-8"); // 对十六进制数进行解码
        writer.write(queryString);
        System.out.println("获取请求参数(get形式)：" + queryString);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("\n===========是POST请求==========");
        req.setCharacterEncoding("utf-8");//设置读取的字符编码
        // 设置响应数据
        String username = req.getParameter("username");
        resp.setHeader("content-type", "text/html;charset=UTF-8");
        resp.getWriter().write("<h1>" + username + "<h1>你好");
        // 获取请求数据
        String method = req.getMethod();
        String contextPath = req.getContextPath();
        StringBuffer requestURL = req.getRequestURL();
        String requestURI = req.getRequestURI();

        System.out.println("请求方法：" + method + "\n虚拟目录：" + contextPath + "\n获取URL" + requestURL + "\n获取URI：" + requestURI);
        // 获取请求头
        System.out.println(req.getHeader("user-agent"));
        // 关于请求体中参数，
        // 用这个方法就比较方便了，而且get还有post方法都可以兼容
        Map<String, String[]> map = req.getParameterMap();
        for (String key : map.keySet()) {
            System.out.print(key + ": ");
            for (String s : map.get(key)) {
                System.out.print(s + " ");
            }
        }
    }
}
