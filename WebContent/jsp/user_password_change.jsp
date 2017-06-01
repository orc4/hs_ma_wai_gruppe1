<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="servlets.*" %>
<%@ page import="data_model.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>    
    <title>Edit User</title>
    <style>
   	body {
   		background-color: #42f48f;
   	}
  	</style>
  </head>  
  <body>
  	<center>
  	<h1>Passwort ändern</h1>
	<form name="form_user_edit" action="password_change" method="post">		
		<table border="1">
			<tbody>
				
				<tr>
					<td>Password:</td>
					<td><input type="password" name="password" value=""></td>		
				</tr>
				<tr>
					<td>Neues Passwort:</td>
					<td><input type="password" name="passwordNew1" value=""></td>		
				</tr>
				<tr>
					<td>Neues Passwort wiederholen:</td>
					<td><input type="password" name="passwordNew2" value=""></td>		
				</tr>
				

				<tr>	
					<td colspan="2"><input type="submit" name="btnSave" value="Save"></td>
				</tr>				
			</tbody>
		</table>
	</form>
	<a href="dashboard.jsp">Main menu</a>
	</center>
  </body>
</html>
