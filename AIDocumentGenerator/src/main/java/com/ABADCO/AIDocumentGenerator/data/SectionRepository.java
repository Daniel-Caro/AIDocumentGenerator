package com.ABADCO.AIDocumentGenerator.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ABADCO.AIDocumentGenerator.model.pojo.Section;

public interface SectionRepository extends JpaRepository<Section, Long> {

	@Query("SELECT s FROM Section s WHERE (:title is null or s.title = :title) and (:content is null or s.content = :content) and (:position is null or s.position = :position) and (:isVisible is null or s.isVisible = :isVisible) and (:document_id is null or s.document.id = :document_id)")
	//@Query("SELECT s FROM Section s WHERE (:title is null or s.title = :title) and (:content is null or s.content = :content)")
	public List<Section> findByTitleAndContentAndPositionAndIsVisibleAndDocumentId(String title, String content, Integer position, Boolean isVisible, Long document_id);

}
