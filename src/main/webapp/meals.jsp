<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table style="border-collapse: collapse" border="1" cellspacing = "4">
    <tr><th>Date</th><th>Description</th><th>Calories</th></tr>
    <c:forEach var="meal" items="${meals}">
        <tr>
            <td><fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
                <fmt:formatDate pattern="dd-MM-yyyy HH:mm" value="${parsedDateTime}" /></td>
            <td>${meal.description}</td>
            <td>
                <c:if test="${meal.excess}">
                    <p style="color:red;">${meal.calories}</p>
                </c:if>

                <c:if test="${not meal.excess}">
                    <p style="color:green;">${meal.calories}</p>
                </c:if>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
