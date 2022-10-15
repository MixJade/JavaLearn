<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>注册</title>
</head>
<body>
开始注册！
<form action="./register" method="post">
    用户名：<input type="text" name="username" id="username" required="required">
    <span id="register_fail" style="display:inline;color:red">${register_fail}</span>
    <br>
    密码：<input type="password" name="password" id="password" required="required">
    <br>
    <input type="submit" value="注册">
</form>
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

</script>
</html>