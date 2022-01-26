<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.pickpl.dto.ViewDiaryDto" %>
<%@ page import="com.pickpl.admin.dto.RcmndDto" %>
<%
boolean isNew = false;
	try {
		int no = ((RcmndDto)request.getAttribute("rcmnd")).getNo();
	} catch(NullPointerException e) {
		int no = 0;
		isNew = true;
	}
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<!-- <link rel="stylesheet" href="../css/admin_style_kd.css"> -->
	<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap" rel="stylesheet">
 	<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	<link rel="stylesheet" href="/Pickpl/css/admin_common.css">
	<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
	<script src="/Pickpl/admin/js/rcmnd_write.js"></script>
	<script src="/Pickpl/admin/js/common.js"></script>
	<title>픽플 관리자</title>
</head>
<body>
	<div class="wrap">
		<nav></nav>
		<div class="main new_reco_wrap">
         <h4 class="box_title">추천 목록 작성</h4>
         <form class="writing_box" action="/Pickpl/adminController" method="post">
         	<input type="hidden" name="no" value="${rcmnd.no }" />
         	<input type="hidden" name="command" value=""/>
            <p>
               <label for="title">추천 글 제목</label>
               <input type="text" name="title" id="title" value="${rcmnd.title }" />
            </p>
            <p>
               <label for="from_date">게시 기간</label>
               <input type="date" name="open_date" id="open_date" value="${rcmnd.open_date }" />&nbsp; ~ &nbsp;
               <input type="date" name="close_date" id="close_date" value="${rcmnd.close_date }" />
            </p>
            <%
            if(!isNew) {
            %>
            	<p>
            		<span>게시 상태</span>
            		<span id="stat">${statText }</span>
            	</p>
            <%
            }
            %>
            <input type="hidden" name="hold" value="${rcmnd.hold }"/>
            <p>선택한 글 목록</p>
			<table class="selected_list">
				<thead>
					<tr>
						<th class="selection">선택</th>
						<th class="index">번호</th>
						<th class="writer_id">작성자</th>
						<th class="pick_cnt">픽수</th>
						<th class="view_cnt">조회수</th>
						<th class="visit_date">방문 날짜</th>
						<th class="place_name">장소명</th>
						<th class="region">지역</th>
						<th class="view">내용보기</th>
					</tr>
				</thead>
				<tbody>
					<%
					ArrayList<ViewDiaryDto> diaryList = (ArrayList<ViewDiaryDto>) request.getAttribute("diaryList");
					if(diaryList != null) {
						for(ViewDiaryDto dto : diaryList) {
							String region = (dto.getAddress().split(" "))[0] + " " + (dto.getAddress().split(" "))[1];
					%>
						<tr data-dId="<%=dto.getD_id() %>" onclick="listSelect(event);">
							<td class="selection"></td>
							<td class="index"><%=diaryList.indexOf(dto) + 1 %></td>
							<td class="writer_id"><%=dto.getWriter_id() %></td>
							<td class="pick_cnt"><%=dto.getPick_count() %></td>
							<td class="view_cnt"><%=dto.getView_count() %></td>
							<td class="visit_date"><%=dto.getVisit_date() %></td>
							<td class="place_name"><%=dto.getPlace_name() %></td>
							<td class="region"><%=region %></td>
							<td class="view"><button type="button" class="view_btn btn_2">보기</button></td>
						</tr>
					<% } } %>
               </tbody>
            </table>
            <div class="recommend_btn_box">
               <div class="order_btn">
                  <button type="button" class="order_down_btn btn_1">아래로 내리기</button>
                  <button type="button" class="order_up_btn btn_1">위로 올리기</button>
                  <button type="button" class="delete_btn btn_1">선택 삭제</button>
                  <button type="button" class="hold_btn hold_Y_btn btn_1">게시 보류</button>
                  <button type="button" class="hold_btn hold_N_btn btn_1">게시 보류 해제</button>
               </div>
               <div class="func_btn">
              <%--<% else if(stat.equals("N")) { %>
                  <button id="stopBtn" class="mdfy_btn btn_4">게시 중지</button>
              <% } %> --%>
                  <button type="button" class="cancel_btn btn_4">취소</button>
                  <button id="saveBtn" class="save_btn btn_4" onclick="return saveCheck();">저장</button>
               </div>
            </div>
         </form> 
         <hr>
         <div class="search_box">
            <h4 class="box_title">검색조건</h4>
            <div class="option_box box">
               <p>
                  <label for="byMonth">월별</label>
                  <select name="byMonth" id="byMonth">
                     <option value="all">전체</option>
                  </select>
               </p>
               <p>
                  <label for="byRegion">지역별</label>
                  <select name="byRegion" id="byRegion">
                     <option value="all">전국</option>
                  </select>
                  <select name="byCity" id="byCity">
                     <option value="all">전체</option>
                  </select>
               </p>
            </div>
            <div class="search_btn_box">
               <button class="btn_3" id="searchBtn">검색</button>
            </div>

            <!-- 검색결과 -->
            <p class="no_result">검색 결과가 없습니다.</p>
            <div class="result_box">
               <p><span class="result_cnt">00</span>건의 검색결과 - <span id="selected_cnt">0</span>개 선택됨</p>
               <table class="result_list">
                  <thead>
                     <tr>
                        <th class="selection">선택</th>
                        <th class="index">번호</th>
                        <th class="writer_id">작성자</th>
                        <th class="pick_cnt">픽수</th>
                        <th class="view_cnt">조회수</th>
                        <th class="visit_date">방문 날짜</th>
                        <th class="place_name">장소명</th>
                        <th class="region">지역</th>
                        <th class="view">내용보기</th>
                     </tr>
                  </thead>
                  <tbody>
                     <!-- <tr data-dId="38" onclick="listMultipleSelect(event);">
                        <td class="selection"></td>
                        <td class="index">1</td>
                        <td class="writer_id">id1</td>
                        <td class="pick_cnt">38</td>
                        <td class="view_cnt">398</td>
                        <td class="visit_date">2021-09-09</td>
                        <td class="place_name">설악산</td>
                        <td class="region">강원도 속초시</td>
                        <td class="view"><button class="view_btn btn_2">보기</button></td>
                     </tr> -->
                     
                  </tbody>
               </table>
               <div class="result_btn_box">
                  <button class="add_btn btn_1" id="addBtn">선택추가</button>
                  <div class="paging_box">
                     <button class="prev_btn material-icons" onclick="prevPage();">navigate_before</button> 
                     <input type="text" name="paging" id="paging" value="1" onkeydown="inputPage(event);" /> / <span class="page_cnt">5</span> 
                     <button class="next_btn material-icons" onclick="nextPage();">navigate_next</button>
                  </div>
               </div>

            </div>
         </div>
		</div>
	</div>
</body>
</html>