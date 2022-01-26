package com.pickpl.action;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.pickpl.dao.DiaryDao;
import com.pickpl.db.DbConn;
import com.pickpl.dto.RcmndListDto;
import com.pickpl.dto.ViewDiaryDto;

public class MainAction implements Action {
	DiaryDao dDao = new DiaryDao();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Connection conn = DbConn.connect();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		HttpSession session = request.getSession();
		String loginId = (String) session.getAttribute("loginId");
		
		String cmd = request.getParameter("command");
		
		if (cmd.equals("main")) {
			// 요즘 뜨는 여행지
			HashMap<String, String> popularList = dDao.getPopular();
			
			// 조회수 높은 픽플
			ArrayList<ViewDiaryDto> highViewList = dDao.getHighViews(loginId);
			
			// 추천 목록에 대한 픽플 가져오기
			ArrayList<RcmndListDto> rcmndList = dDao.getRcmndList();
			for(RcmndListDto dto : rcmndList) {
				dto.setRcmndList(dDao.getDiary(dto.getD_id(), loginId));
			}
			
			request.setAttribute("popularList", popularList);
			request.setAttribute("highViewList", highViewList);
			request.setAttribute("rcmndList", rcmndList);
			
			request.getRequestDispatcher("Controller?command=result&resultAct=main").forward(request, response);	
		}
		// 최근 본 픽플
		else if(cmd.equals("mainRecentList")) {
			if(loginId != null) {
				JSONObject resultObj = new JSONObject();
				JSONArray recentListArr = new JSONArray();
				//ArrayList<ViewDiaryDto> recentList = dDao.getRecent(loginId);
				
				String recent = dDao.getRecent(loginId);
				ArrayList<ViewDiaryDto> recentList = null;
				if(recent != null) {
					recentList = dDao.getDiary(recent, loginId);
				}
				
				for(ViewDiaryDto dto : recentList) {
					JSONObject obj = new JSONObject();
					obj.put("d_id", dto.getD_id());
					obj.put("writer", dto.getWriter_id());
					obj.put("profile", dto.getProfile());
					JSONArray imgArr = new JSONArray();
					for(String img : dto.getImg().split("_")) {
						imgArr.add(img);
					}
					obj.put("img", imgArr);
					obj.put("place_name", dto.getPlace_name());
					obj.put("address", dto.getAddress());
					obj.put("pick_count", dto.getPick_count());
					obj.put("view_count", dto.getView_count());
					obj.put("pick", dto.getPick());
					
					recentListArr.add(obj);
				}
				
				resultObj.put("recentList", recentListArr);
				request.setAttribute("result", resultObj);
				request.getRequestDispatcher("Controller?command=result&resultAct=mainRecentList").forward(request, response);
			}
		} // End
		
	}
}