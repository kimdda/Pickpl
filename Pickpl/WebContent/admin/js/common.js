
// 이전 페이지
function prevPage() {
	$page = $("#paging"); 
	if($page.val() == 1)
		alert("첫 페이지 입니다.");
	if($page.val() > 1) {
		$page.val(--pageIdx);
		searchDiary();
	}
}

// 다음 페이지
function nextPage() {
	$page = $("#paging");
	if(pageIdx == pageCnt)
		alert("마지막 페이지 입니다.");
	if($page.val() < pageCnt) {
		$page.val(++pageIdx);
		searchDiary();
	}
}

// 이동 페이지 입력
function inputPage(e) {
	if(e.keyCode == 13) {
		//console.log(e.target);
		if(e.target.value < 1 || e.target.value > pageCnt) {
			alert("페이지 범위 내에서 입력해 주세요.");
		} else {
			searchDiary();
		}
	}
}