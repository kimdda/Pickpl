<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="/Pickpl/css/admin_common.css">
	<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap" rel="stylesheet">
	<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
	<script src="/Pickpl/admin/js/report_detail.js"></script>
	<title>픽플 관리자</title>
</head>
<body>
	<div class="wrap">
		<nav></nav>
		<div class="main report_detail_wrap">
			<h4 class="box_title">신고 상세 관리</h4>
			<table class="detail_list">
				<tr>
					<th class="">신고분류</th>
					<td id="reportType">${detail.reportType }</td>
					<th class="">처리상태</th>
					<td id="reportStat">${detail.stat }</td>
				</tr>
				<tr>
					<th class="">신고일</th>
					<td id="reportDate">${detail.reportDate }</td>
					<th class="">신고자ID</th>
					<td id="reportID">${detail.reportId }</td>
				</tr>
				<tr>
					<th class="">신고대상ID</th>
					<td id="targetID">${detail.targetId }</td>
					<th class="">신고대상 글번호</th>
					<td id="targetDId">${detail.targetDId }</td>
				</tr>
				<tr>
					<th colspan="4">신고내용</th>
				</tr>
				<tr>
					<td colspan="4" class="reportContents">${detail.contents }</td>
				</tr>
				<tr>
					<th colspan="4">신고 처리 내용</th>
				</tr>
				<tr>
					<td colspan="4" class="reportContents">
						<p class="mng_report mng_comp">${detail.mngContents }</p>
						<button class="btn_3 mng_report mng_u_btn" onclick="toMemberDetail();">회원 관리 이동</button>
						<button class="btn_3 mng_report mng_d_btn" onclick="toDiaryDetail();">글 관리 이동</button>
					</td>
				</tr>
			</table>
			<div class="back">
				<button class="btn_4" id="backBtn">뒤로가기</button>
			</div>
		</div>
	</div>
</body>
</html>