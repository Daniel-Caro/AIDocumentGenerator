package com.ABADCO.AIDocumentGenerator.model.request;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter 
@AllArgsConstructor 
@NoArgsConstructor
public class UpdateDocumentRequest {
	
	@JsonProperty("title") 
	private String title;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@JsonProperty("date") 
	private LocalDate date;
	
	@JsonProperty("authors") 
	private String authors;
	
	@JsonProperty("color") 
	private String color;
	
	@JsonProperty("hasIndex") 
	private Boolean hasIndex;
	
	@JsonProperty("isPaginated") 
	private Boolean isPaginated;

}
