$(function() {
	/*----------------- 회원가입 ------------------*/
	//생년월일
	//년도 추가
	var year = (new Date()).getFullYear();
	for(i=year; i>=1920; i--) {
		$("#birthY").append('<option value="'+ i +'">'+ i +'</option>');
	}
	//월 추가
	for(i=1; i<=12; i++) {
		form = ('00'+i).slice(-2);
		$("#birthM").append('<option value="'+ form +'">' + form + '</option>');
	}
	//일 추가
	for(i=1; i<=31; i++) {
		form = ('00'+i).slice(-2);
		$("#birthD").append('<option value="'+ form +'">' + form + '</option>');
	}
	
	// 아이디 중복확인 버튼
	$("#joinWrap #idCheckBtn").click(function() {
		$("#id").attr("data-checked", "N");
		var inputId = $("#joinWrap #id").val();
		if(!inputId) {
			$("#joinWrap #id").next().text("아이디를 입력해 주세요.").removeClass("ok").addClass("error");
		} else if(inputId.length < 4) {
			$("#joinWrap #id").next().text("아이디는 4자 이상으로 입력해 주세요.").removeClass("ok").addClass("error");
		} else {
			$.ajax({
				type: "post",
				url:"Controller",
				data: {'command': "idCheck", "id": inputId},
				datatype: "json",
				success: function(data) {
					if(data.id_match == "no") {
						$("#joinWrap #id").attr("data-checked", "Y").next().text("사용가능한 아이디 입니다.").removeClass("error").addClass("ok");
					} else {
						$("#joinWrap #id").select().attr("data-checked", "N").next().text("이미 존재하는 아이디입니다.").removeClass("ok").addClass("error");
					}
				},
				error : function(request, status, error) {
					alert("아이디 중복확인 에러");
				}
			})
		}
	});

	// 아이디 수정 시 중복 확인 여부 수정
	$("#joinWrap #id").change(function() {
		if($("#id").attr("data-checked") == "Y") {
			$(this).next().text("");
			$("#id").attr("data-checked", "N");
		}
	}).keyup(function() {
		this.value = this.value.replace(/[^a-z0-9]/g,'');
		$("#id").attr("data-checked","N");
	});

	// 비밀번호 체크
	$("#joinWrap #pw").blur(function() {
		/*if($(this).val().length < 8 && $(this).val()) {
			$(this).next().text("비밀번호는 8자리 이상으로 입력해주세요.").addClass("error");
		}*/
		pwCheck = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,16}$/;
		if(!pwCheck.test($(this).val())) {
			$(this).next().text("비밀번호는 하나 이상의 문자, 숫자, 특수 문자를 포함한 8자 이상으로 입력해주세요.").addClass("error");
		}
		if($("#joinWrap #pw2").val()) {
			checkPwMatch();
		}
	});
	//입력 시 공백문자 지우기
	$("#joinWrap input[type=\"text\"], #joinWrap input[type=\"password\"]").keyup(function() {
		this.value = this.value.replace(/ /gi, "");
	});

	// 비밀번호 일치 체크
	$("#joinWrap #pw2").focus(function() {
		if(!$("#joinWrap #pw").val()) {
			$("#joinWrap #pw").focus().next().text("비밀번호를 먼저 입력해주세요").removeClass("ok").addClass("error");
		} else if($("#joinWrap #pw").val().length < 8) {
			$("#joinWrap #pw").focus();
		}
	}).blur(function() {
		if($("#joinWrap #pw2").val()) {
			checkPwMatch();
		}
	});

	// 키 입력, 포커스 시 오류 메시지 지우기
	$("#joinWrap input[type='text'], #joinWrap input[type=\"password\"").keydown(function() {
		$(this).siblings(".msg").text("").removeClass("error ok");
	});
	$("#joinWrap select, #joinWrap input[type='checkbox']").focus(function() {
		$(this).siblings(".msg").text("").removeClass("error ok");
	});
	$("#joinWrap input[type='radio']").focus(function() {
		$(this).parent().siblings(".msg").text("").removeClass("error ok");
	});
	
});

// 비밀번호 일치 확인 함수
function checkPwMatch() {
	if($("#joinWrap #pw").val() == $("#joinWrap #pw2").val()) {
		$("#joinWrap #pw2").next().text("비밀번호가 일치합니다.").removeClass("error").addClass("ok");
		$("#joinWrap #pw2").attr("data-checked", "Y");
	} else {
		$("#joinWrap #pw2").next().text("비밀번호가 일치하지 않습니다.").removeClass("ok").addClass("error");
		$("#joinWrap #pw2").attr("data-checked", "N");
	}
}

// 회원가입 유효성체크
function checkAll() {
	// 아이디 빈값 체크
	if(!$("#joinWrap #id").val()) {
		// $("html, body").scrollTop($(".id_check").offset().top - 130);
		$("#joinWrap #id").focus().next().text("아이디 입력 후 중복확인을 해 주세요.").addClass("error");
		return false;
	}
	// 아이디 중복확인 여부 체크
	if($("#joinWrap #id").attr("data-checked") == "N") {
		$("#joinWrap #id").focus().next().text("아이디 중복확인을 해 주세요.").addClass("error");
		return false;
	}

	// 비밀번호 빈값 체크
	if(!$("#joinWrap #pw").val()) {
		$("#joinWrap #pw").focus().next().text("비밀번호를 입력해 주세요.").addClass("error");
		return false;
	}

	// 비밀번호 일치 체크
	if($("#joinWrap #pw2").attr("data-checked") == "N") {
		$("#joinWrap #pw2").focus().next().text("비밀번호가 일치하지 않습니다.").addClass("error");
		return false;
	}

	// 이름 체크
	if(!$("#joinWrap #name").val()) {
		$("#joinWrap #name").focus().next().text("이름을 입력해 주세요.").addClass("error");
		return false;
	}

	// 전화번호 체크
	if(!$("#joinWrap #phone2").val() || !$("#joinWrap #phone3").val()) {
		$("#joinWrap #phone1").focus();
		$("#joinWrap #phone3").next().text("전화번호를 입력해 주세요.").addClass("error");
		return false;
	}

	// 이메일 체크
	if(!$("#joinWrap #email").val()) {
		$("#joinWrap #email").focus().next().text("이메일 주소를 입력해 주세요.").addClass("error");
		return false;
	}

	// 이메일 형식 체크
	var emailPattern =/^[\w-]+(\.[\w-]+)*@([\w-]+\.)+[a-zA-Z]+$/;
	if($("#joinWrap #email").val().match(emailPattern) == null) {
		$("#joinWrap #email").focus().next().text("올바르지 않은 형식입니다. 다시 입력해 주세요.").addClass("error");
		return false;
	}

	// 생년월일 입력 체크
	if(!$("#joinWrap #birthY").val() || !$("#joinWrap #birthM").val() || !$("#joinWrap #birthD").val()) {
		$("#joinWrap #birthY").focus();
		$("#joinWrap #birthD").next().text("생년월일을 선택해 주세요.").addClass("error");
		return false;
	}

	// 성별 체크 여부
	if(!$("#joinWrap [name='gender']:checked").length) {
		$("#joinWrap .gender_box .msg").text("성별을 선택해 주세요.").addClass("error");
		return false;
	}
	
	// 개인정보 제공 동의 여부
	if(!$("#joinWrap #policy").is(":checked")) {
		$("#joinWrap #policy").siblings(".msg").text("개인정보 동의에 체크해 주세요. 미체크 시 가입이 불가합니다.").addClass("error");
		return false;
	}

	return true;
}