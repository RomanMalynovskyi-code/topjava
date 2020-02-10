<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>AddMeal</title>
</head>
<body>

<form method="post" action='meals' name="frmAddMeal">
    DateTime : <label>
    <input type="text" name="dateTime" value="${meal.dateTime}"/>
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
