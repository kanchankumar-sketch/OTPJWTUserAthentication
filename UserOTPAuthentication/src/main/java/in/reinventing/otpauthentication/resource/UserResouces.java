package in.reinventing.otpauthentication.resource;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.reinventing.otpauthentication.dtos.EmailDto;
import in.reinventing.otpauthentication.dtos.EmailOtpDto;
import in.reinventing.otpauthentication.dtos.ResponseOTPmessase;
import in.reinventing.otpauthentication.dtos.TokenResponse;
import in.reinventing.otpauthentication.dtos.UserDto;
import in.reinventing.otpauthentication.entity.User;
import in.reinventing.otpauthentication.externalservice.JWTService;
import in.reinventing.otpauthentication.externalservice.MyUserDetailsService;
import in.reinventing.otpauthentication.service.OTPService;
import in.reinventing.otpauthentication.service.UserService;

@RestController
@CrossOrigin
public class UserResouces {
	
	@Autowired
	private UserService userServiceImplementation;
	
	@Autowired
	private OTPService otpServiceImplementation;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	
	@Autowired
	private JWTService jwtService;

	@PostMapping("/register")
	public ResponseEntity<Object> registerUser(@RequestBody @Valid UserDto userDto) {
		try {
			this.userServiceImplementation.registerUser(User.builder().email(userDto.getEmail()).name(userDto.getName()).build());
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(e.getMessage(),HttpStatus.NOT_ACCEPTABLE);
		}		
		return new ResponseEntity<Object>(userDto,HttpStatus.OK);
	}
	
	@PostMapping("/generate_otp")
	public ResponseEntity<Object> generateOtp(@RequestBody @Valid EmailDto email){
		String otp="";
		try {
			otp=this.otpServiceImplementation.generateOTP(email.getEmail());
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(),HttpStatus.NOT_ACCEPTABLE);
		}
		return new ResponseEntity<Object>(new ResponseOTPmessase(otp),HttpStatus.OK);
	}
	
	
	@PostMapping("/validate_otp")
	public ResponseEntity<Object> validateOtp(@RequestBody @Valid EmailOtpDto emailOtpDto) throws Exception{
		String token=null;
		try {
			this.otpServiceImplementation.validateOTP(emailOtpDto.getEmail(), emailOtpDto.getOtp());
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(emailOtpDto.getEmail(), emailOtpDto.getOtp()));
			final UserDetails userDetails=myUserDetailsService.loadUserByUsername(emailOtpDto.getEmail());
			token=this.jwtService.generateToken(userDetails);
		}catch (DisabledException e) {
			return new ResponseEntity<Object>("User desable.",HttpStatus.UNAUTHORIZED);
		} catch (BadCredentialsException e) {
			return new ResponseEntity<Object>("Bad Crendential.",HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(),HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<Object>(new TokenResponse(token),HttpStatus.OK);
	}
	
	@PostMapping("/getUser")
	public UserDto getUser(Principal principal) {
		String email=principal.getName();
		User user=this.userServiceImplementation.getUser(email);
		return UserDto.builder().email(user.getEmail()).name(user.getName()).build();
	}
	
}
