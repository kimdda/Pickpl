package com.pickpl.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

public class ResultAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String resultAct = request.getParameter("resultAct");
//		System.out.println("result " + resultAct + "start!");
		
		
		switch(resultAct) {
		case "main" :
			request.getRequestDispatcher("main.jsp").forward(request, response);
			break;
			
		case "join" :
			response.sendRedirect("join_comp.jsp");
			break;
		
		case "verifyEmail" :
			request.getRequestDispatcher("verifyEmail.jsp").forward(request, response);
			break;
		
		case "findId" :
		case "findPw" :
			request.getRequestDispatcher("find_comp.jsp").forward(request, response);
			break;
		
		case "verifyPwLink" :
			request.getRequestDispatcher("change_pw.jsp").forward(request, response);
			break;
		
		case "diaryDetail" :
			request.getRequestDispatcher("diary_detail.jsp").forward(request, response);
			break;
			
		case "writeDiary" :
		case "mdfyDiary" :
			response.sendRedirect("Controller?command=diaryPage");
			break;
			
		case "diaryPage" :
			request.getRequestDispatcher("diary.jsp").forward(request, response);
			break;
		
		case "pickFolderLoad" :
			request.getRequestDispatcher("pick_folder.jsp").forward(request, response);
			break;
			
		case "pickFolderDetail" :
			request.getRequestDispatcher("pick_detail.jsp").forward(request, response);
			break;
			
		case "myPage" :
			request.getRequestDispatcher("mypage.jsp").forward(request, response);
			break;
			
		case "mdfyMyinfo" :
//			request.getRequestDispatcher("Controller?command=myPage").forward(request, response);
			response.sendRedirect("tomypage.jsp");
			break;
		
		case "chatPage" :
			request.getRequestDispatcher("chat.jsp").forward(request, response);
			break;
		
		default :
			response.setContentType("applictaion/json;charset=UTF-8");
			PrintWriter out = response.getWriter();
//			System.out.println((JSONObject)request.getAttribute("result"));
			out.print((JSONObject)request.getAttribute("result"));
			break;
		}
	}

}
