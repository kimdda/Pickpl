var pageIdx, pageCnt;
var listToShow = 10;

$(function() {
	$("nav").load("/Pickpl/admin/admin_nav.html");
	
	pageIdx = $("#paging").val();
	
	// 검색 버튼
	$("#searchBtn").click(function() {
		pageIdx = 1;
		$("#paging").val(pageIdx);
		fetchList();
	});
	
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
});

// 검색
function fetchList() {
	var stat = $("[name='upStat']:checked").val();
	var region = $("#byRegion").val();
	region = region.substring(region.indexOf("/")+1, region.length);
	var city = $("#byCity").val();
	var condition = $("#searchCondition").val();
	var keyword = $(".search_text").val();
	
	// console.log(stat + " / " + region + " / " + city + " / " + condition + " / " + keyword);
	if(!keyword) condition = "";
	
	$.ajax({
		url: '../adminController',
		type: 'post',
		data: {
			'command': 'diaryList',
			'pageIdx': pageIdx,
			'stat': stat,
			'region' : region,
			'city' : city,
			'condition': condition,
			'keyword': keyword
		},
		dataType: 'json',
		success: function(data){
			// console.log(data);
			if(data.total == 0) {
				$(".no_result").show();
				$(".result_box").hide();
			} else {
				$table = $(".result_box tbody");
				$table.empty();
				
				$(".no_result").hide();
				$(".result_box").show();
				
				pageCnt = data.pageCnt;
				$(".page_cnt").text(pageCnt);
				$(".result_cnt").text(data.total);
				
				paging = (pageIdx - 1) * listToShow;
				$.each(data.list, function(index, item) {
					up_stat = "";
					switch(item.up_stat) {
						case "D" :
							up_stat = "삭제";
							break;
						case "R" :
							up_stat = "신고";
							break;
						case "N" :
							up_stat = "보류";
							break;
						default :
							up_stat = "게시";
							break;
					}
					
					html = 
					'<tr onclick="toDetail(event);">' +
						'<td class="index">' + (paging + index + 1) + '</td>' +
						'<td class="d_id">' + item.d_id + '</td>' +
						'<td class="writer_id">' + item.writer_id + '</td>' +
						'<td class="place_name">'+ item.place_name +'</td>' +
						'<td class="region">' + item.region + '</td>' +
						'<td class="up_date">' + item.up_date + '</td>' +
						'<td class="up_stat">' + up_stat + '</td>' +
					'</tr>';
					$table.append(html);
				});
			}
		}
	});
}

// 리스트 선택 시 페이지 이동
function toDetail(e) {
	var dId = $(e.target).parents("tr").find(".d_id").text();
	//console.log(id);
	if(!$("#form").length) {
		html = 
		'<form action="../adminController" method="post" id="form" name="form">' +
			'<input type="hidden" name="command" value="diaryDetail" />' +
			'<input type="hidden" name="dId" value="'+ dId +'" />' +
		'</form>';
		$(".diary_wrap").append(html);
	} else {
		$("input[name='dId']").val(dId);
	}
	
	$("#form").submit();
}


// 이전 페이지
function prevPage() {
	$page = $("#paging"); 
	if($page.val() == 1)
		alert("첫 페이지 입니다.");
	if($page.val() > 1) {
		$page.val(--pageIdx);
		fetchList();
	}
}

// 다음 페이지
function nextPage() {
	$page = $("#paging");
	if(pageIdx == pageCnt)
		alert("마지막 페이지 입니다.");
	if($page.val() < pageCnt) {
		$page.val(++pageIdx);
		fetchList();
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
		fetchList();
	}
}