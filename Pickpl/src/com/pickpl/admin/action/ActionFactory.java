package com.pickpl.admin.action;

public class ActionFactory {
	private static ActionFactory instance = new ActionFactory();
	
	private ActionFactory() {}
	
	static ActionFactory getInstance() {
		return instance;
	}
	
	Action getAction(String command) {
		Action action = null;
		
		switch(command) {
		case "adminLogin" :
			action = new AdminLoginAction();
			break;
			
		case "diaryList" :
		case "diaryDetail" :
		case "mdfyDiaryStat" :
			action = new AdminDiaryAction();
			break;
			
		case "memberList" :
		case "memberDetail" :
		case "mdfyAccStat" :
		case "mdfyEmail" :
			action = new AdminMemberAction();
			break;
			
		case "rcmndList" :
		case "mngRcmnd" :
		case "searchDiary" :
		case "updateRcmnd" :
		case "writeRcmnd" :
		case "deleteRcmnd" :
			action = new AdminRcmndAction();
			break;
			
		case "mngMemberReport" :
		case "mngDiaryReport" :
		case "reportList" :
		case "reportDetail" :
//		case "reportHistory" :
			action = new AdminReportAction();
			break;
		
		case "result" :
			action = new ResultAction();
			break;
		}
		
		return action;
	}
}
