package com.example.printemps.catalog.infrastructure.persistance;

import com.example.printemps.catalog.domain.WorkId;
import jakarta.persistence.*;

@Entity
@Table(name = "work")
@Access(AccessType.FIELD)
public class WorkEntity {

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

    @Column
    private String publisher;

    @Column(nullable = false)
    private Integer publicationYear;

    @Column
    private String category;

    @Column
    private String type;

    @Column
    private String language;

    @Column
    private String subject;

    @Column(length = 2000)
    private String description;

    protected WorkEntity() {
    }

    public WorkEntity(
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