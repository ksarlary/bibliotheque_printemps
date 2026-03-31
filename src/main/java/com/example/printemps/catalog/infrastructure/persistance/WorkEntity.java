package com.example.printemps.catalog.infrastructure.persistance;

import com.example.printemps.catalog.domain.WorkId;
import jakarta.persistence.*;

import java.util.List;

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

    @ElementCollection
    @CollectionTable(name = "work_authors", joinColumns = @JoinColumn(name = "work_technical_id"))
    @Column(name = "author")
    private List<String> authors;

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

    @ElementCollection
    @CollectionTable(name = "work_subjects", joinColumns = @JoinColumn(name = "work_technical_id"))
    @Column(name = "subject")
    private List<String> subjects;

    @Column(length = 2000)
    private String description;

    protected WorkEntity() {
    }

    public WorkEntity(
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