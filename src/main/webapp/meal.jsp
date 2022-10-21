<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
  <title>${action} meal</title>
</head>
<body>
<h2>${empty meal.id ? 'Add' : 'Update'} meal</h2>
<form method="POST" action='meals?action=add-update' name="frmAddMeal">
  Date : <input type="datetime-local" name="date" id="dateTime" value="${meal.dateTime}"/> <br />
  <script>
    if (document.getElementById("dateTime").value == "")
    {
      var now = new Date();
      now.setMinutes(now.getMinutes() - now.getTimezoneOffset());
      document.getElementById('dateTime').value = now.toISOString().slice(0, 16);
    }
  </script>
  Description : <input type="text" name="description" value="${meal.description}"/> <br />
  Calories : <input type="number" name="calories" value="${meal.calories}"/> <br />
  <input type="hidden" name="mealId" value="${meal.id}"/>
  <input type="submit" value="Подтердить" />
</form>
<form action='meals?action=reset' method="POST">
  <input type="submit" value="Отмена"/>
</form>
</body>
</html>