package com.ABADCO.AIDocumentGenerator.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter 
@AllArgsConstructor 
@NoArgsConstructor
public class CreateUserRequest {
	
	@JsonProperty("username") 
	private String username;
	
	@JsonProperty("email") 
	private String email;
	
	@JsonProperty("password") 
	private String password;

}
