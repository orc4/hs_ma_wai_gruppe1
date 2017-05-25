<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="data_model.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <title>List of Cameras</title>
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
    <script>
  $( function() {
    $( "#cam_date_from" ).datepicker({ dateFormat: 'yy-mm-dd' }).val();;
  } );
  </script>
      <script>
  $( function() {
    $( "#cam_date_to" ).datepicker({ dateFormat: 'yy-mm-dd' }).val();;
  } );
  </script>
</head>
<body>
<center>

<form name="form_cam_search" action="view_cams_search" method="post">		
		<table border="1">
			<tbody>
				<tr>
					<td>camId:</td>
					<td><input type="text" name="camId" value="${camId}" readonly></td>		
				</tr>
				<tr>
					<td>von</td>
					<td><input type="text" name="cam_date_from" id="cam_date_from"></td>		
				</tr>
				<tr>
					<td>bis:</td>
					<td><input type="text" name="cam_date_to" id="cam_date_to"></td>		
				</tr>
				
				
				<tr>	
					<td colspan="2"><input type="submit" name="btnSave" value="Suchen"></td>
				</tr>				
			</tbody>
		</table>
	</form>

	<br>
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
					   <p><fmt:formatDate type = "both" value = "${pic.date}" /></p>
					</td>					
					<td><a href="../getPic?picId=${pics[i.index].id}"><img src="../getPic?picId=${pics[i.index].id}&thumb" style="width:100px;height:100px;"></a>
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