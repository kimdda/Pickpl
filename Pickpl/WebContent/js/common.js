// 새로고침 시 스크롤 상단으로 이동
$(window).on('load', function() {
	$(window).scrollTop(0);
});

$(function() {
	$("header").load("header.jsp");
	$("footer").load("footer.jsp");
	
	// 로그인해서 새로고침 된 경우, 로그인되었습니다 표시
	if(sessionStorage.getItem("justSignIn")) {
		const login = Swal.mixin({
			toast: true,
			position: 'top-end',
			showConfirmButton: false,
			timer: 2000,					  
		});
		
		login.fire({
			icon: 'success',
			title: '로그인되었습니다.'
		});
		sessionStorage.removeItem("justSignIn");
	}
	

	$("html").click(function(event) {
		// 메뉴
		if($(event.target).hasClass("menu_icon") || $(event.target).hasClass("gnb")) {
			if(!$(".gnb").hasClass("active")) {
				$(".gnb").addClass("active");
			} else {
				$(".gnb").removeClass("active");
			}
		} else {
			$(".gnb").removeClass("active");
		}
		
		// 리스트 정렬 순서 변경
		if($(event.target).hasClass("selected_order")) {
			if($(event.target).hasClass("active")) {
				$(event.target).removeClass("active").next().removeClass("show").slideUp(300);
			} else{
				$(event.target).addClass("active").next().addClass("show").slideDown(300);
			}
		} else {
			if($(event.target).hasClass("order_option")) {
				select = $(event.target).text();
				$(".order_option").removeClass("selected");
				$(event.target).addClass("selected");
				$(".selected_order").text(select);
			}
			$(".selected_order").removeClass("active").next().removeClass("show").slideUp(300);
		}
		
	});
	

	// 푸터 검색 영역 클릭 이벤트
	$(document).on("click", "footer .f_search_icon", function() {
		search = $(this).prev().val();
		sessionStorage.setItem("search", search);
		location.href = "list.jsp";
	});
	
	// 푸터 검색 영역 엔터로 검색하기
	$(document).on("keydown", "footer [name='search']", function(event) {
		if(event.keyCode == 13) {
			search = $(this).val();
			sessionStorage.setItem("search", search);
			location.href = "list.jsp";
		}
	});
	
	// 이미지 슬라이드
	$('.diary_img').slick({
		infinite: true,
		arrows: true,
		prevArrow:"<button type='button' class='slick-prev material-icons'>arrow_back_ios_new</button>",
		nextArrow:"<button type='button' class='slick-next material-icons'>arrow_forward_ios</button>"
	});

	// 다이어리 글 계정 클릭 이벤트 - 다이어리 목록 페이지 이동
	$(document).on("click", ".diary .acct_profile, .diary .writer_id", function() {
		writerId = $(this).parent().find(".writer_id").text();
		post_to_url('Controller',{'command':'diaryPage','diaryId': writerId});
	});

	// 다이어리 글 클릭 이벤트 - 상세 페이지 이동
	$(document).on("click", ".diary .diary_img img, .diary .info_box", function() {
		//dId = $(this).parents(".diary").attr("data-dId");
		//post_to_url('Controller',{'command':'diaryDetail','dId': dId});
		addViewCount(event);
	});

	// 픽 폴더 선택 클릭 이벤트
	$(document).on("click", "#pickPop .folder", function() {
		$("#pickPop .folder_list .folder").removeClass("active");
		$(this).addClass("active");
		$("#pickPop .alert_txt").hide();
	});

	// 픽 폴더 추가 버튼 클릭 이벤트
	$(document).on("click", "#pickPop #addFolderBtn", function() {
		$(this).parent().hide();
		$("#pickPop .new_folder_box").show();
		$("#pickPop .folder").removeClass("active");
	});

	// 픽 폴더 추가 취소 버튼 클릭 이벤트
	$(document).on("click", "#pickPop #addCancelBtn", function() {
		$("#pickPop .new_folder_box").hide();
		$("#pickPop #newFolderName").val("");
		$("#pickPop #addFolderBtn").parent().show();
	});

	/*---------  로그인 체크  ---------*/
	
	// 엔터키로 로그인, esc로 닫기
	$(document).on("keydown", "#loginPop input", function(event) {
		$("#loginPop .alert_txt").text("");
		$("#loginPop .login_fail").addClass("hide");
		
		if(event.keyCode == 13) {
			login();
		}
		
		if(event.keyCode == 27) {
			$("#loginPop").hide();
		}
	});
	
	
	//sns 로그인 버튼 준비중..
	$(document).on("click", ".sns_login_btn", function() {
		Swal.fire({
		  text: "곧 만나요",
		  icon: 'info',
		  confirmButtonColor: '#0ea098',
		  confirmButtonText: '확인',
		  reverseButtons: true
		})
	});
	
});

// 픽 버튼 클릭 이벤트
function pickBtn(e) {
	$event = $(e.target);
	$.ajax({
		url: 'Controller',
		type: 'post',
		data: {'command': 'loginCheck'},
		dataType: 'json',
		success: function(data) {
			//console.log(data.loginId);
			if(data.result == "login") {
				isLogin = true;
				pickPop($event);
			} else {
				Swal.fire({
				  text: "로그인 후 이용 가능합니다.",
				  icon: 'info',
				  showCancelButton: true,
				  confirmButtonColor: '#0ea098',
				  confirmButtonText: '로그인',
				  cancelButtonText: '닫기',
				  reverseButtons: true
				}).then((result) => {
				  if (result.isConfirmed) {
				    loginPop();
				  }
				})
			}
		}
	});
}

function pickPop(e) {
	$target = e;
	$cnt = $target.parents(".diary").find(".pick_cnt");
	dId = $target.parents(".diary").attr("data-dId");
	if($(".detail_wrap").length) {
		$cnt = $(".detail_wrap #pickCnt");
		dId = $(".detail_wrap #dId").val();
	}
	// 픽 취소
	if($target.hasClass("picked")) {
		//console.log("picked");
		$.ajax({
			url: 'Controller',
			type: 'post',
			data: {
				'command': 'unpick',
				'dId': dId, 
				//'cnt' : +cnt-1
			},
			dataType: 'json',
			success: function(data) {
				//console.log(data);
				if(data.unpick == "success") {
					$target.removeClass("picked");
					$cnt.text(+($cnt.text()) - 1);
					
					const Toast = Swal.mixin({
					  toast: true,
					  position: 'top-end',
					  showConfirmButton: false,
					  timer: 2000,
					  
					});
			
					Toast.fire({
					  icon: 'success',
					  title: '픽 취소'
					});
				}
			}
		});
	} 
	// 픽
	else {
		//console.log("pick");
		$.ajax({
			url: 'Controller',
			type: 'post',
			data: {
				'command': 'pickFolderList',
				'order' : 'name_desc'
			},
			dataType: 'json',
			success: function(data) {
				//console.log(data);
				if(data.folderList.length > 0) {
					$("#pickPop .folder_list").empty();
					$.each(data.folderList, function(index, item) {
						$("#pickPop .folder_list").append('<p class="folder">'+item.folder+'</p>');
					});
					resetPickPop();
					$("#pickPop").show();	
				}
			}
		});
		
//		$("#pickPop .confirm_btn").click();
	}
}

// 픽 저장
function confirmPick() {	
	if($("#pickPop .folder_list .folder.active").length == "0") {
		$("#pickPop .alert_txt").show();
		return false;
	}
	var folderName = $(".folder_list .folder.active").text();
	$.ajax({
		url: 'Controller',
		type: 'post',
		data: {
			'command': 'pick',
			'dId': dId, 
			'folder': folderName
		},
		dataType: 'json',
		success: function(data) {
			//console.log(data);
			if(data.pick == "success") {
				$target.addClass("picked");
				$target.parents(".diary").find(".pick_cnt").text(+($cnt.text()) + 1);
				$("#pickPop").hide();
			}
		}
	});
}

// 픽 폴더 추가 확인 이벤트
function addPickFolder() {
	newFolderName = $("#newFolderName").val();
	$("#pickPop .folder").each(function() {
		if($(this).text() == newFolderName) {
			$("#pickPop .new_folder_box .alert_txt").show();
			return false;
		}
	});
	// 폴더 추가
	$.ajax({
		url: 'Controller',
		type: 'post',
		data: {
			'command': 'addPickFolder',
			'folderName': newFolderName
		},
		dataType: 'json',
		success: function(data) {
			//console.log(data.folder_check);
			if(data.result == "success") {
				$("#pickPop .folder_list").append('<p class="folder active">'+ newFolderName +'</p>');
				$("#newFolderName").val("");
				$(".new_folder_box").hide();
				$(".add_folder").show();
				$(".folder_list").scrollTop($(".folder_list").prop("scrollHeight"));
			} else {
				$("#pickPop .new_folder_box .alert_txt").show();
			}
		}
	});
}

// 로그인 여부 확인
function loginCheck() {
	$.ajax({
		url: 'Controller',
		type: 'post',
		data: {"command": "loginCheck"},
		dataType: 'json',
		success: function(data) {
			//console.log(data);
			if(data.result == "login") {
				//console.log("login!!!");
				loginId = data.loginId;
				return true;
			} else {
				return false;				
			}
		},
		error : function(request, status, error) {
			alert("로그인 체크 : " + request + "status : " + status + " error : " + error);
		}
	});
}

// 뷰카운트 증가
function addViewCount(e) {
	dId = $(e.target).parents(".diary").data("did");
	$.ajax({
		url: 'Controller',
		type: 'post',
		data: {'command' : 'updateViewNRecent', 'dId':dId},
		dtatType: 'json',
		success: function(data) {
			//console.log(data);
			if(data.viewR == 'success') { 
				//&& data.recentR == 'success') {
				// 뷰 카운트 증가 성공 시 상세 페이지 이동
				goToDetail(dId);
			}
		}
	})
}

// 다이어리 디테일 페이지 접속
function goToDetail(dId) {
	post_to_url('Controller',{'command':'diaryDetail','dId': dId});
}


// 팝업 닫기
function closePop(e) {
	$(e.target).parents(".popup").fadeOut(100);
}

/*---------------  로그인 팝업  ---------------*/
 function loginPop() {
	$("#loginPop input").val("");
	$("#loginPop .alert_txt").text("");
	$("#loginBtn").removeClass("hide");
	$("#loginPop").show();
	$("#loginId").focus();
 }

function login() {
	id = $("#loginPop #loginId");
	pw = $("#loginPop #loginPw");
	if (!id.val()) {
		id.next().text("아이디를 입력해 주세요.");
		return false;
	}
	if(!pw.val()) {
		pw.next().text("비밀번호를 입력해 주세요.");
		return false;
	}
	
	$.ajax({
		type: "post",
		url: "Controller",
		data: {
			'command':'login',
			"id": id.val(), 
			"pw": pw.val()
		},
		datatype:"json",
//		xhr: function() {
//			let xhr = $.ajaxSettings.xhr();
//			xhr.upload.onprogress = function(e) {
//				let percent = e.loaded * 100 / e.total;
//				if(Math.round(percent)<100){
//		        	$("#loginBtn").addClass("hide");
//					$("#loginPop .loader").removeClass("hide");
//				} else{
//		        	$("#loginBtn").removeClass("hide");
//					$("#loginPop .loader").addClass("hide");
//				}
//			};
//			return xhr;
//		},
		success: function(data) {
			//console.log("로그인" + (data.legnth));
			if(data.login_check == "D") {
				$(".login_pop .login_fail").removeClass('hide').find('p').text("이메일 인증 후 로그인이 가능합니다.");
			} else if(data.login_check == "O" || data.login_check == "fail") {
				$(".login_pop .login_fail").removeClass("hide").find('p').text('일치하는 회원이 없습니다.');
			} else {
				sessionStorage.setItem("justSignIn", "true");
				if($(".comp_wrap").length) {
					location.href = "index.jsp";
				} else {
//					$("header").load("header.jsp");
					location.reload();	
				}
				
				$("#loginPop").hide();
			}
		},
		error: function(request, status, error) {
			alert("로그인 에러");
		}
	});
}

// 픽 팝업 리셋
function resetPickPop() {
	$("#pickPop .folder_list .folder").removeClass("active");
	$("#pickPop .alert_txt, #pickPop .new_folder_box").hide();
	$(".add_folder").show();
}


// 메시지 페이지 이동
function toChat(e) {
	id = $(e.target).prev().text();
	post_to_url('Controller', {'command' : 'chatPage', 'chatId': id});
}


// 카카오톡 로그인
function kakao() {
//	console.log("sss");
//	Kakao.Auth.login({
//	  success: function(response) {
//	    console.log(response);
//	  },
//	  fail: function(error) {
//	    console.log(error);
//	  },
//	});
	Kakao.Auth.login({
      success: function(authObj) {
        console.log(JSON.stringify(authObj))
      },
      fail: function(err) {
		console.log(JSON.stringify(err))
      },
    })
}


// post 방식으로 전송하기
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

//<a href="javascript:post_to_url('www.XXXX.com/XXXXhtml',{'type1':'aaa','type2':'bbb'})">POST보내기</a>


