package com.pickpl.dao;

import com.pickpl.dto.MemberDto;

public class TestMemberDao {
	static MemberDao mDao = new MemberDao();
	static MemberDto mDto = new MemberDto("testtest", "1234", "12361.jpeg", "testtest@naver.com", "이름", "01012341234", "2000-01-01", "F", "N");
	// 아이디 중복 체크
	static void testMemberIdCheck() {
		System.out.println(mDao.memberIdCheck("testtest"));
	}
	// 회원가입
	static void testMemberJoin() {
		System.out.println(mDao.memberJoin(mDto, "123412341234"));
	}
	// 가입 시 픽 기본폴더 생성 
	static void testCreatePickFolder() {
		System.out.println(mDao.createPickFolder("test1234"));
	}
	// 아이디 찾기
	static void testFindId() {
		System.out.println(mDao.findId("내이름", "tjwjd990518@naver.com"));
	}	
	// 비밀번호 찾기
	static void testFindPw() {
		System.out.println(mDao.findPw(mDto.getId(), mDto.getName(), mDto.getEmail()));
	}
	// 비밀번호 인증번호 추가
	static void testAddPwLink() {
		System.out.println(mDao.addPwLink("testtest"));
	}
	// 인증번호 확인
	static void testcheckVerifyNo() {
		System.out.println(mDao.checkVerifyNo("testtest", "g808l2CbxdyQU90"));
	}
	// 계정상태변경
	static void testUpdateStat() {
		System.out.println(mDao.updateStat("testtest", "A"));
	}
	// 비밀번호 변경
	static void testUpdatePw() {
		System.out.println(mDao.updatePw("testtest", "12341234"));
	}
	// 로그인(아이디 비번 확인)
	static void testLogincheck() {
		System.out.println(mDao.loginCheck("testtest", "12341234"));
	}
	// 계정상태 확인
	static void testCheckAccStat() {
		System.out.println(mDao.checkAccStat("testtest"));
	}
	// 개인 정보 가져오기
	static void testGetMyInfo() {
		System.out.println(mDao.getMyInfo("testtest"));
	}
	// 개인 정보 수정
	static void testUpdateMyInfo() {
		System.out.println(mDao.updateMyInfo(mDto));
	}
	// 탈퇴 _ 멤버 테이블 업데이트
	static void testByeMember() {
		System.out.println(mDao.byeMember("testtest"));
	}
	// 탈퇴 _ 다이어리 정보 가져오기
	static void testGetByeDiary() {
		System.out.println(mDao.getByeDiary("testtest"));
	}
	// 탈퇴 _ 픽 정보 삭제
	static void testByePick() {
		System.out.println(mDao.byePick("testtest"));
	}
	// 탈퇴 _ 내가 쓴 댓글 정보 업데이트
	static void testByeCmnts() {
		System.out.println(mDao.byeCmnts("testtest"));
	}
	// 탈퇴 _ 최근 본 글 삭제
	static void testByeRecent() {
		System.out.println(mDao.byeRecent("testtest"));
	}
	
	public static void main(String[] args) {
		
		
		// 아이디 중복 체크
//		testMemberIdCheck();
		// 가입
//		testMemberJoin();
		// 아이디 찾기
//		testFindId();
		// 비밀번호 인증번호 추가
//		testAddPwLink();
		// 비밀번호 찾기
//		testFindPw();
		// 인증번호 확인
//		testcheckVerifyNo();
		// 계정상태 변경
//		testUpdateStat();
		// 비밀번호 변경
//		testUpdatePw();
		// 로그인
//		testLogincheck();
		// 계정상태확인
//		testCheckAccStat();
		// 개인정보 가져오기
//		testGetMyInfo();
		// 개인정보 수정
//		testUpdateMyInfo();
		// 탈퇴 _ 멤버 테이블 업데이트
//		testByeMember();
		// 탈퇴 _ 다이어리 정보 가져오기
//		testGetByeDiary();
		// 탈퇴 _ 픽 정보 삭제
//		testByePick();
		// 탈퇴 _ 내가 쓴 댓글 정보 업데이트
//		testByeCmnts();
		// 탈퇴 _ 최근 본 글 삭제
//		testByeRecent();
		// 가입 시 픽 기본폴더 생성 
//		testCreatePickFolder();
		// 내가 쓴 댓글 삭제
//		testDeleteMyCmnt();
	}
}
