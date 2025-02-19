<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Cyber_Vitel
  Date: 12.02.2025
  Time: 12:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
    <%@ include file="toMain.jsp"%>
    <form action="${pageContext.request.contextPath}/registration" method="post">
        <label>
            Username
            <input name="username" type="text" required>
        </label>
        <br>
        <label>
            Password
            <input name="password" type="password" required>
        </label>
        <br>
        <label>
            Gender
            <br>
            <c:forEach var="gender" items="${requestScope.genders}">
                <input name="gender" type="radio" value="${gender}"> ${gender}
                <br>
            </c:forEach>
        </label>
        <br>
        <input type="submit" value="Accept">
        
        <c:if test="${not empty requestScope.errors}">
            <c:forEach var="error" items="${requestScope.errors}">
                <p style="color: red">${error.message}</p>
            </c:forEach>
        </c:if>
    </form>
</body>
</html>
