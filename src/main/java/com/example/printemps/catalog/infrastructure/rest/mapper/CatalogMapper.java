package com.example.printemps.catalog.infrastructure.rest.mapper;

import com.example.printemps.catalog.domain.Copy;
import com.example.printemps.catalog.domain.Work;
import com.example.printemps.catalog.infrastructure.rest.dto.CopyDTO;
import com.example.printemps.catalog.infrastructure.rest.dto.WorkDTO;
import org.springframework.stereotype.Component;

@Component
public class CatalogMapper {

    public WorkDTO toDto(Work work) {
        return new WorkDTO(
                work.getId().value(),
                work.getIsbn(),
                work.getTitle(),
                work.getAuthors(),
                work.getPublisher(),
                work.getPublicationYear(),
                work.getCategory(),
                work.getType(),
                work.getLanguage(),
                work.getSubjects(),
                work.getDescription()
        );
    }

    public CopyDTO toDto(Copy copy) {
        return new CopyDTO(
                copy.getId().value(),
                copy.getWork().getId().value(),
                copy.getBarcode(),
                copy.getStatus().name(),
                copy.getLocation()
        );
    }
}