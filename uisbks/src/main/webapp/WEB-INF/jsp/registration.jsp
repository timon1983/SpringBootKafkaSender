<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en" xmlns:th="http://www.w3.org/1999/xhtml">

<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
</head>
<body>

<c:forEach var="error" items="${error}">
    <c:out value="${error}"/>
</c:forEach>
<table>
    <td>
        <form action="#" th:action="@{/UI/registration}" th:object="${userRegistrationDto}" method="post">
            name:<input type="text" th:field="*{name}" name="name" placeholder="name" required/>
            <br/>
            <br/>
            email:<input type="text" th:field="*{email}" name="email"
                         pattern="^([a-z0-9_-]+\.)*[a-z0-9_-]+@[a-z0-9_-]+(\.[a-z0-9_-]+)*\.[a-z]{2,6}$"
                         placeholder="email" title="Неккоректно введен email" required/>
            <br/>
            <br/>
            password:
            <input type="text" th:field="*{password}" name="password" pattern="^[A-Za-z]{8,}$" placeholder="password"
                   title="Пароль должен содержать не меннее 8 латинских символов" required/>
            <br/>
            <br/>
            password second time:
            <input type="text" th:field="*{password}" name="password" pattern="^[A-Za-z]{8,}$" placeholder="password"
                   title="Пароль должен содержать не меннее 8 латинских символов" required/>
            <br/>
            <br/>
            <input type="submit" value="Зарегистрироваться"/>
        </form>
        <br/>
        <form method="get" action="/UI/login"><button type="submit">Войти</button></form>
    </td>
</table>
</body>
</html>