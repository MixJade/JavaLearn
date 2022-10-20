package myUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

public class BaseServlet extends HttpServlet {
    /**
     * 通过重写service方法，进行请求路径的判断，并提取相应类的方法名;
     * 顺便设置读取编码;
     * 以后其他的Servlet只需要继承这个类就好
     */
    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");//设置读取的字符编码
        //获取请求路径，从路径中得到所需方法
        String requestURI = req.getRequestURI();
        int index = requestURI.lastIndexOf("/");
        String needMethod = requestURI.substring(index + 1);
        //获取继承这个类的类
        Class<? extends BaseServlet> cls = this.getClass();
        //执行与所需方法同名的方法
        try {
            Method method = cls.getMethod(needMethod, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(this, req, resp);
        } catch (Exception e) {
            System.out.println("\n反射产生了异常，可能没有找到:" + needMethod +"\n"+ e);
            e.printStackTrace();
        }
    }
}
