/*프로필 이미지 업로드 미리보기*/
function setThumbnail(event) {
	var reader = new FileReader(); 
	reader.onload = function(event) {
		$(".profile_img").attr("src", event.target.result);
	};
	reader.readAsDataURL(event.target.files[0]); 
}

$(function() {
	/* 비밀번호변경 */
	$("#pwMdfyBtn").click(function() {
		pwMdfyMode();
	});
	
	/* 비밀번호변경 -> 취소*/
	$("#newPwCencel").click(function() {
		pwViewMode();
	});
	
	/* 비밀번호변경 -> 저장*/
	$("#newPwSave").click(function() {
		if(check()) {
			id = $("#id").text();
			pw = $("#pw").val();
			$.ajax({
				type: "post",
				url: "Controller",
				data: {
					'command':'changePw',
					'id': id,
					'pw': pw
				},
				datatype:"json",
				success: function(data) {
//					console.log(data);
					if(data.result == "success") {
						pwViewMode();
						Swal.fire({
							title: '비밀번호 수정이 완료되었습니다.',
							icon: 'success',
							confirmButtonText: '확인',
							confirmButtonColor: '#0ea098',
							timer: 2000,
						});
					}
				},
				error: function(request, status, error) {
					alert("비밀번호 변경 저장 에러");
				}
			});
		}
	});
	
	$("[type=password]").on("keypress", function() {
		$(".new_pw .memberInfoPwFont").text("");
	})
	
	// 수정하기 클릭
	$("#mdfyBtn").click(function() {
		mdfyMode();
	});
	
	/* 수정 취소하기 */
	$("#mdfyCancel").click(function() {
		Swal.fire({
			title: '수정을 취소하시겠습니까?',
			text: '수정 내용은 저장되지 않습니다.',
			icon: 'question',
			showCancelButton: true,
			confirmButtonColor: '#0ea098',
			confirmButtonText: '수정 취소',
			cancelButtonText: '계속 수정',
			focusConfirm: false,
			reverseButtons: true		
		}).then((result) => {
			if (result.isConfirmed) {
				post_to_url("Controller", {'command':'myPage'});
			}
		})
	});
	
	/* 수정 저장하기*/
	$("#mdfySave").click(function() {
		if(!$("#name").val()) {
			$("#name").next().text("이름을 입력해 주세요.");
			return false;
		}
		
		
		if(!$("#phone").val()) {
			$("#phone").next().text("전화번호를 입력해 주세요.");
			return false;
		}
		
		if($("#birthY").val() == "" || $("#birthM").val() == "" || $("#birthD").val() == "") {
			$(".birth_box").next().text("생년월일을 입력해 주세요.");
			return false;			
		}
		
		Swal.fire({
			title: '수정 내용을 저장하시겠습니까?',
			icon: 'question',
			showCancelButton: true,
			cancelButtonColor: '#afafaf',
			confirmButtonColor: '#3085d6',
			confirmButtonText: '저장',
			cancelButtonText: '취소',
			focusConfirm: false,
			reverseButtons: true
		}).then((result) => {
			if (result.isConfirmed) {
				$('#form').submit();
			} else {
				$("#myPageWrap .mdfy_info").show();
				$("#myPageWrap .view_info").hide();
				$("#myPageWrap .new_pw").hide();
			}
		})
	});
	
	$("input[type='text']").on("keypress", function() {
		$(this).next().text("");
	});
	
	$(".birth_box select").click(function() {
		$(".birth_box").next().text("");
	});

	/* 탈퇴하기 */
	$("#outBtn").click(function() {
		Swal.fire({
			title: '탈퇴하시겠습니까?',
			text: "탈퇴 시 활동 내용이 모두 삭제됩니다.",
			icon: 'question',
			showCancelButton: true,
			cancelButtonColor: '#afafaf',
			confirmButtonColor: '#0ea098',
			confirmButtonText: '탈퇴',
			cancelButtonText: '취소',
			focusConfirm: false,
			reverseButtons: true
		}).then((result) => {
			if (result.isConfirmed) {
				$.ajax({
					type: "post",
					url: "Controller",
					data: {'command':'goodbyPickpl'},
					datatype:"json",
					success: function(data) {
						console.log(data);
						if(data.result == "farewell") {
							Swal.fire({
								title: '탈퇴되었습니다.',
								text: '픽플을 이용해 주셔서 감사합니다.',
								icon: 'success',
								confirmButtonColor: '#0ea098',
								confirmButtonText: '확인',
								timer: 2000,
								focusDeny: false
							}).then((result) => {
								location.href = 'index.jsp';
							})
						}
					},
					error: function(request, status, error) {
						alert("탈퇴 에러");
					}
				});
			}
		})
	});
}); // End ready

//정보 수정 유효성 검사
function checkInfo() {
	if(!$("#name").val()) {
		$("#name").next().text("이름을 입력해 주세요.");
		return false;
	}
}

	
/*비밀번호 유효성 검사*/
function check() {
	var pw = $("#pw").val();
	var pw1 = $("#pw1").val();
	var pwCheck = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,16}$/;
	
	if(pw == "") {
		$('.memberInfoPwFont').text('변경하실 비밀번호를 입력해주세요.');
		return false;		
	}
	
	
	if(pw.length < 8 || pw.length > 16){
		$('.memberInfoPwFont').text('8자리 이상 16자리 이하로 입력해주세요.');
		return false;
	}

	if(!pwCheck.test(pw)) {
		$('.memberInfoPwFont').text('비밀번호는 하나 이상의 문자, 숫자, 특수 문자를 포함한 8자 이상으로 입력해주세요.');
		return false;
	}
	
	if(pw1 == "") {
		$('.memberInfoPwFont').text('비밀번호 확인을 입력해주세요.');
		return false;		
	}
	
	if(pw != pw1) {
		$('.memberInfoPwFont').text('비밀번호가 일치하지 않습니다.');		
		return false;
	}
	
	return true;
}

// 수정 모드
function viewMode() {
//	$("[name='command']").val("mdfy");
	$(".view_info").show();
	$(".mdfy_info").hide();
}

function mdfyMode() {
	$(".view_info").hide();
	$(".mdfy_info").show();
}


// 비밀번호 변경 모드
function pwViewMode() {
	$("#pwMdfyBtn").show();
	$(".pw_mdfy_info").hide();
}

function pwMdfyMode() {
	$("input[type='password']").val('');
	$('.memberInfoPwFont').text('');	
	$("#pwMdfyBtn").hide();
	$("#myPageWrap .pw_mdfy_info").show();
}