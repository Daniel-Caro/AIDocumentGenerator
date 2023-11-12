package com.ABADCO.AIDocumentGenerator.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.ABADCO.AIDocumentGenerator.model.pojo.Document;

public interface DocumentService {

	Document getDocumentbyId(String documentid);

	List<Document> getDocumentsByCombinedSearch(String title, Date date, String authors, String color,
			Long user_id );

	Document createDocument(String title, Date date, String authors, String color, Boolean hasIndex,
			Boolean isPaginated, Long user_id);

	Boolean deleteDocument(String documentid);

}
