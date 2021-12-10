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
    <text>История скачиваний файла</text>
</td>
<tr>
    <td>
        <table class="brd">
            <tr>
                <th>ID</th>
                <th>Date of create</th>
                <th>IP User</th>
            </tr>

            <c:forEach var="name" items="${downloadList}">
                <tr>
                    <td>${name.id}</td>
                    <td>${name.dateOfDownload}</td>
                    <td>${name.ipUser}</td>
                </tr>
            </c:forEach>
        </table>

        <form method="get" action="/create/files">
            <button type="submit">Получить список всех загруженных файлов</button>
        </form>

        <form method="get" action="/create">
            <button type="submit">Добавить файл</button>
        </form>

</body>
</html>