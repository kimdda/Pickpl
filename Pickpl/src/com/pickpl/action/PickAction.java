package com.pickpl.action;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.pickpl.dao.PickDao;
import com.pickpl.db.DbConn;

public class PickAction implements Action {
	PickDao pDao = new PickDao();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String loginId = (String) session.getAttribute("loginId");

		String cmd = request.getParameter("command");

		JSONObject resultObj = new JSONObject();
		
		// 픽 취소하기
		if(cmd.equals("unpick")) {
			String dId = request.getParameter("dId");
//			int pick_cnt = Integer.parseInt(request.getParameter("cnt"));
			
			HashMap<String, String> pickFolder = pDao.findPickFolder(loginId, dId);
			String folder = pickFolder.get("folder");
			String pickList = pickFolder.get("pick_d_id");
			if(pickList != null) {
				pickList = pickList.replace(dId+"_", "");				
			}
			
			int pickR = pDao.unpick(pickList, folder, loginId);
			int diaryR = pDao.updatePickCount(Integer.parseInt(dId), cmd);
			
			if(pickR == 1 && diaryR == 1)
				resultObj.put("unpick", "success");
			
			request.setAttribute("result", resultObj);
		} 
		// 픽하기
		else if(cmd.equals("pick")) {
			String folder = request.getParameter("folder");
			String dId = request.getParameter("dId");
//			int pick_cnt = Integer.parseInt(request.getParameter("cnt"));
			
			int pickR = pDao.pick(folder, dId, loginId);
			int cntR = pDao.updatePickCount(Integer.parseInt(dId), cmd);
				
			if(pickR == 1 && cntR == 1) 
				resultObj.put("pick", "success");
			
			request.setAttribute("result", resultObj);
		}
		
		request.getRequestDispatcher("Controller?command=result&resultAct="+cmd).forward(request, response);
		
	}

}
