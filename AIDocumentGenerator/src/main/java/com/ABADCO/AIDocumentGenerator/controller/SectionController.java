package com.ABADCO.AIDocumentGenerator.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ABADCO.AIDocumentGenerator.model.pojo.Section;
import com.ABADCO.AIDocumentGenerator.model.request.CreateSectionRequest;
import com.ABADCO.AIDocumentGenerator.model.request.UpdateSectionRequest;
import com.ABADCO.AIDocumentGenerator.service.SectionService;

@RestController
public class SectionController {
	
	private SectionService service;
	
	@Value("${admin.uuid}")
    private String adminUUID;
	
	@Autowired
	public SectionController(SectionService service) {this.service = service;}
	
	//ADMIN
	@GetMapping("/sections/{sectionid}")
	public ResponseEntity<?> getSectionById(@RequestHeader("Admin-Key") String adminKey, @PathVariable String sectionid) {
		if (adminKey.equals(adminUUID)) {
			Section section = service.getSectionbyId(sectionid);
			if (section != null) {return ResponseEntity.ok(section);}
			else {return ResponseEntity.notFound().build();}
		} else {return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);}
	}
	
	//USER
	@GetMapping("/sections")
	public ResponseEntity<?> getSections(@RequestHeader("User-Key") String userCookie, @RequestParam Optional<String> title, @RequestParam Optional<String> content, @RequestParam Optional<Integer> position, @RequestParam Optional<Boolean> isVisible, @RequestParam Long document_id) {
		List<Section> sections;
		sections = service.getSectionsByCombinedSearch(title.orElse(null), content.orElse(null), position.orElse(null), isVisible.orElse(null), document_id, userCookie);
		
		if (sections != null) {return ResponseEntity.ok(sections); } 
		else { return ResponseEntity.notFound().build();}
	}
	
	//USER
	@PostMapping("/sections")
	public ResponseEntity<Section> createSection(@RequestHeader("User-Key") String userCookie, @RequestBody CreateSectionRequest request) {
		Section newSection = service.createSection(request.getTitle(), request.getContent(), request.getPosition(), request.getIsVisible(), request.getDocument_id(), userCookie);
		if (newSection != null) {return ResponseEntity.ok(newSection);}
		else {return ResponseEntity.notFound().build();}
	}
	
	//USER
	@PutMapping("/sections/{sectionid}")
	public ResponseEntity<Section> updateSection(@RequestHeader("User-Key") String userCookie, @PathVariable String sectionid, @RequestBody UpdateSectionRequest request) {
		Section updatedSection = service.updateSection(sectionid, userCookie, request.getTitle(), request.getContent(), request.getPosition(), request.getIsVisible());
		if (updatedSection != null) {return ResponseEntity.ok(updatedSection);}
		else {return ResponseEntity.notFound().build();}
	}
	
	//USER
	@DeleteMapping("/sections/{sectionid}")
	public ResponseEntity<Boolean> deleteSection(@RequestHeader("User-Key") String userCookie, @PathVariable String sectionid) {
		Boolean result = service.deleteSection(sectionid, userCookie);
		if (result) {return ResponseEntity.ok(result);}
		else {return ResponseEntity.notFound().build();}
	}

}
