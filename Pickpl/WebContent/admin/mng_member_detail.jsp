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
	<!-- <link rel="stylesheet" href="../css/admin_style_cha.css"> -->
	<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap" rel="stylesheet">
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	<link rel="stylesheet" href="/Pickpl/css/admin_common.css">
	<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
	<script src="/Pickpl/admin/js/member_detail.js"></script>
	<title>픽플 관리자</title>
</head>
<body>
	<div class="wrap">
		<nav></nav>
		<div class="main member_detail_wrap">
			<div class="func_btn_box">
				<button class="btn_4" id="backBtn">목록으로</button>
				<button id="statBtn" class="btn_2">계정 비활성화</button>
			</div>
			<div class="member_info">
				<h4 class="box_title">회원 상세</h4>
				<table class="detail_list">
					<thead>
						<colgroup>
							<col width="15%">
							<col width="35%">
							<col width="15%">
							<col width="35%">
						</colgroup>
					</thead>
					<tbody>
						<tr>
							<th>아이디</th>
							<td id="id">${memberInfo.id }</td>
							<th>계정상태</th>
							<td>
								<span id="acctStat">${memberInfo.acctStat }</span>
							</td>
						</tr>	
						<tr>
							<th>이름</th>
							<td id="name">${memberInfo.name }</td>
							<th>성별</th>
							<td id="gender">${memberInfo.gender }</td>
						</tr>
						<tr>
							<th>전화번호</th>
							<td id="phone">${memberInfo.phone }</td>
							<th>이메일</th>
							<td class="email_box">
								<p class="view_mode">
									<span id="email">${memberInfo.email }</span>
									<button id="mdfyMailBtn" class="btn_1">수정</button>
								</p>
								<p class="mdfy_mode">
									<input type="text" name="email" id="mdfyEmail" />
									<br/>
									<button id="cancelBtn" class="btn_4">취소</button>
									<button id="confirmBtn" class="btn_1">완료</button>
								</p>
							</td>
						</tr>
						<tr>
							<th>생년월일</th>
							<td id="birth">${memberInfo.birth }</td>
							<th>광고 메일 동의</th>
							<td id="cfMail">${memberInfo.cfMail }</td>
						</tr>
					</tbody>
				</table>
			</div>
			
			<div class="member_log">
				<h4 class="box_title">회원 활동 이력</h4>
				<table class="member_act_history detail_list" >
					<thead>
						<colgroup>
							<col width="15%">
							<col width="35%">
							<col width="15%">
							<col width="35%">
						</colgroup>
					</thead>
					<tbody>
						<tr>
							<th colspan="">pick 한 수</th>
							<td><span id="pickCnt">${pickInfo }</span>개</td>
							<th colspan="">받은 pick 수</th>
							<td><span id="pickedCnt">${actInfo.picked_count }</span>개</td>
						</tr>
						<tr>
							<th colspan="">등록한 글 수</th>
							<td><span id="upDiaryCnt">${actInfo.up_count }</span>개</td>
							<th colspan="">삭제한 글 수</th>
							<td><span id="delDiaryCnt">${actInfo.del_count }</span>개</td>
						</tr>
					</tbody>
				</table>
			</div>

			<div class="report_history_wrap">
				<h4 class="box_title">신고 내역</h4>
				<table class="detail_list">
					<tbody>
						<tr>
							<th>계정 신고받은 횟수</th>
							<!-- 신고이력보기 버튼 클릭 시 신고관리 페이지에서 해당 글번호로 자동 검색된 결과 보여주기 가능? -->
							<td><span id="reportedCnt">${reportedCnt }</span>회 
							<button id="viewReportHistoryBtn" class="btn_1" onclick="viewReportHistory();">신고이력보기</button></td>
						</tr>
					</tbody>
				</table>
			</div>
			
			<%
				int reportListSize = (Integer)request.getAttribute("reportListSize");
				if(reportListSize > 0) {
					ArrayList<ReportDto> reportList = (ArrayList<ReportDto>)request.getAttribute("reportList");
			%>
			<div class="report_mng_wrap">
				<h4 class="box_title">계정 신고 관리</h4>
				<table class="detail_list">
				<%
				for(ReportDto dto : reportList) {
 				%>
					<tr data-idx="<%=dto.getReportIdx() %>">
						<th>신고 처리</th>
						<td class="report_mng_box">
							<p>
								<label>신고 내용</label>
								<span id="reportContents"><%=dto.getContents() %></span>
							</p>
							<p>
								<label for="reportMngCon">처리 내용</label>
								<input type="text" name="mngContents" id="reportMngCon"/>
							</p>
							<p>
								<label for="memberStat">계정 상태 변경</label>
								<select id="memberStat" name="memberStat">
									<option value="R">신고 계정</option>
									<option value="A">활동 계정</option>
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
			<%} %>
		</div>
	</div>
</body>
</html>