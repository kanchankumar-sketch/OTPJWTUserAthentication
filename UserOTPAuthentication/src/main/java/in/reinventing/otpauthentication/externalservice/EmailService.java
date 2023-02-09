package in.reinventing.otpauthentication.externalservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private ThreadPollService threadPollService;
	
	public void sendMail(String email,String body,String subject) {
		EmailTread emailThread=new EmailTread(email,body,subject,mailSender);
		this.threadPollService.submitTaks(emailThread);
	}
}
