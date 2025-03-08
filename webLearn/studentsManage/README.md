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

## 注册

> 文件: register.jsp , RegisterServlet.java ,CheckCodeServlet.java

register.jsp需要:

1. 通过post的方式提交到`RegisterServlet.java`以执行注册操作
2. 通过资源地址的方式启动`CheckCodeServlet.java`获取验证码图片和验证码字符
3. 点击验证码图片刷新验证码(为避免浏览器缓存,通过js来不断更新每次请求的url,通过时间函数来保证url不同)
4. 点击`看不清`刷新验证码(通过href链接到js)
5. 填写验证码失败时返回错误的提示，这个提示要在输入框获得焦点时消失
6. 在注册失败时能够显示错误的提示
7. 有一个记住密码的勾选框，这个勾选框要默认勾选

RegisterServlet.java需要：

1. 读取请求体传来的参数(用户名、密码、验证码、是否记住密码)
2. 通过session(来自`CheckCodeServlet.java`)来验证验证码，验证失败请求转发会注册页面，同时终止后续操作
3. 通过`UserDemo.userSelectByName`查询数据库是否存在用户名
4. 如果数据库不存在用户名,开始进行注册流程
5. 注册流程：向数据库添加用户名和密码;如果勾选了`记住密码`，设置两个cookie
6. 注册成功后重定向到登录页面
7. 注册失败后请求转发回注册页面

CheckCodeServlet.java需要:

1. 通过工具类`CheckCodeUtil.java`生成验证码与验证码图片
2. 将验证码字符写入session

## 过滤

> 文件:LoginFilter01.java,LoginFilter02.java

1. `LoginFilter01`对所有请求进行字符串编码，这样可以减少代码复用
2. `LoginFilter02`对学生文件夹和`elAttempt.jsp`进行过滤，如果没有登录成功的session,就重定向到登录页面