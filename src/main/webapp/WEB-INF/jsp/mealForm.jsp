<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<head>
    <title><spring:message code="meal.oneTitle"/></title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<section>
    <h2>
        <c:choose>
            <c:when test="${meal.isNew()==true}">
                <spring:message code="meal.createMeal"/>
            </c:when>
            <c:otherwise>
                <spring:message code="meal.editMeal"/>
            </c:otherwise>
        </c:choose>
    </h2>
    <hr>
    <form method="post" action="${pageContext.request.contextPath}/meals/save">
        <input type="hidden" name="id" value="${meal.id}">
        <dl>
            <dt><spring:message code="meals.dateTime"/>:</dt>
            <dd><input type="datetime-local" value="${meal.dateTime}" name="dateTime" required></dd>
        </dl>
        <dl>
            <dt><spring:message code="meals.description"/>:</dt>
            <dd><input type="text" value="${meal.description}" size=40 name="description" required></dd>
        </dl>
        <dl>
            <dt><spring:message code="meals.calories"/>:</dt>
            <dd><input type="number" value="${meal.calories}" name="calories" required></dd>
        </dl>
        <button type="submit"><spring:message code="meal.save"/></button>
        <button onclick="window.history.back()" type="button"><spring:message code="meal.cancel"/></button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
