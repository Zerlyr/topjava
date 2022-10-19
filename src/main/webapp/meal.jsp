<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
  <title>Add new meal</title>
</head>
<body>
<h2>Add/Update Meal</h2>
<form method="POST" action='meals' name="frmAddMeal">
  Date : <input type="datetime-local" name="date" value="${meal.dateTime}"/> <br />
  Description : <input type="text" name="description" value="${meal.description}"/> <br />
  Calories : <input type="number" name="calories" value="${meal.calories}"/> <br />
  <input type="hidden" name="mealId" value="${meal.id}"/>
  <input type="submit" value="Подтердить" />
  <input type="reset" value="Сбросить" />
</form>
</body>
</html>