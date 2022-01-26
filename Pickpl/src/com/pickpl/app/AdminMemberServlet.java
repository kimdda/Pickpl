package com.pickpl.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.pickpl.admin.dto.ReportDto;

@WebServlet("/AdminMemberServlet")
public class AdminMemberServlet extends HttpServlet {
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
		System.out.println("todo : " + toDo);
		
		// 검색 결과
		if(toDo.equals("fetch_list")) {
			int listToShow = 10;
			int pageIdx = Integer.parseInt(request.getParameter("pageIdx"));
			String stat = request.getParameter("stat");
			String condition = request.getParameter("condition");
			String keyword = request.getParameter("keyword");
			if(stat.equals("all")) {
				stat = "%";
			}
			
			JSONObject resultObj = new JSONObject();
			JSONArray listArr = new JSONArray();
			
			// 검색 키워드가 없을 때
			if(keyword == "") {
				String sql = "SELECT count(*) cnt FROM member WHERE acc_stat like ?";
				try {
					PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, stat);
					ResultSet rs = pstmt.executeQuery();
					rs.next();
					int total = rs.getInt("cnt");
					resultObj.put("total", total);
					
					int pageCnt = (total / listToShow) + 1;
					if(total % listToShow == 0)
						pageCnt = total / listToShow;
					resultObj.put("pageCnt", pageCnt);
					rs.close();
					
					sql = "SELECT * FROM (SELECT rownum idx, l.* "
						+ "FROM (SELECT * FROM member WHERE acc_stat like ? ORDER BY join_date DESC) l) list "
						+ "WHERE list.idx >= ? and list.idx < ?";
					
					int startIdx = (pageIdx - 1) * listToShow + 1;
					int endIdx = startIdx + listToShow;
					if(pageIdx == pageCnt && total % listToShow != 0)
						endIdx = startIdx + (total % listToShow);
					
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, stat);
					pstmt.setInt(2, startIdx);
					pstmt.setInt(3, endIdx);
					rs = pstmt.executeQuery();
					while(rs.next()) {
						JSONObject listObj = new JSONObject();
						listObj.put("id", rs.getString("id"));
						listObj.put("name", rs.getString("name"));
						listObj.put("gender", rs.getString("gender"));
						listObj.put("birth", rs.getString("birth"));
						listObj.put("phone", rs.getString("phone"));
						listObj.put("joinDate", rs.getString("join_date"));
						listObj.put("acctStat", rs.getString("acc_stat"));
						
						listArr.add(listObj);
					}
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				resultObj.put("list", listArr);
				out.print(resultObj);
			}
			// 검색 키워드 있을 때
			else {
				StringBuffer sql = new StringBuffer();
				sql.append("SELECT count(*) cnt FROM member WHERE ");
				sql.append(condition);
				sql.append(" = ? and acc_stat like ?");
				
				try {
					PreparedStatement pstmt = conn.prepareStatement(sql.toString());
					pstmt.setString(1, keyword);
					pstmt.setString(2, stat);
					ResultSet rs = pstmt.executeQuery();
					rs.next();
					int total = rs.getInt("cnt");
					resultObj.put("total", total);
					
					int pageCnt = (total / listToShow) + 1;
					if(total % listToShow == 0)
						pageCnt = total / listToShow;
					resultObj.put("pageCnt", pageCnt);
					rs.close();
					
					sql.delete(0, sql.length());
					sql.append("SELECT * FROM (SELECT rownum idx, l.* ");
					sql.append("FROM (SELECT * FROM member WHERE acc_stat like ? and ");
					sql.append(condition);
					sql.append("= ? ORDER BY join_date DESC) l) list ");
					sql.append("WHERE list.idx >= ? and list.idx < ?");
					
					int startIdx = (pageIdx - 1) * listToShow + 1;
					int endIdx = startIdx + listToShow;
					if(pageIdx == pageCnt && total % listToShow != 0)
						endIdx = startIdx + (total % listToShow);
					
					pstmt = conn.prepareStatement(sql.toString());
					pstmt.setString(1, stat);
					pstmt.setString(2, keyword);
					pstmt.setInt(3, startIdx);
					pstmt.setInt(4, endIdx);
					rs = pstmt.executeQuery();
					while(rs.next()) {
						JSONObject listObj = new JSONObject();
						listObj.put("id", rs.getString("id"));
						listObj.put("name", rs.getString("name"));
						listObj.put("gender", rs.getString("gender"));
						listObj.put("birth", rs.getString("birth"));
						listObj.put("phone", rs.getString("phone"));
						listObj.put("joinDate", rs.getString("join_date"));
						listObj.put("acctStat", rs.getString("acc_stat"));
						
						listArr.add(listObj);
					}
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				resultObj.put("list", listArr);
				out.print(resultObj);
			}
		} // End
		// 상세보기
		else if(toDo.equals("viewDetail")) {
			RequestDispatcher rd = request.getRequestDispatcher("/admin/mng_member_detail.jsp");
			String id = request.getParameter("id");
			String sql = "SELECT * FROM member WHERE id=?";
			try {
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, id);
				ResultSet rs = pstmt.executeQuery();
				rs.next();
				request.setAttribute("id", rs.getString("id"));
				request.setAttribute("name", rs.getString("name"));
				String gender = "여";
				if(rs.getString("gender").equals("M"))
					gender = "남";
				request.setAttribute("gender", gender);
				
				String acc_stat = "활성";
				if(rs.getString("acc_stat").equals("D"))
					acc_stat = "이메일 미인증";
				else if(rs.getString("acc_stat").equals("O"))
					acc_stat = "탈퇴";
				else if(rs.getString("acc_stat").equals("R"))
					acc_stat = "신고";
				else if(rs.getString("acc_stat").equals("B"))
					acc_stat = "비활성";
				request.setAttribute("acc_stat", acc_stat);
				
				request.setAttribute("phone", rs.getString("phone"));
				request.setAttribute("email", rs.getString("email"));
				String birth = rs.getString("birth");
				birth = birth.substring(0, birth.indexOf(" "));
				request.setAttribute("birth", birth);
				
				String cf_mail = "동의";
				if(rs.getString("cf_mail").equals("N")) cf_mail = "비동의";
				request.setAttribute("cf_mail", cf_mail);
				rs.close();
				
				sql = "SELECT * FROM diary d, diary_info i WHERE d.diary_id = i.d_id and i.writer_id = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, id);
				rs = pstmt.executeQuery();
				int up_count = 0;
				int del_count = 0;
				int picked_count = 0;
				while(rs.next()) {
					up_count++;
					picked_count += rs.getInt("pick_count");
					if(rs.getString("up_stat").equals("D"))
						del_count++;
				}
				rs.close();
				
				sql = "SELECT * FROM pick WHERE pick_id = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, id);
				rs = pstmt.executeQuery();
				int myPick_count = 0;
				while(rs.next()) {
					myPick_count += rs.getInt("folder_d_count");
				}
				request.setAttribute("myPick_count", myPick_count);
				rs.close();
				
				request.setAttribute("picked_count", picked_count);
				request.setAttribute("up_count", up_count);
				request.setAttribute("del_count", del_count);
				
				ArrayList<ReportDto> reportList = new ArrayList<ReportDto>();
				sql = "SELECT * FROM report WHERE target_id = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, id);
				rs = pstmt.executeQuery();
				int reported_count = 0;
				while(rs.next()) {
					reported_count++;
					if(rs.getString("report_stat").equals("N") && rs.getString("report_type").equals("U")) {
						int idx = rs.getInt("report_idx");
						String reportId = rs.getString("report_id");
						String reportDate = rs.getString("report_date");
						String reportType = rs.getString("report_type");
						String targetId = rs.getString("target_id");
						String targetDId = rs.getString("target_d_id");
						String contents = rs.getString("report_contents");
						String mngContents = rs.getString("report_mng_contents");
						
						reportList.add(new ReportDto(idx, reportId, reportDate, reportType, targetId, targetDId, contents, mngContents, ""));
					}
				}
				request.setAttribute("reportedCnt", reported_count);
				request.setAttribute("reportListCnt", reportList.size());
				request.setAttribute("reportList", reportList);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			rd.forward(request, response);
		} // End
		
		// 계정 상태 변경
		else if(toDo.equals("mdfyAccStat")) {
			JSONObject resultObj = new JSONObject();
			String id = request.getParameter("id");
			String toStat = request.getParameter("toStat");
			System.out.println(id + " / " + toStat);
			String sql = "UPDATE member SET acc_stat=? WHERE id=?";
			try {
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, toStat);
				pstmt.setString(2, id);
				int r = pstmt.executeUpdate();
				String statText = "비활성화";
				if(toStat.equals("A")) statText = "활성화";
				if(r == 1) {
					resultObj.put("update", "OK");
					resultObj.put("type", statText);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			out.print(resultObj);
		} // End
		// 신고 관리
		else if(toDo.equals("mngReport")) {
			JSONObject resultObj = new JSONObject();
			int idx = Integer.parseInt(request.getParameter("idx"));
			String id = request.getParameter("id");
			String mngCon = request.getParameter("mngCon");
			String acctStat = request.getParameter("acctStat");
			
			try {
				String sql = "UPDATE report SET report_mng_contents = ?, report_stat = 'Y' WHERE report_idx = ?";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1,mngCon);
				pstmt.setInt(2, idx);
				int reportR = pstmt.executeUpdate();
				pstmt.close();
				
				sql = "UPDATE member SET acc_stat = ? WHERE id = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, acctStat);
				pstmt.setString(2, id);
				int memberR = pstmt.executeUpdate();
				pstmt.close();
				
				if(reportR == 1 && memberR == 1)
					resultObj.put("update", "OK");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			out.print(resultObj);
		} // End
		
		// 이메일 수정
		else if(toDo.equals("mdfyEmail")) {
			JSONObject resultObj = new JSONObject();
			String id = request.getParameter("id");
			String newEmail = request.getParameter("newEmail");
			String sql = "UPDATE member SET email = ? WHERE id = ?";
			try {
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, newEmail);
				pstmt.setString(2, id);
				int r = pstmt.executeUpdate();
				if(r == 1)
					resultObj.put("update", "OK");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			out.print(resultObj);
		} // End
	}
}
