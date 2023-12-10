package com.ABADCO.AIDocumentGenerator.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ABADCO.AIDocumentGenerator.data.UserRepository;
import com.ABADCO.AIDocumentGenerator.model.pojo.User;

@Service
public class UserServiceImpl implements UserService{
	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	@Autowired
	private UserRepository repository;

	@Override
	public User getUserById(String userid) {
		// TODO Auto-generated method stub
		return repository.findById(Long.valueOf(userid)).orElse(null);
	}

	@Override
	public List<User> getUsersByCombinedSearch(String username, String email) {
		// TODO Auto-generated method stub
		return repository.findByUsernameAndEmail(username, email);
	}

	@Override
	public User createUser(String username, String email, String password) {
		// TODO Auto-generated method stub
		User user = new User();
		user.setUsername(username);
		user.setPassword(encoder.encode(password));
		user.setEmail(email);;
		
		repository.save(user);
		return user;
	}

	@Override
	public User updateUser(String userid, String username, String email, String password) {
		// TODO Auto-generated method stub
		User user = repository.findById(Long.valueOf(userid)).orElse(null);
		
		user.setUsername(username);
		user.setPassword(encoder.encode(password));
		user.setEmail(email);;
		
		repository.save(user);
		return user;
	}

	@Override
	public Boolean deleteUser(String userid) {
		// TODO Auto-generated method stub
		User user = repository.findById(Long.valueOf(userid)).orElse(null);
		if(user != null) {
			repository.delete(user);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Boolean checkLogin(String email, String password) {
		// TODO Auto-generated method stub
		User user = repository.findByEmail(email);
		Boolean result = encoder.matches(password, user.getPassword());
		return result;
	}
	
	

}
