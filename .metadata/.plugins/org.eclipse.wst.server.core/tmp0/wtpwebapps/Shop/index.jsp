<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	
	<title>Login Page</title>
	
	<link rel="stylesheet" type="text/css" href="style.css" />
	
</head>

<body>

	<form id="login-form" method="post" action="http://localhost:8080/Shop/Login">
		<fieldset>
		
			<legend>Log in</legend>
			
			<label for="login">Login</label>
			<input type="text" id="login" name="login"/>
			<div class="clear"></div>
			
			<label for="password">Password</label>
			<input type="password" id="password" name="password"/>
			<div class="clear"></div>
			
			<input type="hidden" name ="action" value = "login" />
			
			<br />
			
			<input type="submit" style="margin: -20px 0 0 287px;" class="button" name="commit" value="Log in"/>	
		</fieldset>
	</form>
	
	<form id="reg-form" method="post" action="http://localhost:8080/Shop/Registration">
			<input type="submit" style="margin: 110px 0 0 0px;" class="button" name="commit" value="Register"/>	
	</form>
</body>

</html>