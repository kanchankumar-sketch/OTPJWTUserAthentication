package in.reinventing.otpauthentication.service;

import java.time.Instant;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.reinventing.otpauthentication.entity.OTP;
import in.reinventing.otpauthentication.entity.User;
import in.reinventing.otpauthentication.exceptions.UserNotFoundException;
import in.reinventing.otpauthentication.externalservice.EmailService;
import in.reinventing.otpauthentication.repository.OTPRepository;
import in.reinventing.otpauthentication.repository.UserRepository;

@Service
@Transactional
public class OTPServiceImplementation implements OTPService{

	@Autowired
	private UserServiceImplementation userServiceImplementation;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private UserRepository userRepository;
	
	private static final String subject="User OTP";
	
	@Override
	public String generateOTP(String email) throws Exception {
		User user=this.userServiceImplementation.getUser(email);
		if(user!=null) {
			OTP otpObj=new OTP();
			String otp=randomOtp();
			otpObj.setOtp(otp);
			user.setOtp(otpObj);
			this.userRepository.save(user);
			String body=String.format("ONE TIME PASSWORD for UserOtpValidationApp is %s valid tile 5 minutes.",otp);
			this.emailService.sendMail(email, body, subject);
			return "OTP successfully send to "+email;
		}
		throw new UserNotFoundException(email+" not found.");
	}
	
	@Override
	public boolean validateOTP(String email,String OTP) throws UserNotFoundException {
		User user=this.userServiceImplementation.getUser(email);
		if(user!=null) {
			OTP otpObj=user.getOtp();
			return (otpObj.getOtp().equals(OTP) && otpObj.getExpireAt().compareTo(Instant.now())>0);
		}
		throw new UserNotFoundException(email+" not found.");
	}
	
	public String randomOtp() {
		Random random = new Random();
		return String.format("%04d", random.nextInt(10000));
	}
}
