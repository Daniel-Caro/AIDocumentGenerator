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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ABADCO.AIDocumentGenerator.model.pojo.Section;
import com.ABADCO.AIDocumentGenerator.service.SectionService;

@RestController
public class SectionController {
	
	private SectionService service;
	
	@Autowired
	public SectionController(SectionService service) {this.service = service;}
	
	@GetMapping("/sections/{sectionid}")
	public ResponseEntity<Section> getSectionById(@PathVariable String sectionid) {
		Section section = service.getSectionbyId(sectionid);
		if (section != null) {return ResponseEntity.ok(section);}
		else {return ResponseEntity.notFound().build();}
	}
	
	@GetMapping("/sections")
	public ResponseEntity<List<Section>> getSections(@RequestParam Optional<String> title, @RequestParam Optional<String> content, @RequestParam Optional<Integer> position, @RequestParam Optional<Boolean> isVisible, @RequestParam Optional<Long> document_id) {
		List<Section> sections;
		sections = service.getSectionsByCombinedSearch(title.orElse(null), content.orElse(null), position.orElse(null), isVisible.orElse(null), document_id.orElse(null));
		
		if (sections != null) {return ResponseEntity.ok(sections); } 
		else { return ResponseEntity.notFound().build();}
	}
	
	@PostMapping("/sections")
	public ResponseEntity<Section> createSection(@RequestParam String title, @RequestParam String content, @RequestParam Integer position,
			@RequestParam Boolean isVisible, @RequestParam Long document_id) {
		Section newSection = service.createSection(title, content, position, isVisible, document_id);
		if (newSection != null) {return ResponseEntity.ok(newSection);}
		else {return ResponseEntity.notFound().build();}
	}
	
	@PutMapping("/sections/{sectionid}")
	public ResponseEntity<Section> updateSection(@PathVariable String sectionid, @RequestParam String title, @RequestParam String content, @RequestParam Integer position, @RequestParam Boolean isVisible) {
		Section updatedSection = service.updateSection(sectionid, title, content, position, isVisible);
		if (updatedSection != null) {return ResponseEntity.ok(updatedSection);}
		else {return ResponseEntity.notFound().build();}
	}
	
	@DeleteMapping("/sections/{sectionid}")
	public ResponseEntity<Boolean> deleteSection(@PathVariable String sectionid) {
		Boolean result = service.deleteSection(sectionid);
		if (result) {return ResponseEntity.ok(result);}
		else {return ResponseEntity.notFound().build();}
	}

}
