<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.pickpl.dto.ViewDiaryDto" %>

<%
	boolean isLogin = false;
	String loginId = (String)session.getAttribute("loginId");
	if(loginId != null) {
		isLogin = true;	
	}
	
	ArrayList<ViewDiaryDto> listPick = (ArrayList<ViewDiaryDto>) request.getAttribute("pickDiaryList");
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
   <script src="https://developers.kakao.com/sdk/js/kakao.js"></script>
   <script src="js/common.js"></script>
   <script>
	   function unpick(e) {
			$target = $(e.target);
			$cnt = $("#diaryCnt");
			dId = $target.parents(".diary").attr("data-dId");
			$.ajax({
				url: 'Controller',
				type: 'post',
				data: {'command': 'unpick','dId': dId},
				dataType: 'json',
				success: function(data) {
					//console.log(data);
					if(data.unpick == "success") {
						$target.parents(".diary").remove();
						$cnt.text(+$cnt.text() - 1);
					}
				}
			});
		}
   </script>
</head>
<body>
<% if(isLogin) { %>
   <header></header>
   <div class="wrap pickDetail_wrap" id="">
      <div class="title_box">
         <h2 id="folder">${folderName }</h2>
         <div class="option_box">
            <h3><span id="diaryCnt">${diaryCnt }</span> 픽</h3>
            <%--<div class="order_box">
				<p class="selected_order">최근 픽 순</p>
				<ul class="order_list">
					<li data-order="my" class="order_option">최근 픽 순</li>
					<li data-order="pick_desc" class="order_option">픽 높은 순</li>
					<li data-order="view_desc" class="order_option">조회 높은 순</li>
				</ul>
			</div>--%>
         </div>
      </div>
      <div class="list_box diary_list">
      <% for(ViewDiaryDto dto : listPick) {%>
         <div data-dId="<%=dto.getD_id() %>" class="diary">
            <div class="place_box">
               <div class="diary_img">
	               <% for(String i : dto.getImg().split("_")) { %>
	                  <img src="img/diary/<%=i %>" alt="">
	               <% } %>
               </div>
               <div class="top_icon">
                  <button class="pick_btn picked" onclick="unpick(event);"></button>
               </div>
               <div class="info_box">
                  <p class="place_name"><%=dto.getPlace_name() %></p>
                  <p class="address"><%=dto.getAddress() %></p>
               </div>
            </div>
            <div class="acct_box">
               <img data-diary="profile" src="img/profile/<%=dto.getProfile() %>" alt="" class="acct_profile">
               <p data-diary="id" class="writer_id"><%=dto.getWriter_id() %></p>
               <p data-diary="pick_count" class="pick_cnt"><%=dto.getPick_count() %></p>
               <p data-diary="view_count" class="view_cnt"><%=dto.getView_count() %></p>
            </div>
         </div>
       <% } %>
             
      </div>
   </div>
   <footer></footer>
 <% } else { %>
 	<script>
		Swal.fire({
			icon: 'error',
		 	html: '<strong>접근권한이 없습니다.</strong>',
		 	confirmButtonText: '확인',
		 	confirmButtonColor : '#0ea098'
		}).then(function() {
		    window.location = "index.jsp";
		});
	</script>
 <% } %>
</body>
</html>