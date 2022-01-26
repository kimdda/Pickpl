package com.pickpl.admin.dao;

import org.json.simple.JSONObject;

import com.pickpl.admin.dto.RcmndDto;

public class TestAdminDao {
	static AdminDao aDao = new AdminDao();

	// 로그인
	static void testLogin() {
		System.out.println(aDao.login("admin", "1234!"));
	}

	// 회원 검색 _ 전체 수
	static void testGetTableTotal() {
		System.out.println(aDao.getSearchMemberTotal("%", "", ""));
	}

	// 회원 목록 검색 결과
	static void testGetMemberList() {
		System.out.println(aDao.getMemberList("%", "id", "id5", 1, 11));
	}

	// 멤버 상세 _ 멤버 테이블
	static void testGetMemberInfo() {
		System.out.println(aDao.getMemberInfo("test1234"));
	}

	// 멤버 상세 _ 활동 내역
	static void testGetMemberActInfo() {
		System.out.println(aDao.getMemberActInfo("test1234"));
	}

	// 멤버 상세 _ 픽 활동 내역
	static void testGetMemberPickInfo() {
		System.out.println(aDao.getMemberPickInfo("test1234"));
	}

	// 미처리 신고 내역
	static void testGetReport() {
//		System.out.println(aDao.getReport("test1234"));
		System.out.println(aDao.getReport(34));
	}

	// 신고 받은 횟수
	static void testGetReportedCount() {
//		System.out.println(aDao.getReportedCount("test1234"));
		System.out.println(aDao.getReportedCount(34));
	}

	// 계정 상태 변경
	static void testMdfyAccStat() {
		System.out.println(aDao.mdfyAccStat("test1234", "A"));
	}

	// 이메일 수정
	static void testMdfyMemberEmail() {
		System.out.println(aDao.mdfyMemberEmail("test1234", "test1234@email.com"));
	}

	// 추천 목록 받아오기
	static void testGetRcmndList() {
		System.out.println(aDao.getRcmndList(1, 11));
	}

	// 추천 글 상세
	static void testGetRcmndDetail() {
		System.out.println(aDao.getRcmndDetail(21));
	}

	// 추천글 작성 _ 다이어리 전체 수
	static void testGetSearchDiaryTotal() {
		System.out.println(aDao.getSearchDiaryTotal("", "", "용인"));
	}

	// 추천글 작성 _ 다이어리 검색
	static void testSearchDiary() {
		System.out.println(aDao.searchDiary("", "", "", 1, 11));
	}

	static RcmndDto rDto = new RcmndDto(61, "testMdfy", "2022-01-09", "2022-01-10", "2022-01-31", "3_4_5_6_", 4, null);

	// 추천글 작성
	static void testWriteRcmnd() {
		System.out.println(aDao.writeRcmnd(rDto));
	}

	// 추천글 수정
	static void testUpdateRcmnd() {
		System.out.println(aDao.updateRcmnd(rDto));
	}

	// 추천글 삭제
	static void testDeleteRcmnd() {
		System.out.println(aDao.deleteRcmnd(62));
	}

	// 다이어리 관리 _ 전체 글 수
	static void testGetDiaryTotal() {
		System.out.println(aDao.getDiaryTotal("%", "", "", "", ""));
	}

	// 다이어리 검색
	static void testGetDiaryList() {
		System.out.println(aDao.getDiaryList("D", "", "", "", "", 1, 11));
	}

	// 다이어리 관리 _ 상세
	static void testGetDiaryDetail() {
		System.out.println(aDao.getDiaryDetail(61));
	}

	// 다이어리 상태 변경
	static void testMdfyDiaryStat() {
		System.out.println(aDao.mdfyDiaryStat(100, "N"));
	}

	// 신고 처리 _ 신고 테이블 변경
	static void testUpdateReport() {
		System.out.println(aDao.updateReport(1, "처리완료"));
	}

	// 신고 목록 _ 전체 수
	static void testGetReportTotal() {
		System.out.println(aDao.getReportTotal("%", "%", "", ""));
	}

	// 신고 목록 _ 리스트
	static void testGetReportList() {
		System.out.println(aDao.getReportList("%", "%", "", "", 1, 11));
	}

	// 신고 상세
	static void testGetReportDetail() {
		System.out.println(aDao.getReportDetail(1));
	}

	public static void main(String[] args) {
		// 로그인
//		testLogin();
		// 회원 검색 _ 전체 수
		testGetTableTotal();
		// 회원 목록 검색 결과
//		testGetMemberList();
		// 멤버 상세 _ 멤버 테이블
//		testGetMemberInfo();
		// 멤버 상세 _ 활동 내역
//		testGetMemberActInfo();
		// 멤버 상세 _ 픽 활동 내역
//		testGetMemberPickInfo();
		// 미처리 신고 내역
//		testGetReport();
		// 신고 받은 횟수
//		testGetReportedCount();
		// 계정 상태 변경
//		testMdfyAccStat();
		// 이메일 수정
//		testMdfyMemberEmail();
		// 추천 목록 받아오기
//		testGetRcmndList();
		// 추천 글 상세
//		testGetRcmndDetail();
		// 추천글 작성 _ 다이어리 전체 수
//		testGetSearchDiaryTotal();
		// 추천글 작성 _ 다이어리 검색
//		testSearchDiary();
		// 추천글 수정
//		testUpdateRcmnd();
		// 추천글 작성
//		testWriteRcmnd();
		// 추천글 삭제
//		testDeleteRcmnd();
		// 다이어리 관리 _ 전체 글 수
//		testGetDiaryTotal();
		// 다이어리 관리 _ 검색
//		testGetDiaryList();
		// 다이어리 관리 _ 상세
//		testGetDiaryDetail();
		// 다이어리 상태 변경
//		testMdfyDiaryStat();
		// 신고 처리 _ 신고 테이블 변경
//		testUpdateReport();
		// 신고 목록 _ 전체 수
//		testGetReportTotal();
		// 신고목록 _ 리스트
//		testGetReportList();
		// 신고 상세
//		testGetReportDetail();
	}
}
