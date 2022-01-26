$(function() {
	// 선택 해제
	$('#deselect_btn').click(function() {
		$('.folder input').prop('checked', false);
		$('#selected_cnt').text(0);
	});
	
	// 선택 개수 변경
	$(document).on('change', "[name=folder]", function() {
		$("#selected_cnt").text($("[name=folder]:checked").length);
	});
	
	// 폴더 추가
	$(".folder_add_btn").click(function() {
		Swal.fire({
			customClass : {
				container : 'addFolderPop',
				title: 'addFolder_title',
				htmlContainer: 'addFolderPop_content'
			},
			title: '픽 폴더 추가하기',
			html: '<p class="error"></p>' +
				'<p class="input_box"><input type="text" id="addFolderName" name="folderName" placeholder="폴더 이름" /></p>',
			//showCloseButton: false,
			showCancelButton: true,
			focusConfirm: false,
			confirmButtonText: '확인',
			cancelButtonText:'취소',
			confirmButtonColor : "#0ea098",
			reverseButtons: true,
			preConfirm : () => {
				newName = $("[name='folderName']").val();
				if(newName == "") {
					$(".error").text("폴더 이름을 입력해 주세요.");
					return false;
				}
				
				$(".folder_box_list .folder").each(function() {
					folderName = $(this).find(".folder_name").text();
					match = false;
					if(folderName == newName) {
						$(".error").text("동일한 이름의 폴더가 존재합니다. 다른 이름을 입력해 주세요.");
						match = true;				
					}
				});
				if(match)
					return false;
			}
		}).then((result) => {
			if (result.isConfirmed) {
				$.ajax({
					url: 'Controller',
					type: 'post',
					data: {"command": 'addPickFolder', "folderName": newName},
					dataType: 'json',
					success: function(data) {
						if (data.result == 'success') {
							$(".folder_box_list").append(
								'<div class="folder">' +
									'<div class="folder_box" onclick="toDetail(event);">' +
										'<input type="checkbox" name="folder">' +
										'<span class="pick_cnt">0</span>' +
									'</div>' +
									'<div class="name_box">' +
										'<p class="folder_name">' + newName + '</p>' +
										'<button class="mdfy_btn material-icons editNameBtn" onclick="edit(event);">edit</button>' +
									'</div>' +
								'</div>'
							);
							
							Swal.fire({
								text : newName + ' 폴더가 추가되었습니다.',
								icon : 'success',
								customClass : 'sweet-size',
								showConfirmButton : true,
								confirmButtonText: '확인',
								timer: 2000
							});
						}
					},
					error: function() {
						alert('폴더 저장 ajax 실패');
					}
					
				})
			}
		})
	});
	
	// 추가 팝업에서 폴더 이름 입력 시 이벤트
	$(document).on('keypress', ".addFolderPop #addFolderName", function() {
		$(this).parent().prev().text("");
	});
	
	// 삭제 버튼 클릭 이벤트
	$("#deleteFolderBtn").click(function() {
		$checked = $('.folder input[name="folder"]:checked');
		if($checked.length == 0) {
			Swal.fire({
				text : '삭제할 폴더를 선택해 주세요.',
				icon : 'warning',
				showConfirmButton: true,
				confirmButtonText: '확인',
				confirmButtonColor: '#0ea098'
			});
		} else {
			deleteFolder($checked);
		}
	});
	
	// 정렬 순서 변경
	$(".order_option").click(function() {
		order = $(this).data("order");
		$.ajax({
			url: 'Controller',
			type: 'post',
			data: {"command": "pickFolderList", "order": order},
			dataType: 'json',
			success: function(data) {
				$("#selected_cnt").text("0");
				$(".folder_box_list").empty();
				$.each(data.folderList, function(index, item) {
					html = '<div class="folder" onclick="toDetail(event);">' +
							'<div class="folder_box" >';
					if(item.folder != "기본폴더") {
						html += '<input type="checkbox" name="folder">';
					}
						html += '<span class="pick_cnt">' + item.folder_cnt + '</span>' +
							'</div>' +
							'<div class="name_box">' +
								'<p class="folder_name">' + item.folder + '</p>';
					if(item.folder != "기본폴더") {
						html +=	'<button class="mdfy_btn material-icons editNameBtn">edit</button>';
					}
					html +=	'</div>' +
						'</div>';
						
					$(".folder_box_list").append(html);
				});
			}
		});
	});
		
});

// 픽 폴더 삭제
function deleteFolder($checked) {
	Swal.fire({
		text : '선택하신 폴더를 정말 삭제하시겠습니까?',
		icon : 'question',
		showCancelButton: true,
		confirmButtonText: '삭제',
		confirmButtonColor: '#0ea098',
		cancelButtonText: '취소',
		reverseButtons: true
	}).then((result) => {
		if (result.isConfirmed) {
			folderName = "";
			folderCnt = 0;
			$checked.each(function() {
				folderName += $(this).parents('.folder').find(".folder_name").text() + "_";
				folderCnt++;
			});
			
			$.ajax({
				url: 'Controller',
				type: 'post',
				data: {"command": "delPickFolder", "folderName": folderName},
				dataType: 'json',
				success: function(data) {
					if (data.result == 'success') {
						$checked.parents(".folder").remove();
						Swal.fire({
							text : '선택한 폴더가 삭제되었습니다.',
							icon : 'success',
							showConfirmButton: true,
							confirmButtonText: '확인',
							confirmButtonColor: '#0ea098',
							timer: 2000
						});
					}
				}
			});

		}
	})
}

// 픽 폴더 변경
function edit(e) {
	$target = $(e.target).prev();
	folder = $target.text();
//	console.log($target.text());
	Swal.fire({
		customClass: {
			container: 'folderNamePop'
		},
		title : '폴더 이름 변경',
		html : 
			'<input type="text" name="folder_name" id="mdfyFolderName" placeholder="'+ folder +'">' +
			'<p class="error"></p>',
		showConfirmButton : true,
		confirmButtonText : '확인',
		showCancelButton: true,
		cancelButtonText : '취소',
		confirmButtonColor: '#0ea098',
		reverseButtons: true,
		preConfirm: () => {
			newName = $("input[name='folder_name']").val();
		    if(!newName) {
				$(".folderNamePop .error").text("폴더 이름을 입력해 주세요.");
				return false;
			}
			$(".folder_box_list .folder").each(function() {
				folderName = $(this).find(".folder_name").text();
				match = false;
				if(folderName == newName) {
					$(".error").text("동일한 이름의 폴더가 존재합니다. 다른 이름을 입력해 주세요.");
					match = true;
					return false;
				}
			});
		}
	}).then((result) => {
		if (result.isConfirmed) {
			newName = $("input[name='folder_name']").val();
			$.ajax({
				url: 'Controller',
				type: 'post',
				data: {
					'command' : 'mdfyPickFolder',
					'folder' : folder,
					'newName' : newName					
				},
				dataType: 'json',
				success: function(data) {
					if(data.result == "success") {
						Swal.fire({
							text : '폴더 이름이 수정되었습니다.',
							icon : 'success',
							showConfirmButton : true,
							confrimButtonText : "확인",
							confrimButtonColor : '#0ea098'
						});						
						$target.text(newName);
					} else {
						Swal.fire({
							text : '폴더 이름 수정에 실패했습니다.',
							icon : 'error',
							showConfirmButton : true,
							confrimButtonText : "확인",
							confrimButtonColor : '#0ea098'
						});	
					}
				}
			});
		}
	});
}

function toDetail(e) {
//	console.log($(e.target).next().find(".folder_name").text());
	if(!$(e.target).is("input")) {
		if($(e.currentTarget).find(".pick_cnt").text() == 0) {
			Swal.fire({
				text : '해당 폴더에 추가된 픽플이 없습니다.',
				icon : 'warning',
				showConfirmButton: true,
				confirmButtonText: '닫기',
				confirmButtonColor: '#0ea098',
			});
		} else {
			folder = $(e.target).next().find(".folder_name").text();
			post_to_url('Controller',{'command':'pickFolderDetail','folder':folder});
		}
	}
}