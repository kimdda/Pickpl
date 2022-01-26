package mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class FindMail {
	private final String CONFIGSET = "ConfigSet";
	private final String HOST = "smtp.naver.com";
	private final int PORT = 587;
	private final String SMTP_USERNAME = "ddahho";
	private final String SMTP_PASSWORD = "TestPickpl";

	private final String FROM = "ddahho@naver.com";
	private final String FROMNAME = "pickpl";
	
	private String to;
	private String verifyNo;
	private String id;
    
	public FindMail() {}
	public FindMail(String email, String verifyNo, String id) {
		to = email;
		this.verifyNo = verifyNo;
		this.id = id;
	}
	
	public String getTo() {
		return to;
	}
	public String getId() {
		return id;
	}
	public String getVerifyNo() {
		return verifyNo;
	}
	
	private String SUBJECT = "픽플 비밀번호 재설정 링크입니다.";
	
    public void send() {
    	StringBuffer body = new StringBuffer();
    	body.append("<div style='padding: 50px; width: 500px; border-top: 2px solid #0ea098; border-bottom: 1px solid #ddd; font-size:18px;'>")
    		.append("<h2 style='margin: 30px 0; color: #0ea098;'>비밀번호 변경하기</h2>")
    		.append("<p><b style='color: #0ea098;'>" + id +"</b>님, <span>아래 버튼을 클릭하면 비밀번호를 변경 페이지로 이동합니다.</span></p>")
	    	.append("<form style='margin: 30px 0 50px' action='http://localhost:9090/Pickpl/Controller' method='post' target='_blank' rel='external'>")
		    	.append("<input type='hidden' name='command' value='verifyPwLink' />")
		    	.append("<input type='hidden' name='id' value='"+ id +"' />")
		    	.append("<input type='hidden' name='verifyNo' value='"+ verifyNo +"' />")
		    	.append("<button style='width: 200px; height:45px; font-size: 18px; background: #0ea098; border: 0; border-radius: 3px; color: #fff;'>비밀번호 변경하기</button>")
	    	.append("</form>")
	    	.append("<h4 style='font-weight: 400;'>픽플 팀 드림</h4>")
	    	.append("<a href='http://localhost:9090/Pickpl/' style=color:#0ea098; text-decoration:none; font-size:14px;'>www.pickpl.com</a>")
    	.append("</div>");
    	
    	
    	Properties props = System.getProperties();
    	props.put("mail.transport.protocol", "smtp");
    	props.put("mail.smtp.port", PORT); 
    	props.put("mail.smtp.starttls.enable", "true");
    	props.put("mail.smtp.auth", "true");

    	Session session = Session.getDefaultInstance(props);

        MimeMessage msg = new MimeMessage(session);
        try {
        	msg.setFrom(new InternetAddress(FROM,FROMNAME));
        	msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        	msg.setSubject(SUBJECT);
        	msg.setContent(body.toString(),"text/html;charset=UTF-8");        	
        	msg.setHeader("X-SES-CONFIGURATION-SET", CONFIGSET);
        	
        	Transport transport = session.getTransport();
        	
        	// Send the message.
        	try	{
        		System.out.println("Sending...");
        		
        		System.out.println(to + " / " + id);
        		
        		transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
        		
        		// Send the email.
        		transport.sendMessage(msg, msg.getAllRecipients());
        		System.out.println("Email sent!");
        		// System.out.println(id);
        	}
        	catch (Exception ex) {
        		System.out.println("The email was not sent.");
        		System.out.println("Error message: " + ex.getMessage());
        		ex.printStackTrace();
        	}
        	finally {
        		transport.close();
        	}
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }
}