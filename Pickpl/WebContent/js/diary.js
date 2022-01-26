//console.log(diaryId);
//console.log(isMy);

loadSummary(diaryId);
loadList(diaryId, 'up_date');

var diaryData, total, pageCnt, pageIdx;
var pageToShow = 12;

// 상단 요약 불러오기
function loadSummary(id) {
	//	console.log("loadSummary 실행");
	$.ajax({
		url: 'Controller',
		type: 'post',
		data: { 'command': 'diarySummary', 'diaryId': id },
		dataType: 'json',
		success: function(data) {
			//console.log(data);
			$("#diary_id").text(data.diaryId);
			$("#diaryCnt").text(data.pickplCnt);
			$("#pickCnt").text(data.pickCnt);
			$("#pickedCnt").text(data.pickedCnt);
		},
		error: function(request, status, error) {

		}
	});
}

// 리스트 불러오기
function loadList(id, order) {
	$.ajax({
		url: 'Controller',
		type: 'post',
		data: { 'command': 'diaryList', 'diaryId': id, 'order': order },
		dataType: 'json',
		success: function(data) {
			if(!data.diaryList.length) $(".no_list").show();
			else $(".no_list").hide();
			
			diaryData = data.diaryList;
			total = data.diaryList.length;
			pageCnt = Math.floor(total / pageToShow) + 1;
			pageIdx = 1;

			$("#searchList").empty();
			addDiary(data.diaryList, pageIdx);
		},
		error: function(request, status, error) {
			alert("list error");
		}
	});
}

// 다이어리 박스
function addDiary(item, pageIdx) {
	startIdx = (pageIdx - 1) * pageToShow;
	endIdx = startIdx + pageToShow;
	if (pageIdx == pageCnt) {
		endIdx = startIdx + (total % pageToShow);
	}

	for (var i = startIdx; i < endIdx; i++) {
		var diaryHtml = '<div data-dId="' + item[i].dId + '" class="diary">' +
			'<div class="place_box">' +
			'<div class="diary_img">';
		for (var j = 0; j < item[i].img.length; j++) {
			diaryHtml += '<img src="img/diary/' + item[i].img[j] + '" alt=""></img>'
		}
		diaryHtml += '</div>' +
			'<div class="top_icon">';
		if (isMy) {
			diaryHtml += '<button class="material-icons edit_btn my_diary" onclick=\"editDiary(event);\">edit</button>' +
				'<button class="material-icons del_btn my_diary">delete</button>';
		} else {
			diaryHtml += '<button class="' + item[i].pick + ' pick_btn other_diary" onclick="pickBtn(event);"></button>';
		}
		diaryHtml += '</div>' +
			'<div class="info_box">' +
			'<p class="place_name">' + item[i].place_name + '</p>' +
			'<p class="address">' + item[i].address + '</p>' +
			'</div>' +
			'</div>';
		diaryHtml += '<div class="acct_box">' +
			'<p class="pick_cnt">' + item[i].pick_count + '</p>' +
			'<p class="view_cnt">' + item[i].view_count + '</p>' +
			'</div>' +
			'</div>';
		//console.log(diaryHtml);
		$("#searchList").append(diaryHtml);
	}
}

//// 새로고침 시 스크롤 상단으로 이동
//$(window).on('beforeunload', function() {
//	$(window).scrollTop(0);
//});

$(function() {
	// 15초마다 상단 정보 업데이트하기
	var intervalID = setInterval(loadSummary, 15000, diaryId);

	// 수정버튼 클릭 시
	$(".edit_btn").on("click", function() {
		dId = $(this).parents(".diary").attr("data-dId");
		location.href = "diary_write.html?dId=" + dId;
	});

	// 다이어리 삭제 버튼 클릭 이벤트
	$(document).on("click", ".del_btn", function(e) {
		dId = $(this).parents(".diary").attr("data-dId");
		Swal.fire({
			title: '삭제하시겠습니까?',
			text: "삭제 후에는 복구가 불가능합니다.",
			icon: 'warning',
			showCancelButton: true,
			confirmButtonColor: '#0ea098',
			cancelButtonColor: '#46616e',
			confirmButtonText: '삭제',
			cancelButtonText: '취소',
			reverseButtons: true,
			focusConfirm: false
		}).then((result) => {
			if (result.isConfirmed) {
				dId = $(e.target).parents(".diary").attr("data-dId");
				$.ajax({
					url: 'Controller',
					type: 'post',
					data: { 'command': 'diaryDel', 'dId': dId },
					dataType: 'json',
					success: function(data) {
						//console.log(data);
						if(data.result == "success") {
							Swal.fire({
								title: '삭제되었습니다.',
								icon: 'success',
								confirmButtonColor: '#0ea098',
								confirmButtonText: '확인',
								timer: 2000,
								focusConfirm: false
							});
						}
					},
					error: function(request, status, error) { }
				})
				loadSummary(diaryId);
				$(e.target).parents(".diary").remove();
			}
		})
	});

	$(document).on("click", ".order_option", function() {
		order = $(this).data("order");
		loadList(diaryId, order);
	});

	$(document).scroll(function() {
		var documentH = $(document).height();
		var scrollBottom = $(document).scrollTop() + $(window).height() + 100;

		if (documentH < scrollBottom) {
			if (pageIdx < pageCnt) {
				pageIdx++;
				addDiary(diaryData, pageIdx);
			}
		}
	});
});

// 수정 버튼 클릭
function editDiary(e) {
	dId = $(e.target).parents(".diary").attr("data-dId");
	location.href = "diary_write.jsp#" + dId;
}