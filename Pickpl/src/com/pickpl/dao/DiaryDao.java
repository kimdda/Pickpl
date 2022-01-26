package com.pickpl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import com.pickpl.db.DBConnection;
import com.pickpl.dto.CmntsDto;
import com.pickpl.dto.DiaryAllDto;
import com.pickpl.dto.RcmndListDto;
import com.pickpl.dto.ReportDto;
import com.pickpl.dto.SearchListDto;
import com.pickpl.dto.ViewDiaryDto;

public class DiaryDao {
	Connection conn = DBConnection.connect();
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	PickDao pDao = new PickDao();
	
	 ArrayList<ViewDiaryDto> getDiaryInfo(ResultSet rs, String loginId) throws SQLException {
		 ArrayList<ViewDiaryDto> list = new ArrayList<ViewDiaryDto>();
		 while(rs.next()) {
			 int d_id = rs.getInt("d_id");
			 String writer_id = rs.getString("writer_id");
			 String profile = rs.getString("profile");
			 String img = rs.getString("img");
			 String place_name = rs.getString("place_name");
			 String address = rs.getString("address");
			 String visit_date = (rs.getString("visit_date").split(" "))[0];
			 int pick_count = rs.getInt("pick_count");
			 int view_count = rs.getInt("view_count");
			 String pick = checkPick(loginId, d_id);
			 list.add(new ViewDiaryDto(d_id, writer_id, profile, img, place_name, 
					 address, visit_date, "", "", pick_count, view_count, pick));			 
		 }
		 return list;
	}
	
	// 픽 받은 개수
	public int getPickedCount(String id) {
		String sql = "SELECT pick_count FROM view_list WHERE writer_id = ?";
		int pickedCnt = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				pickedCnt += rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return pickedCnt;
	}
	
	// 픽한 개수
	public int getPickCount(String id) {
		int pickCnt = 0;
		String sql = "SELECT folder_d_count FROM pick WHERE pick_id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				pickCnt += rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return pickCnt;
	}
	
	// 올린 글 개수
	public int getPickplCount(String id) {
		int pickplCnt = 0;
		String sql = "SELECT count(*) FROM view_list WHERE writer_id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				pickplCnt = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return pickplCnt;
	}
	
	// 픽 리스트
	public ArrayList<Integer> getPickList(String id) {
		ArrayList<Integer> pickList = new ArrayList<Integer>();
		String sql = "SELECT pick_d_id FROM pick WHERE pick_id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			String pick = "";
			while(rs.next()) {
				if(!rs.getString(1).equals("null")) {
					pick += rs.getString(1);
				}
			}
			
			if(!pick.equals("")) {
				for(String p : pick.split("_")) {
					pickList.add(Integer.parseInt(p));
				}				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return pickList;
	}
	
	// 다이어리 검색 
	public ArrayList<SearchListDto> getPlace(String keyword, String order) {
		ArrayList<SearchListDto> searchList = new ArrayList<SearchListDto>();
		
		String sql = "SELECT * FROM view_list l, map m WHERE l.d_id = m.map_d_id and (address like ? or place_name like ?) ORDER BY ";
		switch(order) {
		case "up_date" :
			sql += "up_date DESC";
			break;
		case "pick_count" :
			sql += "pick_count DESC";
			break;
		case "view_count" :
			sql += "view_count DESC";
			break;
		}
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + keyword + "%");
			pstmt.setString(2, "%" + keyword + "%");
			rs = pstmt.executeQuery();
			while(rs.next()) {
				int dId = rs.getInt("d_id");
				String writer_id = rs.getString("writer_id");
				String profile = rs.getString("profile");
				String img = rs.getString("img");
				String place_name = rs.getString("place_name");
				String address = rs.getString("address");
				int pick_count = rs.getInt("pick_count");
				int view_count = rs.getInt("view_count");
				String lat = rs.getString("latitude");
				String lng = rs.getString("longitude");
				
				searchList.add(new SearchListDto(dId, writer_id, profile, img, place_name, 
						address, pick_count, view_count, "", lat, lng));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return searchList;
	}
	
	// 추천 검색어
	public ArrayList<String> getAroundPlaces(String keyword) {
		ArrayList<String> rcmndList = new ArrayList<String>();
		String sql = "SELECT gu, count(gu) count FROM diary d, map m WHERE d.diary_id = m.map_d_id and "
				+ "(address like ? or place_name like ?) GROUP BY gu ORDER BY count DESC";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + keyword + "%");
			pstmt.setString(2, "%" + keyword + "%");
			rs = pstmt.executeQuery();
			while(rs.next()) {
				String place = rs.getString("gu");
				rcmndList.add(place);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rcmndList;
	}
	
	// 추천 검색어 _  뷰수 높은 글 20개 중 2개
	public ArrayList<String> getRandomPlaces() {
		ArrayList<String> list = new ArrayList<String>();
		String sql = "SELECT * FROM (SELECT rownum r, l.* FROM (SELECT * FROM view_list ORDER BY view_count DESC) l) list WHERE r <= 20";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				String place = (rs.getString("address").split(" "))[1];
				if(!list.contains(place)) {
					list.add(place);	
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	// 다이어리 리스트
	public ArrayList<ViewDiaryDto> getDiaryList(String id, String order, String loginId) {
		ArrayList<ViewDiaryDto> diaryList = new ArrayList<ViewDiaryDto>();
		String sql = "SELECT * FROM view_list WHERE writer_id = ? ORDER BY ";
		switch(order) {
		case "up_date" :
			sql += "up_date DESC";
			break;
		case "pick_count" :
			sql += "pick_count DESC";
			break;
		case "view_count" :
			sql += "view_count DESC";
			break;
		}
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			diaryList = getDiaryInfo(rs, loginId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return diaryList;
	}
	
	// 픽 여부 확인
	public String checkPick(String id, int dId) {
		String pick = "pick";
		String sql = "SELECT * FROM pick WHERE pick_id=? and (pick_d_id like ? ESCAPE '/' or pick_d_id like ? ESCAPE '/')";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, "%/_" + dId + "/_%");
			pstmt.setString(3, dId + "/_%");
			rs = pstmt.executeQuery();
			if(rs.next()) {
				pick = "picked";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return pick;
	}
	
	// 글 삭제 시 다이어리_인포 상태 변경
	public int updateDiaryInfo(int dId) {
		String sql = "UPDATE diary_info SET del_date = sysdate, up_stat='D' WHERE d_id=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dId);
			int r = pstmt.executeUpdate();
			return r;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	// 글 삭제 시 _ 다이어리 삭제
	public int deleteDiary(int dId) {
		try {
			String sql = "DELETE FROM diary WHERE diary_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dId);
			int r = pstmt.executeUpdate();
			return r;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
//			DbConn.disconnect(conn, pstmt, rs);
		}
		return 0;
	}
	
	// 글 삭제 시 _ 맵 삭제
	public int deleteMap(int dId) {
		String sql = "DELETE FROM map WHERE map_d_id=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dId);
			int r = pstmt.executeUpdate();
			return r;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	// 글 삭제 _ 픽 리스트에서 삭제
	public void byePickFolderList(int dId) {
		String sql = "SELECT * FROM pick WHERE pick_d_id like ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+dId+"%");
			rs = pstmt.executeQuery();
			while(rs.next()) {
				String pick_id = rs.getString("pick_id");
				String folder = rs.getString("folder");
				String pickList = rs.getString("pick_d_id");
				System.out.println("ori " +pickList);
				
				pickList = pickList.replace(dId+"_", "");
				System.out.println("new " +pickList);
				
				pDao.unpick(pickList, folder, pick_id);
//				System.out.println(folder + " / " + pick_id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 조회수 증가
	public int updateViewCount(int dId) {
		String sql = "UPDATE diary SET view_count = (view_count + 1) WHERE diary_id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dId);
			int r = pstmt.executeUpdate();
			return r;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	// 다이어리 내용 불러오기
	public HashMap<String, String> getDiaryDetail(int dId) {
		HashMap<String, String> diaryDetail = new HashMap<String, String>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT d.*, i.writer_id, i.up_stat, m.profile, w.weather_name, p.latitude, p.longitude, p.do, p.gu ")
			.append("FROM member m, diary_info i, diary d, map p, weather w ")
			.append("WHERE i.d_id = d.diary_id and i.writer_id = m.id and ")
			.append("i.d_id = p.map_d_id and d.weather_id = w.weather_id and diary_id = ?");
		try {
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, dId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				diaryDetail.put("diary_id", String.valueOf(rs.getInt("diary_id")));
				diaryDetail.put("writer_id", rs.getString("writer_id"));
				diaryDetail.put("profile", rs.getString("profile"));
				diaryDetail.put("img", rs.getString("img"));
				diaryDetail.put("place_name", rs.getString("place_name"));
				diaryDetail.put("address", rs.getString("address"));
				diaryDetail.put("weather_id", String.valueOf(rs.getInt("weather_id")));
				diaryDetail.put("weather_name", rs.getString("weather_name"));
				diaryDetail.put("visit_date", rs.getString("visit_date").substring(0, 10));
				diaryDetail.put("visit_time", String.valueOf(rs.getInt("visit_time")));
				diaryDetail.put("contents", rs.getString("contents"));
				diaryDetail.put("drone", rs.getString("drone"));
				diaryDetail.put("public_tran", rs.getString("public_tran"));
				diaryDetail.put("public_info", rs.getString("public_info"));
				diaryDetail.put("park", rs.getString("park"));
				diaryDetail.put("park_info", rs.getString("park_info"));
				diaryDetail.put("toilet", rs.getString("toilet"));
				diaryDetail.put("shower", rs.getString("shower"));
				diaryDetail.put("locker", rs.getString("locker"));
				diaryDetail.put("pick_count", String.valueOf(rs.getInt("pick_count")));
				diaryDetail.put("view_count", String.valueOf(rs.getInt("view_count")));
				diaryDetail.put("up_stat", rs.getString("up_stat"));
				diaryDetail.put("lat", rs.getString("latitude"));
				diaryDetail.put("lng", rs.getString("longitude"));
				diaryDetail.put("do", rs.getString("do"));
				diaryDetail.put("gu", rs.getString("gu"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return diaryDetail;
	}
	
	// 다이어리 주변 지역 _ 지역(시/도) 검색
	public ArrayList<ViewDiaryDto> getAroundRegion(int dId, String region, String loginId) {
		ArrayList<ViewDiaryDto> aroundRegionList = new ArrayList<ViewDiaryDto>();
		String sql = "SELECT * FROM view_list WHERE d_id != ? and address like ? ORDER BY view_count DESC";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dId);
			pstmt.setString(2, region+"%");
			rs = pstmt.executeQuery();
			aroundRegionList = getDiaryInfo(rs, loginId);
//			while(rs.next()) {
//				aroundRegionList.add(getDiaryInfo(rs, loginId));
//			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(aroundRegionList.size() > 10)
			aroundRegionList.subList(0, 10);
		
		return aroundRegionList;
	}
	
	// 다이어리 주변 지역 _ 도시(시//구) 검색
	public ArrayList<ViewDiaryDto> getAroundCity(int dId, String city, String loginId) {
		ArrayList<ViewDiaryDto> aroundCityList = new ArrayList<ViewDiaryDto>();
		String sql = "SELECT * FROM view_list WHERE d_id != ? and address like ? ORDER BY view_count DESC";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dId);
			pstmt.setString(2, "%"+city+"%");
			rs = pstmt.executeQuery();
			while(rs.next()) {
				aroundCityList = getDiaryInfo(rs, loginId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return aroundCityList;
	}
	
	// 메인 요즘 뜨는 여행지(지역)
	public HashMap<String, String> getPopular() {
		HashMap<String, String> popularList = new HashMap<String, String>();
		String sql = "SELECT * FROM view_list l, map m WHERE l.d_id = m.map_d_id ORDER BY l.view_count DESC";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				if(popularList.size() < 8) {
					String region = rs.getString("do");
					String img = rs.getString("img").split("_")[0];
					if(!popularList.containsKey(region))
						popularList.put(region, img);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return popularList;
	}
	
	// 조회수 높은 픽플
	public ArrayList<ViewDiaryDto> getHighViews(String loginId) {
		ArrayList<ViewDiaryDto> highViewList = new ArrayList<ViewDiaryDto>();
		
		String sql = "SELECT * FROM view_list l, map m WHERE l.d_id = m.map_d_id ORDER BY l.view_count DESC";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			ArrayList<ViewDiaryDto> tmp = getDiaryInfo(rs, loginId);
			if(tmp.size() > 12) {
				for(int i=0; i<12; i++) {
					highViewList.add(tmp.get(i));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return highViewList;
	}
	
	// 관리자 추천 목록
	public ArrayList<RcmndListDto> getRcmndList() {
		ArrayList<RcmndListDto> rcmndList = new ArrayList<RcmndListDto>();
		
		String sql = "SELECT * FROM recommend "
				+ "WHERE reco_open_date <= sysdate AND reco_close_date >= sysdate and reco_hold IS NULL";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				int no = rs.getInt("reco_no");
				String title = rs.getString("reco_title");
				String up_date = rs.getString("reco_up_date");
				String open_date = rs.getString("reco_open_date");
				String close_date = rs.getString("reco_close_date");
				String d_id = rs.getString("reco_d_id");
				int d_count = rs.getInt("reco_d_count");
				String hold = rs.getString("reco_hold");
//				ArrayList<ViewDiaryDto> rcmndDiary = getDiary(d_id, loginId);
				
				rcmndList.add(new RcmndListDto(no, title, up_date, open_date, close_date, d_id, d_count, hold, new ArrayList<ViewDiaryDto>()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rcmndList;
	}
	
	// 목록에 대한 픽플 가져오기
	public ArrayList<ViewDiaryDto> getDiary(String dId, String loginId) {
		ArrayList<ViewDiaryDto> diaryList = new ArrayList<ViewDiaryDto>();
		String[] diary = dId.split("_");
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM view_list WHERE d_id in (");
		for(int i=0; i<diary.length; i++) {
			sql.append(i < diary.length - 1 ? "?, " : "?)");
		}
		
		try {
			pstmt = conn.prepareStatement(sql.toString());
			for(int i=1; i<=diary.length; i++) {
				pstmt.setInt(i, Integer.parseInt(diary[i-1]));
			}
			rs = pstmt.executeQuery();
			diaryList = getDiaryInfo(rs, loginId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return diaryList;
	}
	
	// 최근 본 여행지
//	public ArrayList<ViewDiaryDto> getRecent(String loginId) {
//		ArrayList<ViewDiaryDto> recentList = new ArrayList<ViewDiaryDto>();
//		String sql = "SELECT * FROM recent WHERE recent_id = ?";
//		try {
//			pstmt = conn.prepareStatement(sql);
//			pstmt.setString(1, loginId);
//			rs = pstmt.executeQuery();
//			String recent = null;
//			if(rs.next()) {
//				recent = rs.getString("recent_list");
//			}
//			if(recent != null) {
//				recentList = getDiary(recent, loginId);				
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
//		return recentList;
//	}
	
	public String getRecent(String loginId) {
		String recent = null;
		String sql = "SELECT * FROM recent WHERE recent_id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, loginId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				recent = rs.getString("recent_list");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return recent;
	}
	// 최근 본 글 목록 추가
	public int insertRecent(String loginId, String dId) {
		String sql = "INSERT INTO recent(recent_id, recent_list) VALUES(?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, loginId);
			pstmt.setString(2, dId+"_");
			int r = pstmt.executeUpdate();
			return r;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	// 최근 본 글 목록 업데이트
	public int updateRecent(String loginId, ArrayList<String> recentList) {
		String recent = "";
		for(String list : recentList) {
			recent += list+"_";
		}
		
		String sql = "UPDATE recent SET recent_list = ? WHERE recent_id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, recent);
			pstmt.setString(2, loginId);
			int r = pstmt.executeUpdate();
			return r;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	
	// 댓글 가져오기
	public ArrayList<CmntsDto> getCmnts(int dId) {
		ArrayList<CmntsDto> cmntsList = new ArrayList<CmntsDto>();
		
		String sql = "SELECT c.*, m.profile FROM comments c, member m WHERE c.cmnt_id = m.id and cmnt_d_id = ? AND cmnt_stat = 'Y' ORDER BY cmnt_date";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int idx = rs.getInt("cmnt_idx");
				int d_id = rs.getInt("cmnt_d_id");
				String id = rs.getString("cmnt_id");
				String contents = rs.getString("cmnt_contents");
				String date = rs.getString("cmnt_date");
				String profile = rs.getString("profile");
				
				cmntsList.add(new CmntsDto(idx, d_id, id, contents, date, profile));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return cmntsList;
	}
	
	// 댓글 작성
	public int writeCmnt(CmntsDto dto) {
		String sql = "INSERT INTO comments(cmnt_idx, cmnt_d_id, cmnt_id, cmnt_contents) VALUES (cmnt_seq.nextval, ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getD_id());
			pstmt.setString(2, dto.getId());
			pstmt.setString(3, dto.getContents());
			int r = pstmt.executeUpdate();
			return r;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	// 방금 작성한 댓글 글번호
	public int getLastCmntIdx(String loginId) {
		String sql = "SELECT cmnt_idx FROM comments WHERE cmnt_date = (SELECT max(cmnt_date) FROM comments WHERE cmnt_id = ?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, loginId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				int curr = rs.getInt(1);
				return curr;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	// 댓글 삭제
	public int deleteMyCmnt(int idx) {
		String sql = "UPDATE comments SET cmnt_stat = 'N' WHERE cmnt_idx = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			int r = pstmt.executeUpdate();
			return r;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	// 탈퇴 _ 글에 대한 댓글 전체 상태 변경
	public int updateComments(int dId) {
		String sql = "UPDATE comments SET cmnt_stat='N' WHERE cmnt_d_id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dId);
			int r = pstmt.executeUpdate();
			return r;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	// 글 작성 _ 다이어리 인포 추가
	public int insertDiaryInfo(DiaryAllDto dto) {
		String sql = "INSERT INTO diary_info(d_id, writer_id) VALUES (diary_seq.nextval, ?)";
		try {
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, dto.getWriter_id());
			int r = pstmt.executeUpdate();
			return r;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	// 방금 작성한 글번호
	public int getLastDId(String loginId) {
		String sql = "SELECT d_id FROM diary_info WHERE up_date = (SELECT max(up_date) FROM diary_info WHERE writer_id = ?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, loginId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				int curr = rs.getInt(1);
				return curr;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	// 글 작성 _ 다이어리 내용 추가
	public int insertDiary(DiaryAllDto dto) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO diary (DIARY_ID, IMG, PLACE_NAME, ADDRESS, WEATHER_ID, VISIT_DATE, VISIT_TIME, ")
		.append("CONTENTS, DRONE, PUBLIC_TRAN, PUBLIC_INFO, PARK, PARK_INFO, TOILET, SHOWER, LOCKER) ")
		.append("VALUES (?, ?, ?, ?, ?, to_date(?, 'YYYY-MM-DD'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		try {
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, dto.getD_id());
			pstmt.setString(2, dto.getImg());
			pstmt.setString(3, dto.getPlace_name());
			pstmt.setString(4, dto.getAddress());
			pstmt.setInt(5, dto.getWeather_id());
			pstmt.setString(6, dto.getVisit_date());
			pstmt.setInt(7, dto.getVisit_time());
			pstmt.setString(8, dto.getContents());
			pstmt.setString(9, dto.getDrone());
			pstmt.setString(10, dto.getPublic_tran());
			pstmt.setString(11, dto.getPublic_info());
			pstmt.setString(12, dto.getPark());
			pstmt.setString(13, dto.getPark_info());
			pstmt.setString(14, dto.getToilet());
			pstmt.setString(15, dto.getShower());
			pstmt.setString(16, dto.getLocker());
			int r = pstmt.executeUpdate();
			return r;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	// 글 작성 _ 지도 추가
	public int insertMap(DiaryAllDto dto) {
		String sql = "INSERT INTO map(map_d_id, latitude, longitude, do, gu) VALUES(?, ?, ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getD_id());
			pstmt.setString(2, dto.getLatitude());
			pstmt.setString(3, dto.getLongitude());
			pstmt.setString(4, dto.getAddress().split(" ")[0]);
			pstmt.setString(5, dto.getAddress().split(" ")[1]);
			int r = pstmt.executeUpdate();
			return r;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	// 글 수정 _ 다이어리 인포 업데이트
	public int mdfyDiaryInfo(int dId) {
		String update = "UPDATE diary_info SET re_date = sysdate WHERE d_id = ?";
		try {
			pstmt = conn.prepareStatement(update);
			pstmt.setInt(1, dId);
			int r = pstmt.executeUpdate();
			return r;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	// 글 수정 _ 다이어리 업데이트
	public int mdfyDiary(DiaryAllDto dto) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE diary SET img=?, place_name=?, address=?, weather_id=?, ")
		.append("visit_date=to_date(?, 'YYYY-MM-DD'), visit_time=?, contents=?, drone=?, public_tran=?, ")
		.append("public_info=?, park=?, park_info=?, toilet=?, shower=?, locker=? WHERE diary_id = ?");
		try {
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, dto.getImg());
			pstmt.setString(2, dto.getPlace_name());
			pstmt.setString(3, dto.getAddress());
			pstmt.setInt(4, dto.getWeather_id());
			pstmt.setString(5, dto.getVisit_date());
			pstmt.setInt(6, dto.getVisit_time());
			pstmt.setString(7, dto.getContents());
			pstmt.setString(8, dto.getDrone());
			pstmt.setString(9, dto.getPublic_tran());
			pstmt.setString(10, dto.getPublic_info());
			pstmt.setString(11, dto.getPark());
			pstmt.setString(12, dto.getPark_info());
			pstmt.setString(13, dto.getToilet());
			pstmt.setString(14, dto.getShower());
			pstmt.setString(15, dto.getLocker());
			pstmt.setInt(16, dto.getD_id());
			int r = pstmt.executeUpdate();
			return r;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	// 글 수정 _ 지도 업데이트
	public int mdfyMap(DiaryAllDto dto) {
		String sql = "UPDATE map SET latitude = ?, longitude = ?, do = ?, gu = ? WHERE map_d_id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getLatitude());
			pstmt.setString(2, dto.getLongitude());
			pstmt.setString(3, dto.getAddress().split(" ")[0]);
			pstmt.setString(4, dto.getAddress().split(" ")[1]);
			pstmt.setInt(5, dto.getD_id());
			int r = pstmt.executeUpdate();
			return r;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	// 신고 접수
	public int report(ReportDto dto) {
		try {
			String sql = "INSERT INTO report(report_idx, report_id, report_type, target_id, target_d_id, report_contents) "
					+ "VALUES (REPORT_SEQ.NEXTVAL, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getReport_id());
			pstmt.setString(2, dto.getReport_type());
			pstmt.setString(3, dto.getTarget_id());
			pstmt.setInt(4, dto.getTarget_d_id());
			pstmt.setString(5, dto.getContents());
			int r = pstmt.executeUpdate();
			return r;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	// 신고에 따라 상태 변경
	public int reportType(ReportDto dto) {
		String sql = null;
		int r = 0;
			try {
				switch(dto.getReport_type()) {
				case "U" :
					sql = "UPDATE member SET acc_stat='R' WHERE id = ?";
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, dto.getTarget_id());
					r = pstmt.executeUpdate();
					break;
				case "D" :
					sql = "UPDATE diary_info SET up_stat='R' WHERE d_id = ?";
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, dto.getTarget_d_id());
					r = pstmt.executeUpdate();
					break;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		return r;
	}
}
