package com.pickpl.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.pickpl.dao.DiaryDao;
import com.pickpl.dto.SearchListDto;

public class ListAction implements Action {
	DiaryDao dDao = new DiaryDao();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String loginId = (String) session.getAttribute("loginId");
		
		JSONObject resultObj = new JSONObject();
		
		String keyword = request.getParameter("keyword");
		String order = request.getParameter("order");
		
		JSONArray searchListArr = new JSONArray();
		JSONArray rcmndPlaceArr = new JSONArray();
		
		ArrayList<SearchListDto> searchList = dDao.getPlace(keyword, order);
		for(SearchListDto dto : searchList) {
			JSONObject obj = new JSONObject();
			
			obj.put("dId", dto.getD_id());
			obj.put("writer_id", dto.getWriter_id());
			obj.put("profile", dto.getProfile());
			JSONArray imgArr = new JSONArray();
			for(String img : dto.getImg().split("_")) {
				imgArr.add(img);
			}
			obj.put("img", imgArr);
			obj.put("place_name", dto.getPlace_name());
			obj.put("address", dto.getAddress());
			obj.put("pick_count", dto.getPick());
			obj.put("view_count", dto.getView_count());
			if(dto.getWriter_id().equals(loginId))
				obj.put("pick", dDao.checkPick(loginId, dto.getD_id()) + " hide");				
			else
				obj.put("pick", dDao.checkPick(loginId, dto.getD_id()));
			
			obj.put("lat", dto.getLat());
			obj.put("lng", dto.getLng());
			
			searchListArr.add(obj);
		}
		
		// 추천
		ArrayList<String> aroundList = dDao.getAroundPlaces(keyword);
		ArrayList<String> randomList = dDao.getRandomPlaces();
		
		HashSet<String> rcmndPlace = new HashSet<String>();
		
		// 관련 지역
		if(aroundList.size() <= 3) {
			for(int i=0; i<aroundList.size(); i++)
				rcmndPlace.add(aroundList.get(i));
		} else {
			rcmndPlace.add(aroundList.get(0));
			System.out.println("size " + aroundList.size());
			while(rcmndPlace.size() < 3) {
				int n = (int)(Math.random() * (aroundList.size() - 1) + 1);
				System.out.println("n " + n);
				rcmndPlace.add(aroundList.get(n));
			}			
		}
		
		// 랜덤 지역
		while(rcmndPlace.size() < 5) {
			int n = (int)(Math.random() * (randomList.size()));
			rcmndPlace.add(randomList.get(n));
		}
		
		Iterator itr = rcmndPlace.iterator();
		while(itr.hasNext()) {
			rcmndPlaceArr.add(itr.next());
		}
		
		resultObj.put("searchList", searchListArr);
		resultObj.put("recommend", rcmndPlaceArr);
		
		request.setAttribute("result", resultObj);
		request.getRequestDispatcher("Controller?command=result&resultAct=searchList").forward(request, response);
	}
	
}
