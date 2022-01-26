package com.pickpl.admin.action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.pickpl.admin.dao.AdminDao;
import com.pickpl.admin.dto.DiaryDetailDto;
import com.pickpl.admin.dto.DiaryListDto;
import com.pickpl.admin.dto.EtcDto;
import com.pickpl.admin.dto.ReportDto;
import com.pickpl.admin.dto.WeatherDto;

public class AdminDiaryAction implements Action {
	AdminDao aDao = new AdminDao();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cmd = request.getParameter("command");
		
		JSONObject resultObj = new JSONObject();
		
		// 다이어리 목록
		if(cmd.equals("diaryList")) {
			int listToShow = 10;
			int pageIdx = Integer.parseInt(request.getParameter("pageIdx"));
			String stat = request.getParameter("stat");
			if(stat.equals("all")) {
				stat = "%";
			}
			String region = request.getParameter("region");
			if(region.equals("all")) region = "%";
			String city = request.getParameter("city");
			if(city.equals("all")) city = "%";
			
			String condition = request.getParameter("condition");
			String keyword = request.getParameter("keyword");
			
			int total = aDao.getDiaryTotal(stat, region, city, condition, keyword);
			
			int pageCnt = (total / listToShow) + 1;
			if(total % listToShow == 0)
				pageCnt = total / listToShow;
			
			int startIdx = (pageIdx - 1) * listToShow + 1;
			int endIdx = startIdx + listToShow;
			if(pageIdx == pageCnt && total % listToShow != 0)
				endIdx = startIdx + (total % listToShow);
			
			ArrayList<DiaryListDto> diaryList = 
					aDao.getDiaryList(stat, region, city, condition, keyword, startIdx, endIdx);
			JSONArray diaryListArr = new JSONArray();
			
			for(DiaryListDto dto : diaryList) {
				JSONObject obj = new JSONObject();
				obj.put("d_id", dto.getD_id());
				obj.put("writer_id", dto.getWriter_id());
				obj.put("place_name", dto.getPlace_name());
				obj.put("region", dto.getRegion());
				obj.put("up_date", dto.getUp_date());
				obj.put("up_stat", dto.getUp_stat());
				
				diaryListArr.add(obj);
			}
			
			resultObj.put("total", total);
			resultObj.put("pageCnt", pageCnt);
			resultObj.put("list", diaryListArr);
			
			request.setAttribute("result", resultObj);
		}
		// 다이어리 상세
		else if(cmd.equals("diaryDetail")) {
			EtcDto etc = new EtcDto();
			
			int dId = Integer.parseInt(request.getParameter("dId"));
			
			DiaryDetailDto diaryInfo = aDao.getDiaryDetail(dId);
			switch(diaryInfo.getUp_stat()) {
			case "R" :
				diaryInfo.setUp_stat("신고");
				break;
			case "N" :
				diaryInfo.setUp_stat("보류");
				break;
			case "D" :
				diaryInfo.setUp_stat("삭제");
				break;
			default :
				diaryInfo.setUp_stat("게시");
				break;				
			}
			
			diaryInfo.setWeather_text(new WeatherDto().getWeather()[diaryInfo.getWeather_id()]);
			
			if(diaryInfo.getDrone() != null)
				diaryInfo.setDrone(etc.getDrone().get(diaryInfo.getDrone()));
			
			if(diaryInfo.getPublic_tran() != null)
				diaryInfo.setPublic_tran(etc.getPublic_tran().get(diaryInfo.getPublic_tran()));
				
			if(diaryInfo.getPublic_info() != null)
				diaryInfo.setPublic_info(etc.getPublic_info().get(diaryInfo.getPublic_info()));
			
			if(diaryInfo.getPark() != null)
				diaryInfo.setPark(etc.getPark().get(diaryInfo.getPark()));
			
			if(diaryInfo.getPark_info() != null)
				diaryInfo.setPark_info(etc.getPark_info().get(diaryInfo.getPark_info()));
			
			if(diaryInfo.getToilet() != null)
				diaryInfo.setToilet(etc.getToilet().get(diaryInfo.getToilet()));
			
			if(diaryInfo.getShower() != null)
				diaryInfo.setShower(etc.getShower().get(diaryInfo.getShower()));
			
			if(diaryInfo.getLocker() != null)
				diaryInfo.setLocker(etc.getLocker().get(diaryInfo.getLocker()));
			
			int reportedCnt = aDao.getReportedCount(dId);
			ArrayList<ReportDto> reportList = aDao.getReport(dId);
			
			request.setAttribute("diaryDetail", diaryInfo);
			request.setAttribute("imgLength", diaryInfo.getImg().length);
			request.setAttribute("reportListSize", reportList.size());
			request.setAttribute("reportList", reportList);
			request.setAttribute("reportedCnt", reportedCnt);
		} 
		// 다이어리 상태 변경
		else if(cmd.equals("mdfyDiaryStat")) {
			String stat = request.getParameter("stat");
			int dId = Integer.parseInt(request.getParameter("dId"));
			
			int result = aDao.mdfyDiaryStat(dId, stat);
			if(result == 1)
				resultObj.put("update", "success");
			
			request.setAttribute("result", resultObj);
		}
		
		// End
		
		request.getRequestDispatcher("adminController?command=result&resultAct="+cmd).forward(request, response);
	}
}
