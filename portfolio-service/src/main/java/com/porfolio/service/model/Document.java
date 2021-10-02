package com.porfolio.service.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.File;
import java.io.InputStream;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "document")
public class Document {
    @Id
    @Column(name = "document_id")
    private Long documentId;
    @Column(name = "document_name")
    private String documentName;
    @Column(name = "document_type")
    private String documentType;
    @Column(name = "upload_date_time")
    private LocalDateTime uploadDateTime;
    @Transient
    private InputStream documentFile;
    @Transient
    private MultipartFile multipartFile;
}
