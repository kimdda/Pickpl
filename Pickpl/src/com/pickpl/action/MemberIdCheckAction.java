package com.pickpl.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.pickpl.dao.MemberDao;

public class MemberIdCheckAction implements Action {
	MemberDao mDao = new MemberDao();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("applictaion/json");
		JSONObject resultObj = new JSONObject();
		
		String id = request.getParameter("id");
		
		if(mDao.memberIdCheck(id)) 
			resultObj.put("id_match", "no");
		
		
		request.setAttribute("result", resultObj);
		request.getRequestDispatcher("Controller?command=result&resultAct=idCheck").forward(request, response);
	}
}
