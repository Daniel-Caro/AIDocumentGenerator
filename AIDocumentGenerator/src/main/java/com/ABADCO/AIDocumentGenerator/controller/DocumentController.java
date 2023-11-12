package com.ABADCO.AIDocumentGenerator.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ABADCO.AIDocumentGenerator.model.pojo.Document;
import com.ABADCO.AIDocumentGenerator.model.request.GPTChatRequest;
import com.ABADCO.AIDocumentGenerator.model.request.GPTChatResponse;
import com.ABADCO.AIDocumentGenerator.service.DocumentService;

@RestController
public class DocumentController {
	
	private DocumentService service;
	
	@Qualifier("openaiRestTemplate")
    @Autowired
    private RestTemplate restTemplate;
    
    @Value("${openai.model}")
    private String model;
    
    @Value("${openai.api.url}")
    private String apiUrl;
	
	@Autowired
	public DocumentController(DocumentService service) {this.service = service;}
	
	@GetMapping("/documents/bytext")
	public String getRequirements(@RequestParam String text) {
		// create a request
        GPTChatRequest request = new GPTChatRequest(model, text);
        
        // call the API
        GPTChatResponse response = restTemplate.postForObject(apiUrl, request, GPTChatResponse.class);
        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            return "No response";
        }
        
        // return the first response
        return response.getChoices().get(0).getMessage().getContent();
	}
	
	/*@GetMapping("/document/byrequirements")
	public String getText(@RequestParam String requirements) {
		String url = "https://api.deepai.org/api/text-generator"
		// create a request
        GPTChatRequest request = new GPTChatRequest(model, text);
        
        // call the API
        GPTChatResponse response = restTemplate.postForObject(apiUrl, request, GPTChatResponse.class);
        
        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            return "No response";
        }
        
        // return the first response
        return response.getChoices().get(0).getMessage().getContent();
	}*/
	
	@GetMapping("/documents/{documentid}")
	public ResponseEntity<Document> getDocumentById(@PathVariable String documentid) {
		Document document = service.getDocumentbyId(documentid);
		if (document != null) {return ResponseEntity.ok(document);}
		else {return ResponseEntity.notFound().build();}
	}
	
	@GetMapping("/documents")
	public ResponseEntity<List<Document>> getDocuments(@RequestParam Optional<String> title, @RequestParam Optional<Date> date, @RequestParam Optional<String> authors,
			@RequestParam Optional<String> color, @RequestParam Optional<Integer> user_id) {
		List<Document> documents;
		documents = service.getDocumentsByCombinedSearch(title.orElse(null), date.orElse(null), authors.orElse(null), null, color.orElse(null), user_id);
		
		if (documents != null) {return ResponseEntity.ok(documents); } 
		else { return ResponseEntity.notFound().build();}
	}
	
	@PostMapping("/documents")
	public ResponseEntity<Document> createDocument(@RequestParam String title, @RequestParam Date date, @RequestParam String authors, @RequestParam String color, 
			@RequestParam Boolean hasIndex, @RequestParam Boolean isPaginated, @RequestParam Integer user_id) {
		Document newDocument = service.createDocument(title, date, authors, color, hasIndex, isPaginated, user_id);
		if (newDocument != null) {return ResponseEntity.ok(newDocument);}
		else {return ResponseEntity.notFound().build();}
	}
	
	@DeleteMapping("/documents/{documentid}")
	public ResponseEntity<Boolean> deleteDocument(@PathVariable String documentid) {
		Boolean result = service.deleteDocument(documentid);
		if (result) {return ResponseEntity.ok(result);}
		else {return ResponseEntity.notFound().build();}
	}

}
