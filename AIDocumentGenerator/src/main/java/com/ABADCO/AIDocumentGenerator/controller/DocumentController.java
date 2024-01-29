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
import org.springframework.web.client.RestTemplate;

import com.ABADCO.AIDocumentGenerator.model.pojo.Document;
import com.ABADCO.AIDocumentGenerator.model.pojo.User;
import com.ABADCO.AIDocumentGenerator.model.request.AIResponse;
import com.ABADCO.AIDocumentGenerator.model.request.CreateDocumentRequest;
import com.ABADCO.AIDocumentGenerator.model.request.GPTChatRequest;
import com.ABADCO.AIDocumentGenerator.model.request.GPTChatResponse;
import com.ABADCO.AIDocumentGenerator.model.request.GetRequirementsRequest;
import com.ABADCO.AIDocumentGenerator.model.request.GetTextRequest;
import com.ABADCO.AIDocumentGenerator.model.request.UpdateDocumentRequest;
import com.ABADCO.AIDocumentGenerator.service.DocumentService;
import com.ABADCO.AIDocumentGenerator.service.UserService;

@RestController
public class DocumentController {
	
	private DocumentService service;
	
	@Autowired
	private UserService userService;
	
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
    
    @Value("${admin.uuid}")
    private String adminUUID;
	
	@Autowired
	public DocumentController(DocumentService service) {this.service = service;}
	
	//ADMIN
	@PostMapping("/documents/bytext")
	public ResponseEntity<?> getRequirements(@RequestHeader("Admin-Key") String adminKey, @RequestBody GetRequirementsRequest request) {
		if (adminKey.equals(adminUUID)) {
			String finalText = "I want you to obtain a series of requisites from this text. Place all the requisites in a list: " + request.getText();
			// create a request
	        GPTChatRequest requestAI = new GPTChatRequest(model, finalText);
	        
	        // call the API
	        GPTChatResponse response = restTemplate.postForObject(apiUrl, requestAI, GPTChatResponse.class);
	        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
	             return new ResponseEntity<String>("No response", HttpStatus.BAD_REQUEST);
	        }
	        
	        // return the first response
			String result = response.getChoices().get(0).getMessage().getContent();
			AIResponse AIResponse = new AIResponse(result);

			return ResponseEntity.ok(AIResponse);
		
		} else {return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);}
	}
	
	//ADMIN
	@PostMapping("/documents/byrequirements")
	public ResponseEntity<?> getText(@RequestHeader("Admin-Key") String adminKey, @RequestBody GetTextRequest request) {
		if (adminKey.equals(adminUUID)) {
			String finalText = "For a technical documentation before start a project, I want you to make a short text redaction of the project that will be developed taking in account the requisites presented here. Just give the generated text redaction without any introductions: " + request.getRequirements();
			// create a request
	        GPTChatRequest requestAI = new GPTChatRequest(model, finalText);
	        
	        // call the API
	        GPTChatResponse response = restTemplate.postForObject(apiUrl, requestAI, GPTChatResponse.class);
			if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
				return new ResponseEntity<String>("No response", HttpStatus.BAD_REQUEST);
			}
	        
	        // return the first response
			String result = response.getChoices().get(0).getMessage().getContent();
			AIResponse AIResponse = new AIResponse(result);
			
			return ResponseEntity.ok(AIResponse);
		} else {return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);}
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
	
	//ADMIN
	@GetMapping("/documents/{documentid}")
	public ResponseEntity<?> getDocumentById(@RequestHeader("Admin-Key") String adminKey, @PathVariable String documentid) {
		if (adminKey.equals(adminUUID)) {
			Document document = service.getDocumentbyId(documentid);
			if (document != null) {return ResponseEntity.ok(document);}
			else {return ResponseEntity.notFound().build();}
		} else {return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);}
	}

	//ADMIN
	@GetMapping("/view/document")
	public ResponseEntity<?> getDocuments(@RequestHeader("Admin-Key") String adminKey, @RequestParam Optional<String> urlView) {
		if (adminKey.equals(adminUUID)) {
			List<Document> documents;
			documents = service.getDocumentsByUrlView(urlView.orElse(null));
			if (documents != null) {
				return ResponseEntity.ok(documents);
			} else {
				return ResponseEntity.notFound().build();
			}
		} else {
			return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
		}
	}

	//USER
	@GetMapping("/documents")
	public ResponseEntity<?> getDocuments(@RequestHeader("Admin-Key") String adminKey, @RequestHeader("User-Key") String userCookie, @RequestParam Optional<String> title, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)Optional<LocalDate> date, @RequestParam Optional<String> authors, @RequestParam Optional<String> color, @RequestParam Optional<String> urlView) {
		User user = getUserByCookie(userCookie);

		if(!adminKey.equals(adminUUID)) {
			return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
		} else if (user == null) { return new ResponseEntity<String>("Incorrect cookie, user not found", HttpStatus.BAD_REQUEST);}
		

		List<Document> documents;
		documents = service.getDocumentsByCombinedSearch(title.orElse(null), date.orElse(null), authors.orElse(null), color.orElse(null), user.getId(), urlView.orElse(null));
		
		if (documents != null) {return ResponseEntity.ok(documents); } 
		else { return ResponseEntity.notFound().build();}
	}
	
	//USER
	@PostMapping("/documents")
	public ResponseEntity<?> createDocument(@RequestHeader("Admin-Key") String adminKey, @RequestHeader("User-Key") String userCookie, @RequestBody CreateDocumentRequest request) {
		User user = getUserByCookie(userCookie);
		
		if(!adminKey.equals(adminUUID)) {
			return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
		} else if (user == null) {
			return new ResponseEntity<String>("Incorrect cookie, user not found", HttpStatus.BAD_REQUEST);
		}
		

		Document newDocument = service.createDocument(request.getTitle(), request.getDate(), request.getAuthors(), request.getColor(), request.getHasIndex(), user.getId());
		if (newDocument != null) {return ResponseEntity.ok(newDocument);}
		else {return ResponseEntity.notFound().build();}
	}
	
	//USER
	@PutMapping("/documents/{documentid}")
	public ResponseEntity<?> updateDocument(@RequestHeader("Admin-Key") String adminKey, @RequestHeader("User-Key") String userCookie, @PathVariable String documentid, @RequestBody UpdateDocumentRequest request) {
		User user = getUserByCookie(userCookie);
	
		if(!adminKey.equals(adminUUID)) {
			return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
		} else if (user == null) {
			return new ResponseEntity<String>("Incorrect cookie, user not found", HttpStatus.BAD_REQUEST);
		}

		Document updatedDocument = service.updateDocument(documentid, request.getTitle(), request.getDate(), request.getAuthors(), request.getColor(), request.getHasIndex(), userCookie);

		if (updatedDocument != null) {return ResponseEntity.ok(updatedDocument);}

		else {return ResponseEntity.notFound().build();}
	}
	
	//USER
	@DeleteMapping("/documents/{documentid}")
	public ResponseEntity<Boolean> deleteDocument(@RequestHeader("User-Key") String userCookie, @PathVariable String documentid) {
		Boolean result = service.deleteDocument(documentid, userCookie);
		if (result) {return ResponseEntity.ok(result);}
		else {return ResponseEntity.notFound().build();}
	}

	private User getUserByCookie(String userCookie) {
		try {
			return userService.getUserByUUID(userCookie);
		} catch (Exception e) {
			return null;
		}
	}

}