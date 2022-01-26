<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>픽플 - 회원가입 완료</title>
	<link rel="icon" type="image/x-icon" href="img/icon/favicon.ico">
	<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap" rel="stylesheet">
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	<link rel="stylesheet" href="css/common.css">
	<link rel="stylesheet" href="css/style.css">
	<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
	<script type="text/javascript" src="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script>
	<script src="//cdn.jsdelivr.net/npm/sweetalert2@11"></script>
	<script src="js/common.js"></script>
</head>
<body>
	<header></header>
	<div class="wrap comp_wrap" id="joinCompWrap">
		<div class="join_comp comp_box">
			<img src="img/icon/join_comp.svg" alt="" />
			<p>입력하신 이메일로 인증 메일이 전송되었습니다.<br/>
				이메일 인증 후 로그인이 가능합니다.
			</p>
			<a href="index.jsp"><button class="outline_btn">메인으로</button></a>
			<button class="btn" id="loginBtn" onclick="loginPop();">로그인하기</button>
		</div>
	</div>
	<footer></footer>
	<script>
	</script>
</body>
</html>

