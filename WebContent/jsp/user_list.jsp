<%@ page language="java" contentType="text/html" %>
<%@ page import="data_model.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>      
    <title>Benutzerverwaltung</title>     
  </head>
  <body>
  	<table border="1">
  		<tbody>
	  		<tr>
	  			<td>Id</td>
	  			<td>Benutzername</td>				
				<td>Vorname</td>
				<td>Nachname</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>			
			<c:forEach var="user" items="${users}">
				<tr>
					<td><c:out value="${user.id}"/></td>
					<td><c:out value="${user.username}"/></td>
					<td><c:out value="${user.vorname}"/></td>
					<td><c:out value="${user.nachname}"/></td>
					<td>
					  <form action="user_mod_view" method="post">
	    			    <button name="userId" value="${user.id}">Ändern</button>
					  </form>
					</td>
					<td>
					  <form action="user_del" method="post">
	    			    <button name="userId" value="${user.id}">Löschen</button>
					  </form>
					</td>
				</tr>
			</c:forEach>	
  		</tbody>
  	</table>
  	<br>
	
  	<a href="user_add_view">Neuen Benutzer hinzufügen</a>
  	<a href="dashboard.jsp">Main menu</a>
  </body>
</html>
