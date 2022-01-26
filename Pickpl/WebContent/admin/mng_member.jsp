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
	<link rel="stylesheet" href="/Pickpl/css/admin_common.css">
	<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
	<script src="/Pickpl/admin/js/member.js"></script>
	<title>픽플 관리자</title>
</head>
<body>
	<div class="wrap">
		<nav></nav>
		<div class="main member_wrap ">
			<!-- 검색조건 박스 -->
			<h4 class="box_title">회원관리</h4>	
			<div class="box search_box memberSearchWrap">
				<div class="option_box">
					<p class="">계정 상태</p>
					<div class="search_day option_selection">
						<p>
							<label><input type="radio" name="acctStat" value="all" checked/> 전체</label>
						</p>
						<p>
							<label><input type="radio" name="acctStat" value="A"/> 활성</label>
						</p>
						<p>
							<label><input type="radio" name="acctStat" value="D" /> 미인증</label>	
						</p>
						<p>
							<label><input type="radio" name="acctStat" value="B" /> 비활성</label>	
						</p>
						<p>
							<label><input type="radio" name="acctStat" value="R" /> 신고 계정</label>	
						</p>
						<p>
							<label><input type="radio" name="acctStat" value="O" /> 탈퇴</label>	
						</p>
					</div>
				</div>
				<div class="option_box">
					<p>검색 조건</p>
					<select name="searchCondition" id="searchCondition">
						<option value="id">아이디</option>
						<option value="name">이름</option>
						<option value="gender">성별</option>
					</select>
					<input type="text" name="searchKeyword" placeholder="검색 단어를 입력하세요." class="search_text">
				</div>
			</div>
			<div class="search_btn_box">
				<button type="button" class="btn_3" id="searchBtn">검색</button>
			</div>
			
			<!-- 검색 결과 -->
			<p class="no_result">검색 결과가 없습니다.</p>
			<div class="result_box">
				<p><span class="result_cnt">00</span>건의 검색결과</p>
				<table class="member_table">
					<thead>
						<tr>
							<th class="index">번호</th>
							<th class="id">아이디</th>
							<th class="name">이름</th>
							<th class="gender">성별</th>
							<th class="birth">생년월일</th>
							<th class="phone">전화번호</th>
							<th class="joinDate">가입일</th>
							<th class="acctStat">계정 상태</th>
						</tr>
					</thead>
					<tbody>
<!-- 						<tr> -->
<!-- 							<td class="index">1</td> -->
<!-- 							<td class="id">aaa</td> -->
<!-- 							<td class="name">아무개</td> -->
<!-- 							<td class="gender">M</td> -->
<!-- 							<td class="birth">2000-01-01</td> -->
<!-- 							<td class="phone">010-1234-5678</td> -->
<!-- 							<td class="joinDate">2021-01-01</td> -->
<!-- 							<td class="acctStat">활성</td> -->
<!-- 						</tr> -->
					</tbody>
				</table>

				<div class="paging_box" id="">
					<button class="prev_btn material-icons" onclick="prevPage();">navigate_before</button> 
                     <input type="text" name="paging" id="paging" value="1" onkeydown="inputPage(event);" /> / <span class="page_cnt">1</span> 
                     <button class="next_btn material-icons" onclick="nextPage();">navigate_next</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>