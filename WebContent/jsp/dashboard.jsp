<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
<a href="${pageContext.request.contextPath}/manager/password_change_view">Passwort �ndern</a><br> 
<a href="${pageContext.request.contextPath}/manager/view_cams">Eigene Cams anschauen</a> <br>


<h2>Admin Menu</h2>
<p>Das hier darf normal nur der Admin!</p>
<a href="${pageContext.request.contextPath}/manager/user_list">User verwalten</a> <br>
<a href="${pageContext.request.contextPath}/manager/cam_list">Cams verwalten</a> <br>
<a href="${pageContext.request.contextPath}/manager/user_cam_delegate_list">User-Cam Delegation verwalten</a><br> 

</body>
</html>