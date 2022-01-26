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
import com.pickpl.db.DbConn;
import com.pickpl.dto.ViewDiaryDto;

public class DiaryListAction implements Action {
	DiaryDao dDao = new DiaryDao();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String loginId = (String)session.getAttribute("loginId");
		
		JSONObject resultObj = new JSONObject();
		
		String cmd = request.getParameter("command");
		
		String diaryId = request.getParameter("diaryId");
		if(diaryId == null)
			diaryId = loginId;
		
		if(cmd.equals("diaryPage")) {
			request.setAttribute("diaryId", diaryId);
			if(loginId != null && diaryId.equals(loginId)) {
				request.setAttribute("isMy", true);				
			} else {
				request.setAttribute("isMy", false);				
			}
		}
		
		// 상단 정보
		else if(cmd.equals("diarySummary")) {
			int pickCnt = dDao.getPickCount(diaryId);
			int pickedCnt = dDao.getPickedCount(diaryId);
			int pickplCnt = dDao.getPickplCount(diaryId);
			
			resultObj.put("diaryId", diaryId);
			resultObj.put("pickCnt", pickCnt);
			resultObj.put("pickedCnt", pickedCnt);
			resultObj.put("pickplCnt", pickplCnt);
			
			request.setAttribute("result", resultObj);
		}
		// 다이어리 리스트
		else if(cmd.equals("diaryList")) {
			// 다이어리 리스트
			ArrayList<ViewDiaryDto> diaryList = null;
			String order = request.getParameter("order");
			if(order == null)
				order = "up_date";
			diaryList = dDao.getDiaryList(diaryId, order, loginId);
			
			JSONArray diaryListObj = new JSONArray();
			if(diaryList.size() > 0) {
				for(ViewDiaryDto dto : diaryList) {
					JSONObject diaryObj = new JSONObject();
					
					diaryObj.put("dId", dto.getD_id());
//					if(loginId != null)
//						diaryObj.put("pick", dDao.checkPick(loginId, dto.getD_id()));
//					else
//						diaryObj.put("pick", "pick");
					if(dto.getWriter_id().equals(loginId))
						diaryObj.put("pick", dDao.checkPick(loginId, dto.getD_id()) + " hide");				
					else
						diaryObj.put("pick", dDao.checkPick(loginId, dto.getD_id()));
					
					diaryObj.put("writer_id", dto.getWriter_id());
					diaryObj.put("profile", dto.getProfile());
					String[] imgArr = dto.getImg().split("_");
					JSONArray diaryImgArr = new JSONArray();
					for(String img : imgArr) {
						diaryImgArr.add(img);
					}
					diaryObj.put("img", diaryImgArr);
					diaryObj.put("place_name", dto.getPlace_name());
					diaryObj.put("address", dto.getAddress());
					diaryObj.put("pick_count", dto.getPick_count());
					diaryObj.put("view_count", dto.getView_count());
					
					diaryListObj.add(diaryObj);
				}
			}
			resultObj.put("diaryList", diaryListObj);
			request.setAttribute("result", resultObj);
		}
		// 다이어리 삭제
		else if(cmd.equals("diaryDel")) {
			int dId = Integer.parseInt(request.getParameter("dId"));
			
			int updateInfo = dDao.updateDiaryInfo(dId);
			int deleteDiary = dDao.deleteDiary(dId);
			int deleteMap = dDao.deleteMap(dId);
			dDao.byePickFolderList(dId);
			
			if(updateInfo == 1 && deleteDiary == 1 && deleteMap == 1)
				resultObj.put("result", "success");
			
			request.setAttribute("result", resultObj);
		}
		
		request.getRequestDispatcher("Controller?command=result&resultAct="+cmd).forward(request, response);
	}

}
