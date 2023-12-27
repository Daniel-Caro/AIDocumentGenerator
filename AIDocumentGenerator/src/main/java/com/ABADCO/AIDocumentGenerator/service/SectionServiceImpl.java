package com.ABADCO.AIDocumentGenerator.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
	@Transactional(readOnly = true)
	public List<Section> getSectionsByCombinedSearch(String title, String content, Integer position, Long document_id, String userCookie) {
		// TODO Auto-generated method stub
		//INVALID SEARCH FOR THAT USER
		if (!checkDocOwner(userCookie, document_id)) return null;
		return repository.findByTitleAndContentAndPositionAndDocumentId(title, content, position, document_id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Section> getSectionsByDocumentId(Long document_id) {
		// TODO Auto-generated method stub
		return repository.findByDocumentId(document_id);
	}

	@Override
	@Transactional
	public Section createSection(String title, String content, Integer position, Long document_id, String userCookie) {
		if (!checkDocOwner(userCookie, document_id)) return null;
		
		// TODO Auto-generated method stub
		Section section = new Section();
		section.setTitle(title);
		section.setContent(content);
		section.setPosition(position);
		
		Document doc = documentRepository.findById(document_id).orElse(null);
		if (doc == null) return null;
		section.setDocument(doc);
		
		repository.save(section);
		return section;
	}

	@Override
	@Transactional
	public Section updateSection(String sectionid, String userCookie, String title, String content, Integer position) {
		// TODO Auto-generated method stub
		Section section = repository.findById(Long.valueOf(sectionid)).orElse(null);
		
		if (!checkDocOwner(userCookie, section.getDocument().getId())) return null;
		
		section.setTitle(title);
		section.setContent(content);
		section.setPosition(position);
		
		repository.save(section);
		return section;
	}

	@Override
	@Transactional
	public Boolean deleteSection(String sectionid, String userCookie) {
		// TODO Auto-generated method stub
		Section section = repository.findById(Long.valueOf(sectionid)).orElse(null);
		
		if(section != null) {
			if (!checkDocOwner(userCookie, section.getDocument().getId())) return false;
			
			repository.delete(section);
			return true;
		} else {
			return false;
		}
	}
	
	private Boolean checkDocOwner(String userCookie, Long docId) {
		Document doc = documentRepository.findById(docId).orElse(null);
		if (doc != null && doc.getUser().getCookie().equals(userCookie)) return true;
		else return false;
	}

}