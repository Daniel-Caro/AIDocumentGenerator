package com.ABADCO.AIDocumentGenerator.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ABADCO.AIDocumentGenerator.data.DocumentRepository;
import com.ABADCO.AIDocumentGenerator.data.SectionRepository;
import com.ABADCO.AIDocumentGenerator.model.pojo.Document;
import com.ABADCO.AIDocumentGenerator.model.pojo.Section;

@Service
public class SectionServiceImpl implements SectionService{
	
	@Autowired
	private SectionRepository repository;
	
	@Autowired
	private DocumentRepository documentRepository;

	@Override
	public Section getSectionbyId(String sectionid) {
		// TODO Auto-generated method stub
		return repository.findById(Long.valueOf(sectionid)).orElse(null);
	}

	@Override
	public List<Section> getSectionsByCombinedSearch(String title, String content, Integer position, Boolean isVisible,
			Long document_id) {
		// TODO Auto-generated method stub
		return repository.findByTitleAndContentAndPositionAndIsVisibleAndDocumentId(title, content, position, isVisible, document_id);
	}

	@Override
	public Section createSection(String title, String content, Integer position, Boolean isVisible, Long document_id) {
		// TODO Auto-generated method stub
		Section section = new Section();
		section.setTitle(title);
		section.setContent(content);;
		section.setPosition(position);;
		section.setIsVisible(isVisible);;
		
		Document doc = documentRepository.findById(document_id).orElse(null);
		if (doc == null) return null;
		section.setDocument(doc);
		
		repository.save(section);
		return section;
	}

	@Override
	public Section updateSection(String sectionid, String title, String content, Integer position, Boolean isVisible) {
		// TODO Auto-generated method stub
		Section section = repository.findById(Long.valueOf(sectionid)).orElse(null);
		
		section.setTitle(title);
		section.setContent(content);
		section.setPosition(position);
		section.setIsVisible(isVisible);
		
		repository.save(section);
		return section;
	}

	@Override
	public Boolean deleteSection(String sectionid) {
		// TODO Auto-generated method stub
		Section section = repository.findById(Long.valueOf(sectionid)).orElse(null);
		if(section != null) {
			repository.delete(section);
			return true;
		} else {
			return false;
		}
	}

}
