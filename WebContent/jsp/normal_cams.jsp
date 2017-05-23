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
				<td>Id:     </td>	
				<td></td>
				<td></td>	
			</tr>			
			<c:forEach var="cam" items="${cams}" varStatus="i">
				<tr>
					<td><c:out value="${cam.name}"/></td>					
					<td><c:out value="${cam.id}"/></td>
			<td><a href="view_cams_search_view?camId=${cam.id}">durchsuchen</a></td>
					<td>
					<img src="../getPic?picId=${pics[i.index].id}&thumb" style="width:100px;height:100px;">
					</td>
				</tr>
			</c:forEach>	
  		</tbody>
  	</table>
  	<br>
  	<a href="dashboard.jsp">Main menu</a>
</center>
</body>
</html>