<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
   <meta charset="UTF-8">
   <meta http-equiv="X-UA-Compatible" content="IE=edge">
   <meta name="viewport" content="width=device-width, initial-scale=1.0">
   <title>픽플</title>
   <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap" rel="stylesheet">
   <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
   <link rel="stylesheet" href="css/common.css">
   <link rel="stylesheet" href="css/style.css">
   <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
   <script type="text/javascript" src="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script>
   <script src="//cdn.jsdelivr.net/npm/sweetalert2@11"></script>
   <script src="js/common.js"></script>
   <script src="js/find.js"></script>
</head>
<body>
   <header></header>
   <div class="wrap find_wrap" id="findWrap">
      <div class="btn_box">
         <button data-find="findId" class="type_btn id_type_btn active">아이디 찾기</button>
         <button data-find="findPw" class="type_btn pw_type_btn">비밀번호 찾기</button>
      </div>

      <div class="input_box">
         <form action="Controller" name="findForm" method="post" >
            <input type="hidden" name="command" value="findId" />
            <div class="find_pw">
               <label for="id">아이디</label>
               <input type="text" name="id" />
               <p class="msg"></p>
            </div>
            <label for="name">이름</label>
            <input type="text" name="name" />
            <p class="msg"></p>

            <label for="email">이메일 주소</label>
            <input type="text" name="email" />
            <p class="msg"></p>

<!--             <input type="submit" value="아이디 찾기" class="btn" onclick="return checkFind();" /> -->
            <button type="submit" class="btn find_btn" onclick="return checkFind();">
            	<span class="findBtn_text">아이디 찾기</span>
            	<span class="material-icons loader white hide">restart_alt</span>
            </button>
            
         </form>
      </div>
   </div>
   <footer></footer>
</body>
</html>