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
			</tr>			
			<c:forEach var="Cam" items="${cams}">
				<tr>
					<td><c:out value="${Cam.getName}"/></td>					
					<td><c:out value="${Cam.getUri}"/></td>
					<td><c:out value="${Cam.getId}"/></td>
					<td><a href="cam_edit?action=edit&id=${Cam.getId}">change</a></td>
					<td><a href="cam_list?action=delete&id=${Cam.getId}">delete</a></td>
				</tr>
			</c:forEach>	
  		</tbody>
  	</table>
  	<br>
  	<a href="cam_add.jsp">Add new camera</a>
  	<br>
  	<a href="menu.jsp">Main menu</a>
</center>
</body>
</html>