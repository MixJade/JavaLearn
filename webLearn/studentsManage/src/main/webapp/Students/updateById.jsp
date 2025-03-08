<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>修改学生</title>
</head>

<body>
    <h3 style="text-align:center">修改学生</h3>
    <div style="padding-left:40%;">
        <form action="updateStudent" method="post">
        <input type="hidden" name="id" value="${student.id}">
            姓名:<input type="text" name="studentName" required="required" value="${student.studentName}"><br>
            性别:&emsp;
            <input type="radio" name="sex" value="1" id="female" required="required"><label for="female">男&emsp;</label>
            <input type="radio" name="sex" value="0" id="male" required="required"><label for="male">女&emsp;</label>
            <br>
            英语:<input type="number" min="1" max="100" name="englishGrade" required="required"
                value="${student.englishGrade}"><br>
            数学:<input type="number" min="1" max="100" name="mathGrade" required="required"
                value="${student.mathGrade}"><br>
            社团:
            <select name="societyId" id="societyId">
                <option value="1">散人</option>
                <option value="2">头文字D</option>
                <option value="3">轻音部</option>
                <option value="4">黄金之风</option>
                <option value="5">极东魔术昼寝结社</option>
            </select>
            <br>
            身高:<input type="number" min="1.00" max="2.00" step="0.01" name="height" required="required"
                value="${student.height}"><br>
            生日:<input type="date" name="birthday" required="required" value="${student.birthday}"><br>
            &emsp;钱:<input type="text" name="money" oninput="value=value.replace(/[^\d]/g,'')" required="required"
                value="${student.money}"><br>
            <input type="submit">
        </form>
    </div>
</body>
<script>
    window.onload = function () {
        var societyId = document.getElementById("societyId");
        var opts = societyId.getElementsByTagName("option");
        var sId = ${student.societyId} - 1
        opts[sId].selected = true;
        var studentSex = document.getElementsByName("sex");
        var sex = ${student.sex}
        if (sex == 1) {
            studentSex[0].checked = true;
        } else {
            studentSex[1].checked = true;
        }
    }

</script>

</html>