package com.demo;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet("/api/hello")
public class ServletAttempt implements Servlet {
    private static int reqNum = 0;

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
        // 设置响应的字符编码为 UTF-8
        servletResponse.setCharacterEncoding("UTF-8");
        // 设置响应的内容类型为 HTML
        servletResponse.setContentType("text/html;charset=UTF-8");
        reqNum++;
        servletResponse.getWriter().write("<h1>Yes_Sir_This_Page</h1>访问次数" + reqNum
                + "<br>客户端IP地址: " + servletRequest.getRemoteAddr()
                + "<br>客户端端口: " + servletRequest.getRemotePort()
        );
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
