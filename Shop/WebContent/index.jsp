<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
  pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <title>Bookshop</title>
</head>
<body>
  <h2>Bookshop</h2>
  <form method="POST" action="http://localhost:8080/Shop/Login">
    Login:<br /><br />
    <input type="text" name="login" />
    <input type="text" name="password" />
    <input type="hidden" name ="action" value = "login" />
    <input type="submit" value="Login" />
  </form>
  <form method="POST" action="http://localhost:8080/Shop/Login">
    Register:<br /><br />
    <input type="text" name="login" />
    <input type="text" name="password" />
    <input type="hidden" name ="action" value = "register" />
    <input type="submit" value="Register" />
  </form>
</body>
</html>