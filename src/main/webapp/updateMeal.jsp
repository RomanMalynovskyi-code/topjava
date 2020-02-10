<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>UpdateMeal</title>
</head>
<body>
<jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
<jsp:useBean id="formatter" scope="request" type="java.time.format.DateTimeFormatter"/>

<form method="post" action='meals' name="frmUpdateMeal">
    ID : <label>
    <input type="text" name="id" readonly="readonly" value="${meal.id}"/>
</label> <br/>

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
