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
  	<h1>Change User</h1>
	<form name="form_user_edit" action="user_mod" method="post">		
		<table border="1">
			<tbody>
				<tr>
					<td>ID:</td>
					<td><input type="text" name="userId" value="${user.id}"></td>		
				</tr>
				<tr>
					<td>Username:</td>
					<td><input type="text" name="username" value="${user.username}"></td>		
				</tr>
				<tr>
					<td>Password:</td>
					<td><input type="text" name="password" value="${user.password}"></td>		
				</tr>
				<tr>		
					<td>Salt:</td>	
					<td><input type="text" name="salt" value="${user.salt}"></td>
				</tr>	
				<tr>	
					<td>First Name:</td>
					<td><input type="text" name="firstname" value="${user.vorname}"></td>		
				</tr>
				<tr>		
					<td>Last Name:</td>	
					<td><input type="text" name="lastname" value="${user.nachname}"></td>
				</tr>		
				<tr>		
					<td>can_mod_cam:</td>	
					<td><input type="text" name="user_can_mod_cam" value="${user.can_mod_cam}"></td>
				</tr>
				<tr>		
					<td>can_mod_user:</td>	
					<td><input type="text" name="user_can_mod_user" value="${user.can_mod_user}"></td>
				</tr>
				<tr>		
					<td>can_see_all_cam:</td>	
					<td><input type="text" name="user_can_see_all_cam" value="${user.can_see_all_cam}"></td>
				</tr>
				<tr>		
					<td>can_delegate_cam:</td>	
					<td><input type="text" name="user_can_delegate_cam" value="${user.can_delegate_cam}"></td>
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
