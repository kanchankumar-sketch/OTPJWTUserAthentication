package in.reinventing.otpauthentication.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.reinventing.otpauthentication.entity.User;
import in.reinventing.otpauthentication.exceptions.UserAlreadyExistsException;
import in.reinventing.otpauthentication.exceptions.UserNotFoundException;
import in.reinventing.otpauthentication.repository.UserRepository;

@Service
@Transactional
public class UserServiceImplementation implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User registerUser(User user) throws UserAlreadyExistsException  {
		if(!userExists(user.getEmail())) return  this.userRepository.save(user);
		else throw new UserAlreadyExistsException(user.getEmail()+" already exits.");
	}
	
	@Override
	public void deleteUser(String email) throws UserNotFoundException  {
		if(userExists(email)) this.userRepository.deleteByEmail(email);
		else throw new UserNotFoundException(email);
	}
	
	@Override
	public boolean userExists(String email) {
		return this.userRepository.findByEmail(email).isPresent();
	}
	
	@Override
	public User getUser(String email) {
		Optional<User> opUser=this.userRepository.findByEmail(email);
		if(opUser.isPresent()) {
			return opUser.get();
		}
		return null;
	}
	
	@Override
	public List<User> getAllUser(){
		return this.userRepository.findAll();
	}
}
