<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>elAttempt</title>
</head>
<body>
    <h3>Test by EL</h3>
    <a href="https://baijiahao.baidu.com/s?id=1745257873799929740&wfr=spider&for=pc">说明书</a>
    <br>
    ${brands}
    <h3>Circulation by JSTL</h3>
    注意：调用对象的数据是通过调用该对象的get方法实现
    <br>
    注意：里面用了c:forEach 和 c:if
    <br>
    注意：items是遍历容器,var是遍历变量,varStatus是遍历状态
    <table width="50%" border="1">
        <tr>
            <th>排序</th>
            <th>商品ID</th>
            <th>名称</th>
            <th>描述</th>
            <th>状态</th>
        </tr>
    <c:forEach items="${brands}" var = "brand" varStatus="status">
        <tr align="center">
            <td>${status.count}</td>
            <td>${brand.brandId}</td>
            <td>${brand.brandName}</td>
            <td>${brand.brandDescribe}</td>
            <c:if test="${brand.brandStatus==1}">
            <td>上架</td>
            </c:if>
            <c:if test="${brand.brandStatus!=1}">
            <td>禁售</td>
            </c:if>
        </tr>
    </c:forEach>
    </table>
    <br>
    <c:forEach begin="0" end="9" step="1" var="i">
            <a href="#">${i}</a>
    </c:forEach>
</body>
</html>