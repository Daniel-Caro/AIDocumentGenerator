package com.ABADCO.AIDocumentGenerator.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ABADCO.AIDocumentGenerator.model.pojo.Document;
import com.ABADCO.AIDocumentGenerator.model.pojo.User;

@Service
public class DocumentServiceImpl implements DocumentService{
	
	@Autowired
	private DocumentRepository repository;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public Document getDocumentbyId(String documentid) {
		// TODO Auto-generated method stub
		return repository.findById(Long.valueOf(documentid)).orElse(null);
	}

	@Override
	public List<Document> getDocumentsByCombinedSearch(String title, Date date, String authors, String color,
			Long user_id) {
		// TODO Auto-generated method stub
		return repository.findByTitleAndDateAndAuthorsAndColorAndUserId(title, date, authors, color, user_id);
	}

	@Override
	public Document createDocument(String title, Date date, String authors, String color, Boolean hasIndex,
			Boolean isPaginated, Long user_id) {
		// TODO Auto-generated method stub
		String urlView = UUID.randomUUID().toString();
		String urlEdit = UUID.randomUUID().toString();
		
		Document document = new Document();
		document.setTitle(title);
		document.setDate(date);
		document.setAuthors(authors);
		document.setColor(color);
		document.setHasIndex(hasIndex);
		document.setIsPaginated(isPaginated);
		document.setUrlView(urlView);
		document.setUrlEdit(urlEdit);
		
		User user = userRepository.findById(user_id);
		document.setUser(user);
		
		repository.saveDocument(document);
		return document;
	}

	@Override
	public Boolean deleteDocument(String documentId) {
		// TODO Auto-generated method stub
		repository.delete(documentId);
		return true;
	}

}
