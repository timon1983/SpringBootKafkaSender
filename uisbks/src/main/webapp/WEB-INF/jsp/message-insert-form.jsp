<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Upload file</title>
</head>
<body>
<table>
    <td>
<form action="create" method="post" enctype="multipart/form-data">
    <input type="text" name="title" placeholder="title"/>
    <br/>
    <br/>
    <input type="text" name="author" placeholder="author" />
    <br/>
    <br/>
    <input type="file" name="file" placeholder="file"/>
    <br/>
    <br/>
    <input type="submit" />
</form>
    </td>
    <td>
        <form method="get" action="/create/files"><button type="submit">Получить список всех файлов</button></form>
    </td>
</table>
</body>
</html>
