package com.example.printemps.catalog.domain;

import com.example.printemps.catalog.application.models.CreateWorkRequest;
import jakarta.persistence.*;

@Entity
@Table(name = "work")
@Access(AccessType.FIELD)
public class Work {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long technicalId;

    @Embedded
    private WorkId id;

    @Column(nullable = false, unique = true)
    private String isbn;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String authors;

    @Column(nullable = false)
    private String publisher;

    @Column(nullable = false)
    private Integer publicationYear;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String language;

    @Column(nullable = false)
    private String subject;

    @Column(length = 2000)
    private String description;

    protected Work() {
    }

    public Work(
            WorkId id,
            String isbn,
            String title,
            String authors,
            String publisher,
            Integer publicationYear,
            String category,
            String type,
            String language,
            String subject,
            String description
    ) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.authors = authors;
        this.publisher = publisher;
        this.publicationYear = publicationYear;
        this.category = category;
        this.type = type;
        this.language = language;
        this.subject = subject;
        this.description = description;
    }

    public static Work create(WorkId id, CreateWorkRequest request) {
        return new Work(
                id,
                request.isbn(),
                request.title(),
                request.authors(),
                request.publisher(),
                request.publicationYear(),
                request.category(),
                request.type(),
                request.language(),
                request.subject(),
                request.description()
        );
    }

    public void update(
            String title,
            String authors,
            String publisher,
            Integer publicationYear,
            String category,
            String type,
            String language,
            String subject,
            String description
    ) {
        this.title = title;
        this.authors = authors;
        this.publisher = publisher;
        this.publicationYear = publicationYear;
        this.category = category;
        this.type = type;
        this.language = language;
        this.subject = subject;
        this.description = description;
    }

    public Long getTechnicalId() {
        return technicalId;
    }

    public WorkId getId() {
        return id;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthors() {
        return authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public String getCategory() {
        return category;
    }

    public String getType() {
        return type;
    }

    public String getLanguage() {
        return language;
    }

    public String getSubject() {
        return subject;
    }

    public String getDescription() {
        return description;
    }
}