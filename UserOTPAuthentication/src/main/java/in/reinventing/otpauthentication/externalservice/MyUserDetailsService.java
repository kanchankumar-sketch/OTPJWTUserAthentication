package in.reinventing.otpauthentication.externalservice;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import in.reinventing.otpauthentication.entity.User;
import in.reinventing.otpauthentication.service.UserService;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserService userServiceImplementation;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user=this.userServiceImplementation.getUser(username);
		if(user!=null) {
			return new org.springframework.security.core.userdetails.User(username, user.getOtp().getOtp(), new ArrayList<>());
		}
		throw new UsernameNotFoundException("User not found with email: " + username);
	}

}
