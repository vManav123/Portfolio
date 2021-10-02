package com.porfolio.service.service;

import com.porfolio.service.model.Document;
import com.porfolio.service.repository.DocumentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Transactional
@Slf4j
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private StorageService storageService;



    public Document getDocument(long id)
    {
        var document = documentRepository.findById(id).orElse(null);
        if(document!=null)
        {
            var dataFile = storageService.downloadFile(document.getDocumentName());
            document.setDocumentFile(dataFile);
        }
        return document;
    }

    public Document insertDocument(Document document)
    {
        var filename = storageService.uploadFile(document.getMultipartFile());
        document.setDocumentName(filename);
        return documentRepository.save(document);
    }

    public String deleteDocument(long id)
    {
        AtomicReference<String> check = new AtomicReference<>(null);
        documentRepository
                .findById(id)
                .ifPresent(document -> {
                    storageService.deleteFile(document.getDocumentName());
                    documentRepository.delete(document);
                    check.set("success");
                });
        return check.get();
    }

    public Document updateDocument(Document document,long id)
    {
        AtomicReference<Document> documentRef = new AtomicReference<>(null);
        documentRepository
                .findById(id)
                .ifPresent(document1 -> {
                    Document doc;
                    if(document1.getDocumentName()!=null && Objects.equals(document.getDocumentName() , document1.getDocumentName().split(",_")[0]))
                    {
                        document.setDocumentId(id);
                        doc = documentRepository.save(document);
                    }
                    else
                    {
                        storageService.deleteFile(document1.getDocumentName());
                        document.setDocumentName(storageService.uploadFile(document.getMultipartFile()));
                        doc = documentRepository.save(document);
                    }
                    documentRef.set(doc);
                });
        return documentRef.get();
    }



}
