<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>     
    <title>Bibliothek</title>
  </head>
  <body>
	<form name="edit" action="edit" method="post">
		<table border="1">
			<tbody>
				<tr>
					<td>Autor:</td>
					<td><input type="text" name="author" value=""></td>		
					</tr><tr>		
					<td>Titel:</td>	
					<td><input type="text" name="title" value=""></td>
					</tr><tr>	
					<td colspan="2"><input type="submit" name="btnSave" value="Save"></td>
				</tr>
			</tbody>
		</table>
	</form>
  </body>
</html>
