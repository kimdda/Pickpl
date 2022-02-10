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
		
		System.out.println(dId);
		
		JSONObject resultObj = new JSONObject();
		
		int viewR = dDao.updateViewCount(Integer.parseInt(dId));
		
		if(loginId != null) {
			int recentR = 0;
			ArrayList<String> recentList = new ArrayList<String>();
			String recent = dDao.getRecent(loginId);
			if(recent != null) {
				for(String r : recent.split("_")) {
					recentList.add(recent);
				}
			}
			
			if(recentList.contains(dId)) 
				recentList.remove(recentList.indexOf(dId));
			else if(recentList.size() == 15) {
				recentList.remove(14);
			}
			recentList.add(0, dId);
			
			if(recentList.size() == 1) {
				recentR = dDao.insertRecent(loginId, dId);
			} else {
				recentR = dDao.updateRecent(loginId, recentList);
			}
		}
		
		if(viewR == 1)
			resultObj.put("viewR", "success");
		
//		if(recentR == 1)
//			resultObj.put("recentR", "success");
		
		request.setAttribute("result", resultObj);
		request.getRequestDispatcher("Controller?command=result&resultAct=updateViewNRecent").forward(request, response);			
	}
	
}
