<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%
	String result = (String)request.getAttribute("result");
	if(result == null)
		result = "";
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>픽플 - 이메일 인증</title>
	<link rel="icon" type="image/x-icon" href="img/icon/favicon.ico">
	<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap" rel="stylesheet">
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	<link rel="stylesheet" href="css/common.css">
	<link rel="stylesheet" href="css/style.css">
	<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
	<script src="//cdn.jsdelivr.net/npm/sweetalert2@11"></script>
	<script src="https://developers.kakao.com/sdk/js/kakao.js"></script>
	<script src="js/common.js"></script>
</head>
<body>
	<header></header>
	<div class="wrap comp_wrap" id="joinCompWrap">
		<% if(result.equals("verified")) { %>
		<!-- <div class="join_comp comp_box">
			<img src="img/icon/join_comp.svg" alt="" />
			<p>인증이 완료되었습니다.</p>
			<a href="index.jsp"><button class="outline_btn">메인으로</button></a>
			<button class="btn" id="loginBtn">로그인하기</button>
		</div> -->
		<script>
			Swal.fire({
				icon: 'success',
				//title: '접근권한이 없습니다.',
				html: '<strong>인증이 완료되었습니다.</strong>',
				confirmButtonText: '확인',
				confirmButtonColor : '#0ea098',
				timer: 2000
			}).then(function() {
			    window.location = "index.jsp";
			});
		</script>
		<%
		} else { %>
		<!-- <div class="join_comp comp_box">
			<p><b>유효하지 않은 접근입니다</b></p>
		</div> -->
		<script>
			Swal.fire({
				icon: 'error',
				//title: '접근권한이 없습니다.',
				html: '<strong>유효하지 않은 접근입니다.</strong>',
				confirmButtonText: '확인',
				confirmButtonColor : '#0ea098',
				timer: 2000
			}).then(function() {
			    window.location = "index.jsp";
			});
		</script>
		<% } %>
	</div>
	<footer></footer>
	<script>
		$("#loginBtn").click(function() {
			$("#loginPop").show();
		});
	</script>
</body>
</html>