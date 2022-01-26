<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap" rel="stylesheet">
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	<link rel="stylesheet" href="../css/admin_common.css">
	<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
	<script src="js/diary.js"></script>
	<title>픽플 관리자</title>
</head>
<body>
	<div class="wrap">
		<nav></nav>
		<!-- 관리자페이지 - 글관리 -->
		<div class="main diary_wrap diarepo_wrap">
			<h4 class="box_title">글 관리</h4>
			<div class="search_box box">
				<div class="search_stat option_box">
					<p class="">게시 상태</p>
					<div class="option_selection">
						<p>
							
							<label><input type="radio" name="upStat" value="all" checked /> 전체</label>
						</p>
						<p>
							<label><input type="radio" name="upStat" value="Y" /> 게시 중</label>
						</p>
						<p>
							<label><input type="radio" name="upStat" value="N" /> 게시 보류</label>
						</p>
						<p>
							<label><input type="radio" name="upStat" value="R" /> 신고</label>
						</p>
						<p>
							<label><input type="radio" name="upStat" value="D" /> 삭제</label>
						</p>
					</div>
				</div>

				<div class="search_region option_box">
					<p class="">지역</p>
					<div class="option_selection">
						<select name="region" id="byRegion">
		                     <option value="all">전국</option>
		                  </select>
		                  <select name="byRegion" id="byCity">
		                     <option value="all">전체</option>
		                  </select>
					</div>
				</div>

				<div class="search_input option_box">
					<p class="">검색하기</p>
					<select name="condition" id="searchCondition">
						<option value="d_id">글 번호</option>
						<option value="writer_id">작성자 아이디</option>
					</select>
					<input type="text" name="keyword" class="search_text"></input>	
				</div>
			</div>
			<div class="searchBtnArea search_btn_box" id="">
				<button class="btn_3" id="searchBtn">검색</button>
			</div>

			<!-- 검색 결과 -->
			<p class="no_result">검색 결과가 없습니다.</p>
			<div class="result_box">
				<!-- <h4 class="box_title">검색결과</h4> -->
				<p><span class="result_cnt">00</span>건의 검색결과</p>
				<table>
					<thead>
						<tr>
							<th class="index">번호</th>
							<th class="d_id">글번호</th>
							<th class="writer_id">작성자</th>
							<th class="place_name">장소명</th>
							<th class="region">지역(시/도)</th>
							<th class="up_date">게시 날짜</th>
							<th class="up_stat">상태</th>
						</tr>
					</thead>
					<tbody>
						<%--<tr>
							<td class="index">1</td>
							<td class="d_id">345</td>
							<td class="writer_id">idid1</td>
							<td class="place_name">장소명</td>
							<td class="region">경상북도</td>
							<td class="up_date">2021-09-09</td>
							<td class="up_stat">게시 보류</td>
						</tr>--%>
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
	

	<script>
		$("nav").load("admin_nav.html");
		
	</script>
</body>
</html>