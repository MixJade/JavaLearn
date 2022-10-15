package web;

import pojo.Brand;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/brand")
public class BrandServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Brand> brands = new ArrayList<>();
        brands.add(new Brand(101,"西瓜","夏日水果",1));
        brands.add(new Brand(102,"炸鸡","油炸食品",1));
        brands.add(new Brand(103,"倪哥","一种劳动力",0));
        // 存储到请求域里
        req.setAttribute("brands",brands);
        // 请求转发
        req.getRequestDispatcher("elAttempt.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
