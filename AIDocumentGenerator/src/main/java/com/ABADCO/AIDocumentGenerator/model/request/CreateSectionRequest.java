package com.ABADCO.AIDocumentGenerator.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import jakarta.persistence.Lob;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter 
@AllArgsConstructor 
@NoArgsConstructor
public class CreateSectionRequest {
	
	@JsonProperty("title") 
	private String title;
	
	@Lob
	@JsonProperty("content") 
	private String content;
	
	@JsonProperty("position") 
	private Integer position;
	
	@JsonProperty("document_id") 
	private Long document_id;

}
