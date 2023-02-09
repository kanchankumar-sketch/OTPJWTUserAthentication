package in.reinventing.otpauthentication.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

@Component
@Transactional
public interface OTPService {

	public String generateOTP(String email) throws Exception ;
	public boolean validateOTP(String email,String OTP) throws Exception;
}
