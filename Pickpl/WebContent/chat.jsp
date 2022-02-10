<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String loginId = null;
	boolean isLogin = false;
	if((String)session.getAttribute("loginId") != null) {
		isLogin = true;
		loginId = (String)session.getAttribute("loginId");
	}
%>

<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>픽플 - 메시지</title>
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
	<script>var loginId = "<%=loginId %>";</script>
	<script src="js/chat.js"></script>
</head>
<body>
	<header></header>
	<div class="wrap chat_wrap" id="chatWrap">
	<% if(!isLogin) { %>
	
	<% } else { %>
		<div class="top_box">
			<h2>메시지</h2>
			<h3><span id="toId">${chatId }</span>님과의 메시지</h3>
		</div>
		<div class="acct_wrap chat_box">
			<div class="acct_list">
				<!-- <div class="acct_box" data-check="N">
					<div class="img_box">
						<img src="img/icon/profile_default.svg" alt="기본 프로필 사진">
					</div>
					<div class="content">
						<div class="info">
							<p class="msg_id">id1</p>
							<p class="msg_time">09:42</p>
						</div>
						<p class="msg_content">Lorem ipsum dolor sit amet consectetur adipisicing elit.</p>
					</div>
				</div> -->
	
			</div>	<!-- End of acct_list -->
		</div>
		<div class="gradient_box"></div>

		<div class="chat_detail_wrap chat_box">
			<div class="msg_container" id="msgCont">
				<div id="msgWindow" class="msg_wrap">
					
					<!-- <div data-date="2021-03-15" class="msg_list">
						<div data-date="2021-03-15" class="date_box">
							<div class="line"></div>
							<p><span class="year">2021</span>년 <span class="month">3</span>월 <span class="date">15</span>일</p>
						</div>
						
						<div data-msg="in" class="msg_box">
							<img src="img/icon/profile_default.svg" alt="대화상대 프로필 이미지" class="profile_img" />
							<div class="msg">
								<pre data-check="Y">dkdkdkdkdkdkdk dlkfjsdlkfjsdf sldkfjsdkjfsldj dfh dksdf skdf skdjhfasdlfadfaldf sadjfhladjfhlsdf f</pre>
								<pre data-check="Y">iwpe speoirw wperiqwe riwerkfsa dkfas;</pre>
							</div>
							<p class="msg_time">09:00</p>
						</div>
	
						<div data-msg="out" class="msg_box">
							<div class="msg">
								<pre data-check="N">poer wepirouwrq poeiruwe poeiurpwoe ruwpieruwpe wepoiruwpero</pre><br/>
								<pre data-check="N">poer wepirouwrq poeiruwe poeiurpwoe</pre><br/>
								<pre data-check="N">dfk</pre><br/>
							</div>
							<p class="msg_time">09:20</p>
						</div>
					</div> -->

				</div>
			</div>
			<form class="send_box">
				<textarea name="out_msg" id="inputMessage" onkeyup="enterkey()"></textarea>
				<input type="button" value="보내기" class="btn" id="sendBtn" onclick="send();" />
			</form>
		</div>
	<%} %>
	</div>
	<footer></footer>
</body>
</html>