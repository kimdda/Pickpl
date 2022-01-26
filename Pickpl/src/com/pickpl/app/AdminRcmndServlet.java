package com.pickpl.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.pickpl.admin.dto.RcmndDiaryDto;


@WebServlet("/AdminRcmndServlet")
public class AdminRcmndServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("applictaion/json");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String dbid = "pickpl";
		String dbpw = "1234";
		
		Connection conn = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, dbid, dbpw);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		String toDo = request.getParameter("command");
		
		// 추천 목록
		if(toDo.equals("fetch_list")) {
			int listToShow = 10;
			int pageIdx = Integer.parseInt(request.getParameter("pageIdx"));
			
//			ArrayList<RecommendVO> listRecom = new ArrayList<RecommendVO>();
			JSONObject resultObj = new JSONObject();
			JSONArray rcmndList = new JSONArray();
			
			StringBuffer sql = new StringBuffer();
			// 리스트 총 개수 받아오기
			sql.append("SELECT count(*) cnt FROM recommend");
			PreparedStatement pstmt;
			int total = 0;
			int pageCnt = 0;
			try {
				pstmt = conn.prepareStatement(sql.toString());
				ResultSet rs = pstmt.executeQuery();
				while(rs.next()) {
					total = rs.getInt("cnt");					
				}
				resultObj.put("total", total);
//				System.out.println("total : " +total);
				
				pageCnt = (total / listToShow) + 1;
				if(total % listToShow == 0)
					pageCnt = total / listToShow;
				resultObj.put("pageCnt", pageCnt);
//				System.out.println("pageCnt : " +pageCnt);
				
				rs.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			// 페이징된 리스트 받아오기
			sql.delete(0, sql.length());
			sql.append("SELECT * ");
			sql.append("FROM (SELECT rownum idx, l.* FROM (SELECT * FROM recommend ORDER BY reco_up_date) l) list ");
			sql.append("WHERE list.idx >= ? and list.idx < ?");
			int startIdx = (pageIdx - 1) * listToShow + 1;
			int endIdx = startIdx + listToShow;
			if(pageIdx == pageCnt && total % listToShow != 0)
				endIdx = startIdx + (total % listToShow);
			
//			System.out.println("pageIdx " + pageIdx);
			try {
				pstmt = conn.prepareStatement(sql.toString());
				pstmt.setInt(1, startIdx);
				pstmt.setInt(2, endIdx);
				ResultSet rs = pstmt.executeQuery();
				while(rs.next()) {
					JSONObject obj = new JSONObject();
//					System.out.println("obj create");
					int no = rs.getInt("reco_no");
					String title = rs.getString("reco_title");
					String up_date = rs.getString("reco_up_date");
					String open_date = rs.getString("reco_open_date");
					String close_date = rs.getString("reco_close_date");
//					String[] d_id = rs.getString("reco_d_id").split("_");
					JSONArray diaryArr = new JSONArray();
					for(String diary : rs.getString("reco_d_id").split("_")) {
						diaryArr.add(diary);
					}
					int d_count = rs.getInt("reco_d_count");
					String stat = rs.getString("reco_hold");
					
//					listRecom.add(new RecommendVO(no, title, up_date, open_date, close_date, d_id, d_count));
					obj.put("no", no);
					obj.put("title", title);
					obj.put("upDate", up_date);
					obj.put("openDate", open_date);
					obj.put("closeDate", close_date);
					obj.put("diary", diaryArr.toJSONString());
					obj.put("stat", stat);
					obj.put("dCount", d_count);	
					
					rcmndList.add(obj);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			resultObj.put("list", rcmndList);
			out.print(resultObj);
		}
		
		// 추천글 관리
		else if(toDo.equals("mngRcmnd")) {
			
			RequestDispatcher rd = request.getRequestDispatcher("admin/mng_new_rcmnd.jsp");
			int no = Integer.parseInt(request.getParameter("no"));
			String sql = "SELECT * FROM recommend WHERE reco_no=?";
			try {
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, no);
				ResultSet rs = pstmt.executeQuery();
				rs.next();
				request.setAttribute("no", rs.getInt("reco_no"));
				request.setAttribute("title", rs.getString("reco_title"));
				String openDate = rs.getString("reco_open_date");
				request.setAttribute("openDate", openDate.substring(0, openDate.indexOf(" ")));
				String closeDate = rs.getString("reco_close_date");
				request.setAttribute("closeDate", closeDate.substring(0, closeDate.indexOf(" ")));
				String[] reco_d_id = (rs.getString("reco_d_id")).split("_");
//				rs.close();
				
				ArrayList<RcmndDiaryDto> diaryArrList = new ArrayList<RcmndDiaryDto>();
				
				StringBuffer getDiary = new StringBuffer();
				for(String diary : reco_d_id) {
//					System.out.println(Integer.parseInt(diary));
					getDiary.delete(0, getDiary.length());
					getDiary.append("SELECT l.d_id, l.writer_id, l.place_name, l.visit_date, ");
					getDiary.append("l.pick_count, l.view_count, m.do, m.gu ");
					getDiary.append("FROM view_list l, map m WHERE l.d_id = m.map_d_id and l.d_id=?");
					pstmt = conn.prepareStatement(getDiary.toString());
					pstmt.setInt(1, Integer.parseInt(diary));
					ResultSet diaryRs = pstmt.executeQuery();
					while(diaryRs.next()) {
						int diary_id = diaryRs.getInt("d_id");
						String writer = diaryRs.getString("writer_id");
						String placeName = diaryRs.getString("place_name");
						String visitDate = diaryRs.getString("visit_date");
//						visitDate = visitDate.substring(0, visitDate.indexOf(" "));
						int pickCount = diaryRs.getInt("pick_count");
						int viewCount = diaryRs.getInt("view_count");
						String region = diaryRs.getString("do") + " " + diaryRs.getString("gu");
						
						diaryArrList.add(new RcmndDiaryDto(diary_id, writer, pickCount, viewCount, visitDate, placeName, region));
					}
				}
				request.setAttribute("diary", rs.getString("reco_d_id"));
				request.setAttribute("diaryArr", diaryArrList);
				request.setAttribute("cnt", rs.getInt("reco_d_count"));
				request.setAttribute("stat", rs.getString("reco_hold"));
				if(rs.getString("reco_hold") == null)
					request.setAttribute("statText", "");
				else
					request.setAttribute("statText", "게시 보류");
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			rd.forward(request, response);
		}
		
		// 다이어리 목록 검색
		else if(toDo.equals("search_diary")) {
			JSONObject resultObj = new JSONObject();
			JSONArray diaryListArr = new JSONArray();
			
			int total = 0;
			int pageCnt = 0;
			int listToShow = 10;
			int pageIdx = Integer.parseInt(request.getParameter("pageIdx"));
			
			String month = request.getParameter("month");
			if(month.equals("all"))
				month = "";
			String region = request.getParameter("region");
			if(region.equals("all"))
				region = "";
			String city = request.getParameter("city");
				if(city.equals("all"))
					city = "";
			
			String sql = "SELECT count(*) cnt FROM view_list WHERE address like ? and visit_date like ?";
			try {
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, "%" + region + " " + city + "%");
				pstmt.setString(2, "____-%"+month+"-__%");
				ResultSet rs = pstmt.executeQuery();
				rs.next();
				total = rs.getInt("cnt");
				System.out.println("total : " + total);
				resultObj.put("total", total);
//				System.out.println("total : " +total);
				
				pageCnt = (total / listToShow) + 1;
				if(total % listToShow == 0)
					pageCnt = total / listToShow;
				resultObj.put("pageCnt", pageCnt);
//				System.out.println("pageCnt : " +pageCnt);
				
			} catch(SQLException e) {
				e.printStackTrace();
			}
			
			StringBuffer sbSql = new StringBuffer();
			sbSql.append("SELECT * FROM (SELECT rownum idx, ld.* ");
			sbSql.append("FROM (SELECT * FROM view_list l, map m ");
			sbSql.append("WHERE l.d_id = m.map_d_id and address like ? and visit_date like ?) ld) list ");
			sbSql.append("WHERE list.idx >= ? and list.idx < ?");
			
			int startIdx = (pageIdx - 1) * listToShow + 1;
			int endIdx = startIdx + listToShow;
			if(pageIdx == pageCnt && total % listToShow != 0)
				endIdx = startIdx + (total % listToShow);
			
			try {
				PreparedStatement pstmt = conn.prepareStatement(sbSql.toString());
				pstmt.setString(1, "%" + region + " " + city + "%");
				pstmt.setString(2, "____-%" + month + "-__%");
				pstmt.setInt(3, startIdx);
				pstmt.setInt(4, endIdx);
				
				ResultSet rs = pstmt.executeQuery();
				while(rs.next()) {
					JSONObject diaryObj = new JSONObject();
					diaryObj.put("dId", rs.getInt("d_id"));
					diaryObj.put("writer", rs.getString("writer_id"));
					diaryObj.put("pick_count", rs.getInt("pick_count"));
					diaryObj.put("view_count", rs.getInt("view_count"));
					diaryObj.put("visit_date", rs.getString("visit_date"));
					diaryObj.put("place_name", rs.getString("place_name"));
					diaryObj.put("region", rs.getString("do") + " " + rs.getString("gu"));
					
					diaryListArr.add(diaryObj);
				}
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			resultObj.put("list", diaryListArr);
			out.print(resultObj);
		} // End
		
		// 수정
		else if(toDo.equals("update_rcmnd")) {
			RequestDispatcher rd = request.getRequestDispatcher("admin/mng_rcmnd.jsp");
			int no = Integer.parseInt(request.getParameter("no"));
			String title = request.getParameter("title");
			String openDate = request.getParameter("open_date");
			String closeDate = request.getParameter("close_date");
			String dId = request.getParameter("d_id");
			int count = Integer.parseInt(request.getParameter("count"));
			String stat = request.getParameter("stat");
			if(stat.equals("")) {
				stat = null;
			}
			
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE recommend SET reco_title=?, reco_open_date = to_date(?, 'YYYY-MM-DD'), ");
			sql.append("reco_close_date = to_date(?, 'YYYY-MM-DD'), reco_d_id = ?, reco_d_count = ?, reco_hold = ? WHERE reco_no=?");
			
			try {
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());
				pstmt.setString(1, title);
				pstmt.setString(2, openDate);
				pstmt.setString(3, closeDate);
				pstmt.setString(4, dId);
				pstmt.setInt(5, count);
				pstmt.setString(6, stat);				
				pstmt.setInt(7, no);
				
				int r = pstmt.executeUpdate();
				if(r == 1)
					request.setAttribute("update", "success");
				rd.forward(request, response);
//				response.sendRedirect("admin/mng_rcmnd.jsp");
					
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} // End
		
		// 등록
		else if(toDo.equals("new_rcmnd")) {
			RequestDispatcher rd = request.getRequestDispatcher("admin/mng_rcmnd.jsp");
			String title = request.getParameter("title");
			String openDate = request.getParameter("open_date");
			String closeDate = request.getParameter("close_date");
			String dId = request.getParameter("d_id");
			int count = Integer.parseInt(request.getParameter("count"));
			
//			System.out.println(title + " / " + openDate + 
//					" / " + closeDate + " / " + dId + " / " + count);
			
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO recommend(reco_no, reco_title, reco_open_date, ");
			sql.append("reco_close_date, reco_d_id, reco_d_count) ");
			sql.append("VALUES (recommend_seq.nextval, ?, to_date(?, 'YYYY-MM-DD'), to_date(?, 'YYYY-MM-DD'), ?, ?)");
			try {
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());
				pstmt.setString(1, title);
				pstmt.setString(2, openDate);
				pstmt.setString(3, closeDate);
				pstmt.setString(4, dId);
				pstmt.setInt(5, count);
				int r = pstmt.executeUpdate();
				if(r == 1)
					request.setAttribute("insert", "success");
				rd.forward(request, response);	
//				response.sendRedirect("admin/mng_rcmnd.jsp");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} // End
		// 삭제
		else if(toDo.equals("delRcmnd")) {
			RequestDispatcher rd = request.getRequestDispatcher("admin/mng_rcmnd.jsp");
			int no = Integer.parseInt(request.getParameter("no"));
			String sql = "DELETE FROM recommend WHERE reco_no = ?";
			try {
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, no);
				int r = pstmt.executeUpdate();
				if(r == 1)
					request.setAttribute("delete", "success");
				rd.forward(request, response);				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} // End
		
	}

}
