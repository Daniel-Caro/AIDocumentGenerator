package com.ABADCO.AIDocumentGenerator.data;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ABADCO.AIDocumentGenerator.model.pojo.Document;

public interface DocumentRepository extends JpaRepository<Document, Long> {

	public Optional<Document> findById(Long documentid);
	
	@Query("SELECT d FROM Document d WHERE (:title is null or d.title = :title) and (:date is null or d.date = :date) and (:authors is null or d.authors = :authors) and (:color is null or d.color = :color) and (:userId is null or d.user.id = :userId) and (:urlView is null or d.urlView = :urlView)")
	public List<Document> findByTitleAndDateAndAuthorsAndColorAndUserIdAndUrlView(String title, LocalDate date,
			String authors, String color, Long userId, String urlView);

	@Query("SELECT d FROM Document d WHERE (:urlView is null or d.urlView = :urlView)")
	public List<Document> findByUrlView(String urlView);

}