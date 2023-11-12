package com.ABADCO.AIDocumentGenerator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.ABADCO.AIDocumentGenerator.service.UserService;

@RestController
public class UserController {
	
	private UserService service;
	
	@Autowired
	public UserController(UserService service) {this.service = service;}

}
