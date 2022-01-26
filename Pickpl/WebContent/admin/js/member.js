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

});
// 검색
function fetchList() {
	var stat = $("[name='acctStat']:checked").val();
	var condition = $("#searchCondition").val();
	var keyword = $(".search_text").val();
	if(!keyword) condition = "";
	
	$.ajax({
		url: '../adminController',
		type: 'post',
		data: {
			'command': 'memberList', 
			'pageIdx': pageIdx,
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
				
				$(".no_result").hide();
				$(".result_box").show();
				
				pageCnt = data.pageCnt;
				$(".page_cnt").text(pageCnt);
				$(".result_cnt").text(data.total);
				
				$table.empty();
				paging = (pageIdx - 1) * listToShow;
				$.each(data.list, function(index, item) {
					birth = (item.birth).substring(0, (item.birth).indexOf(" "));
					joinDate = (item.joinDate).substring(0, (item.joinDate).indexOf(" "));
					acctStat = "";
					switch(item.acctStat) {
						case "D" :
							acctStat = "미인증";
							break;
						case "O" :
							acctStat = "탈퇴";
							break;
						case "R" :
							acctStat = "신고 계정";
							break;
						case "B" :
							acctStat = "비활성";
							break;
						default :
							acctStat = "활성";
							break;
					}
					
					html = 
					'<tr onclick="toDetail(event);">' +
						'<td class="index">' + (paging + index + 1) + '</td>' +
						'<td class="id">' + item.id + '</td>' +
						'<td class="name">'+ item.name +'</td>' +
						'<td class="gender">' + item.gender + '</td>' +
						'<td class="birth">' + birth + '</td>' +
						'<td class="phone">' + item.phone + '</td>' +
						'<td class="joinDate">' + joinDate + '</td>' +
						'<td class="acctStat">' + acctStat + '</td>' +
					'</tr>';
					$table.append(html);
				});
			}
		}
	});
}

// 리스트 선택 시 페이지 이동
function toDetail(e) {
	var id = $(e.target).parents("tr").find(".id").text();
	//console.log(id);
	if(!$("#form").length) {
		html = 
		'<form action="../adminController" method="post" id="form">' +
			'<input type="hidden" name="command" value="memberDetail" />' +
			'<input type="hidden" name="id" value="'+ id +'" />' +
		'</form>';
		$(".member_wrap").append(html);
	} else {
		$("#form input[name='id']").val(id);
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
		} else {
			fetchList();
		}
	}
}