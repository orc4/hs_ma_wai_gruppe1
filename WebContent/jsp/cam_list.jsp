<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="data_model.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>List of Cameras</title>
</head>
<body>
<center>
	<table border="1">
  		<tbody>
	  		<tr>	  						
				<td>Name:   </td>
				<td>Url:    </td>
				<td>Id:     </td>	
				<td>   </td>	
				<td>    </td>	
			</tr>			
			<c:forEach var="cam" items="${cams}">
				<tr>
					<td><c:out value="${cam.name}"/></td>					
					<td><c:out value="${cam.uri}"/></td>
					<td><c:out value="${cam.id}"/></td>
					<td>
					  <form action="cam_mod_view" method="post">
	    			    <button name="camId" value="${cam.id}">Ändern</button>
					  </form>
					</td>
					<td>
					  <form action="cam_del" method="post">
	    			    <button name="camId" value="${cam.id}">Löschen</button>
					  </form>
					</td>
				</tr>
			</c:forEach>	
  		</tbody>
  	</table>
  	<br>
  	<a href="cam_add_view">Add new camera</a>
  	<br>
  	<a href="menu.jsp">Main menu</a>
</center>
</body>
</html>