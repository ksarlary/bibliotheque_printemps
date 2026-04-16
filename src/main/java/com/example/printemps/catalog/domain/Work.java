package com.example.printemps.catalog.domain;

import com.example.printemps.catalog.application.models.CreateWorkRequest;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "work", indexes = {
        @Index(name = "idx_work_isbn", columnList = "isbn")
})
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

    @ElementCollection
    @CollectionTable(name = "work_authors", joinColumns = @JoinColumn(name = "work_technical_id"))
    @Column(name = "author")
    private List<String> authors;

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

    @ElementCollection
    @CollectionTable(name = "work_subjects", joinColumns = @JoinColumn(name = "work_technical_id"))
    @Column(name = "subject")
    private List<String> subjects;

    @Column(length = 2000)
    private String description;

    protected Work() {
    }

    public Work(
            WorkId id,
            String isbn,
            String title,
            List<String> authors,
            String publisher,
            Integer publicationYear,
            String category,
            String type,
            String language,
            List<String> subjects,
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
        this.subjects = subjects;
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
                request.subjects(),
                request.description()
        );
    }

    public void update(
            String title,
            List<String> authors,
            String publisher,
            Integer publicationYear,
            String category,
            String type,
            String language,
            List<String> subjects,
            String description
    ) {
        this.title = title;
        this.authors = authors;
        this.publisher = publisher;
        this.publicationYear = publicationYear;
        this.category = category;
        this.type = type;
        this.language = language;
        this.subjects = subjects;
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

    public List<String> getAuthors() {
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

    public List<String> getSubjects() {
        return subjects;
    }

    public String getDescription() {
        return description;
    }
}