<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
    <meta charset="UTF-8">
    <title>登录</title>
    <link href="../css/myLogin.css" rel="stylesheet">
</head>

<body>
    <div id="myLoginDiv">
        <form action="./login" method="post">
            <h1 id="login_title">登录</h1>
            <div id="login_fail">${login_fail}</div>
            <p>用户名:<input name="username" type="text" id="username" value="${cookie.username.value}"></p>
            <p>密码:<input name="password" type="password" id="password" value="${cookie.password.value}"></p>
            <p><input type="checkbox" name="remember" id="remember"><label for="remember">记住密码</label></p>
            <div id="subDiv">
                <input type="submit" id="sub_press" value="登录">
                <a href="register.jsp">点击注册</a>
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
        document.getElementById("login_fail").style.display = 'none'
    }
</script>

</html>