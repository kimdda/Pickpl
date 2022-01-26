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
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
	<script src="/Pickpl/admin/js/rcmnd.js"></script>
	<title>픽플 관리자 - 추천 목록 관리</title>
	<script>
		if(`${delete}` == "success")
			alert("삭제가 완료되었습니다.");
		if(`${update}` == "success")
			alert("수정이 완료되었습니다.");
		if(`${insert}` == "success")
			alert("추가가 완료되었습니다.");
	</script>
</head>
<body>
	<div class="wrap">
		<nav></nav>
		<div class="main reco_wrap" id="recoWrap">
			<h4 class="box_title">추천 목록 관리</h4>
			<div class="btn_box">
				<button class="add_list_btn btn_3" onclick='location.href = "/Pickpl/admin/mng_new_rcmnd.jsp"'>목록 추가</button>
			</div>
			<p class="no_result">검색 결과가 없습니다.</p>
			<div class="list_container">
				<table>
					<thead>
						<tr>
							<th class="index">번호</th>
							<th class="title">추천 주제 (제목)</th>
							<th class="diary_cnt">글 수</th>
							<th class="write_date">작성일</th>
							<th class="up_range">게시기간</th>
							<th class="stat">게시 상태</th>
							<th class="func">기능</th>
						</tr>
					</thead>
					<tbody>
						<!-- <tr data-no="35">
							<td class="index">1</td>
							<td class="title">12월의 추천 여행지</td>
							<td class="diary_cnt">10</td>
							<td class="write_date">2021-11-23</td>
							<td class="up_range"><span class="from_date">2021-11-27</span> ~ <span class="to_date">2021-12-27</span></td>
							<td class="stat">게시 종료</td>
							<td class="func"><button class="mdfy_btn btn_1">관리</button>
							<button class="del_btn btn_4">삭제</button></td>
						</tr> -->
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