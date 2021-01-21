<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Driver</title>
</head>
<body>
<h1>Please provide information about driver</h1>
<form method="post" action="${pageContext.request.contextPath}/drivers/create">
    Enter driver's name: <input type="text" name="name">
    Enter license number: <input type="number" name="licenceNumber">
    <button type="submit">Create</button>
</form>
</body>
</html>
