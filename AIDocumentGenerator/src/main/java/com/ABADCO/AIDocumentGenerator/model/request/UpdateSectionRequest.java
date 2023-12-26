package com.ABADCO.AIDocumentGenerator.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Lob;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter 
@AllArgsConstructor 
@NoArgsConstructor
public class UpdateSectionRequest {

	@JsonProperty("title") 
	private String title;

	@Lob
	@JsonProperty("content") 
	private String content;
	
	@JsonProperty("position") 
	private Integer position;
	
	@JsonProperty("isVisible") 
	private Boolean isVisible;
	
}
