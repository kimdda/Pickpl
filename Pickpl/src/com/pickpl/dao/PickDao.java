package com.pickpl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.pickpl.db.DBConnection;
import com.pickpl.dto.PickFolderDto;

public class PickDao {
	Connection conn = DBConnection.connect();
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	// 픽 폴더 불러오기
	public ArrayList<PickFolderDto> getFolderList(String loginId, String order) {
		ArrayList<PickFolderDto> folderList = new ArrayList<PickFolderDto>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM pick WHERE pick_id = ? ");
		switch(order) {
		case "name_asc" :
			sql.append("ORDER BY folder");
			break;
		case "name_desc" :
			sql.append("ORDER BY folder DESC");
			break;
		case "count_asc" :
			sql.append("ORDER BY folder_d_count");
			break;
		default:
			sql.append("ORDER BY folder_d_count DESC");
			break;
		}
		
		try {
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, loginId);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				String id = rs.getString("pick_id");
				String folder = rs.getString("folder");
				String pick_d_id = rs.getString("pick_d_id");
				int folder_d_count = rs.getInt("folder_d_count");
				
				folderList.add(new PickFolderDto(id, folder, pick_d_id, folder_d_count));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return folderList;
	}
	
	// 픽 폴더 추가
	public int insertPickFolder(String loginId, String folderName) {
		String sql = "INSERT INTO pick(pick_id, folder) VALUES(?, ?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, loginId);
			pstmt.setString(2, folderName);
			int r = pstmt.executeUpdate();
			return r;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	// 삭제할 픽 폴더의 글 목록
	public String getDeletePick(String[] folderArr, String loginId) {
		String diary_list = "";
		for(String folder : folderArr) {
			String sql = "SELECT pick_d_id FROM pick WHERE folder=? and pick_id=?";
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, folder);
				pstmt.setString(2, loginId);
				rs = pstmt.executeQuery();
				if(rs.next()) {
					if(rs.getString("pick_d_id") != null) {
						diary_list += rs.getString("pick_d_id");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return diary_list;
	}
	
	// 픽 폴더 삭제
	public int deletePickFolder(String[] folderArr, String loginId) {
		int result = 0;
		for (String folder : folderArr) {
			String sql = "DELETE pick WHERE folder = ? AND pick_id = ?";
			
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, folder);
				pstmt.setString(2, loginId);
				int r = pstmt.executeUpdate();
				if (r == 1) {
					result++;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	// 픽 폴더명 변경
	public int mdfyPickFolder(String newName, String folder, String loginId) {
		String sql = "UPDATE pick SET folder = ? WHERE folder = ? and pick_id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, newName);
			pstmt.setString(2, folder);
			pstmt.setString(3, loginId);
			int r = pstmt.executeUpdate();
			return r;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	// 픽 폴더 상세보기
	public String getPickList(String loginId, String folder) {
		String pickList = null;
		String sql = "SELECT * FROM pick WHERE pick_id = ? and folder = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, loginId);
			pstmt.setString(2, folder);
			rs = pstmt.executeQuery();
			if(rs.next())
				pickList = rs.getString("pick_d_id");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return pickList;
	}
	
	// 픽 하기 _ 픽 업데이트
	public int pick(String folder, String dId, String loginId) {
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE pick SET pick_d_id = ");
		sb.append("(SELECT pick_d_id FROM pick WHERE pick_id = ? and folder = ?) || ?, ");
		sb.append("folder_d_count = folder_d_count + 1 WHERE pick_id=? and folder=?");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, loginId);
			pstmt.setString(2, folder);
			pstmt.setString(3, dId+"_");
			pstmt.setString(4, loginId);
			pstmt.setString(5, folder);
			int r = pstmt.executeUpdate();
			return r;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	// 픽 _ 다이어리 픽 업데이트
	public int updatePickCount(int dId, String cmd) {
		String sql = null;
		if(cmd.equals("pick")) {
			sql = "UPDATE diary SET pick_count = (pick_count + 1) WHERE diary_id=?";			
		} else {
			sql = "UPDATE diary SET pick_count = (pick_count - 1) WHERE diary_id=?";
		}
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
	
	// 픽 취소 _ 해당 폴더 찾기
	public HashMap<String, String> findPickFolder(String loginId, String dId) {
		HashMap<String, String> pickFolder = new HashMap<String, String>();
		String sql = "SELECT * FROM pick WHERE pick_id=? and pick_d_id like ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, loginId);
			pstmt.setString(2, "%" + dId + "%");
			rs = pstmt.executeQuery();
			if(rs.next()) {
				String pick_d_id = rs.getString("pick_d_id");
				String folder = rs.getString("folder");
				
				pickFolder.put("pick_d_id", pick_d_id);
				pickFolder.put("folder", folder);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pickFolder;
	}
	
	// 픽 취소 _ 픽 업데이트
	public int unpick(String pickList, String folder, String loginId) {
		String sql = "UPDATE pick SET pick_d_id = ?, folder_d_count = (folder_d_count - 1) WHERE pick_id = ? and folder = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, pickList);
			pstmt.setString(2, loginId);
			pstmt.setString(3, folder);
			int r = pstmt.executeUpdate();
			return r;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
}
