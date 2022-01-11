<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">

<head>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
    <title>Список файлов</title>
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

<header>

</header>

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
                <th>Author</th>
                <th>Origin file name</th>
                <th>File name for S3</th>
                <th>Content type</th>
            </tr>

            <c:forEach var="name" items="${listOfFiles}">
                <tr>
                    <td><c:out value="${name.id}"/></td>
                    <td><c:out value="${name.title}"/></td>
                    <td><c:out value="${name.size} byte"/></td>
                    <td><c:out value="${name.dateOfCreate}"/></td>
                    <td><c:out value="${name.author}"/></td>
                    <td><c:out value="${name.originFileName}"/></td>
                    <td><c:out value="${name.fileNameForS3}"/></td>
                    <td><c:out value="${name.contentType}"/></td>
                    <td><a href="file-delete/<c:out value='${name.id}'/>">Удалить</a></td>
                </tr>
            </c:forEach>
        </table>


        <form method="get" action="/create/open-file-id/${pageContext.request.remoteAddr}">
            <input type="number" value="0" name="id" placeholder="id"/>
            <button type="submit">Открыть файл по id</button>
        </form>

        <form method="get" action="/create/open-file-name/${pageContext.request.remoteAddr}">
            <input type="text" name="name" placeholder="name"/>
            <button type="submit">Открыть файл по имени</button>
        </form>

        <form method="get" action="/downloaded/">
            <input type="number" value="0" name="id" placeholder="id"/>
            <button type="submit">Получить историю загрузки файла</button>
        </form>

        <form method="get" action="/create">
            <button type="submit">Добавить файл</button>
        </form>

        <form method="post" action="/create/send">
            <input type="text"  name="name" />
            <button type="submit">Отправить файл в SBKC</button>
        </form>

        <form method="get" action="/deleted">
            <button type="submit">Получить список удаленных файлов</button>
        </form>
        <br/>
        <br/>
        <form method="get" action="/UI/logout"><button type="submit">Выйти</button></form>

</body>
</html>
