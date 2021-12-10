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
<c:forEach var="error" items="${error}">
    <c:out value="${error}"/>
</c:forEach>


<form method="get" action="/create/files"><button type="submit">Back</button></form>
</body>
</html>