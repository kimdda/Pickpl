<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="../css/admin_common.css">
	<!-- <link rel="stylesheet" href="../css/admin_style_sj.css"> -->
	<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap" rel="stylesheet">
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
	<script src="js/report.js"></script>
	<title>픽플 관리자</title>
</head>
<body>
	<div class="wrap">
		<nav></nav>
		<!-- 관리자페이지 - 신고관리  -->
		<div class="main report_wrap diarepo_wrap">
			<div class="search_container">
				<h4 class="box_title">신고 관리</h4>
				<div class="box search_box">
					<div class="search_stat option_box">
						<p class="searchFont">신고 분류</p>
						<div class="option_selection">
							<p>
								<label><input type="radio" name="type" value="all" checked/> 전체</label>
							</p>
							<p>
								<label><input type="radio" name="type" value="U" /> 사용자 신고</label>
							</p>
							<p>
								<label><input type="radio" name="type" value="D" /> 글 신고</label>
							</p>
						</div>
					</div>

					<div class="search_stat option_box">
						<p class="searchFont">처리 상태</p>
						<div class="option_selection">
							<p>
								<label><input type="radio" name="stat" value="all" checked/> 전체</label>
							</p>
							<p>
								<label><input type="radio" name="stat" value="N" /> 접수</label>
							</p>
							<p>
								<label><input type="radio" name="stat" value="Y" /> 완료</label>
							</p>
						</div>
					</div>

					<div class="search_input option_box">
						<p class="">검색하기</p>
						<select name="condition" id="searchCondition">
							<option value="report_idx">신고 번호</option>
							<option value="report_id">신고자 아이디</option>
							<option value="target_id">신고대상 아이디</option>
							<option value="target_d_id">신고글 번호</option>
						</select>
						<input type="text" name="keyword" class="search_text"></input>	
					</div>
				</div>
				<div class="search_btn_box" id="">
					<button class="btn_3" id="searchBtn">검색</button>
				</div>
			</div>

			<p class="no_result">검색 결과가 없습니다.</p>
			<div class="result_box">
				<p><span class="result_cnt">00</span>건의 검색결과</p>
				<table>
					<thead>
						<tr>
							<!-- 테이블 머리. css에서 클래스 별로 너비 조정 -->
							<th class="index">번호</th>
							<th class="report_date">신고일</th>
							<th class="report_id">신고자</th>
							<th class="report_type">신고 분류</th>
							<th class="target_id">신고 대상</th>
							<th class="report_stat">처리 상태</th>
						</tr>
					</thead>
					<tbody>
						<!-- 테이블 내용 부분 -->
						<tr>
							<td class="index">1</td>
							<td class="report_date">1</td>
							<td class="report_type">2</td>
							<td class="report_id">3</td>
							<td class="target_id">4</td>
							<td class="report_stat">5</td>
						</tr>
					</tbody>
				</table>
				<div class="paging_box">
                     <button class="prev_btn material-icons" onclick="prevPage();">navigate_before</button> 
                     <input type="text" name="paging" id="paging" value="1" onkeydown="inputPage(event);" /> / <span class="page_cnt"></span> 
                     <button class="next_btn material-icons" onclick="nextPage();">navigate_next</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>