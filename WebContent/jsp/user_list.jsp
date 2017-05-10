<%@ page language="java" contentType="text/html" %>
<%@ page import="data_model.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>      
    <title>Userverwaltung</title>     
  </head>
  <body>
  	<table border="1">
  		<tbody>
	  		<tr>
	  			<td>Id</td>				
				<td>Vorname</td>
				<td>Nachname</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>			
			<c:forEach var="user" items="${users}">
				<tr>
					<td><c:out value="${user.id}"/></td>					
					<td><c:out value="${user.vorname}"/></td>
					<td><c:out value="${user.nachname}"/></td>
					<td><a href="edit?action=edit&id=${user.id}">Ändern</a></td>
					<td><a href="edit?action=delete&id=${user.id}">Löschen</a></td>
				</tr>
			</c:forEach>	
  		</tbody>
  	</table>
  	<br>
  	<a href="edit?action=add">Neues User hinzufügen</a>
  </body>
</html>
