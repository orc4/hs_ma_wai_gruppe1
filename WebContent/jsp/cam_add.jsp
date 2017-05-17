<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Camera</title>
<style>
   	body {
   		background-color: #42f48f;
   	}
</style>
</head>
<body>
  	<h1><center>Add Camera</center></h1>
	<form name="form_cam_add" action="action_cam_add" method="post">
		<center><table border="1">
			<tbody>
				<tr>
					<td>Location:</td>
					<td><input type="text" name="location" value=""></td>		
					</tr><tr>		
					<td>URL:</td>	
					<td><input type="text" name="url" value=""></td>
					</tr><tr>	
					<td colspan="2"><input type="submit" name="btnSave" value="add"></td>
				</tr>
			</tbody>
		</table></center>
	</form>
</body>
</html>