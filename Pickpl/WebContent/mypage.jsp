<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.time.LocalDate" %>
<%@ page import="com.pickpl.dto.MemberDto" %>
<% 
	boolean isLogin = false;
	String loginId = (String)session.getAttribute("loginId");
	if(loginId != null) isLogin = true;
	
	MemberDto info = (MemberDto)request.getAttribute("myInfo");
	
// 	String mdfy = "";
// 	if(session.getAttribute("mdfyMyinfo") != null) {
// 		mdfy = (String)session.getAttribute("mdfyMyinfo");
// 		session.removeAttribute("mdfyMyinfo");
// 	}
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>마이페이지</title>
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
	<script src="js/mypage.js"></script>
</head>
<body>
<header></header>
  <div class="wrap mypage_wrap" id="myPageWrap">
  
	<%
	if(session.getAttribute("mdfyMyinfo") != null) {
		String mdfy = (String)session.getAttribute("mdfyMyinfo");
		if(mdfy.equals("success")) {  %>
	<script>
		Swal.fire({
			title: '수정되었습니다.',
			icon: 'success',
			confirmButtonColor: '#0ea098',
			confirmButtonText: '확인',
			timer: 2000,
		});
	</script>
	<% } else if(mdfy.equals("fail")) { %>
	<script>
		Swal.fire({
			title: '수정 저장에 실패하였습니다..',
			icon: 'error',
			confirmButtonColor: '#0ea098',
			confirmButtonText: '확인',
			timer: 2000,
		});
	</script>
	<% }
		session.removeAttribute("mdfyMyinfo");
	}	
	%>
	<!-- 프로필 영역 -->
	<form action="Controller?command=mdfyMyInfo" id="form" name="frm" method="post" enctype="multipart/form-data">
<!-- 	<form action="Controller" id="form" name="frm" method="post"> -->
<!-- 		<input type="hidden" name="command" value="mdfyMyInfo" /> -->
		<div class="profile" id="profileArea">
			<div class="view_info">
				<img class="profile_img" id="viewPro" src="img/profile/${myInfo.getProfile() }" alt="">
			</div>
			
			<div class="mdfy_info">
				<img class="profile_img" id="mdfyPro" src="img/profile/${myInfo.getProfile() }" alt="">
				<div class="profileBtn" id="">
					<label class="btn mdfy_img_btn">
						<span class="material-icons">add</span><span>사진변경하기</span>
						<input type="file" name="profile_img" id="image" accept="image/*" onchange="setThumbnail(event);"/> 
					</label>
				</div>
			</div>	
		</div>
	

		<div class="member_info" id="">
			<p class="memberInfoStyleFont"><b>아이디</b></p>
			<p id="id">${myInfo.getId() }</p>
			<button type="button" class="sec_btn mdfy_info" id="outBtn">탈퇴하기</button>
	
			<p class="memberInfoStyleFont"><b>이메일</b></p>
			<p id="email">${myInfo.getEmail() }</p>
	
			<p class="memberInfoStyleFont"><b>비밀번호</b></p>
			<button type="button" class="sec_btn pw_view_info" id="pwMdfyBtn">변경하기</button>
			
			<div class="new_pw pw_mdfy_info">
				<p class="memberInfoStyleFont"><b>새 비밀번호</b></p>
				<input type="password" id="pw" name="pw" name="pw1" maxlength="12" placeholder="영문자, 숫자, 특수문자 조합으로 이루어진 8~12자리" />
				
				<p class="memberInfoStyleFont"><b>새 비밀번호 확인</b></p>
				<input type="password" id="pw1" name="pw2" maxlength="12" placeholder="위와 동일하게 입력" />
				
				<p class="memberInfoPwFont error"></p>
				<div class="new_pw_button">
					<button type="button" class="outline_btn" id="newPwCencel">취소</button>
					<button type="button" class="btn" id="newPwSave">저장</button>
				</div>
			</div>
			
			<p class="memberInfoStyleFont"><b>이름</b></p>
			<p class="name view_info">${myInfo.getName() }</p>
			<input type="text" id="name" name="name" value="${myInfo.getName() }" class="mdfy_info" />
			<p class="error"></p>
	
			<p class="memberInfoStyleFont"><b>휴대전화 번호</b></p>
			<p class="phone view_info">${myInfo.getPhone() }</p>
			<input type="text" id="phone" name="phone" value="${myInfo.getPhone() }" class="mdfy_info" />
			<p class="error"></p>
	
			<p class="memberInfoStyleFont"><b>생년월일</b></p>
			<p class="birth view_info">${myInfo.getBirth() }</p>
			<p class="birth_box mdfy_info">
				<select name="birthY" id="birthY">
					<option value="">년도</option>
				<% 
					LocalDate now = LocalDate.now();
					int year = now.getYear();
					for(int i = year; i > year-80; i--) {
						if(i == Integer.parseInt((info.getBirth()).split("-")[0]))
							out.println("<option value='" + i + "' selected>" + i + "</option>");
						else 
							out.println("<option value=\"" + i + "\">"+i+"</option>");
					}
				 %>
				</select>
				
				<select name="birthM" id="birthM">
					<option value="">월</option>
					<%
					for(int i=1; i <= 12; i++) {
						String month = "00" + i;
						month = month.substring((month.length() - 2), month.length());
						if(month.equals((info.getBirth()).split("-")[1]))
							out.println("<option value='" + month + "' selected>" + month + "</option>");
						else 
							out.println("<option value=\"" + month + "\">" + month + "</option>");
					}
					%>
				</select>
				<select name="birthD" id="birthD">
					<option value="">일</option>
					<%
					for(int j=1; j<= 31; j++) {
						String date = "00" + j;
						date = date.substring((date.length() - 2), date.length());
						if(date.equals((info.getBirth()).split("-")[2]))
							out.println("<option value='" + date + "' selected>" + date + "</option>");
						else 
							out.println("<option value=\"" + date + "\">" + date + "</option>");
					}
					%>
				</select>
			</p>
			<p class="error"></p>
	
			<p class="memberInfoStyleFont"><b>성별</b></p>
			<% if(info.getGender().equals("F")) { %>
				<p class="gender view_info">여자</p>
			<%} else { %>
				<p class="gender view_info">남자</p>
			<% } %>
			<div class="radio-input gender_box mdfy_info">
				<p>
					<input type="radio" id="male" name="gender" value="M" <%if(info.getGender().equals("M")) { %> checked <% } %> />
					<label for="male">남자</label>
				</p>
				<p>
					<input type="radio" id="female" name="gender" value="F" <%if(info.getGender().equals("F")) { %> checked <% } %> />
					<label for="female">여자</label>
				</p>
			</div>
			<p class="memberInfoStyleFont"><b>광고 메일 수신 동의 여부</b></p>
			<% if(info.getCf_mail().equals("Y")) { %>
				<p class="cf_mail view_info ">동의</p>
			<% } else { %>
				<p class="cf_mail view_info ">비동의</p>
			<%} %>
			<p class="memberCfmailStyleFont mdfy_info">홈페이지에서 제공하는 이벤트 등을 안내받을 수 있습니다.</p>
			<div class="radio-input cfMail_box mdfy_info">
				<p>
					<input type="radio" id="mailY" name="cf_mail" value="Y" <%if(info.getCf_mail().equals("Y")) { %> checked <% } %> />
					<label for="mailY">동의</label>
				</p>
				<p>
					<input type="radio" id="mailN" name="cf_mail" value="N" <%if(info.getCf_mail().equals("N")) { %> checked <% } %> />
					<label for="mailN">비동의</label>
				</p>
			</div>
		</div>
	</form>
	<!-- 하단버튼 -->
	<div class="myPageUpdateBtn view_info" id="">
		<button class="btn mdfy_btn" id="mdfyBtn">수정하기</button>
	</div>
	<div class="btnArea mdfy_info">
		<button class="outline_btn" id="mdfyCancel">취소하기</button>
		<button class="btn" id="mdfySave">저장하기</button>
	</div>
</div>
<footer></footer>
</body>
</html>