<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create manufacturer</title>
</head>
<body>
<h1>Please provide information about manufacturer</h1>
<form method="post" action="${pageContext.request.contextPath}/manufacturers/create">
    Enter manufacturer's name: <input type="text" name="name">
    Enter manufacturer's country: <input type="text" name="country">
    <button type="submit">Create</button>
</form>
</body>
</html>
