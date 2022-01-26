<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.sql.*" %>

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
    <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=218e8fedd2cb6770e181a0e8a08311e7&libraries=services"></script>
	<script src="js/list.js"></script>
</head>
<body>
<header></header>
<div class="wrap list_wrap" id="listWrap">
  		<!-- 왼쪽 글목록 -->
	<div class="" id="listArea">
		<div class="search_wrap">
			<p>픽플 둘러보기</p>
			<div class="recommend_list" id="recommendList">
				<!-- <span>관련 지역 : &nbsp;</span> -->
<!-- 				<span class="recommend">양양</span> -->
			</div>
			<div class="search_box" id="">
				<input type="search" id="keyword" value="">
				<button class="btn" id="searchBtn">검색</button>
			</div>
			<div class="after_search_box">
				<p class="search_result"><span id="searchArea">강원도</span> 검색결과</p>
				<div class="order_box">
					<p class="selected_order">최신순</p>
					<ul class="order_list">
						<li data-order="up_date" class="order_option selected">최신순</li>
						<li data-order="pick_count" class="order_option">픽높은순</li>
						<li data-order="view_count" class="order_option">조회높은순</li>
					</ul>
				</div>
				<div class="divider"></div>
			</div>
		</div>

		<!-- 검색 결과 다이어리 영역 -->
		<div id="diaryList" class="diary_list">
			<div id="searchList" class="search_list"></div>
		
			<%-- <div data-dId="<%=vo.getD_id()%>" class="diary">
				<div class="place_box">
					<div class="diary_img">
						<%for(int i=0; i<=vo.getImg().length-1; i++) {%>
						<img src="img/diary/<%=vo.getImg()[i]%>" alt="">
						<%}%>
					</div>
					<div class="top_icon">
						<button class="pick pick_btn login"></button>
					</div>
					<div class="info_box">
						<p data-diary="place_name" class="place_name"><%=vo.getPlace_name()%></p>
						<p data-diary="address" class="address"><%=vo.getAddress()%></p>
					</div>
				</div>
				<div class="acct_box">
					<img data-diary="profile" src="img/icon/profile_default.svg" alt="" class="acct_profile">
					<p data-diary="id" class="writer_id"><%=vo.getWriter_id()%></p>
					<p data-diary="pick_count" class="pick_cnt"><%=vo.getPick_count()%></p>
					<p data-diary="view_count" class="view_cnt"><%=vo.getView_count()%></p>
				</div>
			</div>  --%>
		</div>		
	</div>
	
	<!-- 오른쪽 지도 -->
	<div class="" id="rightMapArea">
		<div id="map"></div>
		<!-- 지도 확대, 축소 컨트롤 div 입니다 -->
		<div class="custom_zoomcontrol radius_border"> 
		    <span id="zoomIn" class="material-icons">add</span>  
		    <span id="zoomOut" class="material-icons">remove</span>  
		</div>
	</div>
</div>
<footer></footer>
</body>
</html>