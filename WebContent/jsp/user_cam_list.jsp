<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="data_model.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>User Cam Delegation</title>
</head>
<body>

<center>
<h1>User Cam Zuordnung</h1>
	<table border="1">
  		<tbody>
	  		<tr>	
	  			<td></td>
	  		<c:forEach var="user" items="${users}">  						
				<td>${user.username}</td>
				</c:forEach>	
			
			</tr>			
			<c:forEach var="cam" items="${cams}" varStatus="i">
				<tr>
					<td><c:out value="${cam.name}"/></td>	
					<c:forEach var="user" items="${users}" varStatus="j">  						
						<td>
					  <form action="user_cam_delegate_mod" method="post">
					    <input type="hidden" name="userId" value="${user.id}">
					    <input type="hidden" name="camId" value="${cam.id}">
	    			    <button name="camStatus" style="background-color:${userCamArray[j.index][i.index] ? "green" : "red"}" value="${userCamArray[j.index][i.index]}">Toogle
	    			    

	    			    </button>
					  </form>
					</td>
					</c:forEach>	
			
			</tr>	
				<!-- <td>
					  <form action="cam_mod_view" method="post">
	    			    <button name="camId" value="${cam.id}">Ändern</button>
					  </form>
					</td>
					<td>
					  <form action="cam_del" method="post">
	    			    <button name="camId" value="${cam.id}">Löschen</button>
					  
					</td>
				</tr></form> -->
			</c:forEach>	
  		</tbody>
  	</table>
  	<br>
  	<a href="dashboard.jsp">Main menu</a>
</center>
</body>
</html>