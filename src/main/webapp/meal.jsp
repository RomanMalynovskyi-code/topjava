<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Meal</title>
</head>
<body>

<form method="post" action='meals?' name="frmMeal">
    <label>
        <input hidden="hidden" name="id" value="${meal.id}">
    </label>
    DateTime : <label>
    <input type="text" name="dateTime" value="${meal.dateTime.format(formatter)}"/>
</label> <br/>

    Description : <label>
    <input type="text" name="description" value="${meal.description}"/>
</label> <br/>

    Calories : <label>
    <input type="text" name="calories" value="${meal.calories}"/>
</label> <br/>

    <input type="submit" value="Submit"/>
</form>
</body>
</html>
