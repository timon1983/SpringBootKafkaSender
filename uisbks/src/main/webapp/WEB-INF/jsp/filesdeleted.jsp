<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<!DOCTYPE html>--%>
<html>

<head>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
    <title>Корзина</title>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
</head>

<body>
<style type="text/css">
    table {
        border-collapse: collapse;
    }

    table th,
    table td {
        padding: 0 3px;
    }

    table.brd th,
    table.brd td {
        border: 1px solid #000;
    }
</style>

<td>
    <text>Корзина</text>
</td>
<tr>
    <td>
        <table class="brd">
            <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Size</th>
                <th>Date of create</th>
                <th>Time of create</th>
                <th>Author</th>
                <th>Origin file name</th>
                <th>Content type</th>
                <th>Date of delete</th>
            </tr>

            <c:forEach var="name" items="${listOfFiles}">
                <tr>
                    <td><c:out value="${name.id}"/></td>
                    <td><c:out value="${name.title}"/></td>
                    <td><c:out value="${name.size} byte"/></td>
                    <td><c:out value="${name.dateOfCreate}"/></td>
                    <td><c:out value="${name.timeOfCreate}"/></td>
                    <td><c:out value="${name.author}"/></td>
                    <td><c:out value="${name.originFileName}"/></td>
                    <td><c:out value="${name.contentType}"/></td>
                    <td><c:out value="${name.dateOfDelete}"/></td>
                    <td><a href="deleted/full/<c:out value='${name.id}'/>">Полное удаление</a></td>
                    <td><a href="deleted/restore/<c:out value='${name.id}'/>">Восстановить файл</a></td>
                </tr>
            </c:forEach>
        </table>
        <form method="get" action="/create/files">
            <button type="submit">Получить список всех загруженных файлов</button>
        </form>
        <form method="get" action="/deleted/clean">
            <button type="submit">Очистить список удаленных файлов</button>
        </form>

        <form method="get" action="/create">
            <button type="submit">Добавить файл</button>
        </form>
        <br/>
        <br/>
        <form method="get" action="/UI/logout"><button type="submit">Выйти</button></form>

</body>
</html>
