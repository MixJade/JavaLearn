package filterAttempt;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter("/at01")
public class FilterAttempt02 implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("2.======访问at01资源，过滤器二号开始过滤======");
        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("3.========访问at01资源，过滤器二号结束过滤=======");
    }

    @Override
    public void destroy() {
    }
}
