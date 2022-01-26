var pageIdx, pageCnt;
var today = new Date().setHours(0,0,0);

$(function() {
	$("nav").load("/Pickpl/admin/admin_nav.html");
	
	pageIdx = $(".reco_wrap #paging").val();
	
	fetchList();
});
//삭제 버튼
function delList(e) {
	var no = $(e.target).parents("tr").attr("data-no");
	if(confirm("삭제하시겠습니까?")) {
		post_to_url('/Pickpl/adminController',{'command':'deleteRcmnd','no': no});		
	}
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

// 추천 목록 가져오기
function fetchList() {
	$.ajax({
		url: '/Pickpl/adminController',
		type: 'post',
		data: {
			'command': 'rcmndList',
			'pageIdx': pageIdx
		},
		dataType: 'json',
		success: function(data) {
			//console.log(data.list.length);
			if(data.list.length == 0) {
				$(".list_container").hide();
				$(".no_result").show();
			} else {
				var page = (pageIdx - 1) * 10;
				pageCnt = data.pageCnt;
				$(".page_cnt").text(pageCnt);
				$.each(data.list, function(index, item) {
					var stat = showStat(item.openDate, item.closeDate, item.stat);
					
					$("table tbody").append(
						'<tr data-no="'+item.no+'">' +
							'<td class="index">'+ (page + index + 1) +'</td>' +
							'<td class="title">'+ item.title +'</td>' +
							'<td class="diary_cnt">'+ item.dCount +'</td>' +
							'<td class="write_date">'+ (item.upDate).substring(0, item.upDate.indexOf(" ")) +'</td>' +
							'<td class="up_range">' +
								'<span class="from_date">'+ item.openDate +'</span> ~ ' +
								'<span class="to_date">'+ item.closeDate +'</span>' +
							'</td>' +
							'<td class="stat">'+ stat +'</td>' +
							'<td class="func"><button class="mdfy_btn btn_1" onclick="manageList(event);">관리</button>' +
							'<button class="del_btn btn_4" onclick="delList(event);">삭제</button></td>' +
						'</tr>'
					);
				});
				$(".list_container").show();
				$(".no_result").hide();
			}
			
		}
	})
}

// 관리
function manageList(event) {
	var no = $(event.target).parents("tr").data("no");
	//console.log(no);
	post_to_url('/Pickpl/adminController',{'command':'mngRcmnd','no': no});
}

// 게시 상태
function showStat(open, close, stat) {
	open_date = new Date(open).setHours(0,0,0);
	close_date = new Date(close).setHours(0,0,0);
	if(open_date <= today && close_date >= today) {
		return "게시 중";
	} else {
		if(open_date > today) return "게시 대기";
		if(close_date < today) return "게시 종료";
	}
	
	if(stat == "Y") return "게시 보류";
}

//Post 값 보내기
function post_to_url(path, params, method) {
    method = method || "post"; // 전송 방식 기본값을 POST로
    
    var form = document.createElement("form");
    form.setAttribute("method", method);
    form.setAttribute("action", path);
 
    //히든으로 값을 주입시킨다.
    for(var key in params) {
        var hiddenField = document.createElement("input");
        hiddenField.setAttribute("type", "hidden");
        hiddenField.setAttribute("name", key);
        hiddenField.setAttribute("value", params[key]);
 
        form.appendChild(hiddenField);
    }
 
    document.body.appendChild(form);
    form.submit();
}