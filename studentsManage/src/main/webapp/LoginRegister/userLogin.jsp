<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>登录</title>
</head>

<body>
    <h1>登录</h1>
    <span id="login_fail" style="display:inline;color:red">${login_fail}</span>
    <form action="./login" method="post">
        用户名：<input name="username" type="text" id="username" value="${cookie.username.value}">
        <br>
        密码：<input name="password" type="password" id="password" value="${cookie.password.value}">
        <br>
        <input type="checkbox" name="remember" id="remember"><label for="remember">记住密码</label>
        <br>
        <input type="submit" value="登录">
        <input type="reset" value="重新输入">
        <br>
        <a href="Register.html">点击注册</a>
    </form>
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