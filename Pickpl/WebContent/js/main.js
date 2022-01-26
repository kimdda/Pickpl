var isLogin = false;

$(function() {
	// 로그인 여부 확인 후 최근 리스트 가져오기
	$.ajax({
		type: 'post',
		url: 'Controller',
		data: {"command":"loginCheck"},
		dataType: 'json',
		success: function(data) {
			//console.log(data.result == "login");
			if(data.result == "login") {
				isLogin = true;
				// 최근 본 여행지 리스트 가져오기
				$(".recent_wrap").show();
				getRecentList();
			} else if(data.result == "logout") {
				$(".recent_wrap").hide();
			}
		}
	});
	
	// 전체 둘러보기
	$('.all_list_btn').click(function(){
		location.href = "list.jsp";
	});
	
	// 뜨는 여행지 더보기
	$('.content .more_btn').click(function(){
		sessionStorage.setItem("search", $(this).prev().text());
		location.href ="list.jsp";
	});
	
	$(".popular_list .row_list").slick({
		infinite: true,
		slidesToShow: 4,
		slidesToScroll: 4,
		appendArrows: $(".popular_list .row_btn"),
		prevArrow: '<button class="material-icons row_left_btn">arrow_back_ios_new</button>',
		nextArrow: '<button class="material-icons row_right_btn">arrow_forward_ios</button>'
	});
	
	$(".diary_list").each(function() {
		var $btnBox = $(this).parents(".row").find(".row_btn");
		if($(this).find(".diary").length < 5) {
			$btnBox.hide();
		} else {
			$btnBox.show();
		}
		$(this).slick({
			infinite: true,
			slidesToShow: 4,
			slidesToScroll: 4,
			appendArrows: $btnBox,
			prevArrow: '<button class="material-icons row_left_btn">arrow_back_ios_new</button>',
			nextArrow: '<button class="material-icons row_right_btn">arrow_forward_ios</button>'
		});
	});
	
});

// 최근 본 여행지 리스트 가져오기
function getRecentList() {
	$.ajax({
		type: 'post',
		url: 'Controller',
		data: {"command":"mainRecentList"},
		dataType: 'json',
		success: function(data) {
//			console.log(data);
			if(data.recentList.length == 0) {
				$(".recent_wrap p").show();
			} else {
				$(".recent_wrap p").hide();
				$.each(data.recentList, function(index, item){
					html = 
						'<div data-dId="' + item.d_id + '" class="diary">' +
							'<div class="place_box">' +
								'<div class="diary_img">';
					for(i=0; i<item.img.length; i++) {
						html += '<img src="img/diary/'+ item.img[i] + '" alt="'+item.place_name+'image">';
					}
					html += '</div>' +
							'<div class="top_icon">' +
								'<button class="pick pick_btn" onclick="pickBtn(event);"></button>' +
							'</div>' +
							' <div class="info_box">' +
								'<p class="place_name">' + item.place_name + '</p>' +
								'<p class="address">'+ item.address + '</p>' +
							'</div>' +
						'</div>' +
						'<div class="acct_box">' +
							'<img src="img/profile/'+ item.profile +'" alt="계정 프로필 사진" class="acct_profile">' + 
							'<p class="writer_id">' + item.writer + '</p>' +
							'<p class="pick_cnt">'+ item.pick_count + '</p>' +
							'<p class="view_cnt">'+ item.view_count + '</p>' +
						'</div>' +
					'</div>';
					
					$(".recent_box .diary_list").append(html);
				});
				
				// 이미지 슬라이드
				$('.recent_box .diary_img').slick({
					infinite: true,
					arrows: true,
					prevArrow:"<button type='button' class='slick-prev material-icons'>arrow_back_ios_new</button>",
					nextArrow:"<button type='button' class='slick-next material-icons'>arrow_forward_ios</button>"
				});
			}
			
		},
		error: function() {
			alert('최근 본 여행지 리스트 가져오기 실패');
		}
	})
} 