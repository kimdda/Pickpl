<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.pickpl.dto.PickFolderDto" %>
<%
	boolean isLogin = false;
	String loginId = null;
	if(session.getAttribute("loginId") != null)
		loginId = (String)session.getAttribute("loginId");
	
	ArrayList<PickFolderDto> folderList = (ArrayList<PickFolderDto>) request.getAttribute("result");
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
	<script type="text/javascript" src="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>
	<script src="https://developers.kakao.com/sdk/js/kakao.js"></script>
	<script src="js/common.js"></script>
	<script src="js/pick_folder.js"></script>
</head>
<body>
	<header></header>
	<div class="wrap pickFolderWrap">
<%-- <% if(isLogin) { %> --%>
		<h2 class="title">MY PICK BOX</h2>
		<div class="top_box">
			<div class="btn_box">
				<!-- <button class="folder_revise_btn btn">수정</button> -->
				<button class="folder_add_btn sec_btn" id="addFolderBtn">폴더추가</button>
				<button class="folder_delete_btn btn" id="deleteFolderBtn">삭제</button>
			</div>
			<p class="selected_box"><span id="selected_cnt">0</span>개 폴더 선택됨 <span id="deselect_btn">선택 해제</span></p>
			<div class="order_box">
				<p class="selected_order">글 수 내림차순</p>
				<ul class="order_list">
					<li data-order="count_desc" class="order_option">글 수 내림차순</li>
					<li data-order="count_asc" class="order_option">글 수 오름차순</li>
					<li data-order="name_desc" class="order_option">이름 내림차순</li>
					<li data-order="name_asc" class="order_option">이름 오름차순</li>
				</ul>
			</div>
		</div>
		
		<div class="folder_box_list">
			<%for (PickFolderDto dto : folderList) { %>
			<div class="folder">
				<div class="folder_box" onclick="toDetail(event);">
				<% if(!dto.getFolder().equals("기본폴더")) { %>
					<input type="checkbox" name="folder">
				<% } %>
					<span class="pick_cnt"><%=dto.getFolder_d_count() %></span>
				</div>
				<div class="name_box">
					<p class="folder_name"><%=dto.getFolder() %></p>
					<%if(!dto.getFolder().equals("기본폴더")) { %>
					<button class="mdfy_btn material-icons editNameBtn" onclick="edit(event);">edit</button>
					<% } %>
				</div>
			</div>
			<% } %>
		</div>
	</div>
   <footer></footer>
<%--  <% } else { %> --%>
<!-- 	<script> -->
<!-- // 		Swal.fire({ -->
<!-- // 			icon: 'error', -->
<!-- // 		  	//title: '접근권한이 없습니다.', -->
<!-- // 		 	html: '<strong>접근권한이 없습니다.</strong>', -->
<!-- // 		 	confirmButtonText: '확인', -->
<!-- // 		 	confirmButtonColor : '#0ea098' -->
<!-- // 		}).then(function() { -->
<!-- // 		    window.location = "index.jsp"; -->
<!-- // 		}); -->
<!-- 	</script> -->
<%--  <% } %> --%>
</body>
</html>