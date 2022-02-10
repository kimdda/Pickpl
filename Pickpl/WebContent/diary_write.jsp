<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>
<%
	boolean isLogin = false;
	String loginId = (String)session.getAttribute("loginId");
	if(loginId == null || loginId == "") {
		isLogin = false;
	} else {
		isLogin = true;
	}
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>다이어리 작성</title>
	<link rel="icon" type="image/x-icon" href="img/icon/favicon.ico">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	<link rel="stylesheet" href="css/air-datepicker.css">
	<link rel="stylesheet" href="css/common.css">
	<link rel="stylesheet" href="css/style.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
    <script type="text/javascript" src="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script>
	<script src="//cdn.jsdelivr.net/npm/sweetalert2@11"></script>
	<script src="https://developers.kakao.com/sdk/js/kakao.js"></script>
	<script src="js/air-datepicker.js"></script>
	<script src="js/common.js"></script>
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=218e8fedd2cb6770e181a0e8a08311e7&libraries=services"></script>
	<script src="js/diary_write.js"></script>
</head>
<body>
<header>
</header>
<div class="wrap diary_write_wrap" id="writeWrap">
	<h2 class="diary_write_title"></h2>
	<form action="" id="form" method="post" enctype="multipart/form-data">
		<input type="hidden" name="dId" id="dId" value="" />
		<div class="input_wrap">
			<div class="date left_width">
				<label for="visitDate" class="input_label DATE_ICON">방문 날짜 & 시간</label>
				<input type="text" name="visitDate" id="visitDate" class="visit_date" value="${visit_date }" readonly>
				
				<select name="visitTime" id="visitTime" class="visit_time">
					<option value="">시</option>
				</select>								
			</div>
	
			<div class="weather right_width">
				<label class="input_label">날씨</label>
				<button value="0" id="0" class="weather_btn" type="button"></button>
				<button value="1" id="1" type="button" class="weather_btn"></button>
				<button value="2" id="2" type="button" class="weather_btn"></button>
				<button value="3" id="3" type="button" class="weather_btn"></button>
				<button value="4" id="4" type="button" class="weather_btn"></button>
				<button value="5" id="5" type="button" class="weather_btn"></button>
				<button value="6" id="6" type="button" class="weather_btn"></button>
				<input type="hidden" name="weather_id" id="weatherId" value="0"/>
			</div>
	
			<div class="pictures">
				<p class="PHOTO_ICON">사진 업로드 (최대 5장 가능)</p>
				<div class="picture_area">
					<%--<div class="picture_box">
						<label class="img_upload empty">
							<input type="file" name="diary_img" class="diary_img_input" accept="image/*" onchange="setThumbnail(event);"/>
						</label>
						<button type="button" class="material-icons del_btn">clear</button>
					</div>--%>
				</div>
<!-- 				<label for="addImgBtn"><input type="file" name="diary_img" id="addImgBtn" value=""/></label> -->
			</div>
	
			<div class="place left_width">
				<label for="placeName" class="input_label PLACE_ICON">장소명</label>
				<input type="text" name="placeName" id="placeName" class="place_name" value="${place_name }"/>
			</div>
	
			<div class="address right_width">
				<label for="address" class="input_label ADDR_ICON">주소</label>
				<input type="hidden" name="lat" id="lat"/>
				<input type="hidden" name="lng" id="lng"/>
				<input type="text" name="address" id="address" class="addr" onclick="goPopup();" value="${address }"/>
				<button type="button" class="btn" id="searchAddBtn" onclick="goPopup();">주소검색</button>
				<p class="pin_map_btn" onclick="goMap();">지도에서 직접 선택하시려면 여기를 클릭하세요.</p>
			</div>
	
			<div class="contents">
				<p class="CONTENT_ICON"></p>
				<textarea name="contents" class="contents_text" id="contents" value="${contents }"></textarea>
			</div>
	
			<div class="etc">
				<p class="ect_text ETC_ICON">기타 <span class="etc_info_text">(아는 정보만 체크해주세요.)</span></p>
				<input type="hidden" name="drone_db" id= "drone_db" value="${drone}"/>
				<div class="drone">
					<p class="input_label DRONE_SUB_ICON">드론 이용 가능 여부</p>
					<p class="input_box">				
						<input type="checkbox" name="drone" value="Y" id="droneY"/>
						<label for="droneY">가능</label>
					</p>
					<p class="input_box">
						<input type="checkbox" name="drone" value="N" id="droneN"/>
						<label for="droneN">불가능</label>
					</p>
				</div>
				<input type="hidden" name="public_tran_db" id="public_tran_db" value="${public_tran}"/>
				<div class="public_tran">
					<p class="input_label PUBLIC_SUB_ICON">대중교통 이용 접근성</p>
	 				<p class="input_box">
						<input type="checkbox" name="public_tran" value="Y" id="publicY"/>
						<label for="publicY">가능</label>
					</p>
					<p class="input_box">
						<input type="checkbox" name="public_tran" value="N" id="publicN"/>
						<label for="publicN">불가능</label>
					</p>
				</div>
				<input type="hidden" name="public_info_db" id="public_info_db" value="${public_info}"/>
				<div class="public_info">
					<p class="input_label PUBLICINFO_SUB_ICON">대중교통 추가 정보</p>
					<p class="input_box">
						<input type="checkbox" id="less10" name="public_info" value="less10m"/>
						<label for="less10">10분 미만</label>
					</p>
					<p class="input_box">
						<input type="checkbox" id="less20" name="public_info" value="less20m"/>
						<label for="less20">20분 미만</label>
					</p>
					<p class="input_box">
						<input type="checkbox" id="more20" name="public_info" value="more20m"/>
						<label for="more20">20분 이상</label>
					</p>
				</div>
				<input type="hidden" name="park_db" id="park_db" value="${park}"/>
				<div class="park">
					<p class="input_label PARK_SUB_ICON">주차정보</p>
					<p class="input_box">
						<input type="checkbox" id="parkY" name="park" value="Y"/>
						<label for="parkY">가능</label>
					</p>
					<p class="input_box">
						<input type="checkbox" id="parkN" name="park" value="N"/>
						<label for="parkN">불가능</label>
					</p>
				</div>
				<input type="hidden" name="park_info_db" id="park_info_db" value="${park_info}"/>
				<div class="park_info">
					<p class="input_label PARKINFO_SUB_ICON">주차 추가 정보</p>
					<p class="input_box">
						<input type="checkbox" id="parkF" name="park_info" value="F"/>
						<label for="parkF">무료</label>
					</p>
					<p class="input_box">
						<input type="checkbox" id="parkP" name="park_info" value="P"/>
						<label for="parkP">유료</label>
					</p>
				</div>
				<input type="hidden" name="toilet_db" id="toilet_db" value="${toilet}"/>
				<div class="toilet">
					<p class="input_label TOILET_SUB_ICON">공중화장실</p>
					<p class="input_box">
						<input type="checkbox" id="toiletY" name="toilet" value="Y"/>
						<label for="toiletY">있음</label>
					</p>
					<p class="input_box">
						<input type="checkbox" id="toiletN" name="toilet" value="N"/>
						<label for="toiletN">없음</label>
					</p>
				</div>
				<input type="hidden" name="locker_db" id="locker_db" value="${locker}"/>
				<div class="locker">
					<p class="input_label LOCKER_SUB_ICON">짐보관락커</p>
					<p class="input_box">
						<input type="checkbox" id="lockerY" name="locker" value="Y"/>
						<label for="lockerY">있음</label>
					</p>
					<p class="input_box">
						<input type="checkbox" id="lockerN" name="locker" value="N"/>
						<label for="lockerN">없음</label>
					</p>
				</div>
				<input type="hidden" name="shower_db" id="shower_db" value="${shower}"/>
				<div class="shower" id="shower">
					<p class="input_label SHOWER_SUB_ICON">샤워실</p>
					<p class="input_box">
						<input type="checkbox" id="showerY" name="shower" value="Y"/>
						<label for="showerY">있음</label>
					</p>
					<p class="input_box">
						<input type="checkbox" id="showerN" name="shower" value="N"/>
						<label for="showerN">없음</label>
					</p>
				</div>
			</div>
		</div>
		
		<div class="diary_write_btn">
			<button type="button" class="back_btn outline_btn">취소하기</button>
			<button type="button" class="save_btn btn">저장하기</button>
		</div>
	</form>
</div>
<footer></footer>

	<script type="module">
		import localeKo from './js/ko.js';
		
		var today = new Date();
		dp = new AirDatepicker("#visitDate",{
			locale: localeKo,
			maxDate: new Date(),
			navTitles: {
        		days: '<strong><span>yyyy</span>년</strong> <i><span>MM</span>월</i>',
				months: '<strong>yyyy</strong>년' 
    		},
			onSelect ({date, formattedDate, datepicker}) {
				dp.hide();
			}
		});
	</script>
</body>
</html>