package filterAttempt;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * 芝士过滤器，这个注解的意思是拦截所有资源
 */
@WebFilter("/*")
public class FilterAttempt01 implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("1.======过滤器一号开始过滤======");
        // 放行
        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("4.========过滤器一号结束过滤=======");
    }

    @Override
    public void destroy() {
    }
}
