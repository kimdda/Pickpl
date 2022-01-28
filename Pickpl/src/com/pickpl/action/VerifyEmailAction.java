package com.pickpl.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pickpl.dao.MemberDao;

public class VerifyEmailAction implements Action {
	MemberDao mDao = new MemberDao();
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String command = request.getParameter("command");
		String id = request.getParameter("id");
		String verifyNo = request.getParameter("verifyNo");
		
		
		if(mDao.checkVerifyNo(id, verifyNo)) {
			request.setAttribute("result", "verified");
			if(command.equals("verifyEmail")) {
				mDao.updateStat(id, "A");
			}
		}
		
		request.setAttribute("id", id);
		request.getRequestDispatcher("Controller?command=result&resultAct="+command).forward(request, response);				
	}
}
