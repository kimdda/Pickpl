package com.pickpl.dao;

public class TestPickDao {
	static PickDao pDao = new PickDao();
	// 폴더 리스트 가져오기
	static void testGetFolderList() {
		System.out.println(pDao.getFolderList("id1", ""));
	}
	// 픽 폴더 추가하기
	static void testInsertFolder() {
		System.out.println(pDao.insertPickFolder("test1234", "새폴더"));
	}
	// 삭제할 픽 폴더 내 다이어리 목록
	static void testGetDeletePick() {
		System.out.println(pDao.getDeletePick(new String[]{"기본폴더", "가자"}, "id1"));
	}
	// 픽 폴더 삭제
	static void testDeletePickFolder() {
		System.out.println(pDao.deletePickFolder(new String[]{"새폴더"}, "test1234"));
	}
	// 픽 폴더명 변경
	static void testMdfyPickFolder() {
		System.out.println(pDao.mdfyPickFolder("변경 폴더", "새폴더", "test1234"));
	}
	// 픽 목록 가져오기
	static void testGetPickList() {
		System.out.println(pDao.getPickList("id1", "가고싶다"));
	}
	// 픽 _ 픽 업데이트
	static void testPick() {
		System.out.println(pDao.pick("기본폴더", "88", "test1234"));
	}
	// 픽 취소 _ 해당 폴더 찾기
	static void testFindPickFolder() {
		System.out.println(pDao.findPickFolder("test1234", "88"));
	}
	// 픽 취소 _ 픽 업데이트
	static void testUnpick() {
		System.out.println(pDao.unpick("87_", "기본폴더", "test1234"));
	}
	// 픽 다이어리 업데이트
	static void testUpdatePickCount() {
		System.out.println(pDao.updatePickCount(88, "pick"));
	}
	public static void main(String[] args) {
		// 폴더 리스트 가져오기
		testGetFolderList();
		// 픽 폴더 추가
//		testInsertFolder();
		// 삭제할 픽 폴더 내 다이어리 목록
//		testGetDeletePick();
		// 픽 폴더 삭제
//		testDeletePickFolder();
		// 픽 폴더명 변경
//		testMdfyPickFolder();
		// 픽 목록 가져오기
//		testGetPickList();
		// 픽 _ 픽 업데이트
//		testPick();
		// 픽 취소 _ 해당 폴더 찾기
//		testFindPickFolder();
		// 픽 취소 _ 업데이트
//		testUnpick();
		// 픽 다이어리 업데이트
//		testUpdatePickCount();
	}
}
