package com.pickpl.admin.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.pickpl.admin.dao.AdminDao;
import com.pickpl.admin.dto.MemberDto;
import com.pickpl.admin.dto.ReportDto;

public class AdminMemberAction implements Action {
	AdminDao aDao = new AdminDao();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cmd = request.getParameter("command");
		JSONObject resultObj = new JSONObject();
		
		// 회원 목록
		if(cmd.equals("memberList")) {
			int listToShow = 10;
			int pageIdx = Integer.parseInt(request.getParameter("pageIdx"));
			String stat = request.getParameter("stat");
			if(stat.equals("all")) {
				stat = "%";
			}
			String condition = request.getParameter("condition");
			String keyword = request.getParameter("keyword");
			
			int total = aDao.getSearchMemberTotal(stat, condition, keyword);
			System.out.println("total " + total);
			
			int pageCnt = (total / listToShow) + 1;
			if(total % listToShow == 0)
				pageCnt = total / listToShow;
			
			int startIdx = (pageIdx - 1) * listToShow + 1;
			int endIdx = startIdx + listToShow;
			if(pageIdx == pageCnt && total % listToShow != 0)
				endIdx = startIdx + (total % listToShow);
			
			JSONArray memberListArr = new JSONArray();
			ArrayList<MemberDto> memberList = aDao.getMemberList(stat, condition, keyword, startIdx, endIdx);
			
			for(MemberDto dto : memberList) {
				JSONObject obj = new JSONObject();
				obj.put("id", dto.getId());
				obj.put("name", dto.getName());
				obj.put("gender", dto.getGender());
				obj.put("birth", dto.getBirth());
				obj.put("phone", dto.getPhone());
				obj.put("joinDate", dto.getJoinDate());
				obj.put("acctStat", dto.getAcctStat());
				
				memberListArr.add(obj);
			}
			
			resultObj.put("total", total);
			resultObj.put("pageCnt", pageCnt);
			resultObj.put("list", memberListArr);
			
			request.setAttribute("result", resultObj);
		}
		// 회원 상세
		else if(cmd.equals("memberDetail")) {
			String id = request.getParameter("id");
			MemberDto memberInfo = aDao.getMemberInfo(id);
			HashMap<String, Integer> actInfo = aDao.getMemberActInfo(id);
			int pickInfo = aDao.getMemberPickInfo(id);
			ArrayList<ReportDto> reportList = aDao.getReport(id);
			int reportedCnt = aDao.getReportedCount(id);
			
			request.setAttribute("memberInfo", memberInfo);
			request.setAttribute("actInfo", actInfo);
			request.setAttribute("pickInfo", pickInfo);
			request.setAttribute("reportList", reportList);
			request.setAttribute("reportListSize", reportList.size());
			request.setAttribute("reportedCnt", reportedCnt);
		}
		// 계정 상태 변경
		else if (cmd.equals("mdfyAccStat")) {
			String id = request.getParameter("id");
			String stat = request.getParameter("stat");
			
			String statText = "비활성화";
			if(stat.equals("A")) statText = "활성화";
			
			if(aDao.mdfyAccStat(id, stat) == 1) {
				resultObj.put("update", "success");
				resultObj.put("type", statText);
			}
			
			request.setAttribute("result", resultObj);
		}
		// 이메일 수정
		else if(cmd.equals("mdfyEmail")) {
			String id = request.getParameter("id");
			String newEmail = request.getParameter("newEmail");
			
			int result = aDao.mdfyMemberEmail(id, newEmail);
			if(result == 1) 
				resultObj.put("update", "success");
			
			request.setAttribute("result", resultObj);
		}
		request.getRequestDispatcher("adminController?command=result&resultAct=" + cmd).forward(request, response);
	}

}
