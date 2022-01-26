package com.pickpl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.pickpl.db.DBConnection;
import com.pickpl.dto.MemberDto;

public class MemberDao {
	Connection conn = DBConnection.connect();
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	// 인증번호 발생
	public String generateNo() {
		String verifyNo = "";
		for(int i=0; i<15; i++) {
			int type = (int)(Math.random() * 3);
			if(type == 0) {
				verifyNo += (char)((int)(Math.random() * 10) + 48);
			} else if (type == 1) {
				verifyNo += (char)((int)(Math.random() * 25) + 65);
			} else {
				verifyNo += (char)((int)(Math.random() * 25) + 97);			
			}
		}
		
		return verifyNo;
	}
	
	// 아이디 중복 확인
	public boolean memberIdCheck(String id) {
		String sql = "SELECT count(*) FROM member WHERE id=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(rs.getInt(1) == 0) return true;
				else return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	// 회원가입
	public int memberJoin(MemberDto dto, String verifyNo) {
		String sql = "INSERT INTO member(id, pw, name, phone, email, birth, gender, cf_mail, pw_link) VALUES(?,?,?,?,?,TO_DATE(?,'YYYY-MM-DD'),?,?,?)";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getId());
			pstmt.setString(2, dto.getPw());
			pstmt.setString(3, dto.getName());
			pstmt.setString(4, dto.getPhone());
			pstmt.setString(5, dto.getEmail());
			pstmt.setString(6, dto.getBirth());
			pstmt.setString(7, dto.getGender());
			pstmt.setString(8, dto.getCf_mail());
			pstmt.setString(9, verifyNo);
			int r = pstmt.executeUpdate();
			return r;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
//			DBConnection.disconnect(conn, pstmt, rs);
		}
		
		return 0;
	}
	
	// 가입 시 픽 기본폴더 생성
	public int createPickFolder(String id) {
		String sql = "INSERT INTO pick(pick_id) VALUES(?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			int r = pstmt.executeUpdate();
			return r;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	// 인증번호 확인
	public boolean checkVerifyNo(String id, String verifyNo) {
		String sql = "SELECT * FROM member WHERE id=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			String pw_link = null;
			if(rs.next() ) {
				pw_link = rs.getString("pw_link");
			}
			
			if(verifyNo.equals(pw_link)) return true;
			else return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	// 계정 상태 변경
	public int updateStat(String id, String stat) {
		String sql = "UPDATE member SET acc_stat=?, pw_link=null WHERE id=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, stat);
			pstmt.setString(2, id);
			int r = pstmt.executeUpdate();
			return r;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	// 아이디 찾기
	public String findId(String name, String email) {
		String id = null;
		String sql = "SELECT id FROM member WHERE name=? and email=? and acc_stat!='O'";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, email);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				id = rs.getString("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
//			DBConnection.disconnect(conn, pstmt, rs);
		}
		
		return id;
	}
	
	// 비밀번호 찾기
	public boolean findPw(String id, String name, String email) {
		try {
			String sql = "SELECT count(*) FROM member WHERE id=? and name=? and email=? and acc_stat!='O'";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, name);
			pstmt.setString(3, email);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(rs.getInt(1) == 1) return true;
				else return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
//			DBConnection.disconnect(conn, pstmt, rs);
		}
		return false;
	}
	
	// 비밀번호 인증번호 추가
	public String addPwLink(String id) {
		String verifyNo = generateNo();
		String sql = "UPDATE member SET pw_link = ? WHERE id=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, verifyNo);
			pstmt.setString(2, id);
			int r = pstmt.executeUpdate();
			if(r == 1)
				return verifyNo;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "0";
	}
	
	// 비밀번호 변경
	public int updatePw(String id, String pw) {
		String sql = "UPDATE member SET pw = ?, pw_link=null WHERE id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, pw);
			pstmt.setString(2, id);
			int r = pstmt.executeUpdate();
			return r;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	// 로그인(아이디 비번 확인)
	public boolean loginCheck(String id, String pw) {
		String sql = "SELECT count(*) cnt FROM member WHERE id=? and pw=? and acc_stat != 'O'";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(rs.getInt(1) == 1) return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	// 계정 상태 확인
	public String checkAccStat(String id) {
		String sql = "SELECT * FROM member WHERE id=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getString("acc_stat");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	// 개인 정보 가져오기
	public MemberDto getMyInfo(String loginId) {
		String sql = "SELECT * FROM member WHERE id=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, loginId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				String id = rs.getString("id");
				String profile = rs.getString("profile");
				String email = rs.getString("email");
				String name = rs.getString("name");
				String phone = rs.getString("phone");
				String birth = rs.getString("birth").substring(0, rs.getString("birth").lastIndexOf(" "));
				String gender = rs.getString("gender");
				String cf_mail = rs.getString("cf_mail");
				
				return new MemberDto(id, "", profile, email, name, phone, birth, gender, cf_mail);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new MemberDto();
	}
	
	// 회원 정보 수정
	public int updateMyInfo(MemberDto dto) {
		if(dto.getProfile() == null) {
			String sql = "UPDATE member SET name=?, phone=?, birth=to_date(?, 'YYYY-MM-DD'), "
					+ "gender=?, cf_mail=? WHERE id = ?";
			
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, dto.getName());
				pstmt.setString(2, dto.getPhone());
				pstmt.setString(3, dto.getBirth());
				pstmt.setString(4, dto.getGender());
				pstmt.setString(5, dto.getCf_mail());
				pstmt.setString(6, dto.getId());
				int r = pstmt.executeUpdate();
				return r;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			String sql = "UPDATE member SET profile=?, name=?, phone=?, "
					+ "birth=to_date(?, 'YYYY-MM-DD'), gender=?, cf_mail=? WHERE id = ?";
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, dto.getProfile());
				pstmt.setString(2, dto.getName());
				pstmt.setString(3, dto.getPhone());
				pstmt.setString(4, dto.getBirth());
				pstmt.setString(5, dto.getGender());
				pstmt.setString(6, dto.getCf_mail());
				pstmt.setString(7, dto.getId());
				int r = pstmt.executeUpdate();
				return r;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return 0;
	}
	
	// 탈퇴 _ 멤버 테이블 업데이트
	public int byeMember(String loginId) {
		String sql = "UPDATE member SET out_date = sysdate, acc_stat = 'O' WHERE id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, loginId);
			int r = pstmt.executeUpdate();
			return r;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	// 탈퇴 _ 다이어리 정보 가져오기
	public ArrayList<Integer> getByeDiary(String loginId) {
		ArrayList<Integer> diaryList = new ArrayList<Integer>();
		String sql = "SELECT d_id FROM diary_info WHERE writer_id = ? and up_stat != 'D'";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, loginId);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				diaryList.add(rs.getInt("d_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return diaryList;
	}
	
	// 탈퇴 _ 픽 정보 삭제
	public int byePick(String loginId) {
		String sql = "DELETE FROM pick WHERE pick_id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, loginId);
			int r = pstmt.executeUpdate();
			return r;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	// 탈퇴 _ 내가 쓴 댓글 정보 업데이트
	public int byeCmnts(String loginId) {
		String sql = "UPDATE comments SET cmnt_stat = 'N' WHERE cmnt_id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, loginId);
			int r = pstmt.executeUpdate();
			return r;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	// 탈퇴 _ 최근 본 글 삭제
	public int byeRecent(String loginId) {
		String sql = "DELETE FROM recent WHERE recent_id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, loginId);
			int r = pstmt.executeUpdate();
			return r;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	
} 
