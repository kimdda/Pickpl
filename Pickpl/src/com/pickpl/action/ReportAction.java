package com.pickpl.action;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import com.pickpl.dao.DiaryDao;
import com.pickpl.db.DbConn;
import com.pickpl.dto.ReportDto;

public class ReportAction implements Action {
	DiaryDao dDao = new DiaryDao();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String loginId = (String)session.getAttribute("loginId");
		
		JSONObject resultObj = new JSONObject();
		
		String targetId = request.getParameter("targetId");
		int dId = Integer.parseInt(request.getParameter("dId"));
		String reportType = request.getParameter("reportType");
		String reportCon = request.getParameter("reportCon");
		
		ReportDto report = new ReportDto(0, loginId, "", reportType, targetId, dId, reportCon, "", "");
		
		int reportR = dDao.report(report);
		int updateR = dDao.reportType(report);
		
		if(reportR == 1 && updateR == 1)
			resultObj.put("result", "success");
		
		request.setAttribute("result", resultObj);
		request.getRequestDispatcher("Controller?command=result&resultAct=report").forward(request, response);
	}

}
