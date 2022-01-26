<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String result = (String)request.getAttribute("result");
	if(result == null)
		result = "";
// 	String id = (String)request.getAttribute("id");
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
	<script src="js/common.js"></script>
	<script src="js/changePw.js"></script>
</head>
<body>
<header></header>
<div class="wrap change_wrap" id="chageWrap">
	<div class="change_box">
	<% if(result.equals("verified")) { %>
		<h2>비밀번호 변경</h2>
		<div class="input_box">
			<form action="Controller" name="changeForm" method="post" >
 				<input type="hidden" name="command" value="changePw" />
				<input type="hidden" name="changeId" value="${id }" />
				<label for="changePw">변경 비밀번호</label>
				<input type="password" name="changePw" id="changePw" maxlength="12" placeholder="영문자, 숫자, 특수문자 조합으로 이루어진 8~12자리" onkeydown="onKeypress(event);"/>
				<p class="msg"></p>
				
				<label for="changePwConfrim">변경 비밀번호 확인</label>
				<input type="password" name="changePwConfirm" id="changePwConfirm" maxlength="12" placeholder="위와 동일하게 입력" />
				<p class="msg"></p>
				
				<div class="btn_box">
					<button type="button" class="outline_btn" onclick="location.href='index.jsp';">변경 취소</button>
					<button type="button" class="btn" id="changeBtn" onclick="checkPw();">
						<span class="btn_text">변경하기</span>
						<span class="material-icons loader white hide">restart_alt</span>
					</button>
					
				</div>
		   </form>
		</div>
	<% } else { %>
		<script>
			Swal.fire({
			  icon: 'error',
			  //title: '접근권한이 없습니다.',
			  html: '<strong>유효하지 않은 접근입니다.</strong>',
			  confirmButtonText: '확인',
			  confirmButtonColor : '#0ea098'
			}).then(function() {
			    window.locatsion = "index.jsp";
			});
		</script>
	<% } %>
	</div>
</div>
 <footer></footer>
</body>
</html>