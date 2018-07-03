<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Meals</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
    <script src="bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
<jsp:include page="components/header.jsp"/>
<form>
    <div class="container">
        <h2>Список еды</h2>
        <button type="button" class="btn btn-outline-success align-self-md-start"><a href="meals?id=${-1}&action=edit">
            Добавить</a></button>
    </div>
    <div class="card card-body">

        <div class="container">
            <table class="table table-hover bg-light text-black-50">
                <thead class="thead-light bg-info text-truncate">

                <tr>
                    <th>Дата/Время</th>
                    <th>Описание</th>
                    <th>Калории</th>
                    <th>Изменить</th>
                    <th>Удалить</th>
                </tr>
                </thead>
                <tbody>

                <c:forEach var="meal" items="${meals}">
                    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealWithExceed"/>
                    <c:set var="timeOrigin" value="${meal.dateTime}"/>
                    <c:set var="timeEdit" value="${fn:replace(timeOrigin,'T',' ')}"/>
                    <c:set var="colortext" value="text-success"/>

                    <c:if test="${meal.exceed=='true'}">
                        <c:set var="colortext" value="text-danger"/>
                    </c:if>

                    <tr class="${colortext}">
                        <td>${timeEdit}</td>
                        <td>${meal.description}</td>
                        <td>${meal.calories}</td>
                        <td>
                            <a href="meals?id=${meal.id}&action=edit"><img src="img/pencil.png"></a>
                        </td>
                        <td>
                            <a href="meals?id=${meal.id}&action=delete"><img src="img/delete.png"></a>
                        </td>
                    </tr>

                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</form>
</body>
</html>