package com.ABADCO.AIDocumentGenerator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.ABADCO.AIDocumentGenerator.service.SectionService;

@RestController
public class SectionController {
	
	private SectionService service;
	
	@Autowired
	public SectionController(SectionService service) {this.service = service;}

}
