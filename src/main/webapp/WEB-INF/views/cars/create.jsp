<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create car</title>
</head>
<body>
<h1>Please provide information about car</h1>
<form method="post" action="${pageContext.request.contextPath}/cars/create">
    Enter car model: <input type="text" name="model">
    Enter manufacturer ID: <input type="number" name="manufacturer">
    <button type="submit">Create</button>
</form>
</body>
</html>