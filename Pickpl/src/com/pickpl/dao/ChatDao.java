package com.pickpl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.pickpl.db.DBConnection;
import com.pickpl.dto.ChatDto;

public class ChatDao {
	Connection conn = DBConnection.connect();
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	// 대화 상대 메시지 이력 확인
	public boolean checkSendTo(String loginId, String toId) {
		String sql = "SELECT count(*) FROM chat WHERE (from_id=? and to_id=?) or (to_id=? and from_id=?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, loginId);
			pstmt.setString(2, toId);
			pstmt.setString(3, loginId);
			pstmt.setString(4, toId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(rs.getInt(1) > 0) return true;
				else return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	// 대화 상대 프로필 받아오기
	public String getProfile(String toId) {
		String profile = null;
		String sql = "SELECT profile FROM member WHERE id=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, toId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				profile = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return profile;
	}
	
	// 대화상대 최근이력 가져오기
	public ArrayList<ChatDto> getAcctList(String loginId) {
		ArrayList<ChatDto> acctList = new ArrayList<ChatDto>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT c.*, f.id, m.profile ")
			.append("FROM chat c, (SELECT id, max(idx) max FROM ( ")
			.append("SELECT distinct to_id id, idx FROM chat WHERE from_id=? ")
			.append("UNION ")
			.append("SELECT distinct from_id id, idx FROM chat WHERE to_id=?) group by id) f, member m ")
			.append("WHERE c.idx = f.max AND f.id = m.id ")
			.append("ORDER BY send_date DESC");
		
		try {
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, loginId);
			pstmt.setString(2, loginId);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				int idx = rs.getInt("idx");
				String toId = rs.getString("id");
				String profile = rs.getString("profile");
				String msg = rs.getString("message");
				String send_date = rs.getString("send_date");
				send_date = send_date.substring(0, send_date.indexOf(" "));
				String check_stat = rs.getString("check_stat");
				
				acctList.add(new ChatDto(idx, toId, loginId, msg, send_date, check_stat, profile));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return acctList;
	}
	
	// 내가 읽지 않은 메시지 있는지 확인
	public String checkUnread(String loginId, String fromId) {
		String sql = "SELECT count(*) FROM chat WHERE from_id = ? and to_id = ? and check_stat = 'N'";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, fromId);
			pstmt.setString(2, loginId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(rs.getInt(1) > 0) return "N";
				else return "Y";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return "Y";
	}
	
	// 대화 내역 받아오기
	public ArrayList<ChatDto> getChatList(String loginId, String toId) {
		ArrayList<ChatDto> chatList = new ArrayList<ChatDto>();
		String sql = "SELECT * FROM chat WHERE (from_id=? and to_id=?) or (to_id=? and from_id=?) ORDER BY send_date";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, loginId);
			pstmt.setString(2, toId);
			pstmt.setString(3, loginId);
			pstmt.setString(4, toId);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				int idx = rs.getInt("idx");
				String to_id = rs.getString("to_id");
				String from_id = rs.getString("from_id");
				String msg = rs.getString("message");
				String send_date = rs.getString("send_date");
				String check_stat = rs.getString("check_stat");
				chatList.add(new ChatDto(idx, to_id, from_id, msg, send_date, check_stat, ""));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return chatList;
	}
	
	// 전송 시 대화내역 추가
	public int updateMsg(String loginId, String toId, String msg, String sendDate) {
		String sql = "INSERT INTO chat (idx, from_id, to_id, message, send_date) VALUES(chat_seq.nextval,?,?,?,to_date(?,'YYYY-MM-DD HH24:MI:SS'))";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, loginId);
			pstmt.setString(2, toId);
			pstmt.setString(3, msg);
			pstmt.setString(4, sendDate);
			int r = pstmt.executeUpdate();
			return r;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	// 메시지 확인 업데이트
	public int updateCheck(String loginId, String checkId) {
		String sql = "UPDATE chat SET check_stat='Y' WHERE to_id=? and from_id=? and check_stat='N'";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, loginId);
			pstmt.setString(2, checkId);
			int r = pstmt.executeUpdate();
			return r;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
}
