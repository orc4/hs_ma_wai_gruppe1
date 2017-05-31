<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<body>

<h1>Main Menu</h1>

<h2>Session Infos</h2>
Userid: ${user.id}<br>
Username: ${user.username}<br>
Name: ${user.nachname}, ${user.vorname} <br>

<h2>User Menu</h2>
<p>Das hier darf jeder!</p>
<a href="${pageContext.request.contextPath}/login">login</a><br> 
<a href="${pageContext.request.contextPath}/manager/logout">logout</a><br> 
<a href="${pageContext.request.contextPath}/manager/password_change_view">Passwort ändern</a><br> 
<a href="${pageContext.request.contextPath}/manager/view_cams">Eigene Cams anschauen</a> <br>


<h2>Admin Menu</h2>
<p>Besondere Rechte!</p>




<c:if test = "${ user.can_mod_user }">
       <a href="${pageContext.request.contextPath}/manager/user_list">User verwalten</a> <br>
</c:if>
<c:if test = "${user.can_mod_cam}">
       <a href="${pageContext.request.contextPath}/manager/cam_list">Cams verwalten</a> <br>
</c:if>

<c:if test = "${user.can_delegate_cam}">
       <a href="${pageContext.request.contextPath}/manager/user_cam_delegate_list">User-Cam Delegation verwalten</a><br>
</c:if>


</body>
</html>