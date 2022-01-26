<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap" rel="stylesheet">
	<link rel="stylesheet" href="../css/admin_common.css">
	<!-- <link rel="preconnect" href="https://fonts.googleapis.com"> -->
	<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
	<title>픽플 관리자</title>
</head>
<body>
	<div class="wrap">
		<nav></nav>
		<div class="main index_wrap">
			<div class="logout_box">
				<p><span id="id"></span> 관리자님, 안녕하세요</p>
				<button id="logoutBtn" class="btn_4">로그아웃</button>
			</div>
			<div class="login_box">
				<h2>관리자 로그인</h2>
				<p>
					<input type="text" name="loginId" id="loginId" placeholder="아이디" />
				</p>
				<p>
					<input type="password" name="loginPw" id="loginPw" placeholder="비밀번호" />
				</p>
				<button id="loginBtn" class="btn_4">로그인</button>
			</div>
		</div>
	</div>

	<script>
		$("nav").load("/Pickpl/admin/admin_nav.html");
		
		if(sessionStorage.getItem("loginId") == null) {
			$(".login_box").show();
			$(".logout_box").hide();
		} else {
			$(".login_box").hide();
			$(".logout_box").show();
			$(".logout_box #id").text(sessionStorage.getItem("loginId"));
		}
		
		$("input").keydown(function(event) {
			if(event.keyCode == 13) {
				login();
			}
		});
		
		$("#logoutBtn").click(function() {
			sessionStorage.clear();
			location.reload();
		});
		
		$("#loginBtn").click(function() {
			login();
		});
		
		function login() {
			loginId = $("#loginId").val();
			loginPw = $("#loginPw").val();
			if(!loginId) {
				alert("아이디를 입력해주세요.");				
			} else if(!loginPw) {
				alert("비밀번호를 입력해주세요.");				
			} else {
				$.ajax({
					url: '/Pickpl/adminController',
					type: 'post',
					data: {
						'command' : 'adminLogin',
						'id': loginId, 
						'pw': loginPw
					},
					dataType: 'json',
					success: function(data) {
						if(data.login == "OK") {
							sessionStorage.setItem("loginId", loginId);
							location.reload();
						} else {
							alert("로그인 실패!");
						}
					}
				});
			}
		}
	</script>
</body>
</html>