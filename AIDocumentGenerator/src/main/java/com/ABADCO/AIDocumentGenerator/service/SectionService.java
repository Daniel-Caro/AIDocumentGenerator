package com.ABADCO.AIDocumentGenerator.service;

import java.util.List;

import com.ABADCO.AIDocumentGenerator.model.pojo.Section;

public interface SectionService {

	Section getSectionbyId(String sectionid);

	List<Section> getSectionsByCombinedSearch(String title, String content, Integer position, Long document_id, String userCookie);

	List<Section> getSectionsByDocumentId(Long document_id);

	Section createSection(String title, String content, Integer position, Long document_id, String userCookie);

	Section updateSection(String sectionid, String userCookie, String title, String content, Integer position);

	Boolean deleteSection(String sectionid, String userCookie);

}