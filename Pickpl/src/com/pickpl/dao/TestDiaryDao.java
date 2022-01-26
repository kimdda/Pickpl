package com.pickpl.dao;

import java.util.ArrayList;

import com.pickpl.dto.CmntsDto;
import com.pickpl.dto.DiaryAllDto;
import com.pickpl.dto.RcmndListDto;
import com.pickpl.dto.ReportDto;
import com.pickpl.dto.ViewDiaryDto;

public class TestDiaryDao {
	static DiaryDao dDao = new DiaryDao();

	static DiaryAllDto diary = new DiaryAllDto(104, "test1234", "100-1.jpeg", "ddd", "경기도 용인시 처인구", "2022-01-01", 
			15, 2, "내용입니다.", "Y", null, null, "N", null, 
			"Y", null, null, "3.5353535", "3.345345345", "경기도", "용인시");
	
	// 픽 받은 개수
	static void testGetPickedCount() {
		System.out.println(dDao.getPickedCount("testtest"));
	}
	// 내가 픽한 개수
	static void testGetPickCount() {
		System.out.println(dDao.getPickCount("id1"));
	}
	// 올린 글 개수
	static void testGetPickplCount() {
		System.out.println(dDao.getPickplCount("testtest"));
	}
	// 픽 리스트
	static void testGetPickList() {
		System.out.println(dDao.getPickList("testest"));
	}
	// 다이어리 리스트
	static void testGetDiaryList() {
		for(ViewDiaryDto dto : dDao.getDiaryList("id1", "up_date", "")) {
			System.out.println(dto);
		}
	}
	// 픽 여부 확인
	static void testCheckPick() {
		System.out.println(dDao.checkPick("id1", 88));
	}
	// 다이어리_인포 삭제 시 상태 변경
	static void testUpdateDiaryInfo() {
		System.out.println(dDao.updateDiaryInfo(101));
	}
	// 다이어리 삭제
	static void testDeleteDiary() {
		System.out.println(dDao.deleteDiary(101));
	}
	// 맵 삭제
	static void testDeleteMap() {
		System.out.println(dDao.deleteMap(101));
	}
	// 글 삭제 _ 픽 리스트에서 삭제
	static void testByePickFolderList() {
		dDao.byePickFolderList(114);
	}
	// 뷰카운트 증가
	static void testUpdateViewCount() {
		System.out.println(dDao.updateViewCount(101));
	}
	// 다이어리 내용
	static void testGetDiaryDetail() {
		System.out.println(dDao.getDiaryDetail(101).get("park"));
	}
	// 주변지역_시/도 검색
	static void testGetAroundRegion() {
		System.out.println(dDao.getAroundRegion(101, "강원도", ""));
	}
	// 주변지역_시/군/구 검색
	static void testGetAroundCity() {
		System.out.println(dDao.getAroundCity(101, "춘천시", ""));
	}
	// 다이어리 검색 
	static void testGetPlace() {
		System.out.println(dDao.getPlace("용인", "up_date"));
	}	
	// 추천 지역 주변
	static void testGetAroundPlaces() {
		System.out.println(dDao.getAroundPlaces("서울"));
	}
	// 추천 지역 랜덤
	static void testGetRandomPlaces() {
		System.out.println(dDao.getRandomPlaces());
	}
	// 요즘 뜨는 여행지(지역)
	static void testGetPopuar() {
		System.out.println(dDao.getPopular());
	}
	// 조회수 높은 픽플
	static void testGetHighViews() {
		System.out.println(dDao.getHighViews(null));
	}
	// 관리자 추천 목록
	static void testGetRcmndList() {
		for(RcmndListDto dto : dDao.getRcmndList()) {
			System.out.println(dto);
		}
	}
	// 목록에 대한 픽플 가져오기
	static void testGetDiary() {
		for(ViewDiaryDto dto : dDao.getDiary("96_13_95_11_86_9_", null)) {
			System.out.println(dto);
		}
	}
	// 최근 본 픽플
	static void testGetRecent() {
		System.out.println(dDao.getRecent("id1"));
	}
	// 최근 본 글 목록 추가
	static void testInsertRecent() {
		System.out.println(dDao.insertRecent("testid", "3"));
	}
	// 최근 본 글 목록 업데이트 {
	static void testUpdateRecent() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("3");
		list.add("6");
		list.add("9");
		System.out.println(dDao.updateRecent("testId", list));
	}
	// 댓글 가져오기
	static void testGetCmnts() {
		System.out.println(dDao.getCmnts(66));
	}
	// 댓글 작성
	static void testWriteCmnt() {
		System.out.println(dDao.writeCmnt(new CmntsDto(0, 101, "test1234", "아아아", "2022-01-10", "")));
	}
	// 댓글 삭제
	static void testDeleteMyCmnt() {
		System.out.println(dDao.deleteMyCmnt(24));
	}
	// 마지막 작성 댓글번호
	static void testGetLastCmntIdx() {
		System.out.println(dDao.getLastCmntIdx("test1234"));
	}
	// 탈퇴 _ 글에 대한 댓글 전체 상태 변경
	static void testUpdateComments() {
		System.out.println(dDao.updateComments(102));
	}
	// 다이어리 작성 _ 다이어리 인포 추가
	static void testInsertDiaryInfo() {
		System.out.println(dDao.insertDiaryInfo(diary));
	}
	// 방금 작성한 글번호
	static void testGetLastDId() {
		System.out.println(dDao.getLastDId("test1234"));
	}
	// 글 작성 _ 다이어리 내용 추가
	static void testInsertDiary() {
		System.out.println(dDao.insertDiary(diary));
	}
	// 글 작성 _ 지도 내용 추가
	static void testInsertMap() {
		System.out.println(dDao.insertMap(diary));
	}
	// 글 수정 _ 다이어리 인포 수정
	static void testMdfyDiaryInfo() {
		System.out.println(dDao.mdfyDiaryInfo(104));
	}
	// 글 수정 _ 다이어리 수정
	static void testMdfyDiary() {
		System.out.println(dDao.mdfyDiary(diary));
	}
	// 글 수정 _ 지도 수정
	static void testMdfyMap() {
		System.out.println(dDao.mdfyMap(diary));
	}
	// 신고 접수
	static void testReport() {
		System.out.println(dDao.report(new ReportDto(0, "testtest", "", "U", "test1234", 0, "ddd", "", "")));
	}
	// 신고에 따라 계정 상태 변경
	static void testReportType() {
		System.out.println(dDao.reportType(new ReportDto(0, "testtest", "", "U", "test1234", 0, "ddd", "", "")));
	}
	
	public static void main(String[] args) {
		// 글 삭제 _ 픽 리스트에서 삭제
//		testByePickFolderList();
		// 맵 삭제
//		testDeleteMap();
		// 다이어리 삭제
//		testDeleteDiary();
		// 다이어리_인포 삭제 시 상태 변경
//		testUpdateDiaryInfo();
		// 픽 받은 개수
//		testGetPickedCount();
		// 내가 픽한 개수
//		testGetPickCount();
		// 올린 글 개수
//		testGetPickplCount();
		// 픽 리스트
//		testGetPickList();
		// 다이어리 리스트
//		testGetDiaryList();
		// 픽 여부 확인
//		testCheckPick();
		// 뷰카운트 증가
//		testUpdateViewCount();
		// 다이어리 내용
//		testGetDiaryDetail();
		// 주변지역 _ 시/도 검색
//		testGetAroundRegion();
		// 다이어리 검색 
//		testGetPlace();
//		 추천 지역 주변
//		testGetAroundPlaces();
		// 추천 지역 랜덤
//		testGetRandomPlaces();
		// 요즘 뜨는 여행지(지역)
//		testGetPopuar();
//		 조회수 높은 픽플
//		testGetHighViews();
		// 관리자 추천 목록
//		testGetRcmndList();
		// 추천 목록에 대한 픽플 가져오기
//		testGetDiary();
		// 최근 본 여행지
//		testGetRecent();
		// 최근 본 글 목록 추가
//		testInsertRecent();
		// 최근 본 글 목록 업데이트 {
//		testUpdateRecent();
		// 댓글 가져오기
//		testGetCmnts();
		// 댓글 작성
//		testWriteCmnt();
		// 마지막 작성 댓글번호
//		testGetLastCmntIdx();
		// 댓글 삭제
//		testDeleteMyCmnt();
		// 탈퇴 _ 글에 대한 댓글 상태 변경
//		testUpdateComments();
		// 다이어리 작성 _ 다이어리 인포 추가
//		testInsertDiaryInfo();
		// 방금 작성한 글번호
//		testGetLastDId();
		// 글 작성 _ 다이어리 내용 추가
//		testInsertDiary();
		// 글 작성 _ 지도 내용 추가
//		testInsertMap();
		// 글 수정 _ 다이어리 인포 수정
//		testMdfyDiaryInfo();
		// 글 수정 _ 다이어리 수정
//		testMdfyDiary();
		// 글 수정 _ 지도 수정
//		testMdfyMap();
		// 신고
//		testReport();
		// 신고에 따라 계정 상태 변경
//		testReportType();
	}
}
