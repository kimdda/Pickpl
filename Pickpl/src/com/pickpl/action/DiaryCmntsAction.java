package com.pickpl.action;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.pickpl.dao.DiaryDao;
import com.pickpl.db.DbConn;
import com.pickpl.dto.CmntsDto;

public class DiaryCmntsAction implements Action {
	DiaryDao dDao = new DiaryDao();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String loginId = (String)session.getAttribute("loginId");
		
		String cmd = request.getParameter("command");

		JSONObject resultObj = new JSONObject();
		
		// 댓글 불러오기
		if(cmd.equals("getCmnts")) {
			int dId = Integer.parseInt(request.getParameter("dId"));
			ArrayList<CmntsDto> cmntsList = dDao.getCmnts(dId);
			
			JSONArray cmntListArr = new JSONArray();
			for(CmntsDto dto : cmntsList) {
				JSONObject obj = new JSONObject();
				obj.put("idx", dto.getIdx());
				obj.put("id", dto.getId());
				obj.put("date", dto.getDate());
				obj.put("contents", dto.getContents());
				
				cmntListArr.add(obj);
			}
			
			resultObj.put("loginId", loginId);
			resultObj.put("cmntsList", cmntListArr);
			request.setAttribute("result", resultObj);
		}		
		// 댓글 작성
		else if(cmd.equals("cmntWrite")) {
			int dId = Integer.parseInt(request.getParameter("dId"));
			String contents = request.getParameter("contents");
			
			int result = dDao.writeCmnt(new CmntsDto(0, dId, loginId, contents, "", ""));
			if(result == 1) {
				resultObj.put("result", "success");
			}
			resultObj.put("idx", dDao.getLastCmntIdx(loginId));
			request.setAttribute("result", resultObj);
		}
		// 댓글 삭제
		else if(cmd.equals("deleteMyCmnt")) {
			int idx = Integer.parseInt(request.getParameter("idx"));
			
			int r = dDao.deleteMyCmnt(idx);
			if(r == 1) {
				resultObj.put("result", "success");
			}
			request.setAttribute("result", resultObj);
		}
		// End
		
		request.getRequestDispatcher("Controller?command=result&resultAct=" + cmd).forward(request, response);
	}

}
