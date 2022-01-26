package com.pickpl.action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.pickpl.dao.DiaryDao;
import com.pickpl.dto.ViewDiaryDto;

public class DiaryDetailAroundAction implements Action {
	DiaryDao dDao = new DiaryDao();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String loginId = (String) session.getAttribute("loginId");

		int dId = Integer.parseInt(request.getParameter("dId"));
		String region = request.getParameter("region");
		String city = request.getParameter("city");
		
		JSONObject resultObj = new JSONObject();
		ArrayList<ViewDiaryDto> aroundList = null;
		
		aroundList = dDao.getAroundCity(dId, city, loginId);
		ArrayList<Integer> dIdList = new ArrayList();
		for (ViewDiaryDto dto : aroundList) {
			dIdList.add(dto.getD_id());
		}
		
		if(aroundList == null || aroundList.size() < 10) {
			for(ViewDiaryDto dto : dDao.getAroundRegion(dId, region, loginId)) {
				if(aroundList.size() < 10 && !dIdList.contains(dto.getD_id())) {
					aroundList.add(dto);
					dIdList.add(dto.getD_id());
				}
			}
		}
		
		JSONArray aroundListArr = new JSONArray();
		if(aroundList.size() > 0) {
			for(ViewDiaryDto dto : aroundList) {
				JSONObject diaryObj = new JSONObject();
				
				diaryObj.put("dId", dto.getD_id());
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
				
				aroundListArr.add(diaryObj);
			}
		}
		resultObj.put("aroundList", aroundListArr);
		
		request.setAttribute("result", resultObj);
		request.getRequestDispatcher("Controller?command=result&resultAct=diaryAround").forward(request, response);
	}

}
