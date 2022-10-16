import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebServlet("/at01")//网页的尝试，在wen.xml里面同样配置了这个Servlet的url
public class ServletAttempt implements Servlet {
    @Override
    public void init(ServletConfig servletConfig) {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException {
        System.out.println("========Servlet开始执行=========");
        HttpServletRequest hsr = (HttpServletRequest) servletRequest;
        String requestMode = hsr.getMethod();
        System.out.println(requestMode);
        if ("GET".equals(requestMode)) {
            System.out.println("是GET请求。。。。。。。。。。");
        } else if ("POST".equals(requestMode)) {
            System.out.println("是POST请求。。。。。。。。。。");
        }
        servletResponse.getWriter().write("<h1>Yes_Sir_This_Page</h1>");
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {
        System.out.println("这个网页(at01)被销毁了");
    }

}
