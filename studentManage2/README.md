# 学生管理系统2.0

> * 使用了axios,vue,element-ui,json
> * 在原有基础上，实现了异步请求、数据模型绑定
> * 新增了分页查询、条件查询。丰富了消息弹出框、界面ui
> * 新增类`MyBaseServlet.java`，通过反射来执行servlet

* 修复了bug:偶尔生成验证码时会出现无法建立session

- bug原因:原来生成验证码的servlet是先通过响应来输出再通过请求建立session
- 修复方法:将请求生成session放在响应之前，该bug成功修复

```
public void checkCode(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    // 响应放在请求前面，会出现无法建立会话
    HttpSession session = req.getSession(true);
    // 通过工具类生成验证码与验证码图片
    ServletOutputStream os = resp.getOutputStream();
    String checkCode = CheckCodeUtil.outputVerifyImage(100, 50, os, 4);
    // 将验证码字符写入session
    session.setAttribute("checkCode", checkCode);
}
```