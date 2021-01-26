<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login page</title>
</head>
<body>
<h1>Login page</h1>
<form action="${pageContext.request.contextPath}/login", method="post">
    Driver login:<input type="text" name="login" pattern="^[a-z]*">
    Driver password :<input type="password" name="pwd">

    <button type="submit">Login</button>
</form>
</body>
</html>
