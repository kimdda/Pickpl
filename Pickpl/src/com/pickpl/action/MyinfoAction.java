package com.pickpl.action;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import com.pickpl.dao.MemberDao;
import com.pickpl.db.DbConn;
import com.pickpl.dto.MemberDto;

public class MyinfoAction implements Action {
	MemberDao mDao = new MemberDao();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String loginId = (String) session.getAttribute("loginId");
		
		String cmd = request.getParameter("command");
		
		JSONObject resultObj = new JSONObject();
		
		// 내 정보 불러오기
		if(cmd.equals("myPage")) {
			MemberDto myInfo = mDao.getMyInfo(loginId);
			request.setAttribute("myInfo", myInfo);		
		}
		
//		request.setAttribute("result", resultObj);
		request.getRequestDispatcher("Controller?command=result&resultAct=myPage").forward(request, response);
	}
}
