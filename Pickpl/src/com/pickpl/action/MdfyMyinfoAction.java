package com.pickpl.action;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.pickpl.dao.MemberDao;
import com.pickpl.db.DbConn;
import com.pickpl.dto.MemberDto;

public class MdfyMyinfoAction implements Action {
	MemberDao mDao = new MemberDao();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Connection conn = DbConn.connect();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		HttpSession session = request.getSession();
		String loginId = (String) session.getAttribute("loginId");
		
		
		String path = request.getRealPath("img/profile");
//	    System.out.println("real path : " + path);
	    
	    // upload 폴더가 없으면 만듦. --------------------------
//	    File filePath = new File(path);
//	    if(filePath.exists()==false) {
//	    	filePath.mkdirs();
//	    }
	    // ------------------------------------------------
		
		int sizeLimit = 10*1024*1024;		//10MB 제한
		
		MultipartRequest multi = new MultipartRequest(request, path, sizeLimit, "UTF-8",
	                new DefaultFileRenamePolicy());
		
		Enumeration files = multi.getFileNames();
		String fileObject = (String)(files.nextElement());
		String filename = multi.getFilesystemName(fileObject); //실제 upload된 파일명 받아오기
		
		String name = multi.getParameter("name");
		String phone = multi.getParameter("phone");
		String birth = multi.getParameter("birthY") + "-" + multi.getParameter("birthM") 
					+ "-" + multi.getParameter("birthD");
		String gender = multi.getParameter("gender");
		String cf_mail = multi.getParameter("cf_mail");
		
		System.out.println("file : " + filename);
		System.out.println("name : " + name);
		System.out.println("phone : " + phone);		
		System.out.println("birth : " + birth);		
		System.out.println("gender : " + gender);		
		System.out.println("cf_mail : " + cf_mail);
		
		MemberDto mdfyInfo = new MemberDto(loginId, "", filename, "", name, phone, birth, gender, cf_mail);
		
		int r = mDao.updateMyInfo(mdfyInfo);
		
		if(r == 1) {
			session.setAttribute("mdfyMyinfo", "success");
		} else {
			session.setAttribute("mdfyMyinfo", "fail");
		}
		
		request.getRequestDispatcher("Controller?command=result&resultAct=mdfyMyinfo").forward(request, response);
		
		
	}

}
