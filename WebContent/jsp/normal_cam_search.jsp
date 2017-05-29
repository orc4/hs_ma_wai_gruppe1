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
  <script>
  function fill() {
	  for(var i = 0;i < document.getElementById("cam_time_from").length;i++){
	      if(document.getElementById("cam_time_from").options[i].value == "${cam_time_from}"){
	          document.getElementById("cam_time_from").selectedIndex = i;
	      }
	  }
	  for(var i = 0;i < document.getElementById("cam_time_to").length;i++){
	      if(document.getElementById("cam_time_to").options[i].value == "${cam_time_to}"){
	          document.getElementById("cam_time_to").selectedIndex = i;
	      }
	  }
  }
  </script>
</head>
<body onload="fill()">
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
					<td><input type="text" name="cam_date_from" id="cam_date_from" value="${cam_date_from}"></td>
					<td><select name="cam_time_from" id="cam_time_from">
						<option value="00">00</option>
						<option value="01">01</option>
						<option value="02">02</option>
						<option value="03">03</option>
						<option value="04">04</option>
						<option value="05">05</option>
						<option value="06">06</option>
						<option value="07">07</option>
						<option value="08">08</option>
						<option value="09">09</option>
						<option value="10">10</option>
						<option value="11">11</option>
						<option value="12">12</option>
						<option value="13">13</option>
						<option value="14">14</option>
						<option value="15">15</option>
						<option value="16">16</option>
						<option value="17">17</option>
						<option value="18">18</option>
						<option value="19">19</option>
						<option value="20">20</option>
						<option value="21">21</option>
						<option value="22">22</option>
						<option value="23">23</option>
					</select></td>		
				</tr>
				<tr>
					<td>bis:</td>
					<td><input type="text" name="cam_date_to" id="cam_date_to" value="${cam_date_to}"></td>
					<td><select name="cam_time_to" id="cam_time_to">
						<option value="00">00</option>
						<option value="01">01</option>
						<option value="02">02</option>
						<option value="03">03</option>
						<option value="04">04</option>
						<option value="05">05</option>
						<option value="06">06</option>
						<option value="07">07</option>
						<option value="08">08</option>
						<option value="09">09</option>
						<option value="10">10</option>
						<option value="11">11</option>
						<option value="12">12</option>
						<option value="13">13</option>
						<option value="14">14</option>
						<option value="15">15</option>
						<option value="16">16</option>
						<option value="17">17</option>
						<option value="18">18</option>
						<option value="19">19</option>
						<option value="20">20</option>
						<option value="21">21</option>
						<option value="22">22</option>
						<option value="23">23</option>
					</select></td>		
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
			</tr>			
			<c:forEach var="pic" items="${pics}" varStatus="i">
				<tr>
					<td>
					   <p><fmt:formatDate type = "both" value = "${pic.date}" pattern="dd.MM.yyyy HH:mm" /></p>
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