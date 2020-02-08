<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3 align="center">MealsList</h3>

<table align="center" border=3>
    <thead>
    <tr>
        <th>DateTime</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>
    </thead>
    <tbody>
    <jsp:useBean id="mealToList" scope="request" type="java.util.List"/>
    <c:forEach var="mealTo" items="${mealToList}">
        <tr style="color: ${mealTo.excess==true?'red':'green'}">
            <td>
                <fmt:parseDate value="${mealTo.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parseDateTime"/>
                <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${parseDateTime}"/>
            </td>
            <td>${mealTo.description}</td>
            <td>${mealTo.calories}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
