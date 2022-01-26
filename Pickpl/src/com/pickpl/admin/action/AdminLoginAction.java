package com.pickpl.admin.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.pickpl.admin.dao.AdminDao;

public class AdminLoginAction implements Action {
	AdminDao aDao = new AdminDao();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		
		JSONObject resultObj = new JSONObject();
		
		int result = aDao.login(id, pw);
		
		if(result == 1)
			resultObj.put("login", "OK");
		
		request.setAttribute("result", resultObj);
		request.getRequestDispatcher("Controller?command=result&resultAct=login").forward(request, response);
	}

}
