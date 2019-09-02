<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<base href="${applicationScope.basePath }">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="file/picupload" method="post" enctype="multipart/form-data">
		<input name="userPhone">
		<input type="file" name="file">
		<input type="submit" value="提交">
	</form>
</body>
</html>