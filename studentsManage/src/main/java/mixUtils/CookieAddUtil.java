package mixUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class CookieAddUtil {
    /**
     * 为登录与注册之中添加cookie的操作进行简化
     */
    public static void addMyCookie(HttpServletResponse resp, String username, String password) {
        //如果勾选了"记住密码"设置两个cookie
        Cookie cookieUsername = new Cookie("username", username);
        Cookie cookiePassword = new Cookie("password", password);
        //设置存活时间，半个小时
        cookieUsername.setMaxAge(60 * 30);
        cookiePassword.setMaxAge(60 * 30);
        //给浏览器添加cookie
        resp.addCookie(cookieUsername);
        resp.addCookie(cookiePassword);
    }
}
