package com.example.printemps.catalog.infrastructure.rest.mapper;

import com.example.printemps.catalog.domain.Copy;
import com.example.printemps.catalog.domain.Work;
import com.example.printemps.catalog.infrastructure.rest.dto.CopyDTO;
import com.example.printemps.catalog.infrastructure.rest.dto.WorkDTO;
import com.example.printemps.catalog.infrastructure.rest.dto.WorkDetailDTO;
import org.springframework.stereotype.Component;

import java.util.List;

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
                copy.getLocation(),
                copy.getAcquiredAt()
        );
    }

    public WorkDetailDTO toDetailDto(Work work, List<Copy> copies, List<Work> similarWorks) {
        return new WorkDetailDTO(
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
                work.getDescription(),
                copies.stream().map(this::toDto).toList(),
                similarWorks.stream().map(this::toDto).toList()
        );
    }
}