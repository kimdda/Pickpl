var today = new Date();
var dp = new AirDatepicker();
//var newImgCnt = 0;
function inputClone() {
	html = '<div class="picture_box">' +
				'<label class="img_upload empty">' +
//					'<input type="file" name="new_img' + cnt + '" class="diary_img_input" accept="image/*" onchange="setThumbnail(event);"/>' +
					'<input type="file" name="new_img" class="diary_img_input" accept="image/*" onchange="setThumbnail(event);"/>' +
				'</label>' +
				'<button type="button" class="material-icons del_btn hide">clear</button>' +
			'</div>';
	return html
}
		
function loadClone(index, img) {
	html = '<div class="picture_box">' +
				'<input type="hidden" name="uploaded_img'+ index +'" value="'+ img +'" />' +
				'<label class="img_upload" style="background-image:url(\'img/diary/'+ img +'\')">' +
//					'<input type="file" name="diary_img" class="diary_img_input" accept="image/*" onchange="setThumbnail(event);"/>' +
				'</label>' +
				'<button type="button" class="material-icons del_btn">clear</button>' +
			'</div>'
			
	return html;	
}

$(function() {
	var dId = location.hash.substring(1);
	
	
	for(var i=1; i<=24; i++) {
		$("#visitTime").append('<option value="'+i+'">' + i + '시</option>')
	}

	// 수정일 경우 정보 불러오기
	if(dId != "") {
		isNew = false;
		$("#dId").val(dId);
		$(".diary_write_title").text("다이어리 수정하기");
		$.ajax({
			type:"post",
			url: "Controller",
			data: {'command': 'mdfyDiaryDetail', 'dId' : dId},
			dataType: "json",
			success: function(data) {
				//console.log(data);
				dp.setViewDate(data.visit_date);
				dp.setFocusDate(data.visit_date);
				$("#visitDate").val(data.visit_date);
				$("#visitTime").val(data.visit_time);
				$("#placeName").val(data.place_name);
				$("#address").val(data.address);
				$("#contents").val(data.contents);
				$("#lat").val(data.lat);
				$("#lng").val(data.lng);
				$("#weatherId").val(data.weather_id);
				$(".weather_btn").removeClass("selected");
				$(`.weather_btn[value="${data.weather_id}"]`).addClass("selected");
				if(data.drone != null) {
					$('[name="drone"][value="'+data.drone+'"]').prop("checked", true);
				}
				if(data.public_tran != null) {
					$('[name="public_tran"][value="'+data.public_tran+'"]').prop("checked", true);
				}
				if(data.public_info != null) {
					$('[name="public_info"][value="'+data.public_info+'"]').prop("checked", true);
				}
				if(data.park != null) {
					$('[name="park"][value="'+data.park+'"]').prop("checked", true);
				}
				if(data.park_info != null) {
					$('[name="park_info"][value="'+data.park_info+'"]').prop("checked", true);
				}
				if(data.toilet != null) {
					$('[name="toilet"][value="'+data.toilet+'"]').prop("checked", true);
				}
				if(data.locker != null) {
					$('[name="locker"][value="'+data.locker+'"]').prop("checked", true);
				}
				if(data.shower != null) {
					$('[name="shower"][value="'+data.shower+'"]').prop("checked", true);
				}
				
				$.each(data.imgList, function(index, item) {
					$(".picture_area").prepend(loadClone(index, item));
				});
				
				if($(".picture_area .picture_box").length < 5) {
					$(".picture_area").append(inputClone());
				}
			},
			error: function(request, status, error) {
				console.log(request + " / " + status + " / " + error);
			}
		})
	} else {
		$(".diary_write_title").text("다이어리 작성하기");
		// 작성하기 일 경우, 인풋박스 추가
		$(".picture_area").append(inputClone());
	}


	// 날씨 아이콘 내용 처리
	$('.weather button').click(function(){
		$(this).addClass('selected');
		$(this).siblings().removeClass('selected');
		$('#weatherId').val($(this).val());
	});

	//체크박스 중복 선택관련 처리
	$('div > p > input[type=checkbox]').click(function(){
		var thisName = $(this).prop('name');
		var isChk = $(this).prop('checked');
		
		var chkLength = $('input[name='+thisName+']').length;
		for (var i=0; i<chkLength; i++) {
			$('input[name='+thisName+']').eq(i).prop('checked',false);
		}
		if (isChk == true) {
			$(this).prop('checked', true);
		} else {
			$(this).prop('checked', false);
		}
	});
	
	/* 취소하기 */
	$(".back_btn").click(function() {
		Swal.fire({
			  title: '수정을 취소하시겠습니까?',
			  icon: 'question',
			  showCancelButton: true,
			  cancelButtonColor: '#afafaf',
			  confirmButtonColor: '#0ea098',
			  confirmButtonText: '수정 취소',
			  cancelButtonText: '계속 수정',
			  focusConfirm: false,
			  reverseButtons: true
		}).then((result) => {
			if (result.isConfirmed) {
				history.back();
			  }
		})
	});
	
	// 지우기 버튼(휴지통) 클릭
	$(document).on("click", ".del_btn", function(e) {
		length = $(".picture_box label").not(".empty").length;
		$(this).parent(".picture_box").remove();
		if(length == 5) {
			$(".picture_area").append(inputClone());
		}
		console.log(length);
	});
	
	$(".diary_write_btn .save_btn").click(function() {
		if(fn_submit()) {
			if(location.hash == "") {
				Swal.fire({
					  title: '작성한 글을 등록 하시겠습니까?',
					  icon: 'question',
					  showCancelButton: true,
					  cancelButtonColor: '#afafaf',
					  confirmButtonColor: '#0ea098',
					  confirmButtonText: '작성 완료',
					  cancelButtonText: '돌아가기',
					  focusConfirm: false,
					  reverseButtons: true
				}).then((result) => {
					if (result.isConfirmed) {
						$(".img_upload").not(".empty").find("[type='file']").each(function(index) {
							$(this).attr("name", "new_img"+(index+1));
						});
						
						$("#form").attr("action", "Controller?command=writeDiary");
						$('#form').submit();
					  }
				});
			} else {
				Swal.fire({
					  title: '수정된 게시물을 저장하시겠습니까?',
					  icon: 'question',
					  showCancelButton: true,
					  cancelButtonColor: '#afafaf',
					  confirmButtonColor: '#0ea098',
					  confirmButtonText: '수정 완료',
					  cancelButtonText: '돌아가기',
					  focusConfirm: false,
					  reverseButtons: true
				}).then((result) => {
					if (result.isConfirmed) {
						$(".img_upload").not(".empty").find("[type='file']").each(function(index) {
							$(this).attr("name", "new_img"+(index+1));
						});
						
						$("#form").attr("action", "Controller?command=mdfyDiary");
						$('#form').submit();
		//				Swal.fire({
		//					title: '수정이 완료되었습니다.',
		//					 icon: 'success',
		//					 confirmButtonColor: '#0ea098',
		//					 confirmButtonText: '확인',
		//					 timer: 2000,
		//					 focusDeny: false
		//					}).then((result) => {
		//						
		//						location.href = 'Diary_write-Servlet.jsp#1';
		//					})
					  }
				});
			}	
		}
	})


});
// 저장하기 버튼 클릭 시 작동하는 이벤트
function fn_submit() {
	var visitDate = $('#visitDate').val();
	var visitTime = $('#visitTime').val();
	var weatherId = $('#weatherId').val();
	var placeName = $('#placeName').val();
	var address = $('#address').val();
	var contents_text  = $('.contents_text').val();
	
	if(visitDate == null || visitDate == ('')){
		Swal.fire({
			title : '잠깐만!',
			text : '날짜를 선택해 주세요.',
			icon : 'warning',
			confrimButtonText: "확인",
			showConfirmButton : true
		});
		return false;
	}
	if(visitTime == null || visitTime == ('')){
		Swal.fire({
//			title : '잠깐만!',
			text : '시간을 선택해 주세요.',
			icon : 'warning',
			confrimButtonText: "확인",
			showConfirmButton : true
		});
		return false;
	}
	if($(".picture_box").length < 2) {
		Swal.fire({
//			title : '잠깐만!',
			text : '사진을 선택해 주세요.',
			icon : 'warning',
			confrimButtonText: "확인",
			showConfirmButton : true
		});
		return false;
	}
	if(placeName == null || placeName ==('')){
		Swal.fire({
//			title : '잠깐만!',
			text : '장소명을 입력해 주세요.',
			icon : 'warning',
			confrimButtonText: "확인",
			showConfirmButton : true
		});
		return false;
	}
	if(address == null || address ==('')){
		Swal.fire({
//			title : '잠깐만!',
			text : '주소를 입력해 주세요.',
			type : 'warning',
			confrimButtonText: "확인",
			showConfirmButton : true
		});
		return false;
	}
	if(contents_text == null || contents_text ==('')){ 
		Swal.fire({
//			title : '잠깐만!',
			text : '내용을 작성해 주세요.',
			icon : 'warning',
			showConfirmButton : true,
			confrimButtonText: "확인"
		});
		return false;
	}
	
	return true;
}

function setThumbnail(event) {
	$label = $(event.target).parent();
	var reader = new FileReader();
	reader.onload = function(event) {
		$label.css("background-image", 'url('+event.target.result+')').removeClass("empty");
		$label.next().removeClass("hide");
		if($(".picture_box label").not(".empty").length < 5) {
//			cnt = $(".picture_box").length;
//			console.log(cnt)
//			$(".picture_area").append(inputClone(cnt));	
			$(".picture_area").append(inputClone());			
		}
	};
	reader.readAsDataURL(event.target.files[0]); 
}

function goPopup(){
	// 호출된 페이지(jusoPopup.jsp)에서 실제 주소검색URL(https://www.juso.go.kr/addrlink/addrLinkUrl.do)를 호출하게 됩니다.
    var pop = window.open("jusoPopup.jsp","pop","width=570,height=420, scrollbars=yes, resizable=yes"); 
    
	// 모바일 웹인 경우, 호출된 페이지(jusoPopup.jsp)에서 실제 주소검색URL(https://www.juso.go.kr/addrlink/addrMobileLinkUrl.do)를 호출하게 됩니다.
    //var pop = window.open("/popup/jusoPopup.jsp","pop","scrollbars=yes, resizable=yes"); 
}

function goMap() {
	window.open('map_search.html','window_name','width=530,height=630,location=no,status=no,scrollbars=yes,resizable=no');
}

function jusoCallBack(roadFullAddr){
	// 팝업페이지에서 주소입력한 정보를 받아서, 현 페이지에 정보를 등록합니다.
	$("#address").val(roadFullAddr);
	// 위도, 경도 받아서 등록하기
	geocoder.addressSearch(roadFullAddr, callback)
}

// 사진 추가

// 좌표받아오기
var geocoder = new kakao.maps.services.Geocoder();
var callback = function(result, status) {
    if (status === kakao.maps.services.Status.OK) {
//        console.log(result[0].x);
		$("#lat").val(result[0].y);
		$("#lng").val(result[0].x);
    }
};