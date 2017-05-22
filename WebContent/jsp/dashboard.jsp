<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<body>

<h1>Poor Man Nav</h1>

<h2>Session infos</h2>
<p>Userid: ${user.id}</p>
<p>Username: ${user.username}</p>
<p>Name: ${user.nachname}, ${user.vorname} </p>


<h2>User zeugs</h2>
<p>Das hier darf jeder!</p>
<p><a href="http://localhost:8080/wai_gruppe1/login">login</a></p> 
<p><a href="http://localhost:8080/wai_gruppe1/manager/logout">logout</a></p> 
<p><a href="http://localhost:8080/wai_gruppe1/manager/password_change_view">Passwort aendern</a></p> 
<p><a href="http://localhost:8080/wai_gruppe1/manager?action=handle_view_cams">Eigene Cams Anschauen</a> </p>
<p><a href="http://localhost:8080/wai_gruppe1/manager?action=handle_view_cams_search">Cams durchsuchen</a> </p>
<p><a href="http://localhost:8080/wai_gruppe1/manager?action=handle_view_cam_single">Eine Cam anzeigen</a> </p>


<h2>Admin zeugs</h2>
<p>Das hier darf normal nur der Admin!</p>
<p><a href="http://localhost:8080/wai_gruppe1/manager/user_list">User Verwalten</a> </p>
<p><a href="http://localhost:8080/wai_gruppe1/manager/cam_list">Cams Verwalten</a> </p>
<p><a href="http://localhost:8080/wai_gruppe1/manager/user_cam_delegate_list">User-Cam delegation Verwalten</a></p> 

</body>
</html>