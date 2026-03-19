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
    private String author;

    @Column(nullable = false)
    private String publisher;

    @Column(nullable = false)
    private Integer publicationYear;

    @Column(nullable = false)
    private String category;

    @Column(length = 2000)
    private String description;

    protected Work() {
    }

    private Work(
            WorkId id,
            String isbn,
            String title,
            String author,
            String publisher,
            Integer publicationYear,
            String category,
            String description
    ) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publicationYear = publicationYear;
        this.category = category;
        this.description = description;
    }

    public static Work create(WorkId id, CreateWorkRequest request) {
        return new Work(
                id,
                request.isbn(),
                request.title(),
                request.author(),
                request.publisher(),
                request.publicationYear(),
                request.category(),
                request.description()
        );
    }

    public void update(
            String title,
            String author,
            String publisher,
            Integer publicationYear,
            String category,
            String description
    ) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publicationYear = publicationYear;
        this.category = category;
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

    public String getAuthor() {
        return author;
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

    public String getDescription() {
        return description;
    }
}