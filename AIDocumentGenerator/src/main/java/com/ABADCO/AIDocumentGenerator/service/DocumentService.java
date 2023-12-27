package com.ABADCO.AIDocumentGenerator.service;

import java.time.LocalDate;
import java.util.List;

import com.ABADCO.AIDocumentGenerator.model.pojo.Document;

public interface DocumentService {

	Document getDocumentbyId(String documentid);

	List<Document> getDocumentsByUrlView(String urlView);

	List<Document> getDocumentsByCombinedSearch(String title, LocalDate date, String authors, String color,
			Long user_id, String urlView);

	Document createDocument(String title, LocalDate date, String authors, String color, Boolean hasIndex, Long user_id);

	Boolean deleteDocument(String documentid, String userCookie);
	
	 Document updateDocument(String documentid, String title, LocalDate date, String authors, String color, Boolean hasIndex, String userCookie);

}