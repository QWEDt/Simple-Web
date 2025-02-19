<%--
  Created by IntelliJ IDEA.
  User: Cyber_Vitel
  Date: 12.02.2025
  Time: 14:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<%@ include file="toMain.jsp"%>
    <form action="${pageContext.request.contextPath}/login" method="post">
        <label>
            Username
            <input name="username" type="text">
            <br>
        </label>
        <label>
            Password
            <input name="password" type="password">
            <br>
        </label>
        <input type="submit" value="Login">
    </form>
</body>
</html>
