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
<p><a href="meals?action=add">Add Meal</a></p>
<table style="border-collapse: collapse" border="1" cellspacing = "4">
    <tr><th>Date</th><th>Description</th><th>Calories</th><th>&nbsp;</th><th>&nbsp;</th></tr>
    <c:forEach var="meal" items="${meals}">
        <tr style="color:<c:out value="${not meal.excess ? 'green' : 'red'}"/>">
            <td><fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
                <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${parsedDateTime}" /></td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td>
                <form method="post" action='meals?action=update' style="display:inline;">
                    <input type="hidden" name="mealId" value="${meal.id}">
                    <input type="submit" value="Update">
                </form>
            </td>
            <td>
                <form method="post" action='meals?action=delete' style="display:inline;">
                    <input type="hidden" name="mealId" value="${meal.id}">
                    <input type="submit" value="Delete">
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
