<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
    <meta charset="UTF-8">
    <title>注册</title>
    <link href="../css/myRegister.css" rel="stylesheet">
</head>

<body>
    <div id="myLoginDiv">

        <form action="./register" method="post">
            <h2 id="login_title">开始注册！</h2>
            <div id="register_fail">${register_fail}</div>
            <p>用户名：<input type="text" name="username" id="username" required="required"></p>
            <p>密码：<input type="password" name="password" id="password" required="required"></p>
            <p>验证码: <input type="text" name="checkCode" id="checkCode">
                <img src="./checkCode" id="checkCodeImg">
                <a href="javascript:check_code()" id="changeImg">看不清？</a>
            </p>
            <div id="subDiv">
                <input type="checkbox" name="remember" id="remember" checked="checked"><label
                    for="remember">记住密码</label>
                <input type="submit" id="sub_press" value="注册"><a href="userLogin.jsp">登录?</a>
            </div>
        </form>
    </div>
</body>
<script>
    // 获得焦点隐藏报错
    var userName01 = document.getElementById("username")
    var passWord01 = document.getElementById("password")
    userName01.onblur = hide_fail
    passWord01.onblur = hide_fail
    function hide_fail() {
        document.getElementById("register_fail").style.display = 'none'
    }
    //刷新验证码
    document.getElementById("checkCodeImg").onclick = check_code
    function check_code() {
        document.getElementById("checkCodeImg").src = "./checkCode?" + new Date().getMilliseconds();
    }
</script>

</html>