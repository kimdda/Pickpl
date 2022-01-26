package com.pickpl.action;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pickpl.dao.MemberDao;
import com.pickpl.db.DbConn;

import mail.FindMail;

public class FindAction implements Action {
	MemberDao mDao = new MemberDao();
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Connection conn = DbConn.connect();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String toDo = request.getParameter("command");
		
		// 아이디 찾기
		if(toDo.equals("findId")) {
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			
			String id = mDao.findId(name, email);
			if(id != null) {
				request.setAttribute("result", "idFound");
				request.setAttribute("findId", id);
			} else {
				request.setAttribute("result", "NotFound");				
			}
		}
		
		// 비밀번호 찾기
		else if(toDo.equals("findPw")) {
			String id = request.getParameter("id");
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			
			if(mDao.findPw(id, name, email)) {
				String verifyNo = mDao.addPwLink(id);
				if(!verifyNo.equals("0")) {
					request.setAttribute("result", "pwFound");
					FindMail fm = new FindMail(email, verifyNo, id);
					fm.send();					
				}
			} else {
				request.setAttribute("result", "NotFound");
			}
		} // End
		
		request.getRequestDispatcher("Controller?command=result&resultAct="+toDo).forward(request, response);
	}
}
