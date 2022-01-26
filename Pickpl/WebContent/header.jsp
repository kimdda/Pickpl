<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	boolean isLogin = false;
	String loginId;
	
	try {
		loginId = (String)session.getAttribute("loginId");		
	} catch(NullPointerException e) {
		e.printStackTrace();
		loginId = null;
	}
	
	if(loginId == null || loginId == "") {
		isLogin = false;
	} else {
		isLogin = true;
	}
%>

<div class="header_box">
   <h1><a href="index.jsp"><img src="img/icon/logo.svg" alt="픽플 로고" class="logo"></a></h1>
   <nav>
		<img data-link="list.jsp" src="img/icon/search.svg" alt="" class="search_icon">
		<img src="img/icon/account.svg" alt="" class="menu_icon">
		<% if(isLogin) { %>
		<ul id="loginMenu" class="gnb">
			<li data-link="mypage">마이페이지</li>
			<li data-link="diary">마이 다이어리</li>
			<li data-link="pickF.jsp">마이 픽</li>
			<li data-link="diary_write.jsp">다이어리 작성</li>
			<li data-link="chat.jsp">메시지</li>
			<li data-link="#logout">로그아웃</li>
		</ul>
		<% } else { %>
		<ul id="logoutMenu" class="gnb">
			<li data-link="join.jsp">회원가입</li>
			<li data-link="#loginPop">로그인</li>
		</ul>
		<% } %>
   </nav>
</div>

<!-- 로그인 팝업 -->
<div class="popup login_pop" id="loginPop">
	<div class="background" onclick="closePop(event);"></div>
	<div class="popupBox">
	   <button class="material-icons close_pop_btn" onclick="closePop(event);">clear</button>
	   <div class="popHead">
		  <p>로그인</p>
	   </div>
	   <div class="popBody">
			<!-- <form action="" name="loginForm"> -->
			<div class="login_form">
				<input type='hidden' name="current"/>
				<p class="alert_txt"></p>
				<label for="loginId">아이디</label>
				<input type="text" name="loginId" id="loginId" />
				<p class="alert_txt"></p>
	
				<label for="loginPw">비밀번호</label>
				<input type="password" name="loginPw" id="loginPw" />
				<p class="alert_txt"></p>
				<div>
					<a href="join.jsp" class="find_info">아직 회원이 아니신가요? 픽플 가입하기</a>
				</div>
				
				<div class="login_fail hide">
					<p class=""></p>
				</div>
	
				<div class="btn_box">
<!-- 					<input type="submit" value="로그인" class="btn login_btn" id="loginBtn" onclick="login();" /> -->
					<button class="btn login_btn" id="loginBtn" onclick="login();">로그인</button>
					<span class="material-icons loader hide">restart_alt</span>
				</div>
				
				<div>
					<a href="find.jsp" class="find_info">아이디/비밀번호가 기억나지 않으신가요?</a>
				</div>
			</div>
			<!-- </form> -->
	
			<hr class="divider">
	
			<div class="sns_login_box">
				<button class="sns_login_btn google_login"></button>
				<button class="sns_login_btn naver_login"></button>
				<button class="sns_login_btn kakao_login"></button>
			</div>
	   </div>
	</div>
</div>

 <!-- 픽 팝업 -->
 <% if(isLogin) { %>
 <div class="popup" id="pickPop">
	<div class="background"></div>
	<div class="popupBox">
		<button class="material-icons close_pop_btn" onclick="closePop(event);">clear</button>
		<div class="popHead">
			<p>픽 목록</p>
		</div>
		<div class="popBody">
			<p class="alert_txt error">픽을 저장할 폴더를 선택해주세요.</p>
			<div class="folder_list">
<!-- 				<p class="folder">기본폴더</p> -->
			</div>
			<div class="new_folder_box">
				<p class="folder new_folder active"><input type="text" id="newFolderName" /></p>
				<p class="alert_txt">동일한 폴더 이름이 존재합니다.</p>
				<button class="sec_outline_btn" id="addCancelBtn">취소</button>
				<button class="sec_btn" id="addConfirmBtn" onclick="addPickFolder();">확인</button>
			</div>
			<div class="add_folder">
				<button class="sec_outline_btn" id="addFolderBtn">폴더추가</button>
			</div>
		</div>
		<div class="popFoot">
			<button class="outline_btn cancel_btn" onclick="closePop(event);">취소</button>
			<button class="btn confirm_btn" onclick="confirmPick();">저장</button>
		</div>
	</div>
</div>
<% } %>

<script>
	// 링크 이동
	$("[data-link]").click(function() {
		link =  $(this).attr("data-link");
		if(link == "#loginPop") {
			loginPop();
		} else if (link == "#logout"){
			location.href = "logout.jsp";
		} else if (link == "diary"){
			post_to_url("Controller", {"command":"diaryPage", 'diaryId': `<%=loginId %>`});
		} else if(link == "mypage") {
			post_to_url("Controller", {"command":"myPage"});			
		} else {
			location.href = link;
		}
	});

	
</script>