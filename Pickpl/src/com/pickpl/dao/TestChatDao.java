package com.pickpl.dao;

import com.pickpl.dto.ChatDto;

public class TestChatDao {
	static ChatDao cDao = new ChatDao();
	
	// 대화상대 이력 확인
	static void testCheckSendTo() {
		System.out.println(cDao.checkSendTo("id3", "idid1"));
	}
	// 대화상대 프로필 가져오기
	static void testGetProfile() {
		System.out.println(cDao.getProfile("idid1"));
	}
	//대화상대 최근이력 가져오기
	static void testGetAcctList() {
		for(ChatDto dto : cDao.getAcctList("id1")) {
			System.out.println(dto);			
		}
	}
	// 읽지 않은 메시지 있는지 확인
	static void testCheckUnread() {
		System.out.println(cDao.checkUnread("id1", "id3"));
	}	
	// 대화 내역 받아오기
	static void testGetChatList() {
		System.out.println(cDao.getChatList("id3", "id1"));
	}
	// 전송 시 대화내역 추가
	static void testUpdateMsg() {
		System.out.println(cDao.updateMsg("id3", "id2", "zzz", "2022-01-17 12:23:34"));
	}
	
	public static void main(String[] args) {
		// 대화상대 이력 확인
//		testCheckSendTo();
		// 대화상대 프로필 가져오기
//		testGetProfile();
		//대화상대 최근이력 가져오기
//		testGetAcctList();
		// 읽지 않은 메시지 있는지 확인
		testCheckUnread();
		// 대화 내역 받아오기
//		testGetChatList();
		// 전송 시 대화내역 추가
//		testUpdateMsg();
	}
}
