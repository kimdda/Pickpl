<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String loginId = null;
	if(session.getAttribute("loginId") != null)
		loginId = (String)session.getAttribute("loginId");
	
	boolean isMine = (boolean)request.getAttribute("isMine");
	
	String[] img = ((String)request.getAttribute("img")).split("_");
%>
<!DOCTYPE html>
<html>
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
	<script src="https://developers.kakao.com/sdk/js/kakao.js"></script>
	<script type="text/javascript" src="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>
	<script src="js/common.js"></script>
	<script>
		var lat = ${lat},
			lng = ${lng};
	</script>
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=218e8fedd2cb6770e181a0e8a08311e7&libraries=services"></script>
	<script src="js/diary_detail.js"></script>
</head>
<script>
</script>
<body>
	<header></header>
	<div class="wrap detail_wrap">
		<input type="hidden" name="dId" id="dId" value="${diary_id }"/>
		<div class="title_wrap">
			<h2 class="place_name">${place_name}</h2>
			<div class="title_info_wrap">
				<p class="pick_cnt">
					<span id="pickCnt">${pick_count}</span>픽
				</p>
				<p class="cmnt_cnt">
					<span id="commentCnt">${cmnt_count }</span>개
				</p>
				<p class="view_cnt"><span id="viewCnt">${view_count }</span>뷰</p>
			</div>
			<div class="btn_box">
				<%if(!isMine) { %>
				<button class="reprot_btn func_btn">
					<span class="material-icons report_icon">sentiment_very_dissatisfied</span>
					신고하기
				</button>
				<% } %>
				<button class="share_btn func_btn">
					<span class="material-icons share_icon">share</span> <span>공유하기</span>
				</button>
				<a id="kakao-link-btn" href="javascript:sendLink()">
					<img src="https://developers.kakao.com/assets/img/about/logos/kakaolink/kakaolink_btn_medium.png" />
				</a>
			</div>
		</div>

		<div class="diary_detail_img">
			<%for(String i : img) { %>
			<div class="img_box"><img src="img/diary/<%=i %>" alt=""></div>
			<% } %>
		</div>

		<div class="content_box">
			<div class="id_box">
				<img src="img/icon/profile_default.svg" alt="profile_icon" class="acct_profile"> 
				<span id="writer_id">${writer_id}</span>
				<%if(!isMine) { %>
				<button class="message_btn material-icons" onclick="toChat(event);">chat</button>
				<% } %>
			</div>
			<%if(!isMine) { %>
			<button class="pick_btn ${pick }" onclick="pickBtn(event);"></button>
			<% } %>

			<p class="address ADDR_ICON">${address}</p>

			<p class="date_time DATE_ICON">
				<span class="visit_date">${visit_date_text }</span> <span class="visit_time">${visit_time}</span>시
			</p>

			<p class="weather WEATHER_ICON">
				<img class="weather_icon" src="img/icon/${weather_name }" alt=" ">
			</p>

			<p class="contents">${contents}</p>

			<p class="etc_info ETC_ICON">기타 정보</p>
			<div class="etc_contents">
				<%if(request.getAttribute("drone") != null) { %>
				<p class="etc_icons etc_drone DRONE_SUB_ICON" id="drone">${drone_text }</p>
				<%} %>
				
				<%if(request.getAttribute("public_tran") != null) {  %>
				<p id="tran" class="etc_icons etc_public_tran PUBLIC_SUB_ICON">${public_tran_text }</p>
				<%} %>
				
				<%if(request.getAttribute("public_info") != null) {  %>
				<p id="tran_more" class="etc_icons etc_public_info PUBLICINFO_SUB_ICON">${public_info_text }</p>
				<%} %>
				
				<%if(request.getAttribute("park") != null) {  %>
				<p id="park" class="etc_icons etc_park PARK_SUB_ICON">${park_text }</p>
				<%} %>
				
				<%if(request.getAttribute("park_info") != null) {  %>
				<p id="park_fee" class="etc_icons etc_park_time PARKINFO_SUB_ICON">${park_info_text }</p>
				<%} %>
				
				<%if(request.getAttribute("toilet") != null) {  %>
				<p id="toilet" class="etc_icons etc_toilet TOILET_SUB_ICON">${toilet_text }</p>
				<%} %>
				
				<%if(request.getAttribute("locker") != null) {  %>
				<p id="locker" class="etc_icons etc_locker LOCKER_SUB_ICON">${locker_text }</p>
				<%} %>
				
				<%if(request.getAttribute("shower") != null) {  %>
				<p id="shower" class="etc_icons etc_shower SHOWER_SUB_ICON">${shower_text }</p>
				<%} %>
			</div>
		</div>
		
		<div class="map_area">
			<h3>위치</h3>
			<div id="map"></div>
		</div>
		
		<div class="comments_wrap">
			<h3 class="">댓글 <span class="cmnt_cnt">0</span>개</h3>
			<p class="no_comment">등록된 댓글이 없습니다.</p>
			<div class="comments_box">
				<div class="comments_list">
					<%-- <div class="comment">
						<div class="img_box"><img src="img/icon/profile_default.svg" alt=""></div>
						<div class="info_box">
							<p class=""><span class="cmnt_id">idid10</span> <span class="cmnt_date">2020-09-09 12:30</span></p>
							<p class="cmnt_contents">Lorem ipsum dolor sit amet consectetur adipisicing elit. Ullam cupiditate molestias, alias dolore labore saepe, dolorum est eaque ipsa odit, ab mollitia nam. Est vero laboriosam atque sunt architecto vitae.</p>
							<p class="cmnt_del_btn material-icons">clear</p>
						</div>
					</div> --%>
				</div>			
			</div>
			<div class="cmnt_write_box isLogin">
				<img src="img/icon/profile_default.svg" alt="">
				<textarea name="cmnt" id="cmnt"></textarea>
				<button class="cmnt_btn btn">올리기</button>
			</div>
		</div>

		<!-- <div class="divider"></div> -->

		<div class="around_wrap">
			<h3 class="place_list">주변 여행지</h3>
			<div class="row">
				<div class="row_list">
					<div id="diaryList" class="diary_list">
						<%--<div data-diary="d_id" data-dId="100" class="diary">
							<div class="place_box">
								<div class="diary_img">
									<img src="img/diary/100-01.jpeg" alt=""> <img
										src="img/diary/100-02.jpeg" alt=""> <img
										src="img/diary/100-03.jpeg" alt=""> <img
										src="img/diary/100-04.jpeg" alt="">
								</div>
								<div class="top_icon">
									<button class="pick pick_btn login"></button>
								</div>
								<div class="info_box">
									<p data-diary="place_name" class="place_name"></p>
									<p data-diary="address" class="address">충청북도 ㅇㄹㄴㅇㄹㄴㅇㄹㄴㅇㄹ 영동군
										양산면 dddfffdd 송호리 410</p>
								</div>
							</div>
							<div class="acct_box">
								<img data-diary="profile" src="img/icon/profile_default.svg"
									alt="" class="acct_profile">
								<p data-diary="id" class="writer_id">id1</p>
								<p data-diary="pick_count" class="pick_cnt">200</p>
								<p data-diary="view_count" class="view_cnt">0</p>
							</div>
						</div> --%>
						
					</div>
				
				</div>
				
				<div class="row_btn"></div>
			</div>
		</div>
		
	</div> 

	<footer></footer>
</body>