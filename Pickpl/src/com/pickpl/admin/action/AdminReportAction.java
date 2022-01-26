package com.pickpl.admin.action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.pickpl.admin.dao.AdminDao;
import com.pickpl.admin.dto.ReportDto;

public class AdminReportAction implements Action {
	AdminDao aDao = new AdminDao();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cmd = request.getParameter("command");

		JSONObject resultObj = new JSONObject();
		
		// 계정 신고 처리
		if(cmd.equals("mngMemberReport")) {
			int idx = Integer.parseInt(request.getParameter("idx"));
			String id = request.getParameter("id");
			String mngCon = request.getParameter("mngCon");
			String acctStat = request.getParameter("acctStat");
			
			// 신고 테이블 변경
			int reportR = aDao.updateReport(idx, mngCon);
			// 멤버 테이블 상태 변경
			int statR = aDao.mdfyAccStat(id, acctStat);
			
			if(reportR == 1 && statR == 1)
				resultObj.put("update", "success");
			
			request.setAttribute("result", resultObj);
		}
		// 글 신고 처리
		else if(cmd.equals("mngDiaryReport")) {
			int idx = Integer.parseInt(request.getParameter("idx"));
			int dId = Integer.parseInt(request.getParameter("dId"));
			String mngCon = request.getParameter("mngCon");
			String diaryStat = request.getParameter("upStat");
			
			// 신고 테이블 변경
			int reportR = aDao.updateReport(idx, mngCon);
			// 다이어리 테이블 상태 변경
			int statR = aDao.mdfyDiaryStat(dId, diaryStat);
			
			if(reportR == 1 && statR == 1)
				resultObj.put("update", "success");
			
			request.setAttribute("result", resultObj);
		}
		// 신고 목록
		else if(cmd.equals("reportList")) {
			int listToShow = 10;
			int pageIdx = 1;
			if(request.getParameter("pageIdx") != null) {
				pageIdx = Integer.parseInt(request.getParameter("pageIdx"));				
			}
			String stat = request.getParameter("stat");
			if(stat.equals("all")) {
				stat = "%";
			}
			String type = request.getParameter("type");
			if(type.equals("all")) {
				type = "%";
			}
			String condition = request.getParameter("condition");
			String keyword = request.getParameter("keyword");
			
			int total = aDao.getReportTotal(stat, type, condition, keyword);
			
			int pageCnt = (total / listToShow) + 1;
			if(total % listToShow == 0)
				pageCnt = total / listToShow;
			
			int startIdx = (pageIdx - 1) * listToShow + 1;
			int endIdx = startIdx + listToShow;
			if(pageIdx == pageCnt && total % listToShow != 0)
				endIdx = startIdx + (total % listToShow);
			
			JSONArray reportListArr = new JSONArray();
			ArrayList<ReportDto> reportList = aDao.getReportList(stat, type, condition, keyword, startIdx, endIdx);
			for(ReportDto dto : reportList) {
				JSONObject obj = new JSONObject();
				obj.put("idx", dto.getReportIdx());
				obj.put("report_date", dto.getReportDate());
				obj.put("report_id", dto.getReportId());
				obj.put("type", dto.getReportType());
				obj.put("target_id", dto.getTargetId());
				obj.put("target_d_id", dto.getTargetDId());
				obj.put("stat", dto.getStat());
				
				reportListArr.add(obj);
			}
			resultObj.put("total", total);
			resultObj.put("pageCnt", pageCnt);
			resultObj.put("list", reportListArr);
			
			request.setAttribute("result", resultObj);
		}
		// 신고 상세
		else if(cmd.equals("reportDetail")) {
			int idx = Integer.parseInt(request.getParameter("idx"));
			
			ReportDto detail = aDao.getReportDetail(idx);
			switch(detail.getReportType()) {
			case "U" :
				detail.setReportType("계정 신고");
				break;
			case "D" :
				detail.setReportType("글 신고");
				break;
			}
			
			switch(detail.getStat()) {
			case "N" :
				detail.setStat("신고 접수");
				break;
			case "Y" :
				detail.setStat("처리 완료");
				break;
			}
			
			request.setAttribute("detail", detail);
		}
//		// 신고 이력
//		else if(cmd.equals("reportHistory")) {
//			int listToShow = 10;
//			int pageIdx = Integer.parseInt(request.getParameter("pageIdx"));
//			
//			String condition = request.getParameter("condition");
//			String keyword = request.getParameter("keyword");
//			
//			int total = Integer.parseInt(request.getParameter("total"));
//			
//			int pageCnt = (total / listToShow) + 1;
//			if(total % listToShow == 0)
//				pageCnt = total / listToShow;
//			
//			int startIdx = (pageIdx - 1) * listToShow + 1;
//			int endIdx = startIdx + listToShow;
//			if(pageIdx == pageCnt && total % listToShow != 0)
//				endIdx = startIdx + (total % listToShow);
//			
//			JSONArray reportListArr = new JSONArray();
//			ArrayList<ReportDto> reportList = aDao.getReportList("%", "%", condition, keyword, startIdx, endIdx);
//			for(ReportDto dto : reportList) {
//				JSONObject obj = new JSONObject();
//				obj.put("idx", dto.getReportIdx());
//				obj.put("report_date", dto.getReportDate());
//				obj.put("report_id", dto.getReportId());
//				obj.put("type", dto.getReportType());
//				obj.put("target_id", dto.getTargetId());
//				obj.put("target_d_id", dto.getTargetDId());
//				obj.put("stat", dto.getStat());
//				
//				reportListArr.add(obj);
//			}
//			resultObj.put("total", total);
//			resultObj.put("pageCnt", pageCnt);
//			resultObj.put("list", reportListArr);
//			
//			request.setAttribute("result", resultObj);
//			
//		}
		
		request.getRequestDispatcher("adminController?command=result&resultAct="+cmd).forward(request, response);
	}

}
