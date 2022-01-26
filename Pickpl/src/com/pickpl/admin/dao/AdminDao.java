package com.pickpl.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.pickpl.admin.dto.DiaryDetailDto;
import com.pickpl.admin.dto.DiaryListDto;
import com.pickpl.admin.dto.MemberDto;
import com.pickpl.admin.dto.RcmndDto;
import com.pickpl.admin.dto.ReportDto;
import com.pickpl.db.DBConnection;

public class AdminDao {
	Connection conn = DBConnection.connect();
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	StringBuffer sqlSb = new StringBuffer();
	
	// 로그인
	public int login(String id, String pw) {
		String sql = "SELECT count(*) cnt FROM member WHERE id=? and pw=? and acc_stat = 'mng'";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	// 테이블 전체 로우 수
	public int getTableTotal(String table) {
		int total = 0;
		sqlSb.delete(0, sqlSb.length());
		sqlSb.append("SELECT count(*) FROM ")
			.append(table);
		try {
			pstmt = conn.prepareStatement(sqlSb.toString());
			rs = pstmt.executeQuery();
			if(rs.next() ) {
				total = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	
	// 회원관리 리스트 _ 전체 수
	public int getSearchMemberTotal(String stat, String condition, String keyword) {
		sqlSb.delete(0, sqlSb.length());
		sqlSb.append("SELECT count(*) FROM member WHERE acc_stat like ?");
		if(!keyword.equals("")) {
			sqlSb.append(" and ").append(condition)
				.append(" = '").append(keyword).append("' ");		
		}
		try {
			pstmt = conn.prepareStatement(sqlSb.toString());
			pstmt.setString(1, stat);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	// 회원 목록 검색 결과
	public ArrayList<MemberDto> getMemberList(String stat, String condition, String keyword, int startIdx, int endIdx) {
		ArrayList<MemberDto> list = new ArrayList<MemberDto>();
		sqlSb.delete(0, sqlSb.length());
		sqlSb.append("SELECT * FROM (SELECT rownum idx, l.* ")
			.append("FROM (SELECT * FROM member WHERE acc_stat like ?");
		if(!keyword.equals("")) {
			sqlSb.append(" and ").append(condition)
				.append(" = '").append(keyword).append("' ");		
		}
		sqlSb.append("ORDER BY join_date DESC) l) list ")
			.append("WHERE list.idx >= ? and list.idx < ?");
		try {
			pstmt = conn.prepareStatement(sqlSb.toString());
			pstmt.setString(1, stat);
			pstmt.setInt(2, startIdx);
			pstmt.setInt(3, endIdx);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				String id = rs.getString("id");
				String name = rs.getString("name");
				String gender = rs.getString("gender");
				String birth = rs.getString("birth");
				String phone = rs.getString("phone");
				String joinDate = rs.getString("join_date");
				String acctStat = rs.getString("acc_stat");
				
				list.add(new MemberDto(id, name, gender, birth, phone, "", "", joinDate, acctStat));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	// 회원 상세 정보 _ member table
	public MemberDto getMemberInfo(String id) {
		MemberDto info = null;
		String sql = "SELECT * FROM member WHERE id=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				String name = rs.getString("name");
				String gender = "여";
				String phone = rs.getString("phone");
				String email = rs.getString("email");
				if(rs.getString("gender").equals("M"))
					gender = "남";
				String birth = rs.getString("birth");
				birth = birth.substring(0, birth.indexOf(" "));
				
				String cf_mail = "동의";
				if(rs.getString("cf_mail").equals("N")) cf_mail = "비동의";
				
				String joinDate = rs.getString("join_date");
				
				String acc_stat = "활성";
				if(rs.getString("acc_stat").equals("D"))
					acc_stat = "이메일 미인증";
				else if(rs.getString("acc_stat").equals("O"))
					acc_stat = "탈퇴";
				else if(rs.getString("acc_stat").equals("R"))
					acc_stat = "신고";
				else if(rs.getString("acc_stat").equals("B"))
					acc_stat = "비활성";
				
				info = new MemberDto(id, name, gender, birth, phone, email, cf_mail, joinDate, acc_stat);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return info;
	}
	
	// 회원 정보 _ 활동 내역
	public HashMap<String, Integer> getMemberActInfo(String id) {
		HashMap<String, Integer> actInfo = new HashMap<String, Integer>();
		String sql = "SELECT * FROM diary d, diary_info i WHERE d.diary_id = i.d_id and i.writer_id = ?";
		int up_count = 0;
		int del_count = 0;
		int picked_count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				up_count++;
				picked_count += rs.getInt("pick_count");
				if(rs.getString("up_stat").equals("D"))
					del_count++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		actInfo.put("up_count", up_count);
		actInfo.put("del_count", del_count);
		actInfo.put("picked_count", picked_count);
		
		return actInfo;
	}
	
	// 회원 정보 _ 픽 활동
	public int getMemberPickInfo(String id) {
		int pick_count = 0;
		String sql = "SELECT * FROM pick WHERE pick_id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				pick_count += rs.getInt("folder_d_count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return pick_count;
	}
	
	// 미처리 신고 내역 _ 회원
	public ArrayList<ReportDto> getReport(String id) {
		ArrayList<ReportDto> reportList = new ArrayList<ReportDto>();
		String sql = "SELECT * FROM report WHERE target_id = ? and report_stat='N' and report_type='U'";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				int idx = rs.getInt("report_idx");
				String reportId = rs.getString("report_id");
				String reportDate = rs.getString("report_date");
				String reportType = rs.getString("report_type");
				String targetId = rs.getString("target_id");
				String targetDId = rs.getString("target_d_id");
				String contents = rs.getString("report_contents");
				String mngContents = rs.getString("report_mng_contents");
				
				reportList.add(new ReportDto(idx, reportId, reportDate, reportType, 
						targetId, targetDId, contents, mngContents, "N"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return reportList;
	}
	// 미처리 신고 내역 _ 다이어리
	public ArrayList<ReportDto> getReport(int dId) {
		ArrayList<ReportDto> reportList = new ArrayList<ReportDto>();
		String sql = "SELECT * FROM report WHERE target_d_id = ? and report_stat='N' and report_type='D'";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dId);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				int idx = rs.getInt("report_idx");
				String reportId = rs.getString("report_id");
				String reportDate = rs.getString("report_date");
				String reportType = rs.getString("report_type");
				String targetId = rs.getString("target_id");
				String targetDId = rs.getString("target_d_id");
				String contents = rs.getString("report_contents");
				String mngContents = rs.getString("report_mng_contents");
				
				reportList.add(new ReportDto(idx, reportId, reportDate, reportType, 
						targetId, targetDId, contents, mngContents, "N"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return reportList;
	}
	
	// 신고 받은 횟수 _ 회원
	public int getReportedCount(String id) {
		String sql = "SELECT count(*) FROM report WHERE target_id = ? and report_type = 'U'";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next())
				return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	// 신고 받은 횟수 _ 다이어리
	public int getReportedCount(int dId) {
		String sql = "SELECT count(*) FROM report WHERE target_d_id = ? and report_type = 'D'";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dId);
			rs = pstmt.executeQuery();
			if(rs.next())
				return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	// 계정 상태 변경
	public int mdfyAccStat(String id, String stat) {
		String sql = "UPDATE member SET acc_stat=? WHERE id=?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, stat);
			pstmt.setString(2, id);
			int r = pstmt.executeUpdate();
			return r;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	// 이메일 수정
	public int mdfyMemberEmail(String id, String email) {
		String sql = "UPDATE member SET email = ? WHERE id = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setString(2, id);
			int r = pstmt.executeUpdate();
			return r;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	// 추천 목록 받아오기
	public JSONArray getRcmndList(int startIdx, int endIdx) {
		JSONArray arr = new JSONArray();
		sqlSb.delete(0, sqlSb.length());
		sqlSb.append("SELECT * ");
		sqlSb.append("FROM (SELECT rownum idx, l.* FROM (SELECT * FROM recommend ORDER BY reco_up_date) l) list ");
		sqlSb.append("WHERE list.idx >= ? and list.idx < ?");
		try {
			pstmt = conn.prepareStatement(sqlSb.toString());
			pstmt.setInt(1, startIdx);
			pstmt.setInt(2, endIdx);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				JSONObject obj = new JSONObject();
				int no = rs.getInt("reco_no");
				String title = rs.getString("reco_title");
				String up_date = rs.getString("reco_up_date");
				String open_date = (rs.getString("reco_open_date").split(" "))[0];
				String close_date = (rs.getString("reco_close_date").split(" "))[0];
				JSONArray diaryArr = new JSONArray();
				int d_count = rs.getInt("reco_d_count");
				String stat = rs.getString("reco_hold");
				
				obj.put("no", no);
				obj.put("title", title);
				obj.put("upDate", up_date);
				obj.put("openDate", open_date);
				obj.put("closeDate", close_date);
				obj.put("stat", stat);
				obj.put("dCount", d_count);	
				
				arr.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arr;
	}
	
	// 추천 글 상세
	public RcmndDto getRcmndDetail(int no) {
		RcmndDto dto = null;
		String sql = "SELECT * FROM recommend WHERE reco_no=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				String title = rs.getString("reco_title");
				String open_date = (rs.getString("reco_open_date").split(" "))[0];
				String close_date = (rs.getString("reco_close_date").split(" "))[0];
				String d_id = rs.getString("reco_d_id");
				String hold = rs.getString("reco_hold");
								
				dto = new RcmndDto(no, title, "", open_date, close_date, d_id, 0, hold);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dto;
	}
	
	// 추천글 _ 다이어리 검색 총 수
	public int getSearchDiaryTotal(String month, String region, String city) {
		sqlSb.delete(0, sqlSb.length());
		sqlSb.append("SELECT count(*) FROM view_list WHERE address like ? ")
		.append("and to_char(visit_date, 'YYYY-MM-DD') like ?");
		try {
			pstmt = conn.prepareStatement(sqlSb.toString());
			pstmt.setString(1, region + "% " + city + "%");
			pstmt.setString(2, "____-%" + month + "-__%");
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	// 추천글 작성 _ 다이어리 검색
	public JSONArray searchDiary(String region, String city, String month, int startIdx, int endIdx) {
		JSONArray arr = new JSONArray();
		
		sqlSb.delete(0, sqlSb.length());
		sqlSb.append("SELECT * FROM (SELECT rownum idx, l.* ")
			.append("FROM (SELECT * FROM view_list v, map m ")
			.append("WHERE v.d_id = m.map_d_id and address like ? and ")
			.append("to_char(visit_date, 'YYYY-MM-DD') like ? ORDER BY view_count DESC) l) list ")
			.append("WHERE list.idx >= ? and list.idx < ?");

		try {
			pstmt = conn.prepareStatement(sqlSb.toString());
			pstmt.setString(1, region + "% " + city + "%");
			pstmt.setString(2, "____-%" + month + "-__%");
			pstmt.setInt(3, startIdx);
			pstmt.setInt(4, endIdx);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				JSONObject obj = new JSONObject();
				obj.put("dId", rs.getInt("d_id"));
				obj.put("writer", rs.getString("writer_id"));
				obj.put("pick_count", rs.getInt("pick_count"));
				obj.put("view_count", rs.getInt("view_count"));
				obj.put("visit_date", (rs.getString("visit_date").split(" "))[0]);
				obj.put("place_name", rs.getString("place_name"));
				obj.put("region", rs.getString("do") + " " + rs.getString("gu"));
				
				arr.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arr;
	}
	
	// 추천글 작성
	public int writeRcmnd(RcmndDto dto) {
		sqlSb.delete(0, sqlSb.length());
		sqlSb.append("INSERT INTO recommend(reco_no, reco_title, reco_open_date, ")
			.append("reco_close_date, reco_d_id, reco_d_count) ")
			.append("VALUES (recommend_seq.nextval, ?, to_date(?, 'YYYY-MM-DD'), ")
			.append("to_date(?, 'YYYY-MM-DD'), ?, ?)");
		
		try {
			pstmt = conn.prepareStatement(sqlSb.toString());
			pstmt.setString(1, dto.getTitle());
			pstmt.setString(2, dto.getOpen_date());
			pstmt.setString(3, dto.getClose_date());
			pstmt.setString(4, dto.getD_id());
			pstmt.setInt(5, dto.getD_count());
			int r = pstmt.executeUpdate();
			return r;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	// 추천글 수정
	public int updateRcmnd(RcmndDto dto) {
		sqlSb.delete(0, sqlSb.length());
		sqlSb.append("UPDATE recommend SET reco_title=?, reco_open_date = to_date(?, 'YYYY-MM-DD'), ")
			.append("reco_close_date = to_date(?, 'YYYY-MM-DD'), reco_d_id = ?, ")
			.append("reco_d_count = ?, reco_hold = ? WHERE reco_no=?");
		
		try {
			pstmt = conn.prepareStatement(sqlSb.toString());
			pstmt.setString(1, dto.getTitle());
			pstmt.setString(2, dto.getOpen_date());
			pstmt.setString(3, dto.getClose_date());
			pstmt.setString(4, dto.getD_id());
			pstmt.setInt(5, dto.getD_count());
			pstmt.setString(6, dto.getHold());				
			pstmt.setInt(7, dto.getNo());
			int r = pstmt.executeUpdate();
			return r;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	// 추천글 삭제
	public int deleteRcmnd(int no) {
		String sql = "DELETE FROM recommend WHERE reco_no = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			int r = pstmt.executeUpdate();
			return r;			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	// 다이어리 관리 _ 전체 글 수
	public int getDiaryTotal(String stat, String region, String city, String condition, String keyword) {
		sqlSb.delete(0, sqlSb.length());
		sqlSb.append("SELECT count(*) FROM diary_info i, diary d ")
			.append("WHERE i.d_id = d.diary_id(+) and i.up_stat like ? ");
		if(stat.equals("D") || stat.equals("%")) {
			sqlSb.append("and (d.address like ? or d.address is null)");
		} else {
			sqlSb.append("and d.address like ?");			
		}
		if(!keyword.equals("")) {
			sqlSb.append(" and ").append(condition).append(" = '").append(keyword).append("'");
		}
		try {
			pstmt = conn.prepareStatement(sqlSb.toString());
			pstmt.setString(1, stat);
			pstmt.setString(2, "%" + region + " " + city + "%");
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	// 다이어리 관리 _ 검색 목록
	public ArrayList<DiaryListDto> getDiaryList(String stat, String region, String city, 
			String condition, String keyword, int startIdx, int endIdx) {
		ArrayList<DiaryListDto> list = new ArrayList<DiaryListDto>();
		int total = 0;
		
		sqlSb.delete(0, sqlSb.length());
		sqlSb.append("SELECT * FROM (SELECT rownum idx, l.* ")
			.append("FROM (SELECT * FROM diary_info i, diary d ")
			.append("WHERE i.d_id = d.diary_id(+) and i.up_stat like ?")
			.append(" and (d.address like ? or d.address is null)");
		if(!keyword.equals("")) {
			sqlSb.append(" and ").append(condition).append(" = '").append(keyword).append("'");
		}
		sqlSb.append(" ORDER BY i.d_id DESC) l) list ")
		.append("WHERE list.idx >= ? and list.idx < ?");
		try {
			pstmt = conn.prepareStatement(sqlSb.toString());
			pstmt.setString(1, stat);
			pstmt.setString(2, "%" + region + " " + city + "%");
			pstmt.setInt(3, startIdx);
			pstmt.setInt(4, endIdx);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				total++;
				int d_id = rs.getInt("d_id");
				String writer_id = rs.getString("writer_id");
				String place_name = rs.getString("place_name");
				String up_date = rs.getString("up_date");
				up_date = up_date.substring(0, up_date.indexOf(" "));
				String up_stat = rs.getString("up_stat");
				String regionCity = rs.getString("address");
				if(regionCity != null)
					regionCity = (regionCity.split(" "))[0] + " " + (regionCity.split(" "))[1];
				
				list.add(new DiaryListDto(d_id, writer_id, place_name, regionCity, up_date, up_stat));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	// 다이어리 관리 _ 상세
	public DiaryDetailDto getDiaryDetail(int dId) {
		DiaryDetailDto info = new DiaryDetailDto();
		String sql = "SELECT * FROM diary_info i, diary d WHERE i.d_id = d.diary_id and i.d_id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				int d_id = rs.getInt("d_id");
				String writer_id = rs.getString("writer_id");
				String up_date = rs.getString("up_date");
				up_date = up_date.substring(0, up_date.indexOf(" "));
				String re_date = rs.getString("re_date");
				if(re_date != null)
					re_date = re_date.substring(0, re_date.indexOf(" "));
				String del_date = rs.getString("del_date");
				if(del_date != null)
					del_date = del_date.substring(0, del_date.indexOf(" "));
				String up_stat = rs.getString("up_stat");
				int pick_count = rs.getInt("pick_count");
				int view_count = rs.getInt("view_count");
				String visit_date = rs.getString("visit_date");
				visit_date = visit_date.substring(0, visit_date.indexOf(" "));
				int visit_time = rs.getInt("visit_time");
				int weather_id = rs.getInt("weather_id");
				String place_name = rs.getString("place_name");
				String address = rs.getString("address");
				String contents = rs.getString("contents");
				String drone = rs.getString("drone");
				String public_tran = rs.getString("public_tran");
				String public_info = rs.getString("public_info");
				String park = rs.getString("park");
				String park_info = rs.getString("park_info");
				String toilet = rs.getString("toilet");
				String shower = rs.getString("shower");
				String locker = rs.getString("locker");
				String[] img = rs.getString("img").split("_");
				
				info = new DiaryDetailDto(d_id, writer_id, up_date, re_date, del_date, 
						up_stat, pick_count, view_count, visit_date, visit_time, weather_id, "",
						place_name, address, contents, drone, public_tran, public_info, park,
						park_info, toilet, shower, locker, img);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return info;
	}
	
	// 다이어리 상태 변경
	public int mdfyDiaryStat(int dId, String stat) {
		String sql = "UPDATE diary_info SET up_stat = ? WHERE d_id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, stat);
			pstmt.setInt(2, dId);
			int r = pstmt.executeUpdate();
			return r;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	// 신고처리 _ 신고 테이블 변경
	public int updateReport(int idx, String mngCon) {
		String sql = "UPDATE report SET report_mng_contents = ?, report_stat = 'Y' WHERE report_idx = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mngCon);
			pstmt.setInt(2, idx);
			int r = pstmt.executeUpdate();
			return r;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	// 신고 목록 _ 전체 수
	public int getReportTotal(String stat, String type, String condition, String keyword) {
		sqlSb.delete(0, sqlSb.length());
		sqlSb.append("SELECT count(*) FROM report WHERE report_stat like ? and report_type like ?");
		if(!condition.equals("")) {
			sqlSb.append(" and ").append(condition).append(" = '").append(keyword).append("'");
		}
		try {
			pstmt = conn.prepareStatement(sqlSb.toString());
			pstmt.setString(1, stat);
			pstmt.setString(2, type);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	// 신고 목록 _ 리스트
	public ArrayList<ReportDto> getReportList(String stat, String type, String condition, String keyword, int startIdx, int endIdx) {
		ArrayList<ReportDto> list = new ArrayList<ReportDto>();
		sqlSb.delete(0, sqlSb.length());
		sqlSb.append("SELECT * FROM ")
			.append("(SELECT rownum idx, l.* FROM (")
			.append("SELECT * FROM report WHERE report_stat like ? and report_type like ?");
		if(!condition.equals("")) {
			sqlSb.append(" and ").append(condition).append(" = '").append(keyword).append("'");
		}
		sqlSb.append(" ORDER BY report_idx DESC) l ) list ")
			.append("WHERE list.idx >= ? and list.idx < ?");
		
		try {
			pstmt = conn.prepareStatement(sqlSb.toString());
			pstmt.setString(1, stat);
			pstmt.setString(2, type);
			pstmt.setInt(3, startIdx);
			pstmt.setInt(4, endIdx);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				int idx = rs.getInt("report_idx");
				String report_date = rs.getString("report_date");
				report_date = report_date.substring(0, report_date.indexOf(" "));
				String report_id = rs.getString("report_id");
				String report_type = rs.getString("report_type");
				String target_id = rs.getString("target_id");
				String target_d_id = rs.getString("target_d_id");
				String report_contents = rs.getString("report_contents");
				String report_mng_contents = rs.getString("report_mng_contents");
				String report_stat = rs.getString("report_stat");
				
				list.add(new ReportDto(idx, report_id, report_date, report_type, target_id, 
						target_d_id, report_contents, report_mng_contents, report_stat));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	// 신고 상세
	public ReportDto getReportDetail(int idx) {
		ReportDto detail = null;
		String sql = "SELECT * FROM report WHERE report_idx = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				int reportIdx = rs.getInt("report_idx");
				String reportId = rs.getString("report_id");
				String reportDate = rs.getString("report_date");
				String reportType = rs.getString("report_type");
				String targetId = rs.getString("target_id");
				String targetDId = rs.getString("target_d_id");
				String contents = rs.getString("report_contents");
				String mngContents = rs.getString("report_mng_contents");
				String stat = rs.getString("report_stat");
				
				detail = new ReportDto(reportIdx, reportId, reportDate, reportType, targetId, targetDId, contents, mngContents, stat);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return detail;
	}
}
