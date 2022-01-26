<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.pickpl.dto.*" %>
<%
	HashMap<String, String> popularList = (HashMap<String, String>) request.getAttribute("popularList");
	Set<String> keys = popularList.keySet();
	ArrayList<ViewDiaryDto> highViewList = (ArrayList<ViewDiaryDto>) request.getAttribute("highViewList");
	ArrayList<RcmndListDto> rcmndList = (ArrayList<RcmndListDto>) request.getAttribute("rcmndList");
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
	<script src="js/main.js"></script>	
</head>
<body>
	<header></header>
	<div id="main" class="main_img" alt="MainPageImg">
		<h1 class="main_sentence">새로운 인생샷을 추가해보세요</h1>
		<button class="all_list_btn">픽플 둘러보기</button>
	</div>
	<div class="index_wrapper">
		<div class="index_wrap" id="indexWrap">
			<!-- 요즘 뜨는 여행지 -->
			<h2 class ="place_list">요즘 뜨는 여행지</h2>
			<div class="row popular_list">
				<div class="row_list">
				<% 
					Iterator itr = keys.iterator();
					while(itr.hasNext()) {
						String region = (String)itr.next();
				%>
						<div class="single_destination">
							<div class="img_box">
								<img src="img/diary/<%=popularList.get(region) %>" alt="<%=region %>_img">
							</div>
							<div class="content">
								<p class="city_name"><%=region %></p>
								<button class="more_btn btn">더보기</button>
							</div>
						</div>
				<%		
					}
				%>
				</div>
				<div class="row_btn">
					<%--<button class="material-icons row_left_btn">arrow_back_ios_new</button>
						 <button class="material-icons row_right_btn">arrow_forward_ios</button>--%>
				</div>
			</div>
		</div>
		<div class="banner">
			<h3>Let's 픽플</h3>
		</div>
		<div class="index_wrap">
			<!-- 추천 여행지 -->
			<h2 class="place_list">많이 보는 여행지</h2>
			<div class="row">
				<div class="row_list">
					<div id="diaryList" class="diary_list">
					<%for(ViewDiaryDto vo : highViewList) { %>
						<div data-dId="<%=vo.getD_id() %>" class="diary">
							<div class="place_box">
								<div class="diary_img">
									<%for(String img : vo.getImg().split("_")) { %>
									<img src="img/diary/<%=img %>" alt="">
									<% } %>
								</div>
								 <div class="top_icon">
											<button class="<%=vo.getPick() %> pick_btn" onclick="pickBtn(event);"><%=vo.getPick() %></button>
										</div>
								<div class="info_box">
									<p data-diary="place_name" class="place_name"><%=vo.getPlace_name() %></p>
									<p data-diary="address" class="address"><%=vo.getAddress() %></p>
								</div>
							</div>
							<div class="acct_box">
								<img data-diary="profile" src="img/profile/<%=vo.getProfile() %>" alt="" class="acct_profile">
								<p data-diary="id" class="writer_id"><%=vo.getWriter_id() %></p>
								<p data-diary="pick_count" class="pick_cnt"><%=vo.getPick_count() %></p>
								<p data-diary="view_count" class="view_cnt"><%=vo.getView_count() %></p>
							</div>
						</div>
					<% } %>
					</div>
				</div>
				
				<div class="row_btn">
					<%--<button class="material-icons row_left_btn">arrow_back_ios_new</button>
						 <button class="material-icons row_right_btn">arrow_forward_ios</button>--%>
				</div>
			</div>
			
			<%for(RcmndListDto r : rcmndList) { %>
				<h2 class="place_list"><%=r.getTitle() %></h2>
				<div class="row">
					<div class="row_list">
						<div id="diaryList" class="diary_list">
						<%for(ViewDiaryDto vo : r.getRcmndList()) { %>
							<div data-dId="<%=vo.getD_id() %>" class="diary">
								<div class="place_box">
									<div class="diary_img">
										<%for(String img : vo.getImg().split("_")) { %>
										<img src="img/diary/<%=img %>" alt="">
										<% } %>
									</div>
									 <div class="top_icon">
												<button class="<%=vo.getPick() %> pick_btn" onclick="pickBtn(event);"></button>
											</div>
									<div class="info_box">
										<p data-diary="place_name" class="place_name"><%=vo.getPlace_name() %></p>
										<p data-diary="address" class="address"><%=vo.getAddress() %></p>
									</div>
								</div>
								<div class="acct_box">
									<img data-diary="profile" src="img/profile/<%=vo.getProfile() %>" alt="" class="acct_profile">
									<p data-diary="id" class="writer_id"><%=vo.getWriter_id() %></p>
									<p data-diary="pick_count" class="pick_cnt"><%=vo.getPick_count() %></p>
									<p data-diary="view_count" class="view_cnt"><%=vo.getView_count() %></p>
								</div>
							</div>
						<% } %>
						</div>
					</div>
					
					<div class="row_btn">
						<%--<button class="material-icons row_left_btn">arrow_back_ios_new</button>
						 <button class="material-icons row_right_btn">arrow_forward_ios</button>--%>
					</div>
				</div>
			<% } %>
			<div class="recent_wrap">
				<h2 class="place_list">최근 본 여행지</h2>
				<div class="row recent_box">
					<p class="no_list">픽플을 둘러보세요</p>
					<div id="diaryList" class="diary_list row_list">
						<!-- <div data-dId="100" class="diary">
							<div class="place_box">
								<div class="diary_img">
									<img src="img/diary/100-01.jpeg" alt="">
									<img src="img/diary/100-02.jpeg" alt="">
									<img src="img/diary/100-03.jpeg" alt="">
									<img src="img/diary/100-04.jpeg" alt="">
								</div>
								  <div class="top_icon">
											<button class="pick pick_btn login"></button>
										</div>
								<div class="info_box">
									<p data-diary="place_name" class="place_name"></p>
									<p data-diary="address" class="address">충청북도 ㅇㄹㄴㅇㄹㄴㅇㄹㄴㅇㄹ 영동군 양산면 dddfffdd 송호리 410</p>
								</div>
							</div>
							<div class="acct_box">
								<img data-diary="profile" src="img/icon/profile_default.svg" alt="" class="acct_profile">
								<p data-diary="id" class="writer_id">id1</p>
								<p data-diary="pick_count" class="pick_cnt">200</p>
								<p data-diary="view_count" class="view_cnt">0</p>
							</div>
						</div> -->
						
						
					</div>
					
					<div class="row_btn">
						<%--<button class="material-icons row_left_btn">arrow_back_ios_new</button>
						 <button class="material-icons row_right_btn">arrow_forward_ios</button>--%>
					</div>
				</div>
			</div>
			
		</div>
	</div>
		
	<footer></footer>
	<%
		if(session.getAttribute("logout") != null) {
	%>
		<script>
			const Toast = Swal.mixin({
			  toast: true,
			  position: 'top-end',
			  showConfirmButton: false,
			  timer: 2000,
			  //timerProgressBar: true,
			  
			});
	
			Toast.fire({
			  icon: 'success',
			  title: '로그아웃되었습니다.'
			});
		</script>
		
	<% 
		}
		session.removeAttribute("logout");
	%>
</body>
</html>

