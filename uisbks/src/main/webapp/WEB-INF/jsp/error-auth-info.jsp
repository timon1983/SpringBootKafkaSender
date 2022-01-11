<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>com.example.sbks</title>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
</head>

<body>
<c:forEach var="message" items="${message}">
    <c:out value="${message}"/>
</c:forEach>


<br/>
<br/>
<form method="get" action="/UI/login"><button type="submit">Войти</button></form>
</body>
</html>