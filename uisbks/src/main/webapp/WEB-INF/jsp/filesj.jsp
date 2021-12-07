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
                <th>File name for S3</th>
                <th>Content type</th>
            </tr>

            <c:forEach var="name" items="${listOfFiles}">
                <tr>
                <td>${name.id}</td>
                <td>${name.title}</td>
                <td>${name.size}</td>
                <td>${name.dateOfCreate}</td>
                <td>${name.timeOfCreate}</td>
                <td>${name.author}</td>
                <td>${name.originFileName}</td>
                <td>${name.fileNameForS3}</td>
                <td>${name.contentType}</td>
            </tr>
            </c:forEach>
        </table>

        <form method="post" action="/create/file-delete/">
            <input type="number" name="id" placeholder="id"/>
            <button type="submit">Удалить файл</button></form>

        <form method="post" action="/create/open-file">
            <input type="number" value="id" name="id" placeholder="id"/>
            <button type="submit">Открыть файл</button></form>

        <form method="get" action="/create">
            <button type="submit">Добавить файл</button></form>

</body>
</html>
