var dId;
$(function() {
	$("nav").load("/Pickpl/admin/admin_nav.html");
	
	$(".picture img").each(function() {
		if($(this).attr("src") == "img/diary/") $(this).hide();
	});
	
	$("#etc span").each(function() {
		if($(this).text() != "") $(this).addClass("selected");
	});
	
	// 신고이력 없으면 버튼 숨기기
	if($("#reportedCnt").text() == "0")
		$("#viewReportHistoryBtn").hide();

	dId = $("#dId").text();

	diaryStatBtn();
	
	// 글 게시 상태 변경 버튼
	$("#statBtn").click(function() {
		var toStat = $("#statBtn").attr("data-stat");
		var statText = "게시 보류";
		if(toStat == "Y") statText = "게시";
		if(confirm(`다이어리 상태를 ${statText}로 변경하겠습니까?`)) {
			$.ajax({
				url: '/Pickpl/adminController',
				type: 'post',
				data: {
					'command': 'mdfyDiaryStat', 
					'dId': dId,
					'stat' : toStat
				},
				dataType: 'json',
				success: function(data) {
					//console.log(data);
					if(data.update == "success") {
						alert(`다이어리 상태를 ${statText}로 변경했습니다.`);
						location.reload();
					}
				}
			});
		}
	});
	
	// 목록으로
	$("#backBtn").click(function() {
		location.href = "/Pickpl/admin/mng_diary.jsp";
	});
	
	
});

// 신고 이력 보기  -- 추가하기
function viewReportHistory() {
	sessionStorage.setItem("searchCondition", "target_d_id");
	sessionStorage.setItem("keyword", dId);
	url = "/Pickpl/admin/mng_report.jsp";
	var win = window.open(url, "PopupWin", "width=1000,height=600");
}


// 신고 처리
function mngReport(e) {
	var idx = $(e.target).parents("tr").attr("data-idx");
	var mngContents = $(e.target).parents("tr").find("#reportMngCon").val();
	var diaryStat = $("#diaryStat").val();
	var statText = "신고 글";
	if(diaryStat == "Y") statText = "게시";
	
	if(!mngContents) {
		alert("처리 내용 입력해 주세요.");
	} else {
		confirmText =
				 `신고번호 : ${idx}` +
				`\n신고대상 : ${dId}` +
				`\n처리내용 : ${mngContents}`+
				`\n게시 상태 : ${statText}` +
				'\n신고 처리를 완료하시겠습까?';
		if(confirm(confirmText)) {
			$.ajax({
				url: '/Pickpl/adminController',
				type: 'post',
				data: {
					'command': 'mngDiaryReport',
					'idx': idx,
					'mngCon' : mngContents,
					'upStat' : diaryStat,
					'dId' : dId
				},
				dataType: 'json',
				success: function(data) {
					//console.log(data);
					if(data.update == "success") {
						alert('신고 처리가 완료되었습니다.');
						location.reload();
					}
				}
			});
		}
	}
}

// 다이어리 게시 상태
function diaryStatBtn() {
	var $btn = $("#statBtn");
	var diaryStat = $("#upStat").text();
	
	if(diaryStat == "삭제") {
		$btn.hide();
	} else {
		$btn.show();
		if(diaryStat == "게시") {
			$btn.text("게시 중지").attr("data-stat", "N");
		} else if(diaryStat == "보류") {
			$btn.text("게시").attr("data-stat", "Y");
		} else {
			$btn.text("신고 해제").attr("data-stat", "Y");				
		}
	}
}