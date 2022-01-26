package com.pickpl.action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import com.pickpl.dao.DiaryDao;

public class UpdateViewNRecentAction implements Action {
	DiaryDao dDao = new DiaryDao();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String loginId = (String) session.getAttribute("loginId");
		
		String cmd = request.getParameter("command");
		String dId = request.getParameter("dId");
		
		JSONObject resultObj = new JSONObject();
		
		int viewR = dDao.updateViewCount(Integer.parseInt(dId));
		
		ArrayList<String> recentList = new ArrayList<String>();
		for(String recent : dDao.getRecent(loginId).split("_")) {
			recentList.add(recent);
		}
		
		if(recentList.contains(dId)) 
			recentList.remove(recentList.indexOf(dId));
		else if(recentList.size() == 15) {
			recentList.remove(14);
		}
		recentList.add(0, dId);
		
		int recentR = 0;
		if(recentList.size() == 0) {
			recentR = dDao.insertRecent(loginId, dId);
		} else {
			recentR = dDao.updateRecent(loginId, recentList);
		}
		
		if(viewR == 1)
			resultObj.put("viewR", "success");
		
		if(recentR == 1)
			resultObj.put("recentR", "success");
		
		request.setAttribute("result", resultObj);
		request.getRequestDispatcher("Controller?command=result&resultAct=" + cmd).forward(request, response);			
	}
	
}
