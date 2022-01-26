<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.pickpl.admin.dto.ReportDto" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="/Pickpl/css/admin_common.css">
	<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap" rel="stylesheet">
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
	<script src="/Pickpl/admin/js/diary_detail.js"></script>
	<title>픽플 관리자</title>
</head>
<body>
	<div class="wrap">
		<nav></nav>
		<div class="main diary_detail_wrap">
			<div class="func_btn_box">
				<button class="btn_4" id="backBtn">목록으로</button>
				<button data-stat="N" id="statBtn" class="btn_2">게시 중지</button>
			</div>
			<div class="diary_info">
				<h4 class="box_title">다이어리 정보</h4>
				<table class="detail_list">
					<tr>
						<th class="">글 번호</th>
						<td id="dId">${diaryDetail.dId }</td>
						<th class="">아이디</th>
						<td id="id">${diaryDetail.writer_id }</td>
					</tr>
					<tr>
						<th class="">게시일</th>
						<td id="upDate">${diaryDetail.up_date }</td>
						<th class="">수정일</th>
						<td id="reDate">${diaryDetail.re_date }</td>
					</tr>
					<tr>
						<th class="">삭제일</th>
						<td id="delDate">${diaryDetail.del_date }</td>
						<th class="">게시상태</th>
						<td id="upStat">${diaryDetail.up_stat }</td>
					</tr>
					<tr>
						<th class="">pick 수</th>
						<td id="pickCnt">${diaryDetail.pick_count }</td>
						<th class="">view 수</th>
						<td id="viewCnt">${diaryDetail.view_count }</td>
					</tr>
				</table>
			</div>
			<!-- diary table-->
			<div class="diary">
				<h4 class="box_title">다이어리 내용</h4>
				<table class="detail_list">
					<tr>
						<th class="">방문 날짜</th>
						<td id="visitDate">${diaryDetail.visit_date }</td>
						<th class="">방문 시간</th>
						<td id="visitTime">${diaryDetail.visit_time }시</td>
					</tr>
				
					<tr>
						<th class="">날씨</th>
						<td id="weather">${diaryDetail.weather_text }</td>
						<th class="">명소 이름</th>
						<td id="place_name">${diaryDetail.place_name }</td>
					</tr>
					<tr>
						<th class="">주소</th>
						<td id="address" colspan="3">${diaryDetail.address }</td>
					</tr>
					<tr>
						<th class="">내용</th>
						<td id="contents" colspan="3">${diaryDetail.contents }</td>
					</tr>
					<tr>
						<th class="">기타</th>
						<td colspan="3" id="etc">
							<span id="drone">${diaryDetail.drone }</span>
							<span id="publicTran">${diaryDetail.public_tran }</span>
							<span id="publicInfo">${diaryDetail.public_info }</span>
							<span id="park">${diaryDetail.park }</span>
							<span id="parkInfo">${diaryDetail.park_info }</span>
							<span id="toilet">${diaryDetail.toilet }</span>
							<span id="locker">${diaryDetail.locker }</span>
							<span id="shower">${diaryDetail.shower }</span>
						</td>
					</tr>
					<tr>
						<th colspan="4">사진</th>
					</tr>
					<tr>
						<td colspan="4" class="picture">
							<img src="img/diary/${diaryDetail.img[0]}" alt="">
							<img src="img/diary/${diaryDetail.img[1]}" alt="">
							<img src="img/diary/${diaryDetail.img[2]}" alt="">
							<img src="img/diary/${diaryDetail.img[3]}" alt="">
							<img src="img/diary/${diaryDetail.img[4]}" alt="">
						</td>
					</tr>
				</table>
			</div>

			<div class="report_history_wrap">
				<h4 class="box_title">신고 내역</h4>
				<table class="detail_list">
					<tbody>
						<tr>
							<th>신고받은 횟수</th>
							<!-- 신고이력보기 버튼 클릭 시 신고관리 페이지에서 해당 글번호로 자동 검색된 결과 보여주기 가능? -->
							<td>
								<span id="reportedCnt">${reportedCnt }</span>회 
								<button id="viewReportHistoryBtn" class="btn_1" onclick="viewReportHistory();">신고이력보기</button>
							</td>
						</tr>
					</tbody>
				</table>
			</div>

			<%
				if((int)request.getAttribute("reportListSize") > 0) {
					ArrayList<ReportDto> reportList = 
							(ArrayList<ReportDto>)request.getAttribute("reportList");
					
			%>
				<div class="report_mng_wrap">
					<h4 class="box_title">신고 관리</h4>
					<table class="detail_list">
				<% for(ReportDto dto : reportList) { %>
					<tr data-idx="<%=dto.getReportIdx() %>">
						<th>신고처리</th>
						<td class="report_mng_box">
							<p>
								<label for="">신고내용</label>
								<span id="reportContents"><%=dto.getContents() %></span>
							</p>
							<p>
								<label for="reportMngCon">처리내용</label>
								<input type="text" name="mngContents" id="reportMngCon"/>
							</p>
							<p>
								<label for="diaryStat">다이어리 상태</label>
								<select id="diaryStat" name="diaryStat">
									<option value="R">신고 글</option>
									<option value="Y">신고 해제</option>
								</select>
							</p>
							<p>
								<label for="">신고처리</label>
								<button class="btn_1" id="reportMngBtn" onclick="mngReport(event);">처리 완료</button>
							</p>
						</td>
					</tr>
				<% } %>
				</table>
			</div>
		<% } %>
		</div>
	</div>
</body>
</html>