var pageIdx, pageCnt;
var added = [];
var selected = [];
var today = new Date().setHours(0,0,0);

$(function() {
	$("nav").load("/Pickpl/admin/admin_nav.html");
	
	// 업데이트/작성 종류별 command 값 추가
	if($("[name='no']").val()) 
		$("[name='command']").val("updateRcmnd");
	else 
		$("[name='command']").val("writeRcmnd");
	
	// 상단 타이틀 수정
	if($("[name='command']").val() == "update_rcmnd")
		$(".box_title").text("추천 목록 수정");
	
	//추가된 다이어리
	for(i=0; i<$(".selected_list tbody tr").length; i++) {
		added.push($(".selected_list tbody tr").eq(i).attr("data-dId"));
	}
	
	// 검색 기간 월 추가
	for(i=1; i<=12; i++) {
		value = ("00"+i).slice(-2);
		$("#byMonth").append('<option value="'+ value +'">'+ i +'월</option>')
	}
	
	// 게시 상태 변경 버튼 보이기
	showStatBtn();
	
	
	/* 시도 불러오기*/
    var xhr = new XMLHttpRequest();     
    var HttpUrl = "http://openapi.nsdi.go.kr/nsdi/eios/service/rest/AdmService/admCodeList.json"; /*URL*/     
    var parameter = '?' + encodeURIComponent("authkey") +"="+encodeURIComponent("15e9e41512e9e3b1dcdd8e"); /*authkey Key*/
     
    xhr.open('GET', HttpUrl + parameter);
    xhr.onreadystatechange = function () {     
        if (this.readyState == 4) {     
			//console.log('Status: '+this.status+' Headers: '+JSON.stringify(this.getAllResponseHeaders())+' Body: '+this.responseText);
			data = JSON.parse(this.responseText).admVOList.admVOList;
			$.each(data, function(index, item) {
				$("#byRegion").append('<option value="'+ item.admCode + "/" + item.lowestAdmCodeNm +'">' + item.lowestAdmCodeNm + '</option>')
			});
		}     
    };     
	xhr.send('');
	
	/*시군구 불러오기*/
	$(document).on("change","#byRegion",function() {
		sido_code = $("#byRegion").val().substring(0, $("#byRegion").val().indexOf("/"));
	    var xhr = new XMLHttpRequest();
	    var HttpUrl = "http://openapi.nsdi.go.kr/nsdi/eios/service/rest/AdmService/admSiList.json"; /*URL*/     
	    var parameter = '?' + encodeURIComponent("authkey") +"="+encodeURIComponent("212d8acc7ed45c2f07c13d"); /*authkey Key*/     
	    parameter += "&" + encodeURIComponent("admCode") + "=" + encodeURIComponent(sido_code); /* 시도 코드(2자리) */  
	    
	    xhr.open('GET', HttpUrl + parameter);
	    xhr.onreadystatechange = function () {     
	        if (this.readyState == 4) {     
				// alert('Status: '+this.status+' Headers: '+JSON.stringify(this.getAllResponseHeaders())+' Body: '+this.responseText);     
				//console.log(JSON.parse(this.responseText));
				data = JSON.parse(this.responseText).admVOList.admVOList;
				html = '<option value="all">전체</option>';
				$.each(data, function(index, item) {
					html += '<option value="'+ item.lowestAdmCodeNm +'">' + item.lowestAdmCodeNm + '</option>'
				});
				$("#byCity").html(html);
			}     
	    };     
		xhr.send('');   
	});
	
	// 날짜 변경 시
	$("input[type='date']").change(function() {
		//console.log("change");
		showStatBtn();
	});
		
	// 검색 버튼 클릭 이벤트
	$("#searchBtn").click(function() {
		pageIdx = 1;
		$("#paging").val(1);
		searchDiary();
	});
      
	// 삭제
	$(".delete_btn").click(function() {
		dId = $(".selected_list tr.selected").attr("data-dId");
		added.splice(added.indexOf(dId), 1);
		$(".selected_list tr.selected").remove();
	});
	
	// 아래로 내리기
	$(".order_down_btn").click(function() {
		row = $(".selected_list tr.selected");
		index = $(".selected_list tr.selected").index();
		
		if(index < $(".selected_list tbody tr").length - 1) {
			row.insertAfter(row.next());
		};
	});
	// 위로 올리기
	$(".order_up_btn").click(function() {
		row = $(".selected_list tr.selected");
		index = $(".selected_list tr.selected").index();
		
		if(index > 0) {
			row.insertBefore(row.prev());
		};
	});
	
	//게시 상태 변경 버튼 
	$(".hold_btn").click(function() {
		var $input = $("input[name='hold']");
		var $tag = $("#stat");
		if($(this).hasClass("hold_Y_btn")) {
			$input.val("Y");
		} else {
			$input.val("");
		}
		showStatBtn();
	});
	
	//취소
	$(".cancel_btn").click(function() {
		check = "변경사항이 저장되지 않습니다.\n취소하시겠습니까?";
		if(confirm(check) == true) {
			$("input").val("");
			history.back();
		}
	});
	
	// 선택 추가하기 버튼 이벤트
	$("#addBtn").click(function() {
		var $selected = $(".result_list tbody tr.selected");
		for(i=0; i<$selected.length; i++) {
			dId = $selected.eq(i).attr("data-dId");
			if(added.indexOf(dId) == -1) {
				added.push(dId);
				clone = $selected.eq(i).clone();
				clone.attr("onclick", "listSelect(event)");
				clone.removeClass("selected");
				clone.find(".index").text(added.length);
				$(".selected_list tbody").append(clone);
			}
		}
		selected.splice(0, selected.length);
		//$(window).scrollTop($(".selected_list").offset().top - 50);
		$("html, body").animate({scrollTop : $(".selected_list").offset().top - 50});
	});
	
});

// 저장
function saveCheck() {
	var title = $("#title").val();
	if(!title) {
		alert("제목을 입력하세요.");
		return false;
	}
	
	var open_date = $("#open_date").val();
	if(!open_date) {
		alert("게시 시작 날짜를 입력하세요.");
		return false;
	}
	var close_date = $("#close_date").val();
	if(!close_date) {
		alert("게시 종료 날짜를 입력하세요.");
		return false;
	}
	// 선택한 글 목록
	$selected = $(".selected_list tbody tr");
	var d_id = "";
	for(i=0; i<$selected.length; i++) {
		d_id += $selected.eq(i).attr("data-dId") + "_";
	}
	var count = $selected.length;
	if(count == 0) {
		alert("추천 글을 추가해주세요.");
		return false;
	}
	
	var stat;
	if(new Date(close_date).setHours(0,0,0) < new Date(open_date).setHours(0,0,0)) {
		alert("종료일은 시작일 이후로 지정해주세요.");
		return false;
	}
	if($("#stat").length) {
		stat = $("[name='hold']").val();
	} else {	
		if(new Date(open_date).setHours(0,0,0) <= new Date().setHours(0,0,0)) {
			console.log("state Y");
		}
	}
	
	$(".writing_box").append('<input type="hidden" name="d_id" value="'+ d_id +'"/>');
	$(".writing_box").append('<input type="hidden" name="count" value="'+ count +'"/>');
	
	if(confirm("저장하시겠습니까?")) {
		return true;
	} else {
		return false;
	}
}

// 리스트 선택 이벤트
function listSelect(e) {
//	console.log($(e.target).hasClass("view_btn"));
	if($(e.target).hasClass("view_btn")) {
		dId = $(e.target).parents("tr").attr("data-dId");
		window.open("Controller?command=diaryDetail&dId="+dId, "_blank", "resizable=yes,top=0,left=0,width=1200,height=600");
	} else {
		target = $(e.target).parents("tr");
		if(target.hasClass("selected")) {
			target.removeClass("selected");
		} else {
			$("tbody tr").removeClass("selected");
			target.addClass("selected");
		}		
	}
}

// 리스트 다중 선택 이벤트
function listMultipleSelect(e) {
	dId = $(e.target).attr("data-dId");
	if($(e.target).hasClass("view_btn")) {
		dId = $(e.target).parents("tr").attr("data-dId");
		window.open("Controller?command=diaryDetail&dId="+dId, "_blank", "resizable=yes,top=0,left=0,width=1200,height=500");
	} else {
		target = $(e.target).parents("tr");
		if(target.hasClass("selected")) {
	        target.removeClass("selected");
	        index = selected.indexOf(dId);
			selected.splice(index, 1);
		} else {
			target.addClass("selected");
			selected.push(dId);
		}
		$("#selected_cnt").text(selected.length);		
	}
}

// 다이어리 검색
//function searchDiary(pageIdx, month, region, city) {
function searchDiary() {
	var month = $("#byMonth").val();
	var region = $("#byRegion").val();
	if(region != 'all')
		region = region.slice(region.indexOf("/") + 1);
	
	var city = $("#byCity").val();
	if(city != 'all')
		city = city.slice(city.indexOf("/") + 1);
	
	pageIdx = $("#paging").val();
	
	$.ajax({
		url: '/Pickpl/adminController',
		type: 'post',
		data: {
			'command': 'searchDiary', 
			'pageIdx': pageIdx,
			'month' : month,
			'region' : region,
			'city' : city
		},
		dataType: 'json',
		success: function(data) {
			if(!data.total) {
				$(".no_result").show();
				$(".result_box").hide();
			} else {
				pageCnt = data.pageCnt;
				$(".page_cnt").text(pageCnt);
				$(".result_cnt").text(data.total);
				
				$(".result_box tbody").empty();
				$(".no_result").hide();
				$(".result_box").show();
				
				$.each(data.list, function(index, item) {
					diaryBox(index, item);
				});
				
//				$(window).scrollTop($(".result_box").offset().top);
				$("html, body").animate({scrollTop: $(".result_box").offset().top});
			}
		}
	});
}

// 다이어리 검색 결과 리스트 생성
function diaryBox(index, item) {
	var page = (pageIdx - 1) * 10;
	if(added.indexOf(""+item.dId) == -1) {
		html = '<tr data-dId="'+ item.dId +'" onclick="listMultipleSelect(event);">';						
	} else {
		html = '<tr data-dId="'+ item.dId +'" class="selected" onclick="listMultipleSelect(event);">';						
	}
		
	html += 
		'<td class="selection"></td>' +
		'<td class="index">' + (page + index + 1) + '</td>' +
		'<td class="writer_id">' + item.writer + '</td>' +
        '<td class="pick_cnt">' + item.pick_count + '</td>' +
        '<td class="view_cnt">' + item.view_count + '</td>' +
		'<td class="visit_date">' + item.visit_date + '</td>' +
		'<td class="place_name">' + item.place_name + '</td>' +
        '<td class="region">' + item.region + '</td>' +
        '<td class="view"><button class="view_btn btn_2">보기</button></td>' +
     '</tr>';

	$(".result_box tbody").append(html);
}

// 게시 상태 관련
function showStatBtn() {
	open_date = new Date($("#open_date").val()).setHours(0,0,0);
	close_date = new Date($("#close_date").val()).setHours(0,0,0);
	if(open_date <= today && close_date >= today) {
		$("#stat").text("게시 중");
		if($("[name='hold']").val() == 'Y') {
			$(".hold_Y_btn").hide();
			$(".hold_N_btn").show();
		} else {
			$(".hold_N_btn").hide();
			$(".hold_Y_btn").show();
		}
	} else {
		$(".hold_btn").hide();
		if(open_date > today) $("#stat").text("게시 대기");
		if(close_date < today) $("#stat").text("게시 종료");
	}
	
	if($("[name='hold']").val() == "Y") $("#stat").text("게시 보류");
}

// 이전 페이지
function prevPage() {
	$page = $("#paging"); 
	if($page.val() == 1)
		alert("첫 페이지 입니다.");
	if($page.val() > 1) {
		$page.val(--pageIdx);
		searchDiary();
	}
}

// 다음 페이지
function nextPage() {
	$page = $("#paging");
	if(pageIdx == pageCnt)
		alert("마지막 페이지 입니다.");
	if($page.val() < pageCnt) {
		$page.val(++pageIdx);
		searchDiary();
	}
}

// 이동 페이지 입력
function inputPage(e) {
	if(e.keyCode == 13) {
		//console.log(e.target);
		if(e.target.value < 1 || e.target.value > pageCnt) {
			alert("페이지 범위 내에서 입력해 주세요.");
			return false;
		}
		
		pageIdx = e.target.value;
		searchDiary();
	}
}