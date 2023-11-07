package com.ABADCO.AIDocumentGenerator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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
	
	@GetMapping("/document/bytext")
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

}
