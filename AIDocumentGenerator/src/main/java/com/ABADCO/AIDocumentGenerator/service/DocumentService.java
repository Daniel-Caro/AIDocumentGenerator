package com.ABADCO.AIDocumentGenerator.service;

import java.time.LocalDate;
import java.util.List;

import com.ABADCO.AIDocumentGenerator.model.pojo.Document;

public interface DocumentService {

	Document getDocumentbyId(String documentid);

	List<Document> getDocumentsByUrlView(String urlView);

	List<Document> getDocumentsByUrlEdit(String urlEdit);

	List<Document> getDocumentsByCombinedSearch(String title, LocalDate date, String authors, String color,
			Long user_id, String urlView, String urlEdit);

	Document createDocument(String title, LocalDate date, String authors, String color, Boolean hasIndex,
			Boolean isPaginated, Long user_id);

	Boolean deleteDocument(String documentid, String userCookie);
	
	 Document updateDocument(String documentid, String title, LocalDate date, String authors, String color, Boolean hasIndex,
				Boolean isPaginated, String userCookie);

}