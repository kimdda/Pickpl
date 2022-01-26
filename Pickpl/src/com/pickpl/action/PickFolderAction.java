package com.pickpl.action;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.pickpl.dao.DiaryDao;
import com.pickpl.dao.PickDao;
import com.pickpl.db.DbConn;
import com.pickpl.dto.PickFolderDto;
import com.pickpl.dto.ViewDiaryDto;

public class PickFolderAction implements Action {
	PickDao pDao = new PickDao();
	DiaryDao dDao = new DiaryDao();
	
//	public DiaryListVO diary(ResultSet rs) throws SQLException {
//		int dId = rs.getInt("d_id");
//		String pick = "picked";
//		String writer = rs.getString("writer_id");
//		String profile = rs.getString("profile");
//		String[] img = rs.getString("img").split("_");
//		String place_name = rs.getString("place_name");
//		String address = rs.getString("address");
//		String up_date = rs.getString("up_date");
//		int pick_count = rs.getInt("pick_count");
//		int view_count = rs.getInt("view_count");
//		
//		return new DiaryListVO(dId, writer, profile, img, place_name, address, up_date, pick_count, view_count, pick);
//	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Connection conn = DbConn.connect();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		HttpSession session = request.getSession();
		String loginId = (String) session.getAttribute("loginId");
		
		String cmd = request.getParameter("command");

		JSONObject resultObj = new JSONObject();
		
		// 픽 폴더 목록 가져오기
		if(cmd.equals("pickFolderList")) {
			String order = request.getParameter("order");
			if(order == null) {
				order = "load";
			}
			
			ArrayList<PickFolderDto> folderList = pDao.getFolderList(loginId, order);
			
			if(order.equals("load")) {
				request.setAttribute("result", folderList);
				request.getRequestDispatcher("Controller?command=result&resultAct=pickFolderLoad").forward(request, response);
			} else {
				JSONArray folderArrList = new JSONArray();
				for(PickFolderDto dto : folderList) {
					JSONObject folderObj = new JSONObject();
					folderObj.put("folder", dto.getFolder());
					folderObj.put("folder_cnt", dto.getFolder_d_count());
					
					folderArrList.add(folderObj);
				}
				resultObj.put("folderList", folderArrList);
				request.setAttribute("result", resultObj);
				request.getRequestDispatcher("Controller?command=result&resultAct=pickFolder").forward(request, response);
			}
			
		}
		// 픽 폴더 추가하기
		else if(cmd.equals("addPickFolder")) {
			String folderName = request.getParameter("folderName");
			
			int result = pDao.insertPickFolder(loginId, folderName);
			
			if(result == 1) 
				resultObj.put("result", "success");
			else 
				resultObj.put("result", "fail");
			
			request.setAttribute("result", resultObj);
			request.getRequestDispatcher("Controller?command=result&resultAct=addPickFolder").forward(request, response);
			
		}
		//픽 폴더 삭제
		else if(cmd.equals("delPickFolder")) {
			String[] folderArr = request.getParameter("folderName").split("_");
			
			// 다이어리의 픽 수 변경
			String pickList = pDao.getDeletePick(folderArr, loginId); 
			for(String pick : pickList.split("_")) {
				pDao.updatePickCount(Integer.parseInt(pick), "unpick");
			}
			
			// 픽 폴더 삭제
			int successCount = pDao.deletePickFolder(folderArr, loginId);
			if (folderArr.length == successCount) {
				resultObj.put("result", "success");
			}
			request.setAttribute("result", resultObj);
			request.getRequestDispatcher("Controller?command=result&resultAct="+cmd).forward(request, response);
			
		}
		// 픽 폴더명 변경 
		else if(cmd.equals("mdfyPickFolder")) {
			String folder = request.getParameter("folder");
			String newName = request.getParameter("newName");
			
			int result = pDao.mdfyPickFolder(newName, folder, loginId);
			if(result == 1)
				resultObj.put("result", "success");
			
			request.setAttribute("result", resultObj);
			request.getRequestDispatcher("Controller?command=result&resultAct="+cmd).forward(request, response);
		}
		// 픽 폴더 디테일
		else if(cmd.equals("pickFolderDetail")) {
			String folder = request.getParameter("folder");
			String pickList = pDao.getPickList(loginId, folder);
			
			ArrayList<ViewDiaryDto> pickDiaryList = dDao.getDiary(pickList, loginId);

			request.setAttribute("folderName", folder);
			request.setAttribute("diaryCnt", pickDiaryList.size());
			request.setAttribute("pickDiaryList", pickDiaryList);
			request.getRequestDispatcher("Controller?command=result&resultAct="+cmd).forward(request, response);
		} // End
		
		
	}
}
