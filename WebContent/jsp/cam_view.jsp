<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="data_model.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View Cam</title>
<style>
   	body {
   		background-color: #42f48f;
   	}
</style>
</head>
<body>
  	<center><h1>View Camera</h1></center>
	<form name="form_cam_view" action="../cam_list?action=handle_view_cam_single" method="post">
		<input type="hidden" name="cam_id" value="1">
		<center>
		<table border=1>
		  <tbody>
			<tr>
				<td>Year:</td>
				<td><select name="cam_date_year">
					<option value="2017">2017</option>
					<c:forEach var="Year" items="${years}">
						<option value="<c:out value="${Year}"/>"><c:out value="${Year}"/></option>
					</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td>Month:</td>
				<td><select name="cam_date_month">
					<option value=""></option>
					<c:forEach var="Month" items="${months}">
						<option value="<c:out value="${Month}"/>"><c:out value="${Month}"/></option>
					</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td>Day:</td>
				<td><select name="cam_date_day">
					<option value=""></option>
					<c:forEach var="Day" items="${days}">
						<option value="<c:out value="${Day}"/>"><c:out value="${Day}"/></option>
					</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td>Hour:</td>
				<td><select name="cam_date_hour">
					<option value=""></option>
					<c:forEach var="Hour" items="${hours}">
						<option value="<c:out value="${Hour}"/>"><c:out value="${Hour}"/></option>
					</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" name="btnView" value="view"></td>
			</tr>
		  </tbody>
		</table>
		<table border="1">
			<tbody>
				<c:forEach var="Pic" items="${pics}">
					<tr>
						<td><img src="<c:out value="${Pic.path}"/>"></td>
					</tr>
				</c:forEach>					
			</tbody>
		</table>
		</center>
	</form>
</body>
</html>