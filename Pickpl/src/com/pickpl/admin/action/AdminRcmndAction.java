package com.pickpl.admin.action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.pickpl.admin.dao.AdminDao;
import com.pickpl.admin.dto.RcmndDto;
import com.pickpl.dao.DiaryDao;
import com.pickpl.dto.ViewDiaryDto;

public class AdminRcmndAction implements Action {
	AdminDao aDao = new AdminDao();
	DiaryDao dDao = new DiaryDao();
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cmd = request.getParameter("command");
		
		JSONObject resultObj = new JSONObject();
		
		// 추천 목록
		if(cmd.equals("rcmndList")) {
			int listToShow = 10;
			int pageIdx = Integer.parseInt(request.getParameter("pageIdx"));
			
			int total = aDao.getTableTotal("recommend");
			int pageCnt = (total / listToShow) + 1;
			if(total % listToShow == 0)
				pageCnt = total / listToShow;
			
			int startIdx = (pageIdx - 1) * listToShow + 1;
			int endIdx = startIdx + listToShow;
			if(pageIdx == pageCnt && total % listToShow != 0)
				endIdx = startIdx + (total % listToShow);
			
			JSONArray rcmndList = aDao.getRcmndList(startIdx, endIdx);
			
			resultObj.put("pageCnt", pageCnt);
			resultObj.put("list", rcmndList);
			request.setAttribute("result", resultObj);
		}
		// 추천글 관리
		else if(cmd.equals("mngRcmnd")) {
			int no = Integer.parseInt(request.getParameter("no"));
			RcmndDto rcmnd = aDao.getRcmndDetail(no);
			ArrayList<ViewDiaryDto> diaryList = dDao.getDiary(rcmnd.getD_id(), null);
			
			if(rcmnd.getHold() == null) {
				request.setAttribute("statText", "");
			} else {
				request.setAttribute("statText", "게시 보류");
			}
			
			request.setAttribute("rcmnd", rcmnd);
			request.setAttribute("diaryList", diaryList);
		}
		
		// 다이어리 검색
		else if(cmd.equals("searchDiary")) {
			int listToShow = 10;
			
			int pageIdx = Integer.parseInt(request.getParameter("pageIdx"));			
			String month = request.getParameter("month");
			if(month.equals("all"))
				month = "";
			String region = request.getParameter("region");
			if(region.equals("all"))
				region = "";
			String city = request.getParameter("city");
				if(city.equals("all"))
					city = "";
			
			int total = aDao.getSearchDiaryTotal(month, region, city);
			int pageCnt = (total / listToShow) + 1;
			if(total % listToShow == 0)
				pageCnt = total / listToShow;
			
			int startIdx = (pageIdx - 1) * listToShow + 1;
			int endIdx = startIdx + listToShow;
			if(pageIdx == pageCnt && total % listToShow != 0)
				endIdx = startIdx + (total % listToShow);
			
			JSONArray searchList = aDao.searchDiary(region, city, month, startIdx, endIdx);
			
			resultObj.put("list", searchList);
			resultObj.put("total", total);
			resultObj.put("pageCnt", pageCnt);
			
			request.setAttribute("result", resultObj);
		}
		// 수정
		else if(cmd.equals("updateRcmnd")) {
			int no = Integer.parseInt(request.getParameter("no"));
			String title = request.getParameter("title");
			String openDate = request.getParameter("open_date");
			String closeDate = request.getParameter("close_date");
			String dId = request.getParameter("d_id");
			int count = Integer.parseInt(request.getParameter("count"));
			String hold = request.getParameter("hold");
			if(hold.equals("")) hold = null;
			
			int result = aDao.updateRcmnd(
					new RcmndDto(no, title, "", openDate, closeDate, dId, count, hold));
			if(result == 1)
				request.setAttribute("update", "success");
		}
		// 작성
		else if(cmd.equals("writeRcmnd")) {
			String title = request.getParameter("title");
			String openDate = request.getParameter("open_date");
			String closeDate = request.getParameter("close_date");
			String dId = request.getParameter("d_id");
			int count = Integer.parseInt(request.getParameter("count"));
			
			int result = aDao.writeRcmnd(new RcmndDto(0, title, null, openDate, 
					closeDate, dId, count, null));
			if(result == 1)
				request.setAttribute("insert", "success");
		}
		// 삭제
		else if(cmd.equals("deleteRcmnd")) {
			int no = Integer.parseInt(request.getParameter("no"));
			
			if(aDao.deleteRcmnd(no) == 1)
				request.setAttribute("delete", "success");
		}
		request.getRequestDispatcher("adminController?command=result&resultAct="+cmd).forward(request, response);
	}
}
