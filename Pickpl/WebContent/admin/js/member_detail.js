var id;
$(function() {
	$("nav").load("/Pickpl/admin/admin_nav.html");

	id = $("#id").text();
	
	// 신고이력 없으면 버튼 숨기기
	if($("#reportedCnt").text() == "0")
		$("#viewReportHistoryBtn").hide();

	acctStatBtn();
	
	// 계정 활성화 / 비활성화 버튼
	$("#statBtn").click(function() {
		var toStat = $("#statBtn").attr("data-stat");
		var statText = "비활성화";
		if(toStat == "A") statText = "활성화";
		if(confirm(`계정 상태를 ${statText} 하겠습니까?`)) {
			$.ajax({
				url: '/Pickpl/adminController',
				type: 'post',
				data: {
					'command': 'mdfyAccStat', 
					'id': id,
					'stat' : toStat
				},
				dataType: 'json',
				success: function(data) {
					//console.log(data);
					if(data.update == "success") {
						alert(`계정이 ${statText} 되었습니다.`);
						location.reload();
					}
				}
			});
		}
	});
	
	// 이메일 수정
	$("#mdfyMailBtn").click(function() {
		$("#mdfyEmail").val($("#email").text());
		$(".email_box .view_mode").hide();
		$(".email_box .mdfy_mode").show();
	});
	
	//이메일 수정 취소
	$("#cancelBtn").click(function() {
		$("#mdfyEmail").val("");
		$(".email_box .view_mode").show();
		$(".email_box .mdfy_mode").hide();
	});
	
	// 이메일 수정 저장
	$("#confirmBtn").click(function() {
		newEmail = $("#mdfyEmail").val();
		confirmText = `아이디: ${id}` +
					`\n이메일 : ${newEmail}` +
					`\n수정 내용을 저장하시겠습니까?`;
		if(confirm(confirmText)) {
			$.ajax({
				url: 'adminController',
				type: 'post',
				data: {
					'command': 'mdfyEmail', 
					'id' : id,
					'newEmail' : newEmail
				},
				dataType: 'json',
				success: function(data) {
					//console.log(data);
					if(data.update == "success") {
						alert('이메일 수정이 완료되었습니다.');
						location.reload();
					}
					$("#mdfyEmail").val("");
					$(".email_box .view_mode").show();
					$(".email_box .mdfy_mode").hide();
				}
			});
		}
	});
	
	// 목록으로
	$("#backBtn").click(function() {
		location.href = "/Pickpl/admin/mng_member.jsp";
	});	
});

// 신고 이력 보기  -- 추가하기
function viewReportHistory() {
	sessionStorage.setItem("searchCondition", "target_id");
	sessionStorage.setItem("keyword", id);
	url = "/Pickpl/admin/mng_report.jsp";
	var win = window.open(url, "PopupWin", "width=1000,height=600");
}

// 신고 처리
function mngReport(e) {
	var idx = $(e.target).parents("tr").attr("data-idx");
	var mngContents = $(e.target).parents("tr").find("#reportMngCon").val();
	var acctStat = $("#memberStat").val();
	var statText = "신고 계정";
	if(acctStat == "A") statText = "활동 계정";
	
	if(!mngContents) {
		alert("처리 내용 입력해 주세요.");
	} else {
		confirmText = `신고번호 : ${idx}` +
				`\n신고대상 : ${id}` +
				`\n처리내용 : ${mngContents}` +
				`\n계정상태 : ${statText}` +
				`\n신고 처리를 완료하시겠습까?`;
		if(confirm(confirmText)) {
			$.ajax({
				url: '/Pickpl/adminController',
				type: 'post',
				data: {
					'command': 'mngMemberReport',
					'idx': idx,
					'mngCon' : mngContents,
					'acctStat' : acctStat,
					'id' : id
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

// 계정 활성화 / 비활성화 버튼
function acctStatBtn() {
	var $btn = $("#statBtn");
	var acctStat = $("#acctStat").text();
	
	if(acctStat == "활성" || acctStat == "비활성" || acctStat == "신고") {
		$btn.show();
		if(acctStat == "활성") {
			$btn.text("계정 비활성화").attr("data-stat", "B");
		} else if(acctStat == "비활성") {
			$btn.text("계정 활성화").attr("data-stat", "A");
		} else {
			$btn.text("신고 계정 해제").attr("data-stat", "A");				
		}
	} else {
		$btn.hide();
		$("#mdfyMailBtn").hide();
	}
}