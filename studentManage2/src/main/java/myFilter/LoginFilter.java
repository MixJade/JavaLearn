package myFilter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/manager/*", "/plentiful/*"})
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
    }

    /**
     * 过滤学生相关资源;
     * 验证是否登录;
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        //转化request对象
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        //获取session
        HttpSession session = req.getSession();
        Object user = session.getAttribute("user");
        if (user != null) {
            //放行
            filterChain.doFilter(request, response);
        } else {
            String contextPath = req.getContextPath();
            resp.sendRedirect(contextPath + "/loginPage/userLogin.html");
        }
    }

    @Override
    public void destroy() {

    }
}
