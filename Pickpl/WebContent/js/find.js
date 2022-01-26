$(function() {
	if(location.hash == "#pw") {
		$('input[name="command"]').val("findPw");
		$(".type_btn").addClass("active").not($(".pw_type_btn")).removeClass("active");
		$(".find_pw").show();
		$(".findBtn_text").text("비밀번호 찾기");
	}
	
	if($(".find_wrap").length > 0) {
		$("input").not("[type='hidden']").val("");
		$(".find_btn .findBtn_text").removeClass("hide");
		$(".find_btn .loader").addClass("hide");	
	
		command = $('input[name="command"]');

		// 아이디 찾기 / 비밀번호 찾기 클릭 이벤트
		$(".type_btn").click(function() {
			command.val($(this).data("find"));
			$(".type_btn").addClass("active").not($(this)).removeClass("active");
	
			if(findForm.command.value == "findId") {
				$(".find_pw").hide();
				$(".findBtn_text").text("아이디 찾기");
			} else {
				$(".find_pw").show();
				$(".findBtn_text").text("비밀번호 찾기");
			}
		});
      
		$("input").keydown(function() {
			$(this).next().text("");
		});
	  
	}
});

function showLoginPop() {
   $("#loginPop").show();
}

function checkFind() {
	if(find == "pw") {
		if(!findForm.id.value) {
			$('input[name="id"]').focus().next().text("아이디를 입력해 주세요.");
			return false;
		}
	}

	if(!findForm.name.value) {
		$('input[name="name"]').focus().next().text("이름을 입력해 주세요.");
		return false;
	}

	if(!findForm.email.value) {
		$('input[name="email"]').focus().next().text("이메일 주소를 입력해 주세요.");
		return false;
	}

	var emailPattern =/^[\w-]+(\.[\w-]+)*@([\w-]+\.)+[a-zA-Z]+$/;
	email = findForm.email.value;
	if(email.match(emailPattern) == null) {
		$('input[name="email"]').focus().next().text("올바르지 않은 형식입니다. 다시 입력해 주세요.");
		return false;
	}
	
	$(".find_btn span").toggleClass("hide");
	return true;
}

