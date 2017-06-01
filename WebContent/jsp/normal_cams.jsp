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
				<td></td>	
			</tr>			
			<c:forEach var="cam" items="${cams}" varStatus="i">
				<tr>
					<td><c:out value="${cam.name}"/></td>					
					<td><c:out value="${cam.id}"/></td>
					<td><a href="view_cams_search_view?camId=${cam.id}">durchsuchen</a></td>
					<td><a href="view_cam_single?camId=${cam.id}">st�bern</a></td>
					<td>
					
					<c:choose>
	  					<c:when test="${pics[i.index]==null}">
	  						<img src="http://localhost:8080/wai_gruppe1/not_available.jpg" style="width:100px;height:100px;">
	  					</c:when>
	  					<c:otherwise>
	  						<a href="../getPic?picId=${pics[i.index].id}"><img src="../getPic?picId=${pics[i.index].id}&thumb" style="width:100px;height:100px;"></a>
	  					</c:otherwise>
					</c:choose>
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