<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<!DOCTYPE html>--%>
<html>

<head>
    <%--    <meta charset="UTF-8">--%>
    <title>Modification XML</title>
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
    <text>Список файлов</text>
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
                <th>Time of delete</th>
            </tr>

            <c:forEach var="name" items="${listOfFiles}">
                <tr>
                    <td>${name.id}</td>
                    <td>${name.title}</td>
                    <td>${name.size} byte</td>
                    <td>${name.dateOfCreate}</td>
                    <td>${name.timeOfCreate}</td>
                    <td>${name.author}</td>
                    <td>${name.originFileName}</td>
                    <td>${name.contentType}</td>
                    <td>${name.dateOfDelete}</td>
                    <td>${name.timeOfDelete}</td>
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

</body>
</html>