<%--
  Created by IntelliJ IDEA.
  User: elena
  Date: 25-Jan-21
  Time: 18:23
  To change this template use File | Settings | File Templates.
--%>
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
