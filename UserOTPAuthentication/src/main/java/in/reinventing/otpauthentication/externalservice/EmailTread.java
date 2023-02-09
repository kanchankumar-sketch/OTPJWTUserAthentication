package in.reinventing.otpauthentication.externalservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class EmailTread implements Runnable{

	private String toEmail;
	private String body;
	private String subject;
	private JavaMailSender mailSender;
	
	@Override
	public void run() {
		SimpleMailMessage message=new SimpleMailMessage();
		message.setFrom("KanchanAI");
		message.setTo(toEmail);
		message.setText(body);
		message.setSubject(subject);
		mailSender.send(message);
		System.out.println("mail sent to "+toEmail+"....");
		
	}

}
