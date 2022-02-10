<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String result = (String)request.getAttribute("result");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>픽플</title>
	<link rel="icon" type="image/x-icon" href="img/icon/favicon.ico">
	<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap" rel="stylesheet">
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	<link rel="stylesheet" href="css/common.css">
	<link rel="stylesheet" href="css/style.css">
	<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
	<script type="text/javascript" src="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script>
	<script src="//cdn.jsdelivr.net/npm/sweetalert2@11"></script>
	<script src="https://developers.kakao.com/sdk/js/kakao.js"></script>
	<script src="js/common.js"></script>
	<script src="js/find.js"></script>
</head>
<body>
	<header></header>
	<div class="wrap comp_wrap" id="findCompWrap">
		<div class="find_comp comp_box">
			<%if(result.equals("NotFound")) { %>
				<img src="img/icon/found_not.svg" alt="" />		
			<%} else {%>
				<img src="img/icon/found.svg" alt="" />
			<%} %>
			<%if(result.equals("idFound")) { %>
				<p>회원님의 아이디는 <span class="id">${findId }</span> 입니다.</p>
				<a href="find.jsp#pw"><button class="btn">비밀번호 찾기</button></a>
			<% } else if(result.equals("pwFound")) { %>
				<p>비밀번호 재설정 링크가 이메일로 전송되었습니다.</p>
				<a href="index.jsp"><button class="outline_btn">메인으로</button></a>
				<button class="btn" onclick="showLoginPop();">로그인하기</button>
			<%} else if(result.equals("NotFound")) {%>
				<p>일치하는 회원정보가 없습니다.</p>
				<a href="find.jsp"><button class="outline_btn">다시찾기</button></a>
				<a href="join.jsp"><button class="btn">회원가입</button></a>
			<%} %>
		</div>
	</div>
	<footer></footer>
</body>
</html>