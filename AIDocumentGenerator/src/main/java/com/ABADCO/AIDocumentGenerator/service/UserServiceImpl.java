package com.ABADCO.AIDocumentGenerator.service;

import java.util.List;
import java.util.UUID;

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
	
	/*@Autowired
	private DocumentService documentService;*/

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
		
		String cookie = UUID.randomUUID().toString();
		
		user.setUsername(username);
		user.setPassword(encoder.encode(password));
		user.setEmail(email);;
		user.setCookie(cookie);
		
		repository.save(user);
		return user;
	}

	@Override
	public User updateUser(String userid, String username, String email, String password) {
		// TODO Auto-generated method stub
		User user = repository.findById(Long.valueOf(userid)).orElse(null);
				
		user.setUsername(username);
		user.setPassword(encoder.encode(password));
		user.setEmail(email);
		
		repository.save(user);
		return user;
	}

	@Override
	public Boolean deleteUser(String userid) {
		// TODO Auto-generated method stub
		User user = repository.findById(Long.valueOf(userid)).orElse(null);
		
		/*//Debemos borrar los documentos asociados al usuario
		List<Document> documentsToDelete = documentService.getDocumentsByCombinedSearch(null, null, null, null, Long.valueOf(userid), null, null);
		for(Document document: documentsToDelete) {
			documentService.deleteDocument(String.valueOf(document.getId()), userCookie);
		}*/
		
		if(user != null) {
			repository.delete(user);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String checkLogin(String email, String password) {
		// TODO Auto-generated method stub
		try {
			User user = repository.findByEmail(email);
			Boolean result = encoder.matches(password, user.getPassword());
			if (result) {
				return user.getCookie();
			} else return "Incorrect password";
		} catch(Exception e) {
			return "Error finding user";
		}
	}

	@Override
	public User getUserByUUID(String userCookie) {
		// TODO Auto-generated method stub
		return repository.findByUUID(userCookie);
	}
	
	

}
