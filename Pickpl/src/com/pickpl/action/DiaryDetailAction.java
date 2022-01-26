package com.pickpl.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.pickpl.admin.dto.EtcDto;
import com.pickpl.dao.DiaryDao;

public class DiaryDetailAction implements Action {
	DiaryDao dDao = new DiaryDao();
	EtcDto etc = new EtcDto();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String loginId = (String)session.getAttribute("loginId");
		
		String cmd = request.getParameter("command");
		int dId = Integer.parseInt(request.getParameter("dId"));
		
		HashMap<String, String> diaryDetail = dDao.getDiaryDetail(dId);
		
		if(loginId != null && loginId.equals(diaryDetail.get("writer_id"))) {
			request.setAttribute("isMine", true);
		} else {
			request.setAttribute("isMine", false);
		}
		
		if(cmd.equals("diaryDetail")) {
			if(diaryDetail.get("drone") != null) {
				diaryDetail.put("drone_text", etc.getDrone().get(diaryDetail.get("drone")));
			}
			
			if(diaryDetail.get("public_tran") != null) {
				diaryDetail.put("public_tran_text", etc.getPublic_tran().get(diaryDetail.get("public_tran")));
			}
			
			if(diaryDetail.get("public_info") != null) {
				diaryDetail.put("public_info_text", etc.getPublic_info().get(diaryDetail.get("public_info")));
			}
			
			if(diaryDetail.get("park") != null) {
				diaryDetail.put("park_text", etc.getPark().get(diaryDetail.get("park")));
			}
			
			if(diaryDetail.get("park_info") != null) {
				diaryDetail.put("park_info_text", etc.getPark_info().get(diaryDetail.get("park_info")));
			}
			
			if(diaryDetail.get("toilet") != null) {
				diaryDetail.put("toilet_text", etc.getToilet().get(diaryDetail.get("toilet")));
			}
			
			if(diaryDetail.get("shower") != null) {
				diaryDetail.put("shower_text", etc.getShower().get(diaryDetail.get("shower")));
			}
			
			if(diaryDetail.get("locker") != null) {
				diaryDetail.put("locker_text", etc.getLocker().get(diaryDetail.get("locker")));
			}
			
			String visit_date = (String)diaryDetail.get("visit_date");
			diaryDetail.put("visit_date_text", (visit_date.substring(0,4) + "년 "
					+ visit_date.substring(5,7) + "월 " + visit_date.substring(8,10) + "일"));
			
			diaryDetail.put("pick", dDao.checkPick(loginId, dId));
			
			Set<String> diaryKeys = diaryDetail.keySet();
			Iterator<String> itr = diaryKeys.iterator();
			while(itr.hasNext()) {
				String key = itr.next();
				request.setAttribute(key, diaryDetail.get(key));
			}
			
			request.getRequestDispatcher("Controller?command=result&resultAct=diaryDetail").forward(request, response);
		} 
		// 글 수정 시 내용 불러오기
		else if(cmd.equals("mdfyDiaryDetail")) {
			JSONObject resultObj = new JSONObject();
			Set<String> diaryKeys = diaryDetail.keySet();
			Iterator<String> itr = diaryKeys.iterator();
			
			while(itr.hasNext()) {
				String key = itr.next();
				resultObj.put(key, diaryDetail.get(key));
			}
			
			JSONArray imgList = new JSONArray();
			for(String img : diaryDetail.get("img").split("_")) {
				imgList.add(img);
			}
			resultObj.put("imgList", imgList);
			
			request.setAttribute("result", resultObj);
			request.getRequestDispatcher("Controller?command=result&resultAct=mdfyDiaryDetail").forward(request, response);
		}
		
	}
	
}
