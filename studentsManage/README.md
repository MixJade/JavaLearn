# 学生管理系统

> 经典老项目，关于增删改查

## 登录

> 文件:LoginServlet.java 和 userLogin.jsp

userLogin.jsp需要:

1. 通过post的方式提交到`LoginServlet.java`
2. 在登录失败时能够显示密码错误的提示，这个提示要在用户名或密码输入框获得焦点时消失
3. 有一个记住密码的勾选框，同时，点击`记住密码`这四个字也可以勾选上
4. 能够获取cookie的信息写入用户名和密码框(通过`${cookie.password.value}`)

LoginServlet.java需要:

1. 获取`userLogin.jsp`传来的信息，通过`UserDemo.java`的`userSelect(username, password)`方法对`loginmixjade`表进行查询
2. 查询失败则带着登录失败的信息请求转发回`userLogin.jsp`页面
3. 查询成功设置session,内容是查询返回的`UserMessage`对象
4. 如果登录成功且勾选了`记住密码`,则设置两个cookie,一个记用户名,另一个记密码
5. 注意,通过post方法查询,需要设置读取`req.setCharacterEncoding("utf-8");`,防止因为中文乱码查询失败
6. 登录成功后,重定向到`/Students/students`页面,注意,这里要用获取根路径,防止因为项目改名而失败