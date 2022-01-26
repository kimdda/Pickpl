<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="Controller" method="post" name="form">
		<input type="hidden" name="command" value="myPage" /> 
	</form>
	<script>
		form.submit();
	</script>
</body>
</html>