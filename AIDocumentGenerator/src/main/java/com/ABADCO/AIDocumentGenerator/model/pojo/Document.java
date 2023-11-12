package com.ABADCO.AIDocumentGenerator.model.pojo;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "documents")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Document {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "date")
	private Date date;
	
	@Column(name = "authors")
	private String authors;
	
	@Column(name = "color")
	private String color;
	
	@Column(name = "is_paginated")
	private Boolean isPaginated;
	
	@Column(name = "has_index")
	private Boolean hasIndex;
	
	@Column(name = "url_view")
	private String urlView;
	
	@Column(name = "url_edit")
	private String urlEdit;
	
	@ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

}
