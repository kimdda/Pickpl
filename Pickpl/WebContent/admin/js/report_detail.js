var stat,
	type;
$(function() {
	$("nav").load("/Pickpl/admin/admin_nav.html");
	
	$(".mng_report").hide();
	if($("#reportStat").text() == "처리 완료") {
		$(".mng_comp").show();
	} else {
		if($("#reportType").text() == "계정 신고") {
			$(".mng_u_btn").show();
		} else {
			$(".mng_d_btn").show();
		}
	}
	
	// 목록으로
	$("#backBtn").click(function() {
		location.href = "/Pickpl/admin/mng_report.jsp";
	});
	
});

function toMemberDetail() {
	id = $("#targetID").text();
	console.log(id);
	if(!$("#form").length) {
		html = 
		'<form action="/Pickpl/adminController" method="post" id="form">' +
			'<input type="hidden" name="command" value="memberDetail" />' +
			'<input type="hidden" name="id" value="'+ id +'" />' +
		'</form>';
		$(".report_detail_wrap").append(html);
	} else {
		$("[name='id']").val(id);
	}
	$("#form").submit();
}

function toDiaryDetail() {
	dId = $("#targetDId").text();
	if(!$("#form").length) {
		html = 
		'<form action="/Pickpl/adminController" method="post" id="form">' +
			'<input type="hidden" name="command" value="diaryDetail" />' +
			'<input type="hidden" name="dId" value="'+ dId +'" />' +
		'</form>';
		$(".report_detail_wrap").append(html);
	} else {
		$("[name='dId']").val(dId);
	}
	$("#form").submit();
}