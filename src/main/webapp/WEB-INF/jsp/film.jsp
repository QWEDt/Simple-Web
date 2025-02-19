<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Cyber_Vitel
  Date: 12.02.2025
  Time: 18:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${requestScope.film.name}</title>
</head>
<body>
    <%@ include file="toMain.jsp"%>
    <%@ include file="top.jsp"%>

    <h1>${requestScope.film.name} - <fmt:formatNumber value="${requestScope.film.score}" maxFractionDigits="1"/></h1>
    <p>${requestScope.film.description}</p>
    <p>Length - ${requestScope.film.length}</p>
    <p>Realised - ${requestScope.film.releaseDate}</p>
    <h2>Reviews</h2>
    <c:if test="${not empty sessionScope.user}">
        <form action="${pageContext.request.contextPath}/film?id=${requestScope.film.id}" method="post">
            <h2>Leave a Review</h2>
            <label>
                Give a rating
                <input name="score" type="number" min="0" max="10" step="1">
                <br>
            </label>
            <label>
                Write what you think
                <textarea name="text"></textarea>
                <br>
            </label>
            <input type="submit" value="OK">
        </form>
    </c:if>
    <c:forEach var="review" items="${requestScope.film.reviewDtoList}">
        <h3>${review.user.username} - ${review.score}</h3>
        <h4>${review.publicationDate}</h4>
        <p>${review.text}</p>
        <br>
    </c:forEach>
</body>
</html>
