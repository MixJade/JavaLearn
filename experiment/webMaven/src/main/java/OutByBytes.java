import org.apache.commons.io.IOUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;

@WebServlet("/outByBytes")//字节流输出，可以输出图片
public class OutByBytes extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("===========是字节流输出==========");
        FileInputStream fileInputStream = new FileInputStream("C:\\Users\\11141\\Pictures\\Camera Roll\\September20th (17).jpeg");
        // 获取Response字节输出流
        ServletOutputStream servletOutputStream = resp.getOutputStream();
        // 使用工具类，要在pom.xml里面导入
        IOUtils.copy(fileInputStream, servletOutputStream);
        fileInputStream.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("===========是POST请求==========");
    }
}
