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
	<form name="form_cam_view" action="view_cam_single" method="post">
		<input type="hidden" name="camId" value="${camId}">
		<center>
		<table border=1>
		  <tbody>
			<tr>
				<td>Year:</td>
				<td><select name="cam_date_year" style="width:55px" onChange="document.forms['form_cam_view'].submit();">
					<option value=""></option>
					<c:choose>
						<c:when test="${years == null && year != null}">
							<option value="<c:out value="${year}"/>" selected><c:out value="${year}"/></option>
						</c:when>
						<c:otherwise>
							<c:forEach var="yr" items="${years}">
								<c:choose>
									<c:when test="${year == yr}">
		        						<option value="<c:out value="${yr}"/>" selected><c:out value="${yr}"/></option>
		    						</c:when>
		    						<c:otherwise>
		    							<option value="<c:out value="${yr}"/>"><c:out value="${yr}"/></option>
		    						</c:otherwise>
								</c:choose>
							</c:forEach>						
						</c:otherwise>
					</c:choose>
				</select></td>
			</tr>
			<tr>
				<td>Month:</td>
				<td><select name="cam_date_month" style="width:55px" onChange="document.forms['form_cam_view'].submit();">
					<option value=""></option>
					<c:choose>
						<c:when test="${months == null && month != null}">
							<option value="<c:out value="${month}"/>" selected><c:out value="${month}"/></option>
						</c:when>
						<c:otherwise>
							<c:forEach var="mn" items="${months}">
								<c:choose>
									<c:when test="${month == mn}">
		        						<option value="<c:out value="${mn}"/>" selected><c:out value="${mn}"/></option>
		    						</c:when>
		    						<c:otherwise>
		    							<option value="<c:out value="${mn}"/>"><c:out value="${mn}"/></option>
		    						</c:otherwise>
								</c:choose>
							</c:forEach>						
						</c:otherwise>
					</c:choose>
				</select></td>
			</tr>
			<tr>
				<td>Day:</td>
				<td><select name="cam_date_day" style="width:55px" onChange="document.forms['form_cam_view'].submit();">
					<option value=""></option>
					<c:choose>
						<c:when test="${days == null && day != null}">
							<option value="<c:out value="${day}"/>" selected><c:out value="${day}"/></option>
						</c:when>
						<c:otherwise>
							<c:forEach var="dy" items="${days}">
								<c:choose>
									<c:when test="${day == dy}">
		        						<option value="<c:out value="${dy}"/>" selected><c:out value="${dy}"/></option>
		    						</c:when>
		    						<c:otherwise>
		    							<option value="<c:out value="${dy}"/>"><c:out value="${dy}"/></option>
		    						</c:otherwise>
								</c:choose>
							</c:forEach>						
						</c:otherwise>
					</c:choose>
				</select></td>
			</tr>
			<tr>
				<td>Hour:</td>
				<td><select name="cam_date_hour" style="width:55px" onChange="document.forms['form_cam_view'].submit();">
					<option value=""></option>
					<c:choose>
						<c:when test="${hours == null && hour != null}">
							<option value="<c:out value="${hour}"/>" selected><c:out value="${hour}"/></option>
						</c:when>
						<c:otherwise>
							<c:forEach var="hr" items="${hours}">
								<c:choose>
									<c:when test="${hour == hr}">
		        						<option value="<c:out value="${hr}"/>" selected><c:out value="${hr}"/></option>
		    						</c:when>
		    						<c:otherwise>
		    							<option value="<c:out value="${hr}"/>"><c:out value="${hr}"/></option>
		    						</c:otherwise>
								</c:choose>
							</c:forEach>						
						</c:otherwise>
					</c:choose>
				</select></td>
			</tr>
			<tr>
				<td colspan="2"><center><input type="submit" name="btnView" value="view"></center></td>
			</tr>
		  </tbody>
		</table>
		<c:if test="${pics != null}">		
		<br><br>
		<table border="1">
			<tbody>
				<tr>
					<c:forEach var="Pic" items="${pics}" varStatus="i">
						<td><a href="../getPic?picId=${Pic.id}" target="_blank"><img src="../getPic?picId=${Pic.id}&thumb" width="64" height="48"></a></td>
						<c:if test="${i.index > 0 && i.index % 4 == 0}"><c:out value="</tr><tr>" escapeXml="false" /></c:if>
					</c:forEach>					
				</tr>
			</tbody>
		</table>
		</c:if>
		<br>
		<a href="dashboard.jsp">Main menu</a>
		</center>
	</form>
</body>
</html>