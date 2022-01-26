package mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JoinMail {
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
    
	public JoinMail() {}
	public JoinMail(String email, String verifyNo, String id) {
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
	
	private String SUBJECT = "픽플 가입인증 메일입니다.";
	
    public void send() {
    	StringBuffer body = new StringBuffer();
    	body.append("<div style='padding: 50px; width: 500px; border-top: 2px solid #0ea098; border-bottom: 1px solid #ddd; font-size:18px;'>");
    		body.append("<h2 style='margin: 30px 0; color: #0ea098;'>이메일 주소 인증</h2>");
    		body.append("<p style='margin-bottom: 5px;'>안녕하세요.</p> <p style='margin-bottom: 15px;'>픽플을 이용해 주셔서 감사드립니다.</p>");
    		body.append("<p><b>" + id +"</b>님, <span style='color: #0ea098;'>아래 버튼을 클릭하여 회원가입을 완료해주세요.</span></p>");
    		body.append("<p style='margin-bottom: 15px;'>이메일 인증 후 로그인이 가능합니다.</p> <p>감사합니다.</p>");
	    	body.append("<form style='margin: 30px 0 50px' target='_blank' action='http://localhost:9090/Pickpl/Controller' method='post'>");
		    	body.append("<input type='hidden' name='command' value='verifyEmail' />");
		    	body.append("<input type='hidden' name='id' value='"+ id +"' />");
		    	body.append("<input type='hidden' name='verifyNo' value='"+ verifyNo +"' />");
		    	body.append("<button style='width: 200px; height:50px; font-size: 16px; background: #0ea098; border: 0; border-radius: 3px; color: #fff;'>이메일 인증하기</button>");
	    	body.append("</form>");
	    	body.append("<h4 style='font-weight:400;'>픽플 팀 드림</h4>");
	    	body.append("<a href='http://localhost:9090/Pickpl/' style=color:#0ea098; text-decoration:none; font-size:14px;'>www.pickpl.com</a>");
    	body.append("</div>");
    	
    	
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