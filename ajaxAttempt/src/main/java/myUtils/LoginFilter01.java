package myUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter("/*")
public class LoginFilter01 implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
    }

    /**
     * 设置读取的字符编码
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        request.setCharacterEncoding("utf-8");//设置读取的字符编码
        //放行
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
