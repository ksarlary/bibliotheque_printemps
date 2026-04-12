package com.example.printemps.catalog.infrastructure.persistance;

import com.example.printemps.catalog.domain.Copy;
import com.example.printemps.catalog.domain.Work;
import com.example.printemps.catalog.domain.WorkId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SpringJpaWorkRepository extends JpaRepository<Work, Long> {

    Optional<Work> findById(WorkId id);

    @Query("""
            SELECT DISTINCT w FROM Work w
            WHERE (:keyword IS NULL
                   OR LOWER(w.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
                   OR EXISTS (SELECT a FROM w.authors a WHERE LOWER(a) LIKE LOWER(CONCAT('%', :keyword, '%'))))
            AND (:isbn IS NULL OR w.isbn = :isbn)
            AND (:type IS NULL OR w.type = :type)
            AND (:language IS NULL OR LOWER(w.language) = LOWER(:language))
            AND (:year IS NULL OR w.publicationYear = :year)
            AND (:subject IS NULL OR EXISTS (SELECT s FROM w.subjects s WHERE LOWER(s) = LOWER(:subject)))
            AND (:location IS NULL OR EXISTS (SELECT c FROM Copy c WHERE c.work = w AND LOWER(c.location) LIKE LOWER(CONCAT('%', :location, '%'))))
            """)
    List<Work> searchWithFilters(
            @Param("keyword") String keyword,
            @Param("isbn") String isbn,
            @Param("type") String type,
            @Param("language") String language,
            @Param("year") Integer year,
            @Param("subject") String subject,
            @Param("location") String location
    );

    @Query("""
            SELECT DISTINCT w FROM Work w
            WHERE w.id.value <> :workIdValue
            AND (EXISTS (SELECT s FROM w.subjects s WHERE s IN :subjects)
                 OR EXISTS (SELECT a FROM w.authors a WHERE a IN :authors))
            """)
    List<Work> findSimilarTo(
            @Param("workIdValue") String workIdValue,
            @Param("subjects") List<String> subjects,
            @Param("authors") List<String> authors,
            Pageable pageable
    );
}