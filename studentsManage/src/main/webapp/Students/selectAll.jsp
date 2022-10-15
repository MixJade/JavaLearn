<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>selectAll</title>
</head>
<body>
    <h2>Circulation by JSTL</h2>
    <h3>当前操作人：${user.nameJade}</h3>

    注意：调用对象的数据是通过调用该对象的get方法实现
    <br>
    我可以重写java类中的get方法，但我偏要用jsp中的choose
    <br>
    <button onclick="myFunction01()">添加数据</button>
    <br>
    <table width="90%" border="1">
        <tr>
            <th>id</th>
            <th>姓名</th>
            <th>性别</th>
            <th>英语</th>
            <th>数学</th>
            <th>社团</th>
            <th>身高</th>
            <th>生日</th>
            <th>钱</th>
            <th>排序</th>
            <th>操作</th>
        </tr>
    <c:forEach items="${studentsMessage}" var = "student" varStatus="status">
        <tr align="center">
            <td>${student.id}</td>
            <td>${student.studentName}</td>
            <c:choose>
                <c:when test="${student.sex}">
                    <td>男</td>
                </c:when>
                <c:otherwise>
                    <td>女</td>
                </c:otherwise>
            </c:choose>
            <td>${student.englishGrade}</td>
            <td>${student.mathGrade}</td>
            <td>${student.societyName}</td>
            <td>${student.height}</td>
            <td>${student.birthday}</td>
            <td>${student.money}</td>
            <td>${status.count}</td>
            <td><a href="updateStudent?id=${student.id}">修改</a>&emsp;<a href="delete?id=${student.id}">删除</a></td>
        </tr>
    </c:forEach>
    </table>

</body>
    <script>
        function myFunction01() {
            location.href="./addStudent.html"
        }

    </script>
</html>