<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" %>
<%@ page import="servlets.UserLogin" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<style>
     	body {
     		background-color: #42f48f;
     	}
	</style>
</head>

<body>
  	<center><h1>Login</h1></center>
	<form name="form_user_login" action="action_user_login" method="post">
		<center>
		<table border="1">
			<tbody>
				<tr>
					<td>Username:</td>
					<td><input type="text" name="username" value=""></td>		
				</tr>
				<tr>		
					<td>Password:</td>	
					<td><input type="password" name="password" value=""></td>
				</tr>
				<tr>	
					<td colspan="2"><input type="submit" name="btnSave" value="login"></td>
				</tr>
			</tbody>
		</table>
		</center>
	</form>
	<center>
		No account yet?
		<a href="abc" >Register</a>
	</center>
</body>
</html>
