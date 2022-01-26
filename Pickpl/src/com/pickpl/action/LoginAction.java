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

public class LoginAction implements Action {
	MemberDao mDao = new MemberDao();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		JSONObject resultObj = new JSONObject();
		
		String toDo = request.getParameter("command");
		
		if(toDo.equals("login")) {
			String loginId = request.getParameter("id");
			String loginPw = request.getParameter("pw");
			String accStat = null;
			
//			System.out.println(loginId + " / " + loginPw);
			if(mDao.loginCheck(loginId, loginPw)) {
				accStat = mDao.checkAccStat(loginId);
				resultObj.put("login_check", accStat);
				session.setAttribute("loginId", loginId);
			} else {
				resultObj.put("login_check", "fail");				
			}
			
			request.setAttribute("result", resultObj);
			request.getRequestDispatcher("Controller?command=result&resultAct=login").forward(request, response);
		}
		
		// 로그인 상태 체크
		else if(toDo.equals("loginCheck")) {
			System.out.println((String)session.getAttribute("loginId"));
			    //세션 객체 만들기
			if(session.getAttribute("loginId") == null) {
				resultObj.put("result", "logout");
			} else {
				resultObj.put("loginId", (String)session.getAttribute("loginId"));
				resultObj.put("result", "login");
			}
			request.setAttribute("result", resultObj);
			request.getRequestDispatcher("Controller?command=result&resultAct=loginCheck").forward(request, response);
		} // End
		
	}

}
