package com.pickpl.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.pickpl.dao.MemberDao;

public class ChangePwAction implements Action {
	MemberDao mDao = new MemberDao();
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 비밀번호 변경
		JSONObject resultObj = new JSONObject();
		
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		
		if(mDao.updatePw(id, pw) == 1)
			resultObj.put("result", "success");
		
		request.setAttribute("result", resultObj);
			
		request.getRequestDispatcher("Controller?command=result&resultAct=changePw").forward(request, response);
	}
}
