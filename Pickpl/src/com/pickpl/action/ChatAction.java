package com.pickpl.action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.pickpl.dao.ChatDao;
import com.pickpl.dto.ChatDto;

public class ChatAction implements Action {
	ChatDao cDao = new ChatDao();
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String loginId = (String) session.getAttribute("loginId");
		
		String cmd = request.getParameter("command");
		
		JSONObject resultObj = new JSONObject();
		
		// 대화상대 전달
		if (cmd.equals("chatPage")) {
			request.setAttribute("chatId", request.getParameter("chatId"));
		}
		// 대화상대 있는지 확인
		else if(cmd.equals("checkSendTo")) {
			String toId = request.getParameter("sendTo");
			boolean checkSendTo = cDao.checkSendTo(loginId, toId);
			resultObj.put("result", checkSendTo);
			if(!checkSendTo) {
				resultObj.put("profile", cDao.getProfile(toId));
			}
			request.setAttribute("result", resultObj);
		}
		// 대화상대 리스트 받아오기
		else if(cmd.equals("chatAcct")) {
			JSONArray acctListArr = new JSONArray();
			ArrayList<ChatDto> acctList = cDao.getAcctList(loginId);
			
			if(acctList != null) {
				for(ChatDto dto : acctList) {
					JSONObject obj = new JSONObject();
					obj.put("sendTo", dto.getTo_id());
					obj.put("profile", dto.getProfile());
					obj.put("msg", dto.getMessage());
					obj.put("send_date", dto.getSend_date());
					obj.put("unCheck", cDao.checkUnread(loginId, dto.getTo_id()));
					
					acctListArr.add(obj);
				}
			}
			resultObj.put("acctList", acctListArr);
			request.setAttribute("result", resultObj);
		}
		// 메시지 내역 받아오기
		else if(cmd.equals("chatMsg")) {
			JSONArray msgListArr = new JSONArray();
			String sendTo = request.getParameter("sendTo");
			
			ArrayList<ChatDto> msgList = cDao.getChatList(loginId, sendTo);
			if(msgList != null) {
				for(ChatDto dto : msgList) {
					JSONObject obj = new JSONObject();
					if(dto.getTo_id().equals(loginId)) {
						obj.put("type", "in");
					} else {
						obj.put("type", "out");
					}
					obj.put("idx", dto.getIdx());
					obj.put("msg", dto.getMessage());
					obj.put("date", dto.getSend_date());
					obj.put("check", dto.getCheck_stat());
					obj.put("profile", cDao.getProfile(sendTo));
					
					msgListArr.add(obj);
				}
			}
			
			resultObj.put("msgList", msgListArr);
			request.setAttribute("result", resultObj);
		}
		// 메시지 전송
		else if(cmd.equals("sendMsg")) {
			String toId = request.getParameter("toId");
			String message = request.getParameter("message");
			String sendDate = request.getParameter("sendDate");
			
			if(cDao.updateMsg(loginId, toId, message, sendDate) == 1) {
				resultObj.put("result", "sucess");
			}
			request.setAttribute("result", resultObj);
		}
		// 메시지 확인
		else if(cmd.equals("checkMsg")) {
			String checkId = request.getParameter("check_id");
			
			resultObj.put("result", cDao.updateCheck(loginId, checkId));
			request.setAttribute("result", resultObj);
		} // End
		
		request.getRequestDispatcher("Controller?command=result&resultAct=" + cmd).forward(request, response);
	}
}
