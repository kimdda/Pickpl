package com.pickpl.action;

public class ActionFactory {
	private static ActionFactory instance = new ActionFactory();
	
	private ActionFactory() {};
	
	static ActionFactory getInstance() {
		return instance;
	}
	
	Action getAction(String command) {
		Action action = null;
		switch(command) {
		case "main" :
		case "mainRecentList" :
			action = new MainAction();
			break;
		
		case "join" :
			action = new MemberJoinAction();
			break;
			
		case "idCheck" :
			action = new MemberIdCheckAction();
			break;
			
		case "verifyEmail" :
		case "verifyPwLink" :
			action = new VerifyEmailAction();
			break;
		
		case "login" :
		case "loginCheck" :
			action = new LoginAction();
			break;
		
		case "diaryPage" :
		case "diarySummary" :
		case "diaryList" :
		case "diaryDel" :
			action = new DiaryListAction();
			break;
		
		case "chatPage" :
		case "checkSendTo" :
		case "chatAcct" :
		case "chatMsg" :
		case "sendMsg" :
		case "checkMsg" :
			action = new ChatAction();
			break;
		
		case "findId" : 
		case "findPw" :
			action = new FindAction();
			break;
		
		case "changePw" :
			action = new ChangePwAction();
			break;
			
		case "searchList" : 
			action = new ListAction();
			break;
			
		case "writeDiary":
		case "mdfyDiary" :
			action = new DiaryWriteAction();
			break;
			
		case "updateViewCount" :
			action = new UpdateViewNRecentAction();
			break;
			
		case "diaryDetail" :
		case "mdfyDiaryDetail" :
			action = new DiaryDetailAction();
			break;
			
		case "diaryAround" :
			action = new DiaryDetailAroundAction();
			break;
			
		case "deleteMyCmnt" :
		case "getCmnts" :
		case "cmntWrite" :
			action = new DiaryCmntsAction();
			break;
			
		case "report" :
			action = new ReportAction();
			break;
		
		case "pickFolderList" :
		case "addPickFolder" :
		case "delPickFolder" :
		case "mdfyPickFolder" :
		case "pickFolderDetail" :
			action = new PickFolderAction();
			break;
			
		case "myPage" :
			action = new MyinfoAction();
			break;
			
		case "mdfyMyInfo" : 
			action = new MdfyMyinfoAction();
			break;
			
		case "goodbyPickpl" :
			action = new GoodByeAction();
			break;
		
		case "unpick" :
//		case "folder_list" :
		case "pick" :
		case "folder_check" :
			action = new PickAction();
			break;
			
		case "result" :
			action = new ResultAction();
			break;
		}
		return action;
	}
}
