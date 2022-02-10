$(function() {
	// 마커를 담을 배열입니다
	var markers = [];
	var markerImg = 'img/icon/map_pin_list_icon_1.png';
	var markerFocusImg = 'img/icon/map_pin_icon.png';
	
	var markerHover = markerFocus = new kakao.maps.MarkerImage(markerFocusImg, new kakao.maps.Size(28, 39))
	var markerClick = new kakao.maps.MarkerImage(markerFocusImg, new kakao.maps.Size(24, 35));
	var markerUnfocus = new kakao.maps.MarkerImage(markerImg, new kakao.maps.Size(24, 35));
	
	var listToShow = 10;
	var total, pageCnt, pageIdx, startIdx, endIdx;
	var listData;

	var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
		mapOption = {
			center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
			level: 3 // 지도의 확대 레벨
		};  

	// 지도를 생성합니다    
	var map = new kakao.maps.Map(mapContainer, mapOption); 

	// 장소 검색 객체를 생성합니다
	var ps = new kakao.maps.services.Places();

	// 검색 키워드 지정
	if(sessionStorage.getItem("search") != null) {
		keyword = sessionStorage.getItem("search");
		order = $(".order_box .order_option.selected").data("order");
		$("#keyword").val(keyword);
		searchList(keyword, order, placesSearchCB);
		sessionStorage.clear();
	} else {
		searchPlaces();
	}
	
	// 엔터키로 검색
	$("#keyword").keydown(function(event) {
		if(event.keyCode == 13) {
			searchPlaces();
		}
	});
	
	// 검색 버튼 클릭
	$("#searchBtn").click(function() {
		searchPlaces();
	});
	
	// 연관 검색어 클릭
	$(document).on('click', ".recommend", function() {
		keyword = $(this).text();
		$("#keyword").val(keyword);
//		searchList(keyword, placesSearchCB);
		searchPlaces();
	});
	
	// 정렬 순서
	$(document).on("click", ".order_option", function() {
		order = $(this).data("order");
		searchPlaces();
	});
	
	$(document).on("click", ".pin_box", function() {
		dId = $(this).attr("data-did");
		post_to_url('Controller',{'command':'diaryDetail','dId': dId});
	});
		
	// 리스트 스크롤 이벤트
	var lastScroll = 0;
	var wrapHeight = $(".list_wrap").height();
	var searchHeight = $(".search_wrap").height();
//	var listWrapHeight = $(".diary_list").height();
	$(".diary_list").scroll(function() {
		var listHeight = $(".search_list").height();
		var scrollBottom = $(".diary_list").height() + $(".diary_list").scrollTop()
		
//		if(lastScroll > $(".diary_list").scrollTop()) {
//			$(".search_wrap").stop().slideDown(function() {
//				$(".diary_list").height(wrapHeight - searchHeight);
//			});
//		} else {
//			$(".search_wrap").stop().slideUp(function() {
//				$(".diary_list").height(wrapHeight);				
//			});
//			
//		}
		lastScroll = $(".diary_list").scrollTop();
//		console.log(scrollBottom + " / " + listHeight);
		if(scrollBottom >= listHeight) {
			console.log("hit bottom");
			if(pageIdx < pageCnt) {
				pageIdx++;
				displayPlaces(listData, pageIdx);
			}
		}
	});
	
	// 윈도우 리사이즈 
	$(window).resize(function() {
		wrapHeight = $(".list_wrap").height()
		if($(".search_wrap").is(":visible")) {
			$(".diary_list").height(wrapHeight - searchHeight)
		} else {
			$(".diary_list").height(wrapHeight);
		}
		$("#rightMapArea").height(wrapHeight);
		map.setBounds(bounds);
	});
	
	// 키워드 검색을 요청하는 함수입니다
	function searchPlaces() {
		var keyword = document.getElementById('keyword').value;
		var order = $(".order_box .order_option.selected").data("order");
		searchList(keyword, order, placesSearchCB);
	}
	
	// db에서 결과 가져오기
	function searchList(keyword, order, callback) {
		$("#searchArea").text(keyword);
		if(keyword == null || keyword == ""){
			$("#searchArea").text("전체");	
		}
		$.ajax({
			type: "post",
			url:"Controller",
			data: {'command': 'searchList', "keyword": keyword, 'order': order},
			datatype: "json",
			success: function(data) {
				 console.log(data);
				listData = data.searchList;
				recommendData = data.recommend;
				
				total = listData.length;
				pageCnt = Math.floor(total / listToShow) + 1;
				pageIdx = 1;
				
				// 추천 검색어
				$(".recommend_list").empty();
				var recommendLength = recommendData.length;
				if(recommendData.length > 5) {
					recommendLength = 5;
				}
				for(j=0; j<recommendLength; j++) {
					$(".recommend_list").append('<span class="recommend">'+recommendData[j]+'</span>');
				}
				
				// 리스트 관련
				$(".search_list").empty();
				removeMarker();
				if(total == 0) {
					$(".search_list").append('<p class="no_list">검색결과가 없습니다.</p>');
				} else {
					displayPlaces(listData, pageIdx);					
					// 이미지 슬라이드
					$('.search_list .diary_img').slick({
						infinite: true,
						arrows: true,
						prevArrow:"<button type='button' class='slick-prev material-icons'>arrow_back_ios_new</button>",
						nextArrow:"<button type='button' class='slick-next material-icons'>arrow_forward_ios</button>"
					});
				}
			},
			error : function(request, status, error) {
				alert(request + "status : " + status + " error : " + error);
			}
		});
	}
	

	// 장소검색이 완료됐을 때 호출되는 콜백함수 입니다
	function placesSearchCB(data, status, pagination) {
		if (status === kakao.maps.services.Status.OK) {

			// 정상적으로 검색이 완료됐으면 검색 목록과 마커를 표출합니다
			displayPlaces(data);

		} else if (status === kakao.maps.services.Status.ZERO_RESULT) {
			alert('검색 결과가 존재하지 않습니다.');
			return;
		} else if (status === kakao.maps.services.Status.ERROR) {
			alert('검색 결과 중 오류가 발생했습니다.');
			return;
		}
	}
   
	var clickedOverlay = null;
	var clickedMarker = null;

	// 검색 결과 목록과 마커를 표출하는 함수입니다
	var bounds = new kakao.maps.LatLngBounds();
	function displayPlaces(places, pageIdx) {
		var listEl = document.getElementById('searchList'), 
			fragment = document.createDocumentFragment();
			
		if(pageIdx == 1) {
			// 검색 결과 목록에 추가된 항목들을 제거합니다
			removeAllChildNods(listEl);
	
			// 지도에 표시되고 있는 마커를 제거합니다
			removeMarker();
		}
		
		startIdx = (pageIdx - 1) * listToShow;
		endIdx = startIdx + listToShow;
		if(pageIdx == pageCnt)
			endIdx = startIdx + (total % listToShow);
		
		
		for (var i=startIdx; i<endIdx; i++) {
			// 마커를 생성하고 지도에 표시합니다
			var placePosition = new kakao.maps.LatLng(Number(places[i].lat), Number(places[i].lng)),
				marker = addMarker(placePosition, i), 
  				itemEl = getListItem(i, places[i]); // 검색 결과 항목 Element를 생성합니다

			// 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
			// LatLngBounds 객체에 좌표를 추가합니다
			bounds.extend(placePosition);
			
			function addOverlay(marker, places) {
				var overlay = new kakao.maps.CustomOverlay({
	       			yAnchor: 1.2,
	       			zIndex: 5,
	       			position: marker.getPosition()
	       		});
	              
	       		var content = document.createElement('div');
	       		content.classList.add("pin_box");
 	       		content.setAttribute("data-dId", places.dId);
	       		content.innerHTML =  
	       			'<div class="img_box">' +
 	       				'<img src="img/diary/'+ places.img[0] +'" alt="">' +
	       			'</div>' +
	       			'<div class="info_box">' +
 	       				'<p data-diary="view_count" class="view_cnt">'+places.view_count+'</p>' +
	       				'<p class="place_name">'+places.place_name+'</p>' +
	       			'</div>';

	       		overlay.setContent(content);
	       		return overlay;
           }

           (function(marker, places) {
				// 마커 클릭 시 커스텀 오버레이
				kakao.maps.event.addListener(marker, 'click', function() {
					var position = new kakao.maps.LatLng(Number(places.lat)+0.3,Number(places.lng));
					map.panTo(position);
            	   	var overlay = addOverlay(marker, places);
        
	       			if (clickedOverlay) {
       					clickedOverlay.setMap(null);
	       			}

					if(clickedMarker) {
						clickedMarker.setImage(markerUnfocus);
					}
					
	       			overlay.setMap(map);
	       			clickedOverlay = overlay;

					marker.setImage(markerClick);
					clickedMarker = marker;
	       		});
				
               // 리스트에 마우스 올릴 경우
               itemEl.onmouseover =  function () {
					var position = new kakao.maps.LatLng(Number(places.lat), Number(places.lng));
					//map.setLevel(level);
					map.panTo(position);
					
	       			if (clickedOverlay) {
						clickedOverlay.setMap(null);
						marker.setImage(markerUnfocus);
	       			}

					if(clickedMarker) {
						clickedMarker.setImage(markerUnfocus);
					}
					
					marker.setImage(markerHover);
					marker.setZIndex(1);
					//marker.setOpacity(1);
               };
			
               // 리스트에서 마우스 나올 경우
               itemEl.onmouseout =  function () {
//					map.panTo(bounds);
//					clickedOverlay.setMap(null);
					$.each(marker, function() {
						marker.setImage(markerUnfocus);	
					})
					marker.setImage(markerUnfocus);
					marker.setZIndex(0);
            	};
           })(marker, places[i]);

           fragment.appendChild(itemEl);
       }


		// 검색결과 항목들을 검색결과 목록 Elemnet에 추가합니다
		listEl.appendChild(fragment);
		//menuEl.scrollTop = 0;

		// 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
		map.setBounds(bounds);
		var level = map.getLevel();
	}

	// 검색결과 항목을 Element로 반환하는 함수입니다
	function getListItem(index, places) {
		var el = document.createElement('div');
		var imgTag = "";
		$.each(places.img, function(index, item) {
			imgTag += '<img src="img/diary/'+ item +'" alt="">';
		});
		el.innerHTML =
			'<div class="place_box">' +
				'<div class="diary_img">' +
					imgTag +
				'</div>' +
				'<div class="top_icon">' +
					'<button class="'+ places.pick +' pick_btn" onclick="pickBtn(event);"></button>' +
				'</div>' +
				'<div class="info_box">' +
					'<p data-diary="place_name" class="place_name">'+ places.place_name +'</p>' +
					'<p data-diary="address" class="address">' + places.address + '</p>' +
				'</div>' +
			'</div>' +
			'<div class="acct_box">' +
				'<img data-diary="profile" src="img/profile/'+ places.profile +'" alt="" class="acct_profile">' +
				'<p data-diary="id" class="writer_id">'+ places.writer_id +'</p>' +
				'<p data-diary="pick_count" class="pick_cnt">'+ places.pick_count +'</p>' +
				'<p data-diary="view_count" class="view_cnt">'+ places.view_count +'</p>' +
			'</div>';
			
		el.className = 'diary';
		el.setAttribute("data-dId", places.dId);
		el.setAttribute("idx", index+1);
		
		return el;
	}

	// 마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
	function addMarker(position, idx, title) {
 		var imageSrc = markerImg, // 마커 이미지 url, 스프라이트 이미지를 씁니다
 			imageSize = new kakao.maps.Size(24, 35),  // 마커 이미지의 크기
			markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize),
			marker = new kakao.maps.Marker({
				position: position, // 마커의 위치
				image: markerImage
			});

		marker.setMap(map); // 지도 위에 마커를 표출합니다
		markers.push(marker);  // 배열에 생성된 마커를 추가합니다

		return marker;
	}

	// 지도 위에 표시되고 있는 마커를 모두 제거합니다
	function removeMarker() {
		for ( var i = 0; i < markers.length; i++ ) {
			markers[i].setMap(null);
		}   
		markers = [];
	}

	// 검색결과 목록 하단에 페이지번호를 표시는 함수입니다
	function displayPagination(pagination) {
		var paginationEl = document.getElementById('pagination'),
			fragment = document.createDocumentFragment(),
			i; 

		// 기존에 추가된 페이지번호를 삭제합니다
		while (paginationEl.hasChildNodes()) {
			paginationEl.removeChild (paginationEl.lastChild);
		}

		for (i=1; i<=pagination.last; i++) {
			var el = document.createElement('a');
			el.href = "#";
			el.innerHTML = i;

			if (i===pagination.current) {
				el.className = 'on';
			} else {
				el.onclick = (function(i) {
					return function() {
						pagination.gotoPage(i);
					}
				})(i);
			}

			fragment.appendChild(el);
		}
		paginationEl.appendChild(fragment);
	}

	// 검색결과 목록의 자식 Element를 제거하는 함수입니다
	function removeAllChildNods(el) {   
		while (el.hasChildNodes()) {
			el.removeChild (el.lastChild);
		}
	}
	
	
	
	// 지도 확대 컨트롤
	function zoomIn() {
		map.setLevel(map.getLevel() - 1);
	}
	
	// 지도 축소 컨트롤
	function zoomOut() {
		map.setLevel(map.getLevel() + 1);
	}
	
	$("#zoomIn").click(function() {
		zoomIn();
	});
	$("#zoomOut").click(function() {
		zoomOut();
	})

	// 지도가 이동, 확대, 축소로 인해 지도영역이 변경되면 마지막 파라미터로 넘어온 함수를 호출하도록 이벤트를 등록합니다
	kakao.maps.event.addListener(map, 'bounds_changed', function() {             
	    
	    // 지도 영역정보를 얻어옵니다 
	    var bounds = map.getBounds();
	    
	    // 영역정보의 남서쪽 정보를 얻어옵니다 
	    var swLatlng = bounds.getSouthWest();
	    
	    // 영역정보의 북동쪽 정보를 얻어옵니다 
	    var neLatlng = bounds.getNorthEast();
	    
	    var message = '<p>영역좌표는 남서쪽 위도, 경도는  ' + swLatlng.toString() + '이고 <br>'; 
	    message += '북동쪽 위도, 경도는  ' + neLatlng.toString() + '입니다 </p>'; 
	    
	});

});


