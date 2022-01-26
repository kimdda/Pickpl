var pageIdx, pageCnt;
var listToShow = 10;

$(function() {
	$("nav").load("/Pickpl/admin/admin_nav.html");
	
	pageIdx = $("#paging").val();
	
	if(sessionStorage.getItem("searchCondition") != null) {
		var condition = sessionStorage.getItem("searchCondition");
		var keyword = sessionStorage.getItem("keyword");
		$("#searchCondition").val(condition);
		$(".search_text").val(keyword);
		
		fetchList();
		
		sessionStorage.removeItem("searchCondition");
		sessionStorage.removeItem("keyword");
	}
	
	// 검색 버튼
	$("#searchBtn").click(function() {
		pageIdx = 1;
		$("#paging").val(pageIdx);
		fetchList();
	});
	
});

// 검색
function fetchList() {
	var type = $("[name='type']:checked").val();
	var stat = $("[name='stat']:checked").val();
	var condition = $("#searchCondition").val();
	var keyword = $(".search_text").val();
	
	
	if(!keyword) condition = "";
		
//	 console.log(stat + " / "+ type + " / " + condition + " / " + keyword);
	$.ajax({
		url: '/Pickpl/adminController',
		type: 'post',
		data: {
			'command': 'reportList',
			'pageIdx': pageIdx,
			'type' : type,
			'stat': stat,
			'condition': condition,
			'keyword': keyword
		},
		dataType: 'json',
		success: function(data){
			//console.log(data);
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
					report_type = "";
					switch(item.type) {
					case "U" :
						report_type = "계정 신고";
						break;
					default :
						report_type= "글 신고";
						break;
					}
					
					report_stat = "";
					switch(item.stat) {
						case "N" :
							report_stat = "접수";
							break;
						default :
							report_stat = "완료";
							break;
					}
					
					html = 
					'<tr onclick="toDetail(event);" data-idx="' + item.idx + '">' +
						'<td class="index">' + (paging + index + 1) + '</td>' +
						'<td class="report_date">' + item.report_date + '</td>' +
						'<td class="report_id">'+ item.report_id +'</td>' + 
						'<td class="report_type">' + report_type + '</td>';
						
					if(item.type == "U")
						html += '<td class="report_id">'+ item.target_id +'</td>';
					else
						html += '<td class="report_id">'+ item.target_d_id +'</td>';
						
					html +=	'<td class="report_stat">' + report_stat + '</td>' +
						'</tr>';
					$table.append(html);
				});
			}
		},
		error : function(request, status, error) {
			alert("list error : " + request + "status : " + status + " error : " + error);
		}
	});
}

// 리스트 선택 시 페이지 이동
function toDetail(e) {
	var idx = $(e.target).parents("tr").data("idx");
	//console.log(idx);
	if(!$("#form").length) {
		html = 
		'<form action="../adminController" method="post" id="form">' +
			'<input type="hidden" name="command" value="reportDetail" />' +
			'<input type="hidden" name="idx" value="'+ idx +'" />' +
		'</form>';
		$(".report_wrap").append(html);
	} else {
		$("[name='dId']").val(idx);	
	}
	$("#form").submit();
}