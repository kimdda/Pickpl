var webSocket = new WebSocket('ws://localhost:9090/Pickpl/BroadSocket');
var inputMessage = document.getElementById('inputMessage');
var sendTo;
var msgIdx = 0;
var acctData, chatData, acctTotal, chatTotal;
var acctPageCnt, acctPageIdx, listPageIdx;
var acctToShow = 7;

$(function() {
	if(loginId == "null") {
		Swal.fire({
		  icon: 'error',
		  //title: '접근권한이 없습니다.',
		  html: '<strong>접근 권한이 없습니다.</strong>',
		  confirmButtonText: '확인',
		  confirmButtonColor : '#0ea098'
		}).then(function() {
		    window.location = "index.jsp";
		});
	}
	
	sendTo = $("#toId").text();
	listLoader();

	// 채팅 목록 선택 이벤트
	$(document).on('click', ".acct_box", function() {
		$(".acct_box").removeClass("active");
		$(this).addClass("active").attr("data-check", "Y");
		$("#inputMessage").val("");
		sendTo = $(this).find(".msg_id").text();
		$("#toId").text(sendTo);
		$("#msgWindow").empty();
		
		chatLoader(sendTo);
	});
	
	

	// 대화영역 스크롤 이벤트
	$(".msg_container").scroll(function() {
		var scrollTop = $(this).scrollTop();
		var wrapHeight = $(this).height();
		var msgHeight = $(".msg_wrap").outerHeight();

		if((wrapHeight + scrollTop) + 5 > msgHeight) {
			//console.log("hit bottom");
			lastMsg = $(".msg_wrap .msg_box[data-msg='in']:last-of-type pre:last-of-type");
			msgCheck(sendTo);

			var checkingMsg = $("[data-msg='in'] pre[data-check='N']").length;
			var checkMsg = "/";
			if(checkingMsg > 0) {
				for(var i=0; i<checkingMsg; i++) {
					checkMsg += $("[data-msg='in'] pre[data-check='N']").eq(i).data("idx") + "-";
				}
			}
			webSocket.send(checkMsg);
			$("[data-msg='in'] pre[data-check='N']").attr("data-check", "Y");
		}
	});
	
	// 대화상대 목록 스크롤 이벤트
	$(".acct_wrap").scroll(function() {
		var acctListH = $(".acct_list").height();
		var scrollBottom = $(".acct_wrap").height() + $(".acct_wrap").scrollTop() + 1;
		if(scrollBottom >= acctListH) {
//			console.log("hit bottom");
			if(acctPageIdx < acctPageCnt) {
				acctPageIdx++;
				addAcctBox(acctData, acctPageIdx);			
			}
//			console.log(acctPageIdx);
		}
	});
});

function websocket() {
	webSocket.onerror = function(event) {
		onError(event)
	};
	webSocket.onopen = function(event) {
		onOpen(event)
	};
	webSocket.onmessage = function(event) {
		onMessage(event)
	};
}

function onMessage(event) {
	if((event.data).startsWith("/")) {
		var msgIdx = event.data.substring(1).split("-");
		for(var i=0; i<msgIdx.length; i++) {
			$("[data-idx='"+msgIdx[i]+"']").attr("data-check", "Y");
		}
	} else {
		var message = event.data.split("|");
		var receiver = message[0];
		var sender = message[1];
		var content = message[2];
		var time = message[3].substring(message[3].indexOf(" ")+1, message[3].lastIndexOf(":"));
		var rIdx = message[4];
  
		if(content != "" && receiver == loginId && sender == sendTo) {
			addMsgBox("in", content, time, "N", rIdx);
			if($(".date_box").last().find("p").text() != "오늘") {
				addDateBox(dateFormat(new Date));
			}
		}
	}
}

function onOpen(event) {
	//$("#msgWindow").append("<p class='chat_content'>"+sendTo+"님과의 채팅에 참여하였습니다.</p>");
}

function onError(event) {
	 alert(event.data);
}

function send() {
	msgIdx++;
	sendTime = (new Date).toLocaleTimeString().substring(0, 5);
	current = dateTimeFormat(new Date());
	if (($("#inputMessage").val()).trim() != "") {
		if($(".date_box").last().find("p").text() != "오늘") {
			addDateBox(dateFormat(new Date));	
		}
		sendTo = $("#toId").text();
		
		addMsgBox('out', $("#inputMessage").val(), sendTime, 'N', (sendTo+msgIdx));
		
		webSocket.send(sendTo + "|" + loginId + "|" + $("#inputMessage").val() + "|" + current + "|" + (sendTo+msgIdx));
	}
	// db올리기
	updateMsg(($("#inputMessage").val()).trim(), current);
	$("#inputMessage").val("");
	$("#msgCont").scrollTop($("#msgCont").prop("scrollHeight"));
}
// 엔터키를 통해 send함
function enterkey() {
	if (window.event.keyCode == 13) {
		 send();
	}
}


// 대화상대 박스
function addAcctBox(item, acctPageIdx) {
	startIdx = (acctPageIdx - 1) * acctToShow;
	endIdx = startIdx + acctToShow;
	if(acctPageIdx == acctPageCnt)
		endIdx = startIdx + (acctTotal % acctToShow);
		
	for(i=startIdx; i<endIdx; i++) {
		listHtml = 
			'<div class="acct_box" data-check="'+ item[i].unCheck +'">' +
				'<div class="img_box">' +
					'<img src="img/profile/'+ item[i].profile +'" alt="기본 프로필 사진">' +
				'</div>' +
				'<div class="content">' +
					'<div class="info">' +
							'<p class="msg_id">'+ item[i].sendTo +'</p>' +
							'<p class="msg_time">' + item[i].send_date + '</p>' +
					'</div>' +
					'<p class="msg_content">' + item[i].msg + '</p>' +
				'</div>' +
			'</div>';
		$(".acct_list").append(listHtml);
	}
}



// 대화상대 목록 로드
function listLoader() {
	$.ajax({
		url : 'Controller',
		type : 'post',
		data : {'command': 'chatAcct','id': loginId},
		dataType : 'json',
		success : function(data){
			acctData = data.acctList;
			acctTotal = data.acctList.length;
			acctPageCnt = Math.floor(acctTotal / acctToShow) + 1;
			acctPageIdx = 1;
			$(".acct_list").empty();
			addAcctBox(acctData, acctPageIdx);
		},
		error :function(request, status, error) {
			console.log("error");
		},
		complete: function() {
			sendTo = $("#toId").text();
			if($("#toId").text() == "") {
				sendTo = $(".acct_box:first-of-type").find(".msg_id").text();
				$("#toId").text(sendTo);
			}
			$(".acct_list .msg_id").each(function() {
				if($(this).text() == $("#toId").text())
					$(this).parents(".acct_box").addClass("active");
			});
			chatLoader(sendTo);
			if($(".msg_container").height() >= $(".msg_wrap").outerHeight()) {
				msgCheck(sendTo);
			}
			$(".acct_box.active").attr('data-check', 'Y');
			websocket();
		}
	});
}

// 날짜 박스 추가
function addDateBox(date) {
	date_year = date.split("-")[0];
	date_month = date.split("-")[1];
	date_date = date.split("-")[2];
	if(date == dateFormat(new Date)) {
		$("#msgWindow").append(
			'<div data-date="'+ date +'" class="date_box">' +
				'<div class="line"></div>' +
				'<p>오늘</p>' +
			'</div>'
		);
	} else {
		$("#msgWindow").append(
			'<div data-date="'+ date +'" class="date_box">' +
				'<div class="line"></div>' +
				'<p><span class="year">'+date_year+'</span>년 <span class="month">'+date_month+'</span>월 <span class="date">'+date_date+'</span>일</p>' +
			'</div>'
		);
	}
}

// 메시지 박스 추가
function addMsgBox(type, msg, time, check, idx, sendDate) {
	if(type == 'in') {
		$("#msgWindow").append(
			'<div data-msg="' + type + '" data-date="' + sendDate + '" class="msg_box">' +
				'<img src="'+ $(".acct_box.active").find(".img_box img").attr("src") +'" alt="대화상대 프로필 이미지" class="profile_img" />' +
				'<div class="msg">' +
					'<pre data-check="' + check + '" data-idx="'+ idx + '">' + msg + '</pre>' +
				'</div>' +
				'<p class="msg_time">'+ time +'</p>' +
			'</div>'
		);
	} else {
		$("#msgWindow").append(
			'<div data-msg="' + type + '" data-date="' + sendDate + '"class="msg_box">' +
				'<div class="msg">' +
					'<pre data-check="' + check + '" data-idx="'+ idx +'">' + msg + '</pre>' +
				'</div>' +
				'<p class="msg_time">'+ time +'</p>' +
			'</div>'
		);
	}
}

// 메시지 내역 추가
function chatLoader() {
	toId = $(".acct_box.active").find(".msg_id").text();
	//console.log(toId);
	$.ajax({
		url : 'Controller',
		type : 'post',
		data : {
			'command':'chatMsg',
			'sendTo' : toId
		},
		dataType : 'json',
		success : function(data){
			$(".msg_wrap").empty();
			$.each(data.msgList, function(index, item) {
				itemDate = new Date(item.date);
				msgDate = dateFormat(itemDate);
				msgTime = timeFormat(itemDate);
				
				// 날짜 비교군
				dateBox = $(".msg_box").last().data("date");
	
				// 날짜가 다른 경우, 날짜 추가
				if(index == 0 || dateBox != msgDate) {
					addDateBox(msgDate);
				}
				
				// 메시지 추가				
				addMsgBox(item.type, item.msg, msgTime, item.check, item.idx, msgDate);
			});
			
			$("#msgCont").scrollTop($("#msgCont").prop("scrollHeight"));

		},
		error :function(request, status, error) {
			console.log("error");
		},
		complete: function() {
			
		}
	})
}

// 메시지 전송
function updateMsg(content, sendDate) {
	toId = $("#toId").text();
	$.ajax({
		url : 'Controller',
		type : 'post',
		data : {
			'command':'sendMsg',
			'toId' : toId, 
			'message': content, 
			'sendDate': sendDate
		},
		dataType : 'json',
		success : function(data){
			listLoader();
		}
	});
}

// 메시지 체크 업데이트
function msgCheck(checkId) {
	$.ajax({
		url : 'Controller',
		type : 'post',
		data : {
			'command':'checkMsg',
			'check_id' : checkId
		},
		dataType : 'json',
		success : function(data){
			//console.log("check : " + data.update);
			$(".msg_wrap pre[data-msg='in'][data-check='N']").attr("data-check", "Y");
		},
		error :function(request, status, error) {
			alert("checkupdate - status : " + status + ", message : " + request.responseText + ", error : " + error);
		}
	});
}

function dateTimeFormat(newDate) {
	year = newDate.getFullYear();
	month = ('00' + (newDate.getMonth() + 1)).slice(-2);
	date = ('00' + newDate.getDate()).slice(-2);
	time = newDate.toLocaleTimeString('it-IT');

	return year + "-" + month + "-" + date + " " + time;
}

function dateFormat(newDate) {
	year = newDate.getFullYear();
	month = ('00' + (newDate.getMonth() + 1)).slice(-2);
	date = ('00' + newDate.getDate()).slice(-2);
	
	return year + "-" + month + "-" + date;
}

function timeFormat(newDate) {
	time = newDate.toLocaleTimeString();
	time = time.substring(0, (time).lastIndexOf(":"));

	return time;
}

