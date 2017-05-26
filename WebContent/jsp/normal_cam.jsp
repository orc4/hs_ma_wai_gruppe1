<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="data_model.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <title>Show single cam</title>
  <style>
   	body {
   		background-color: #42f48f;
   	}
  </style>
</head>
<body>
<h1>Show single cam</h1>
<center>
	<table border="1">
  		<tbody>
  		<tr>
  			<c:forEach var="day" items="${days}" varStatus="i">
	  			<td>				
	  			<fmt:formatDate var="date_year" value="${day}" pattern="yyyy" /> 
	  			<fmt:formatDate var="date_month" value="${day}" pattern="MM" /> 
	  			<fmt:formatDate var="date_day" value="${day}" pattern="dd" /> 
				 	 
					<a href="view_cam_single?camId=${camId}&cam_date_year=${date_year}
						&cam_date_month=${date_month}&cam_date_day=${date_day}"> 
						<fmt:formatDate type = "both" value = "${day}" pattern="dd-MM-yyyy" />	
					</a>
				</td>	
				<c:if test="${(i.index+1)%10==0 }"></tr><tr></c:if>
			</c:forEach>
			</tr>	
  		</tbody>
  	</table>
  	
  		<table border="1">
  		<tbody>
  		<tr>
  			<c:forEach var="hour" items="${hours}" varStatus="i">
	  			<td>		
	  			<fmt:formatDate var="date_year" value="${currentDate}" pattern="yyyy" /> 
	  			<fmt:formatDate var="date_month" value="${currentDate}" pattern="MM" /> 
	  			<fmt:formatDate var="date_day" value="${currentDate}" pattern="dd" /> 
	  			<a href="view_cam_single?camId=${camId}&cam_date_year=${date_year}&cam_date_month=${date_month}&cam_date_day=${date_day}&cam_date_hour=${hour}">${hour}
	  			</a>		
				</td>	
			</c:forEach>
			</tr>	
  		</tbody>
  	</table>

	<table border="1">
  		<tbody>
	  		<tr>	  						
				<td>Date:   </td>
				<td>Pic:     </td>	
				<td>   </td>	
			</tr>			
			<c:forEach var="pic" items="${pics}" varStatus="i">
				<tr>
					<td>
					   <p><fmt:formatDate type = "both" value = "${pic.date}" pattern="dd.MM.yyyy HH:mm"/></p>
					</td>					
					<td><a href="../getPic?picId=${pics[i.index].id}"><img src="../getPic?picId=${pics[i.index].id}&thumb" style="width:100px;height:100px;"></a>
					</td>
				</tr>
			</c:forEach>	
  		</tbody>
  	</table>


  	<a href="dashboard.jsp">Main menu</a>
</center>
</body>
</html>