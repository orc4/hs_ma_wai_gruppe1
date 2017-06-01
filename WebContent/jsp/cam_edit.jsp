<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit Camera</title>
<style>
   	body {
   		background-color: #42f48f;
   	}
</style>
</head>
<body>
	<center>
	<h1>Edit Camera</h1>
	<form name="form_user_edit" action="cam_mod" method="post">		
		<table border="1">
			<tbody>
				<tr>
					<td>ID:</td>
					<td><input type="text" name="camId" value="${cam.id}" style="width: 75px"></td>		
				</tr>
				<tr>
					<td>URL</td>
					<td><input type="text" name="camUri" value="${cam.url}" style="width: 300px"></td>		
				</tr>
				<tr>
					<td>Name:</td>
					<td><input type="text" name="camName" value="${cam.name}" style="width: 300px"></td>		
				</tr>
				
				<tr>	
					<td colspan="2"><input type="submit" name="btnSave" value="Save"></td>
				</tr>				
			</tbody>
		</table>
	</form>
	<br>
	<a href="dashboard.jsp">Main menu</a>
  </center>

</body>
</html>