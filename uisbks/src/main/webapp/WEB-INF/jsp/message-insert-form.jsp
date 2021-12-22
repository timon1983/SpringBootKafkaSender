<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en" xmlns:th="http://www.w3.org/1999/xhtml">

<head>
    <meta charset="UTF-8">
    <title>Upload file</title>
</head>
<body>
<table>
    <td>
        <form action="#" th:action="@{/create}" th:object="${dtoRequestMessage}" method="post"
              enctype="multipart/form-data">
            title:<input type="text" th:field="*{title}" name="title" placeholder="title"/>
            <br/>
            <br/>
            author:<input type="text" th:field="*{author}" name="author" placeholder="author"/>
            <br/>
            <br/>
            file:<input type="file" th:field="*{file}" name="file" placeholder="file"/>
            <br/>
            <br/>
            <input type="submit"/>
        </form>
    </td>
    <td>
        <form method="get" action="/create/files">
            <button type="submit">Получить список всех файлов</button>
        </form>
        <form method="get" action="/deleted">
            <button type="submit">Получить список удаленных файлов</button>
        </form>
    </td>
</table>
</body>
</html>

