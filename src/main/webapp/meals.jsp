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
<p><a href="meals?action=insert">Add User</a></p>
<table style="border-collapse: collapse" border="1" cellspacing = "4">
    <tr><th>Date</th><th>Description</th><th>Calories</th><th>&nbsp;</th><th>&nbsp;</th></tr>
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
            <td><a href="meals?action=edit&mealId=<c:out value="${meal.id}"/>">Update</a></td>
            <td><a href="meals?action=delete&mealId=<c:out value="${meal.id}"/>">Delete</a></td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
