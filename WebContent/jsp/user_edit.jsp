<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="servlets.*" %>
<%@ page import="data_model.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>    
    <title>Edit User</title>
  </head>  
  <body>
	<form name="form_user_edit" action="action_user_edit" method="post">		
		<table border="1">
			<tbody>
				<tr>
					<td>Username:</td>
					<td><input type="text" name="username" value="${user.getUsername}"></td>		
				</tr>
				<tr>
					<td>Password:</td>
					<td><input type="text" name="password" value="${user.getPassword}"></td>		
				</tr>
				<tr>	
					<td>First Name:</td>
					<td><input type="text" name="firstname" value="${user.getVorname}"></td>		
				</tr>
				<tr>		
					<td>Last Name:</td>	
					<td><input type="text" name="lastname" value="${user.getNachname}"></td>
				</tr>			
				<tr>	
					<td colspan="2"><input type="submit" name="btnSave" value="Save"></td>
				</tr>				
			</tbody>
		</table>
		<input type="hidden" name="id" value="${user.getId}">
	</form>
  </body>
</html>
