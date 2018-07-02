<%@ page import="ru.javawebinar.topjava.model.Meal" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.io.*,java.util.Locale" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Edit</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
    <script src="bootstrap/js/bootstrap.min.js"></script>
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
</head>
<body>
<jsp:include page="components/header.jsp"/>
<form method="post" action="meals" enctype="application/x-www-form-urlencoded">
    <input type="hidden" name="id" value="${meal.id}">
    <div class="card card-body">
        <div class="form-group">
            <div class="col-3">
                <label for="date">Время/Период</label>
                <input type="datetime-local" class="form-control" id="date" name="date"
                       value="${meal.dateTime}" required>
            </div>
        </div>
        <div class="form-group">
            <div class="col-3">
                <label for="description">Описание</label>
                <input type="text" class="form-control" id="description" name="description"
                       value="${meal.description}" required>
            </div>
        </div>
        <div class="form-group">
            <div class="col-3">
                <label for="calories">Калории</label>
                <input type="text" class="form-control" id="calories" name="calories"
                       value="${meal.calories}" required>
            </div>
        </div>
    </div>
    <br>
    <button type="submit" class="btn btn-success" name="save" value="1">Сохранить</button>
    <button type="button" onclick="window.history.back()" class="btn btn-info" name="CancelEdit" value="1">
        Отменить
    </button>
</form>
</body>
</html>
