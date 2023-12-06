package com.ABADCO.AIDocumentGenerator.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ABADCO.AIDocumentGenerator.model.pojo.User;
import com.ABADCO.AIDocumentGenerator.model.request.CheckLogin;
import com.ABADCO.AIDocumentGenerator.model.request.CreateUserRequest;
import com.ABADCO.AIDocumentGenerator.service.UserService;

@RestController
public class UserController {
	
	private UserService service;
		
	@Autowired
	public UserController(UserService service) {this.service = service;}
	
	@GetMapping("/users/{userid}")
	public ResponseEntity<User> getUserById(@PathVariable String userid) {
		User user = service.getUserById(userid);
		if (user != null) {return ResponseEntity.ok(user);}
		else {return ResponseEntity.notFound().build();}
	}
	
	@GetMapping("/users")
	public ResponseEntity<List<User>> getUsers(@RequestParam Optional<String> username, @RequestParam Optional<String> email) {
		List<User> users;
		users = service.getUsersByCombinedSearch(username.orElse(null), email.orElse(null));
		
		if (users != null) {return ResponseEntity.ok(users); } 
		else { return ResponseEntity.notFound().build();}
	}
	
	@PostMapping("/users")
	public ResponseEntity<User> createUser(@RequestBody CreateUserRequest request) {
		User newUser = service.createUser(request.getUsername(), request.getEmail(), request.getPassword());
		if (newUser != null) {return ResponseEntity.ok(newUser);}
		else {return ResponseEntity.notFound().build();}
	}
	
	@PutMapping("/users/{userid}")
	public ResponseEntity<User> updateUser(@PathVariable String userid, @RequestBody CreateUserRequest request) {
		User updatedUser = service.updateUser(userid, request.getUsername(), request.getEmail(), request.getPassword());
		if (updatedUser != null) {return ResponseEntity.ok(updatedUser);}
		else {return ResponseEntity.notFound().build();}
	}
	
	@DeleteMapping("/users/{userid}")
	public ResponseEntity<Boolean> deleteUser(@PathVariable String userid) {
		Boolean result = service.deleteUser(userid);
		if (result) {return ResponseEntity.ok(result);}
		else {return ResponseEntity.notFound().build();}
	}
	
	@GetMapping("/users/login")
	public ResponseEntity<Boolean> checkLogin(@RequestBody CheckLogin request) {
		Boolean result = service.checkLogin(request.getUsername(), request.getPassword());
		return ResponseEntity.ok(result);
	}

}
