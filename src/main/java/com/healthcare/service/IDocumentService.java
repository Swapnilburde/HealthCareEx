package com.healthcare.service;

import java.util.List;

import com.healthcare.entity.Document;

public interface IDocumentService {

	void saveDocument(Document doc);
	List<Object[]> getDocumentIdAndName();
	void deleteDocumentById(Long id);
	Document getDocumentById(Long id);
	List<Document> getAllImgs();
}
