package com.pickpl.action;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pickpl.dao.MemberDao;
import com.pickpl.db.DbConn;
import com.pickpl.dto.MemberDto;

import mail.JoinMail;

public class MemberJoinAction implements Action {
	MemberDao mDao = new MemberDao();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Connection conn = DbConn.connect();
		PreparedStatement pstmt = null;
		
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String name = request.getParameter("name");
		String phone = request.getParameter("phone1") + request.getParameter("phone2") + request.getParameter("phone3");
		String email = request.getParameter("email");
		String birth = request.getParameter("birthY") + "-" + request.getParameter("birthM") + "-" + request.getParameter("birthD");
		String gender = request.getParameter("gender");
		String cf_mail = request.getParameter("cf_mail");
		if(cf_mail == null)
			cf_mail = "N";
		
		String verifyNo = mDao.generateNo();

		int joinResutl = mDao.memberJoin(new MemberDto(id, pw, "", email, name, phone, birth, gender, cf_mail), verifyNo);
		int pickFolderR = mDao.createPickFolder(id);
		
		if(joinResutl == 1) {
			JoinMail jm = new JoinMail(email, verifyNo, id);
			jm.send();
			request.setAttribute("result", "success");
		} else {
			request.setAttribute("result", "fail");
		}
		
		request.getRequestDispatcher("Controller?command=result&resultAct=join").forward(request, response);;
	}

}
