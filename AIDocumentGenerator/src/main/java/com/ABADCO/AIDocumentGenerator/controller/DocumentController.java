package com.ABADCO.AIDocumentGenerator.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ABADCO.AIDocumentGenerator.model.pojo.Document;
import com.ABADCO.AIDocumentGenerator.model.request.GPTChatRequest;
import com.ABADCO.AIDocumentGenerator.model.request.GPTChatResponse;
import com.ABADCO.AIDocumentGenerator.model.request.CreateDocumentRequest;
import com.ABADCO.AIDocumentGenerator.model.request.GetRequirementsRequest;
import com.ABADCO.AIDocumentGenerator.model.request.UpdateDocumentRequest;
import com.ABADCO.AIDocumentGenerator.service.DocumentService;

@RestController
public class DocumentController {
	
	private DocumentService service;
	
	Logger logger = LoggerFactory.getLogger(DocumentController.class);
	
	@Qualifier("openaiRestTemplate")
    @Autowired
    private RestTemplate restTemplate;
    
    @Value("${openai.model}")
    private String model;
    
    @Value("${openai.api.url}")
    private String apiUrl;
    
    @Value("${deepai.api.url}")
    private String deepAiApiUrl;
    
    @Value("${deepai.api.key}")
    private String deepAiKey;
	
	@Autowired
	public DocumentController(DocumentService service) {this.service = service;}
	
	@GetMapping("/documents/bytext")
	public String getRequirements(@RequestBody GetRequirementsRequest request) {
		String finalText = "I want you to obtain a series of requisites from this text. Place all the requisites in a list: " + request.getText();
		// create a request
        GPTChatRequest requestAI = new GPTChatRequest(model, finalText);
        
        // call the API
        GPTChatResponse response = restTemplate.postForObject(apiUrl, requestAI, GPTChatResponse.class);
        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            return "No response";
        }
        
        // return the first response
        return response.getChoices().get(0).getMessage().getContent();
	}
	
	/*@GetMapping("/documents/byrequirements")
	public String getText(@RequestParam String requirements) {
		requirements = "Dado una serie de requisitos para una aplicación genera un texto descriptivo del funcionamiento de dicha aplicación, el idioma del texto será el mismo que el de los requisitos:" + requirements;
		String command = String.format("curl -F 'text=%s' -H 'api-key:%s' %s", requirements, deepAiKey, deepAiApiUrl);
		ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
		Process process;
		try {
			process = processBuilder.start();
			InputStream ins = process.getInputStream();
			BufferedReader read = new BufferedReader(new InputStreamReader(ins));
			StringBuilder sb = new StringBuilder();
			Stream<String> lines = read.lines();
			lines.
			for(String line :lines) {
				
			}
			lines.forEach(line -> {
			     logger.debug("line>"+line);
			     sb.append(line);
			});// close the buffered reader
			read.close();/*
			 * wait until process completes, this should be always after the    
			 * input_stream of processbuilder is read to avoid deadlock 
			 * situations
			 */ 
			/*process.waitFor();/* exit code can be obtained only after process completes, 0 
			 * indicates a successful completion
			 */
			/*int exitCode = process.exitValue();
			logger.debug("exit code::"+exitCode);// finally destroy the process
			process.destroy();
			return "aaa";
		} catch (InterruptedException | UnsupportedOperationException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Error occured";
		}
	}*/
	
	@GetMapping("/documents/{documentid}")
	public ResponseEntity<Document> getDocumentById(@PathVariable String documentid) {
		Document document = service.getDocumentbyId(documentid);
		if (document != null) {return ResponseEntity.ok(document);}
		else {return ResponseEntity.notFound().build();}
	}
	
	@GetMapping("/documents")
	public ResponseEntity<List<Document>> getDocuments(@RequestParam Optional<String> title, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)Optional<LocalDate> date, @RequestParam Optional<String> authors, @RequestParam Optional<String> color, @RequestParam Optional<Long> user_id, @RequestParam Optional<String> urlView, @RequestParam Optional<String> urlEdit) {
		List<Document> documents;
		documents = service.getDocumentsByCombinedSearch(title.orElse(null), date.orElse(null), authors.orElse(null), color.orElse(null), user_id.orElse(null), urlView.orElse(null), urlEdit.orElse(null));
		
		if (documents != null) {return ResponseEntity.ok(documents); } 
		else { return ResponseEntity.notFound().build();}
	}
	
	@PostMapping("/documents")
	public ResponseEntity<Document> createDocument(@RequestBody CreateDocumentRequest request) {
		Document newDocument = service.createDocument(request.getTitle(), request.getDate(), request.getAuthors(), request.getColor(), request.getHasIndex(), request.getIsPaginated(), request.getUser_id());
		if (newDocument != null) {return ResponseEntity.ok(newDocument);}
		else {return ResponseEntity.notFound().build();}
	}
	
	@PutMapping("/documents/{documentid}")
	public ResponseEntity<Document> updateDocument(@PathVariable String documentid, @RequestBody UpdateDocumentRequest request) {
		Document updatedDocument = service.updateDocument(documentid, request.getTitle(), request.getDate(), request.getAuthors(), request.getColor(), request.getHasIndex(), request.getIsPaginated());
		if (updatedDocument != null) {return ResponseEntity.ok(updatedDocument);}
		else {return ResponseEntity.notFound().build();}
	}
	
	@DeleteMapping("/documents/{documentid}")
	public ResponseEntity<Boolean> deleteDocument(@PathVariable String documentid) {
		Boolean result = service.deleteDocument(documentid);
		if (result) {return ResponseEntity.ok(result);}
		else {return ResponseEntity.notFound().build();}
	}

}
