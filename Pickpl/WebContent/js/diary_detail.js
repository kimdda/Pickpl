var loginId;

$(function() {
	// 지도
	var mapContainer = document.getElementById('map'),
    mapOption = {
        center: new kakao.maps.LatLng(lat, lng),
        level: 5 
    };
	var map = new kakao.maps.Map(mapContainer, mapOption);
//	var markerPosition  = new kakao.maps.LatLng(lat, lng); 
	var marker = new kakao.maps.Marker({
	    position: new kakao.maps.LatLng(lat, lng)
	});
	marker.setImage(new kakao.maps.MarkerImage('img/icon/map_pin_icon.png', new kakao.maps.Size(24, 35)));
	marker.setMap(map);

	// 댓글 내용 가져오기 
	getCommentList();

	// 댓글 작성하기 클릭 이벤트
	$('.cmnt_btn').click(function() {
		// 로그인 여부 확인
		$.ajax({
			url: 'Controller',
			type: 'post',
			data: {"command": "loginCheck"},
			dataType: 'json',
			success: function(data) {
				// console.log(data);
				if(data.result == "login") {
					var contents = ($('#cmnt').val()).trim(); 
					if (!contents) { 
						Swal.fire({
							text : '댓글 내용을 작성해 주세요.',
							icon : 'warning',
							customClass : 'sweet-size',
							showConfirmButton : true,
							confirmButtonText : "확인",
						});
						return; 
					}
					
					// 내용이 있는 경우에 ajax 실행
					var dId = $("[name='dId']").val().trim();
					writeCmnt(dId, contents, data.loginId);
				} else {
					Swal.fire({
						text : '로그인 이후 댓글 등록이 가능합니다.',
						icon : 'warning',
						customClass : 'sweet-size',
						showConfirmButton : true,
						confirmButtonText : "로그인하기",
						confirmButtonColor : "#0ea098",
						showCancelButton: true,
						cancelButtonText: '닫기',
						reverseButtons: true
					}).then((result) => {
						if (result.isConfirmed) {
							loginPop();
						}
					});
					return false;
				}
			},
			error : function(request, status, error) {
				alert("로그인 체크 : " + request + "status : " + status + " error : " + error);
			}
		});		
	});
	
	// 공유하기 클릭
	$('.share_btn').click(function() {
		let currentUrl = encodeURI(window.document.location.href + "?command=diaryDetail&dId=" + $("#dId").val());

		let t = document.createElement("textarea");
        document.body.appendChild(t);
        t.value = currentUrl;
        t.select();
        document.execCommand('copy');
        document.body.removeChild(t);
		
        const copy = Swal.mixin({
			toast: true,
			position: 'top-end',
			showConfirmButton: false,
			timer: 1500,					  
		});
		
		copy.fire({
			icon: 'success',
			title: '주소가 복사되었습니다.'
		});
			
	});
		
	// 다이어리 글 계정 클릭 이벤트 - 다이어리 목록 페이지 이동
	$(document).on("click", ".id_box .acct_profile, .id_box #writer_id", function() {
		writerId = $("#writer_id").text();
		post_to_url('Controller',{'command':'diaryPage','diaryId': writerId});
	});
		

	// 신고하기
	$('.reprot_btn').click(function() {
		var reportType;
		var reportReason;
		var targetId = $("#writer_id").text();
		var d_id = $("#dId").val();
		
		$.ajax({
			url: 'Controller',
			type: 'post',
			data: {"command": "loginCheck"},
			dataType: 'json',
			success: function(data) {
				if(data.result == "login") {
					reportTypePop();
				} else {
					loginPop();
				}
			},
			error : function(request, status, error) {
				alert("신고 로그인 체크 : " + request + "status : " + status + " error : " + error);
			}
		})
	});
	
	// 신고하기 팝업 라디오 버튼 클릭 이벤트
	$(document).on('change', ".reportPop input[type='radio']", function() {
		$(this).parents(".report_content").find(".error").addClass("hide");
		if($(this).val() == 3) {
			$(".inputbox").show();
		} else {
			$(".inputbox").hide();
		}
	});
	
	// 신고하기 팝업 신고사유 작성 이벤트
	$(document).on('keydown', ".reportPop #reportInput", function() {
		$(this).parent().next().addClass("hide");
	});
	
	
	// 주변 여행지
	var address = ($(".content_box .address").text()).split(" ");
	around($('[name="dId"]').val(), address[0], address[1]);
	
}); // End of ready

// 카카오톡 공유하기
function sendLink() {
	let currentUrl = encodeURI(window.document.location.href + "?command=diaryDetail&dId=" + $("#dId").val());
	Kakao.init('83962cd308fc51141a2a8d05929fddc5');
	Kakao.Link.sendDefault({
		objectType: 'feed',
		content: {
			title: '픽플(PICKPL)',
			description: $(".place_name").text() + ' 다이어리 공유해요!',
			imageUrl: 'localhost:9090/Pickpl/' + $(".img_box:first-of-type img").attr("src"),
		    link: {
				mobileWebUrl: currentUrl,
				webUrl: currentUrl,
			},	
		},
		buttons: [
			{
				title: '다이어리로 이동하기',
				link: {
					mobileWebUrl: currentUrl,
					webUrl: currentUrl
				},
			},
		]
	});
}

// 주변 여행지
function around(dId,region, city) {
	$.ajax({
		type : 'post', 
		url : 'Controller',
		data : {
			'command': 'diaryAround',
			'dId' : dId,
			'region': region,
			'city': city
		},
		dataType : 'json',
		success : function(data) {
			$(".around_wrap .diary_list").empty();
			$.each(data.aroundList, function(index, item) {
				html = '<div data-dId="' + item.dId + '" class="diary">' +
					'<div class="place_box">' +
					'<div class="diary_img">';
					$.each(item.img, function(index, img) {
						html += '<img src="img/diary/'+ img +'" alt="">'
					});
				html +=	'</div>' +
						'<div class="top_icon">' +
							'<button class="' + item.pick + ' pick_btn"  onclick="pickBtn(event);"></button>' +
							'</div>' +
							'<div class="info_box">' +
								'<pclass="place_name">' + item.place_name + '</p>' +
								'<p class="address">' + item.address + '</p>' +
							'</div>' +
						'</div>' +
						'<div class="acct_box">' +
							'<img data-diary="profile" src="img/profile/' + item.profile 
								+ '"alt="" class="acct_profile">' +
							'<p class="writer_id">' + item.writer_id + '</p>' + 
							'<p class="pick_cnt">' + item.pick_count + '</p>' +
							'<p class="view_cnt">' + item.view_count + '</p>' +
						'</div>' +
					'</div>'
				
				$(".around_wrap .diary_list").append(html);
			});
			
			// 주변 여행지 슬릭
			$(".around_wrap .diary_list").slick({
				infinite: true,
				slidesToShow: 5,
				slidesToScroll: 5,
				appendArrows: $(".around_wrap .row_btn"),
				prevArrow: '<button class="material-icons row_left_btn">arrow_back_ios_new</button>',
				nextArrow: '<button class="material-icons row_right_btn">arrow_forward_ios</button>'
			});
		}
	});
}

// 신고 대상 선택 팝업
function reportTypePop() {
	Swal.fire({
		customClass : {
			container : 'report_wrap',
			popup: 'reportPop',
			title: 'report_title',
			htmlContainer: 'report_content'
		},
		title: '신고 대상 선택',
		html: '<p class="error hide">신고 대상을 선택해주세요.</p>' + 
			'<p class="option"><label><input type="radio" name="reportType" value="U"/>해당 아이디 신고</label></p>' +
			'<p class="option"><label><input type="radio" name="reportType" value="D"/>해당 글 신고</label></p>',
		showCloseButton: true,
		showCancelButton: true,
		focusConfirmButton: false,
		confirmButtonText: '다음',
		cancelButtonText:'취소',
		confirmButtonColor : "#0ea098",
		reverseButtons: true,
		preConfirm : () => {
			if($("[name='reportType']:checked").length == 0) {
				$(".error").removeClass("hide");
				return false;
			}
		}
	}).then((result) => {
		if (result.isConfirmed) {
			reportType = $("[name='reportType']:checked").val();
		    reportReasonPop();
		} else {
			reportType = "";
		}
	});
	
}

// 신고 사유 선택 팝업
function reportReasonPop() {
	Swal.fire({
		customClass : {
			container : 'report_wrap',
			popup: 'reportPop',
			title: 'report_title',
			htmlContainer: 'report_content'
		},
		title: '신고 사유 선택',
		html: '<p class="error hide">신고 사유를 선택해주세요.</p>' + 
			'<p class="option"><label><input type="radio" name="reportContent" value="1"/>해당 글에 부적절한 내용이 포함</label></p>' +
			'<p class="option"><label><input type="radio" name="reportContent" value="2"/>광고성 글</label></p>' +
			'<p class="option"><label><input type="radio" name="reportContent" value="3"/>직접 입력</label></p>' +
			'<p class="option inputbox"><input type="text" id="reportInput"/></p>' +
			'<p class="input_error error hide">사유를 입력해 주세요.</p>',
		showCloseButton: true,
		showCancelButton: true,
		focusConfirmButton: false,
		confirmButtonText: '신고',
		cancelButtonText:'뒤로',
		confirmButtonColor : "#0ea098",
		reverseButtons: true,
		preConfirm : () => {
			$checked = $("[name='reportContent']:checked");
			if($checked.length == 0) {
				$(".error").removeClass("hide");
				return false;
			}
			
			if($checked.val() == 3 && !$("#reportInput").val()) {
				$(".input_error").removeClass("hide");
				return false;
			}
		}
	}).then((result) => {
		if (result.isConfirmed) {
			reportReason = $("[name='reportContent']:checked").val();
			if(reportReason == 3) {
				reportReason = $("#reportInput").val();
			} else {
				reportReason = $("[name='reportContent'][value='"+reportReason+"']").parent().text();
			}
			
			$.ajax({
				url: 'Controller',
				type: 'post',
				data: {
					"command": "report", 
					"targetId" : $("#writer_id").text(), 
					"dId" : $("#dId").val(),
					"reportType" : reportType,
					"reportCon" : reportReason
				},
				dataType: 'json',
				success: function(data) {
					if(data.result == 'success') {
						popupText = "현재 다이어리에 대한 신고가 완료되었습니다.";
						if(reportType == "U") {
							popupText = "아이디 " + $("#writer_id").text() + "에 대한 신고가 완료되었습니다.";
						}
								
					    Swal.fire({
							icon: 'success',
							title: '신고가 완료되었습니다.',
							confirmButtonText: '확인',
							confirmButtonColor : "#0ea098",
							timer: 2000
						});
					}
				},
				error: function() {
					alert('신고하기 실패');
				}
			});
	
		} else if (result.dismiss === Swal.DismissReason.cancel) {
			reprotReason = "";
			reportTypePop();
		}
	});
}

// 댓글 관련 내용 가져오기 시작
function getCommentList() { // 댓글 내용 가져오는 함수
	var dId = $('[name="dId"]').val(); 
	$.ajax({
		type : 'post', 
		url : 'Controller',
		data : {
			'command' : 'getCmnts',
			"dId" : dId
		},
		dataType : 'json',
		success : function(data) {
			var commentCnt = data.cmntsList.length;
			$('#commentCnt').text(commentCnt);
			$('.comments_wrap .cmnt_cnt').text(commentCnt);
			
			if(commentCnt == 0) {
				$(".no_comment").show();
				$(".comments_box").hide();
			} else {
				$(".no_comment").hide();
				$(".comments_box").show();
			}
			
			$(".comments_list").empty();
			$.each(data.cmntsList, function(index, item) {
				html = '<div class="comment" data-idx="'+ item.idx + '">' +
						'<div class="img_box"><img src="img/profile/profile_default.svg" alt=""></div>' +
						'<div class="info_box">' +
							'<p class="">';
							
					if(item.id == $("#writer_id").text())
						html += '<span class="cmnt_id writers_cmnt">' + item.id + '</span>';
					else
						html += '<span class="cmnt_id">' + item.id + '</span>';
						
						html +=	'<span class="cmnt_date">' + item.date + '</span>' +	
				 			'</p>' +
							'<p class="cmnt_contents">'+ item.contents + '</p>';
					if(item.id == data.loginId) {
						html += '<p class="cmnt_del_btn material-icons" onclick="delCmnt(event);">clear</p>';
					}
					
				html +=	'</div>' +
					'</div>'
				
				$('.comments_list').append(html);
			});
			
		},
		error : function(request, status, error) {
			$(".no_comment").show().text("오류로 인해 댓글을 불러올 수 없습니다. 새로고침 해주세요.");
			$(".comments_box").hide();
		},
		complete: function() {
			$(".comments_box").scrollTop($(".comments_list").height() -  $(".comments_box").height());
		}
	});
}

// 댓글 작성하기
function writeCmnt(dId, contents, loginId) {
	$.ajax({
		type : 'post', 
		url : 'Controller',
		data : {
			'command': 'cmntWrite',
			'dId': dId,
			'contents': contents
		},
		dataType : 'json',
		success : function(data) {
			//console.log(data);
			if (data.result == 'success') {
				today = dateTimeFormat(new Date());
				
				$(".no_comment").hide();
				$(".comments_box").show();
				
				$('.comments_list').append(
					'<div class="comment" data-idx="'+ data.idx +'">' +
						'<div class="img_box"><img src="img/profile/profile_default.svg" alt=""></div>' +
						'<div class="info_box">' +
							'<p class=""><span class="cmnt_id">' + loginId + '</span>' +
							'<span class="cmnt_date">' + today + '</span></p>' +
							'<p class="cmnt_contents">'+ contents + '</p>' +
							'<p class="cmnt_del_btn material-icons" onclick="delCmnt(event);">clear</p>' +
						'</div>' +
					'</div>'
				);
				
				$(".comments_wrap .cmnt_cnt, #commentCnt").text($(".comments_list .comment").length);
				
				Swal.fire({
					text : '댓글이 추가되었습니다.',
					icon : 'success',
					showConfirmButton : true,
					confirmButtonColor : "#0ea098",
					confirmButtonText : '확인'
				});
				
				$('#cmnt').val('');
			} else {
				Swal.fire({
					text : '댓글 작성에 실패하였습니다.',
					icon : 'warning',
					showConfirmButton : true,
					confirmButtonText : "확인",
					confirmButtonColor : "#0ea098"
				});
			}
		},
		error : function(request, status, error) { },
		complete : function() {
			$(".comments_box").scrollTop($(".comments_list").height() -  $(".comments_box").height());
		}
	});
}

// 댓글 삭제하기
function delCmnt(e) {
	idx = $(e.target).parents(".comment").data("idx");
	$.ajax({
		type : 'post', 
		url : 'Controller',
		data : {
			'command': 'deleteMyCmnt',
			'idx': idx
		},
		dataType : 'json',
		success : function(data) {
			// console.log(data);
			if(data.result == "success") {
				$(".comment[data-idx='"+idx+"']").remove();
				Swal.fire({
					text : '댓글이 삭제되었습니다.',
					icon : 'success',
					showConfirmButton : true,
					confirmButtonColor : "#0ea098",
					confirmButtonText : '확인'
				});
			}
		},
		error : function(request, status, error) {
			console.log("댓글 삭제 오류");
		},
	});
}

function dateTimeFormat(newDate) {
	year = newDate.getFullYear();
	month = ('00' + (newDate.getMonth() + 1)).slice(-2);
	date = ('00' + newDate.getDate()).slice(-2);
	time = newDate.toLocaleTimeString();

	return year + "-" + month + "-" + date + " " + time;
}