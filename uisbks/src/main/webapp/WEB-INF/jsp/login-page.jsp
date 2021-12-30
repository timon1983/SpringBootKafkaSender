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
        <form action="#" th:action="@{/login}" th:object="${authDto}" method="post">
            email:<input type="text" th:field="*{email}" name="email" placeholder="email"/>
            <br/>
            <br/>
            password:<input type="text" th:field="*{password}" name="password" placeholder="password"/>
            <br/>
            <br/>
            <input type="submit"/>
        </form>
    </td>
</table>
</body>
</html>
