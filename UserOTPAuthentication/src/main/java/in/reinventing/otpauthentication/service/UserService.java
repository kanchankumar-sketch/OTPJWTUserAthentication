package in.reinventing.otpauthentication.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import in.reinventing.otpauthentication.entity.User;

@Component
@Transactional
public interface UserService {

	public User registerUser(User user) throws Exception;
	public void deleteUser(String email) throws Exception;
	public boolean userExists(String email);
	public User getUser(String email);
	List<User> getAllUser();
}
