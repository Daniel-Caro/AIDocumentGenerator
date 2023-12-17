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
public class AIResponse {
	
	@JsonProperty("response") 
	private String response;

}
