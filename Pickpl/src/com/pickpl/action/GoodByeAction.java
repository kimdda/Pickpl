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

import org.json.simple.JSONObject;

import com.pickpl.dao.DiaryDao;
import com.pickpl.dao.MemberDao;
import com.pickpl.db.DbConn;

public class GoodByeAction implements Action {
	MemberDao mDao = new MemberDao();
	DiaryDao dDao = new DiaryDao();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Connection conn = DbConn.connect();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		HttpSession session = request.getSession();
		String loginId = (String) session.getAttribute("loginId");
		
		JSONObject resultObj = new JSONObject();
		
		
		// 삭제할 다이어리 글번호 받아와서
		ArrayList<Integer> diaryList = mDao.getByeDiary(loginId);
		for(int diary : diaryList) {
			dDao.deleteDiary(diary);	// diary table 삭제
			dDao.deleteMap(diary);		// map table 삭제
			dDao.updateComments(diary);	// diary에 대한 cmnt 전체 삭제
			dDao.updateDiaryInfo(diary);	// diary_info 업데이트
			dDao.byePickFolderList(diary);	// pick table 글 번호 픽한 폴더에서 번호 삭제
		}

		// 내가 쓴 댓글 삭제
		mDao.byeCmnts(loginId);
		
		// 내 픽 폴더 삭제
		mDao.byePick(loginId);
		
		// 최근 본 글 삭제
		mDao.byeRecent(loginId);
		
		// 멤버테이블 업데이트
		mDao.byeMember(loginId);
		
		resultObj.put("result", "farewell");
		
		session.invalidate();
		request.setAttribute("result", resultObj);
		request.getRequestDispatcher("Controller?command=result&resultAct=goodbyePickpl").forward(request, response);
	}

}
