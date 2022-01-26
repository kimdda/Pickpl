package com.pickpl.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.pickpl.dao.DiaryDao;
import com.pickpl.dto.DiaryAllDto;

public class DiaryWriteAction implements Action {
	DiaryDao dDao = new DiaryDao();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String loginId = (String) session.getAttribute("loginId");
		
		String cmd = request.getParameter("command");
		
		String path = request.getRealPath("img/diary");
	    
	    // upload �뤃�뜑媛� �뾾�쑝硫� 留뚮벀. --------------------------
//	    File filePath = new File(path);
//	    if(filePath.exists()==false) {
//	    	filePath.mkdirs();
//	    }
	    // ------------------------------------------------
		
		int sizeLimit = 10*1024*1024;		//10MB �젣�븳
		
		MultipartRequest multi = new MultipartRequest(request, path, sizeLimit, "UTF-8",
	                new DefaultFileRenamePolicy());
		
		ArrayList<String> newImgList = new ArrayList<String>();
		ArrayList<String> uploadedImgList = new ArrayList<String>();
		
		Enumeration files = multi.getFileNames();
		while (files.hasMoreElements()) {
		    String param = (String)files.nextElement();
		    newImgList.add(multi.getFilesystemName(param));
		}
		
		String uploaded1 = multi.getParameter("uploaded_img0");
		uploadedImgList.add(uploaded1);
		String uploaded2 = multi.getParameter("uploaded_img1");
		uploadedImgList.add(uploaded2);
		String uploaded3 = multi.getParameter("uploaded_img2");
		uploadedImgList.add(uploaded3);
		String uploaded4 = multi.getParameter("uploaded_img3");
		uploadedImgList.add(uploaded4);
		String uploaded5 = multi.getParameter("uploaded_img4");
		uploadedImgList.add(uploaded5);
		
		String place_name = multi.getParameter("placeName");
		String address = multi.getParameter("address");
		String visit_date = multi.getParameter("visitDate");
		int visit_time = Integer.parseInt(multi.getParameter("visitTime"));
		int weather_id = Integer.parseInt(multi.getParameter("weather_id"));
		String contents = multi.getParameter("contents");
		String drone = multi.getParameter("drone");
		String public_tran = multi.getParameter("public_tran");
		String public_info = multi.getParameter("public_info");
		String park = multi.getParameter("park");
		String park_info = multi.getParameter("park_info");
		String toilet = multi.getParameter("toilet");
		String locker = multi.getParameter("locker");
		String shower = multi.getParameter("shower");
		String lat = multi.getParameter("lat");
		String lng = multi.getParameter("lng");
		
		String img = "";
		for(String i : uploadedImgList) {
			if(i != null) {
				img += i+"_";
			}
		}
		for(String i : newImgList) {
			if(i != null) {
				img += i+"_";				
			}
		}
		
		DiaryAllDto diary = new DiaryAllDto(0, loginId, img, place_name, address, visit_date, 
				visit_time, weather_id, contents, drone, public_tran, public_info, park, park_info, 
				toilet, shower, locker, lat, lng, (address.split(" "))[0], (address.split(" "))[1]);
		
		// 湲� �닔�젙
		if(cmd.equals("mdfyDiary")) {
			int dId = Integer.parseInt(multi.getParameter("dId"));
			diary.setD_id(dId);
			
			int infoR = dDao.mdfyDiaryInfo(dId);
			int diaryR = dDao.mdfyDiary(diary);
			int mapR = dDao.mdfyMap(diary);
			
			if(diaryR ==1 && infoR == 1 && mapR == 1) {
				session.setAttribute("mdfyDiary", "success");
			} else {
				session.setAttribute("mdfyDiary", "fail");
			}
		}
		// 湲� �옉�꽦
		else if(cmd.equals("writeDiary")) {
			int infoR = dDao.insertDiaryInfo(diary);
			
			int dId = dDao.getLastDId(loginId);
			diary.setD_id(dId);
			
			int diaryR = dDao.insertDiary(diary);
			int mapR = dDao.insertMap(diary);
			
			if(infoR == 1 && diaryR == 1 && mapR == 1) {
				session.setAttribute("diaryWrite", "success");
			} else {
				session.setAttribute("diaryWrite", "fail");
			}
		}
		request.getRequestDispatcher("Controller?command=result&resultAct=" + cmd).forward(request, response);
	}

}
