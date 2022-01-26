package com.pickpl.admin.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

public class ResultAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String cmd = request.getParameter("resultAct");
		System.out.println("result " + cmd + " start");
		
		switch(cmd) {
		case "diaryDetail" :
			request.getRequestDispatcher("admin/mng_diary_detail.jsp").forward(request, response);
			break;
		
		case "memberDetail" :
			request.getRequestDispatcher("admin/mng_member_detail.jsp").forward(request, response);
			break;
		
		case "mngRcmnd" :
			request.getRequestDispatcher("admin/mng_new_rcmnd.jsp").forward(request, response);
			break;
			
		case "writeRcmnd" :
		case "updateRcmnd" :
		case "deleteRcmnd" :
			request.getRequestDispatcher("admin/mng_rcmnd.jsp").forward(request, response);
			break;
		
		case "reportDetail" :
			request.getRequestDispatcher("admin/mng_report_detail.jsp").forward(request, response);
			break;
		
		default :
			response.setContentType("applictaion/json;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print((JSONObject)request.getAttribute("result"));
			break;
		}
	}
}
