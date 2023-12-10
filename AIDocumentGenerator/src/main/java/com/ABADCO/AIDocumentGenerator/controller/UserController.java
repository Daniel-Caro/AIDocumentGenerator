package com.ABADCO.AIDocumentGenerator.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ABADCO.AIDocumentGenerator.model.pojo.User;
import com.ABADCO.AIDocumentGenerator.model.request.CheckLogin;
import com.ABADCO.AIDocumentGenerator.model.request.CreateUserRequest;
import com.ABADCO.AIDocumentGenerator.service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class UserController {
	
	private UserService service;
		
	@Autowired
	public UserController(UserService service) {this.service = service;}
	
	@Value("${admin.uuid}")
    private String adminUUID;
	
	//ADMIN
	@GetMapping("/users/{userid}")
	public ResponseEntity<?> getUserById(@RequestHeader("Admin-Key") String adminKey, @PathVariable String userid) {
		if (adminKey.equals(adminUUID)) {
			User user = service.getUserById(userid);
			if (user != null) {return ResponseEntity.ok(user);}
			else {return ResponseEntity.notFound().build();}
		} else {return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);}
	}
	
	//ADMIN
	@GetMapping("/users")
	public ResponseEntity<?> getUsers(@RequestHeader("Admin-Key") String adminKey, @RequestParam Optional<String> username, @RequestParam Optional<String> email) {
		if (adminKey.equals(adminUUID)) {
			List<User> users;
			users = service.getUsersByCombinedSearch(username.orElse(null), email.orElse(null));
			
			if (users != null) {return ResponseEntity.ok(users); } 
			else { return ResponseEntity.notFound().build();}
		} else {return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);}
	}
	
	//ADMIN
	@PostMapping("/users")
	public ResponseEntity<?> createUser(@RequestHeader("Admin-Key") String adminKey, @RequestBody CreateUserRequest request) {
		if (adminKey.equals(adminUUID)) {
			User newUser = service.createUser(request.getUsername(), request.getEmail(), request.getPassword());
			if (newUser != null) {return ResponseEntity.ok(newUser);}
			else {return ResponseEntity.notFound().build();}
		} else {return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);}
	}
	
	//ADMIN
	@PutMapping("/users/{userid}")
	public ResponseEntity<?> updateUser(@RequestHeader("Admin-Key") String adminKey, @PathVariable String userid, @RequestBody CreateUserRequest request) {
		if (adminKey.equals(adminUUID)) {
			User updatedUser = service.updateUser(userid, request.getUsername(), request.getEmail(), request.getPassword());
			if (updatedUser != null) {return ResponseEntity.ok(updatedUser);}
			else {return ResponseEntity.notFound().build();}
		} else {return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);}
	}
	
	//ADMIN
	@DeleteMapping("/users/{userid}")
	public ResponseEntity<?> deleteUser(@RequestHeader("Admin-Key") String adminKey, @PathVariable String userid) {
		if (adminKey.equals(adminUUID)) {
			Boolean result = service.deleteUser(userid);
			if (result) {return ResponseEntity.ok(result);}
			else {return ResponseEntity.notFound().build();}
		} else {return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);}
	}
	
	@PostMapping("/users/login")
	public ResponseEntity<Boolean> checkLogin(@RequestBody CheckLogin request, HttpServletResponse response) {
		String result = service.checkLogin(request.getEmail(), request.getPassword());
		if(result == "Incorrect password" || result == "Error finding user") {
			return ResponseEntity.ok(false);
		} else {
			Cookie cookie = new Cookie("user-cookie", result);
			cookie.setPath("/");
			response.addCookie(cookie);
			return ResponseEntity.ok(true);
		}
	}

}
