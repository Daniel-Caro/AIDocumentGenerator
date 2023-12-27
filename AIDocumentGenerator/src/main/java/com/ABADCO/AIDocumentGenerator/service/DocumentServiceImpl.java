package com.ABADCO.AIDocumentGenerator.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ABADCO.AIDocumentGenerator.data.DocumentRepository;
import com.ABADCO.AIDocumentGenerator.data.UserRepository;
import com.ABADCO.AIDocumentGenerator.model.pojo.Document;
import com.ABADCO.AIDocumentGenerator.model.pojo.Section;
import com.ABADCO.AIDocumentGenerator.model.pojo.User;

@Service
public class DocumentServiceImpl implements DocumentService{
	
	@Autowired
	private DocumentRepository repository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private SectionService sectionService;

	@Override
	public Document getDocumentbyId(String documentid) {
		// TODO Auto-generated method stub
		return repository.findById(Long.valueOf(documentid)).orElse(null);
	}

	
	@Override
	public List<Document> getDocumentsByCombinedSearch(String title, LocalDate date, String authors, String color,
			Long user_id, String urlView, String urlEdit) {
		// TODO Auto-generated method stub
		return repository.findByTitleAndDateAndAuthorsAndColorAndUserIdAndUrlViewAndUrlEdit(title, date, authors, color, user_id, urlView, urlEdit);
	}

	@Override
	public List<Document> getDocumentsByUrlView(String urlView) {
		// TODO Auto-generated method stub
		return repository.findByUrlView(urlView);
	}

	@Override
	public List<Document> getDocumentsByUrlEdit(String urlEdit) {
		// TODO Auto-generated method stub
		return repository.findByUrlEdit(urlEdit);
	}

	@Override
	public Document createDocument(String title, LocalDate date, String authors, String color, Boolean hasIndex,
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
		
		User user = userRepository.findById(user_id).orElse(null);
		if (user == null) return null;
		document.setUser(user);
		
		repository.save(document);
		return document;
	}
	
	public Document updateDocument(String documentid, String title, LocalDate date, String authors, String color, Boolean hasIndex,
			Boolean isPaginated, String userCookie) {
		Document document = repository.findById(Long.valueOf(documentid)).orElse(null);
		
		if (!document.getUser().getCookie().equals(userCookie)) return null;
		
		document.setTitle(title);
		document.setDate(date);
		document.setAuthors(authors);
		document.setColor(color);
		document.setHasIndex(hasIndex);
		document.setIsPaginated(isPaginated);
		
		repository.save(document);
		return document;
	}

	@Override
	public Boolean deleteDocument(String documentId, String userCookie) {
		// TODO Auto-generated method stub
		Document document = repository.findById(Long.valueOf(documentId)).orElse(null);
		
		//Debemos borrar todas las secciones relacionadas con el documento
		List<Section> sectionsToDelete = sectionService.getSectionsByDocumentId(Long.valueOf(documentId));
		for(Section section: sectionsToDelete) {
			sectionService.deleteSection(String.valueOf(section.getId()), userCookie);
		}
		
		if(document != null && document.getUser().getCookie().equals(userCookie)) {
			repository.delete(document);
			return true;
		} else {
			return false;
		}
	}

}