package in.reinventing.otpauthentication;

import java.util.Base64;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserOtpAuthenticationApplication {
static {
	System.setProperty("spring.mail.username",new String(Base64.getDecoder().decode("a2FuY2hhbmt1bWFyODgxOUBnbWFpbC5jb20=")));
	System.setProperty("spring.mail.password",new String(Base64.getDecoder().decode("c2ViZW1yYmVnZmpqdnJubg==")));

}
	public static void main(String[] args) {
		SpringApplication.run(UserOtpAuthenticationApplication.class, args);
	}

}
