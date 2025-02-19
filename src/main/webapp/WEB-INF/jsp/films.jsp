<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Cyber_Vitel
  Date: 09.02.2025
  Time: 11:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Films</title>
</head>
<body>
<%@ include file="toMain.jsp"%>
    <%@ include file="top.jsp"%>
    <ul>
    <c:forEach var="film" items="${requestScope.films}">
        <li>
            <a href="${pageContext.request.contextPath}/film?id=${film.id}"><h2>${film.title} - <fmt:formatNumber value="${film.rating}" maxFractionDigits="1"/></h2></a>
            <p>${film.description}</p>
        </li>
    </c:forEach>
    </ul>
</body>
</html>
