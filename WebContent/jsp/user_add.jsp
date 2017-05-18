<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>     
    <title>Add User</title>
     <style>
     	body{
     		background-color: #42f48f;
     	}
      </style>
  </head>
  <body>
  	<h1><center>Add User</center></h1>
	<form name="form_user_add" action="action_user_add" method="post">
		<center>
		<table border="1">
			<tbody>
				<tr>
					<td>Username:</td>
					<td><input type="text" name="username" value=""></td>		
				</tr>
				<tr>
					<td>Password:</td>
					<td><input type="text" name="password" value=""></td>		
				</tr>
				<tr>	
					<td>First Name:</td>
					<td><input type="text" name="firstname" value=""></td>		
				</tr>
				<tr>		
					<td>Last Name:</td>	
					<td><input type="text" name="lastname" value=""></td>
				</tr>
				<tr>	
					<td colspan="2"><input type="submit" name="btnSave" value="add"></td>
				</tr>
			</tbody>
		</table>
		</center>
	</form>
  </body>
</html>
