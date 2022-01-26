<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>픽플 - 회원가입</title>
	<link rel="icon" type="image/x-icon" href="img/icon/favicon.ico">
	<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap" rel="stylesheet">
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	<link rel="stylesheet" href="css/common.css">
	<link rel="stylesheet" href="css/style.css">
	<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
	<script type="text/javascript" src="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script>
	<script src="//cdn.jsdelivr.net/npm/sweetalert2@11"></script>
	<script type="text/javascript" src="https://static.nid.naver.com/js/naverLogin_implicit-1.0.3.js" charset="utf-8"></script>
	<script src="https://static.nid.naver.com/js/naveridlogin_js_sdk_2.0.2.js" charset="utf-8"></script>
	<script src="https://developers.kakao.com/sdk/js/kakao.js"></script>
<!-- 	<script src="js/naver_login_sdk.js"></script> -->
	<script src="js/common.js"></script>
	<script src="js/join.js"></script>
</head>
<body>
   <header></header>
   <div class="wrap" id="joinWrap">
         <div class="join">
            <h2>회원가입</h2>
                  <div id="naver_id_login"></div>
            <div class="sns_login_box">
                  <button class="sns_login_btn google_login"></button>
                  <button class="sns_login_btn naver_login" onclick="naverLogin();"></button>
                  <button class="sns_login_btn kakao_login" onclick="kakao();"></button>
            </div>
            <hr class="divider">
            <form action="Controller" name="joinForm" method="post">
            	<input type="hidden" name="command" value="join"/>
                  <div class="id_check">
                     <div class="text-input">
                        <label for="id">아이디</label>
                        <input data-checked="N" type="text" id="id" name="id" maxlength="12" placeholder="공백 없이 영문, 숫자 조합으로 이루어진 4~12자리" />
                        <p class="msg"></p>
                     </div>
                     <button type="button" id="idCheckBtn" class="sec_btn">중복확인</button>
                  </div>
                  <div class="text-input">
                     <label for="pw">비밀번호</label>
                     <input type="password" id="pw" name="pw" maxlength="12" placeholder="영문자, 숫자, 특수문자 조합으로 이루어진 8~12자리" />
                     <p class="msg"></p>
                  </div>
                  <div class="text-input">
                     <label for="pw2">비밀번호 확인</label>
                     <input data-checked="N" type="password" id="pw2" name="pw2" maxlength="12" placeholder="위와 동일하게 입력" />
                     <p class="msg"></p>
                  </div>
                  <div class="text-input">
                     <label for="name">이름</label>
                     <input type="text" id="name" name="name" />
                     <p class="msg"></p>
                  </div>
                  <div class="phone_box text-input">
                     <label for="phone">전화번호</label>
                     <!-- <input type="text" id="phone" name="phone" /> -->
                     <select name="phone1" id="phone1">
                        <option value="010">010</option>
                        <option value="011">011</option>
                        <option value="016">016</option>
                        <option value="017">017</option>
                        <option value="019">019</option>
                        <!-- <option value="not">없음</option> -->
                     </select>
                     <span> - </span>
                     <input type="text" name="phone2" id="phone2" maxlength="4" onKeyup="this.value=this.value.replace(/[^-0-9]/g,'');">
                     <span> - </span>
                     <input type="text" name="phone3" id="phone3" maxlength="4" onKeyup="this.value=this.value.replace(/[^-0-9]/g,'');">
                     <p class="msg"></p>
                  </div>
                  <div class="text-input">
                     <label for="email">이메일 주소</label>
                     <input type="text" id="email" name="email" />
                     <p class="msg"></p>
                  </div>
                  <div class="text-input">
                     <label for="birthY">생년월일</label>
                     <div class="birth_box">
                        <select name="birthY" id="birthY">
                              <option value="">년도</option>
                        </select>
                        <select name="birthM" id="birthM">
                              <option value="">월</option>
                        </select>
                        <select name="birthD" id="birthD">
                              <option value="">일</option>
                        </select>
                        <p class="msg"></p>
                     </div>
                  </div>
                  <div class="gender_box radio-input">
                     <p>성별</p>
                     <div>
                        <input type="radio" id="male" name="gender" value="M" />
                        <label for="male">남자</label>
                     </div>
                     <div>
                        <input type="radio" id="female" name="gender" value="F" />
                        <label for="female">여자</label>
                     </div>
                     <p class="msg"></p>
                  </div>
                  <fieldset class="agree_box">
                     <div class="checkbox-input">
                        <input type="checkbox" id="policy" name="policy" value="Y" />
                        <label for="policy">(필수) <a href="#">이용약관</a> 및 <a href="#">개인정보 제공</a>에 동의합니다.</label>
                        <p class="msg"></p>
                     </div>
                     <div class="checkbox-input">
                        <input type="checkbox" id="cfMail" name="cf_mail" value="Y" />
                        <label for="cfMail">(선택) <a href="#">알림 및 광고메일</a> 수신에 동의합니다.</label>
                     </div>
               </fieldset>

               <button type="submit" id="joinBtn" class="btn" onclick="return checkAll();">가입하기</button>
            </form>
         </div>
   </div>
   <footer></footer>
</body>
</html>