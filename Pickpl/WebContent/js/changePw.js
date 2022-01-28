
function checkPw() {
	// 비밀번호 빈값 체크
	if(!$("#changePw").val()) {
		$("#changePw").focus().addClass("error").next().text("비밀번호를 입력해 주세요.").addClass("error");
		return false;
	}
	
	// 비밀번호 형식 체크
	pwCheck = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,16}$/;
	if(!pwCheck.test($("#changePw").val())) {
		$("#changePw").focus().next().text("비밀번호는 하나 이상의 문자, 숫자, 특수 문자를 포함한 8자 이상으로 입력해주세요.").addClass("error");
	}

	// 비밀번호 확인 빈값 체크
	if(!$("#changePwConfirm").val()) {
		$("#changePwConfirm").focus().addClass("error").next().text("비밀번호 확인을 입력해 주세요.").addClass("error");
		return false;
	}
	
	// 비밀번호 일치 체크
	if($("#changePw").val() != $("#changePwConfirm").val()) {
		$("#changePwConfirm").focus().addClass("error").next().text("비밀번호가 일치하지 않습니다.").addClass("error");
		return false;
	}
	
	$("#changeBtn").find("span").toggleClass("hide");
	$.ajax({
		type: "post",
		url:"Controller",
		data: {
			"command": "changePw", 
			"id":changeForm.changeId.value, 
			"pw": changeForm.changePw.value
		},
		datatype: "json",
		success: function(data) {
			console.log(data);
			if(data.result == "success") {
				changeForm.reset();
				Swal.fire({
					icon: 'success',
					title: '비밀번호가 변경되었습니다.',
					confirmButtonText: '확인',
					confirmButtonColor : '#0ea098'
				}).then(function() {
				    window.location = "index.jsp";
				});
			}
		},
		error : function(request, status, error) {
			alert("비밀번호 변경 에러");
		}
	});
}

function onKeypress() {
	$("input[type='password']").removeClass("error").next().text("").removeClass("error");
}

$(function() {
	// 비밀번호 체크
	$("#changePw").on('blur', function() {
		pwCheck = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,16}$/;
		if(!pwCheck.test($(this).val())) {
			$(this).next().text("비밀번호는 하나 이상의 문자, 숫자, 특수 문자를 포함한 8자 이상으로 입력해주세요.").addClass("error");
		}
		
	});
	
	// 비밀번호 일치 확인
	$("#changePwConfirm").on('keyup', function() {
		if($("#changePwConfirm").val() && $("#changePw").val() != $("#changePwConfirm").val()) {
			$("#changePw").addClass("error");
			$("#changePwConfirm").addClass("error").next().text("비밀번호가 일치하지 않습니다.").addClass("error");
		} else if($("#changePwConfirm").val() && $("#changePw").val() == $("#changePwConfirm").val()) {
			$("input[type='password']").removeClass("error")
			$(this).next().removeClass("error").text("비밀번호가 일치합니다.");
		}
	});
	
	//입력 시 공백문자 지우기
	$("input[type=\"password\"]").keyup(function() {
		this.value = this.value.replace(/ /gi, "");
	});
});