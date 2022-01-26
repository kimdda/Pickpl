<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	String loginId = (String)session.getAttribute("loginId");
	boolean isLogin = false;
	boolean isMy = (boolean)request.getAttribute("isMy");
	
	if(loginId != null) {
		isLogin = true;	
	}

%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>픽플</title>
	<link rel="icon" type="image/x-icon" href="img/icon/favicon.ico">
<!-- 	<link rel="stylesheet" type="text/css" href="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.css"/> -->
	<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap" rel="stylesheet">
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	<link rel="stylesheet" href="css/common.css">
	<link rel="stylesheet" href="css/style.css">
	<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
	<script type="text/javascript" src="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script>
	<script src="//cdn.jsdelivr.net/npm/sweetalert2@11"></script>
	<script src="js/common.js"></script>
	<script>
		var diaryId = `${diaryId }`;
		var isMy = ${isMy };
	</script>
	<script src="js/diary.js"></script>
</head>
<body>
	<header></header>
	<div class="wrap diary_wrap" id="diaryWrap" data-diary="my">
	<% if(!isLogin && isMy) { %>
		<script>
			Swal.fire({
				  icon: 'error',
			  //title: '접근권한이 없습니다.',
			  html: '<strong>접근 권한이 없습니다.</strong>',
			  confirmButtonText: '확인',
			  confirmButtonColor : '#0ea098'
			}).then(function() {
			    window.location = "index.jsp";
			});
		</script>
	<% } else { %>
		<div id="diaryAcct" class="diary_acct">
			<div class="info_box">
				<div class="profile_box">
					<img src="img/profile/profile_default.svg" alt="">
				</div>
				<div class="acc_box">
					<div class="id_box">
						<p id="diary_id"></p>
						<%if(!isMy) { %>
<!-- 						<a href="" class="other_diary"><span class="material-icons chat-icon">chat</span></a> -->
						<span class="other_diary material-icons chat-icon" onclick="toChat(event);">chat</span>
						<% } %>
					</div>
					<div class="activity_box">
						<div id="diaryCnt">${pickpl}</div>
						<% if(isLogin && isMy) { %>
						<div id="pickCnt" class="my_diary">${pick }</div>
						<% } %>
						<div id="pickedCnt">${picked }</div>
					</div>
				</div>
			</div>
			<% if(isLogin && isMy) { %>
			<div class="write_btn_box my_diary">
				<button id="writeDiaryBtn" class="btn" onclick="location.href='diary_write.jsp';">다이어리 작성</button>
			</div>
			<% } %>
		</div>

		<div class="diary_list_box">
			<div class="order_box">
				<p class="selected_order">최신순</p>
				<ul class="order_list">
					<li data-order="up_date" class="order_option selected">최신순</li>
					<li data-order="pick_count" class="order_option">픽높은순</li>
					<li data-order="view_count" class="order_option">조회높은순</li>
				</ul>
			</div>
			
			
			<div class="no_list">
				<p>첫 픽플을 올려보세요!</p>
			</div>
			<div id="diaryList" class="diary_list">
				<div id="searchList" class="search_list"></div>
				<!-- <div data-dId="99" class="diary">
					<div class="place_box">
						<div class="diary_img">
							<img src="img/diary/99-01.jpeg" alt="">
							<img src="img/diary/99-02.jpeg" alt="">
							<img src="img/diary/99-03.jpeg" alt="">
						</div>
						<div class="top_icon">
							<button class="pick pick_btn other_diary"></button>
							<button class="material-icons edit_btn my_diary">edit</button>
							<button class="material-icons del_btn my_diary">delete</button>
						</div>
						<div class="info_box">
							<p data-diary="place_name" class="place_name">송호금강 물빛다리</p>
							<p data-diary="address" class="address">충청북도 영동군 양산면 송호리 410</p>
						</div>
					</div>
					<div class="acct_box ">
						<p data-diary="pick_count" class="pick_cnt">200</p>
						<p data-diary="view_count" class="view_cnt">0</p>
					</div>
				</div> -->
			</div>
		</div>
		<% } %>
	</div>
	<footer></footer>
</body>
</html>