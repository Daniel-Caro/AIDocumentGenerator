package com.ABADCO.AIDocumentGenerator.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ABADCO.AIDocumentGenerator.model.pojo.Section;

public interface SectionRepository extends JpaRepository<Section, Long> {

	@Query("SELECT s FROM Section s WHERE (:title is null or s.title = :title) and (:content is null or s.content = :content)" +
			" and (:order is null or s.order = :order) and (:isVisible is null or s.isVisible = :isVisible) and (:documentId is null or s.document.id = :document_id)")
	List<Section> findByTitleAndContentAndOrderAndIsVisibleAndDocumentId(String title, String content, Integer order,
			Boolean isVisible, Long document_id);

}
