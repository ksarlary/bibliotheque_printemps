package com.example.printemps.catalog.infrastructure.rest;

import com.example.printemps.catalog.application.models.AddCopyRequest;
import com.example.printemps.catalog.application.models.CreateWorkRequest;
import com.example.printemps.catalog.application.models.SearchWorksQuery;
import com.example.printemps.catalog.application.models.TransferCopyRequest;
import com.example.printemps.catalog.application.models.UpdateCopyStatusRequest;
import com.example.printemps.catalog.application.models.UpdateWorkRequest;
import com.example.printemps.catalog.application.usecases.AddCopy;
import com.example.printemps.catalog.application.usecases.ArriveCopy;
import com.example.printemps.catalog.application.usecases.CreateWork;
import com.example.printemps.catalog.application.usecases.SearchCopiesByWork;
import com.example.printemps.catalog.application.usecases.SearchSimilarWorks;
import com.example.printemps.catalog.application.usecases.SearchWorkById;
import com.example.printemps.catalog.application.usecases.SearchWorks;
import com.example.printemps.catalog.application.usecases.TransferCopy;
import com.example.printemps.catalog.application.usecases.UpdateCopyStatus;
import com.example.printemps.catalog.application.usecases.UpdateWork;
import com.example.printemps.catalog.domain.Copy;
import com.example.printemps.catalog.domain.CopyId;
import com.example.printemps.catalog.domain.Work;
import com.example.printemps.catalog.domain.WorkId;
import com.example.printemps.catalog.infrastructure.rest.dto.CopyDTO;
import com.example.printemps.catalog.infrastructure.rest.dto.WorkDetailDTO;
import com.example.printemps.catalog.infrastructure.rest.dto.WorkDTO;
import com.example.printemps.catalog.infrastructure.rest.mapper.CatalogMapper;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/catalog")
public class CatalogController {

    private final CreateWork createWork;
    private final AddCopy addCopy;
    private final SearchWorks searchWorks;
    private final SearchWorkById searchWorkById;
    private final SearchCopiesByWork searchCopiesByWork;
    private final SearchSimilarWorks searchSimilarWorks;
    private final UpdateWork updateWork;
    private final UpdateCopyStatus updateCopyStatus;
    private final TransferCopy transferCopy;
    private final ArriveCopy arriveCopy;
    private final CatalogMapper mapper;

    public CatalogController(
            CreateWork createWork,
            AddCopy addCopy,
            SearchWorks searchWorks,
            SearchWorkById searchWorkById,
            SearchCopiesByWork searchCopiesByWork,
            SearchSimilarWorks searchSimilarWorks,
            UpdateWork updateWork,
            UpdateCopyStatus updateCopyStatus,
            TransferCopy transferCopy,
            ArriveCopy arriveCopy,
            CatalogMapper mapper
    ) {
        this.createWork = createWork;
        this.addCopy = addCopy;
        this.searchWorks = searchWorks;
        this.searchWorkById = searchWorkById;
        this.searchCopiesByWork = searchCopiesByWork;
        this.searchSimilarWorks = searchSimilarWorks;
        this.updateWork = updateWork;
        this.updateCopyStatus = updateCopyStatus;
        this.transferCopy = transferCopy;
        this.arriveCopy = arriveCopy;
        this.mapper = mapper;
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    @PostMapping("/works")
    public WorkDTO createWork(@RequestBody CreateWorkRequest request) {
        Work work = createWork.execute(request);
        return mapper.toDto(work);
    }

    @GetMapping("/works")
    public List<WorkDTO> searchWorks(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String language,
            @RequestParam(required = false) String subject,
            @RequestParam(required = false) Boolean availableOnly,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) String location
    ) {
        return searchWorks.execute(new SearchWorksQuery(keyword, isbn, type, language, subject, availableOnly, year, location))
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @GetMapping("/works/{workId}")
    public WorkDetailDTO getWorkById(@PathVariable String workId) {
        WorkId id = new WorkId(workId);
        Work work = searchWorkById.execute(id);
        List<Copy> copies = searchCopiesByWork.execute(id);
        List<Work> similar = searchSimilarWorks.execute(id);
        return mapper.toDetailDto(work, copies, similar);
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    @PutMapping("/works/{workId}")
    public WorkDTO updateWork(
            @PathVariable String workId,
            @RequestBody UpdateWorkRequest request
    ) {
        Work work = updateWork.execute(new WorkId(workId), request);
        return mapper.toDto(work);
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    @PostMapping("/works/{workId}/copies")
    public CopyDTO addCopy(@PathVariable String workId, @RequestBody AddCopyRequest request) {
        Copy copy = addCopy.execute(new WorkId(workId), request);
        return mapper.toDto(copy);
    }

    @GetMapping("/works/{workId}/copies")
    public List<CopyDTO> getCopiesByWork(@PathVariable String workId) {
        return searchCopiesByWork.execute(new WorkId(workId)).stream()
                .map(mapper::toDto)
                .toList();
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    @PatchMapping("/copies/{copyId}/status")
    public CopyDTO updateCopyStatus(
            @PathVariable String copyId,
            @RequestBody UpdateCopyStatusRequest request
    ) {
        Copy copy = updateCopyStatus.execute(new CopyId(copyId), request);
        return mapper.toDto(copy);
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    @PatchMapping("/copies/{copyId}/transfer")
    public CopyDTO transferCopy(
            @PathVariable String copyId,
            @Valid @RequestBody TransferCopyRequest request
    ) {
        Copy copy = transferCopy.execute(new CopyId(copyId), request);
        return mapper.toDto(copy);
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    @PatchMapping("/copies/{copyId}/arrive")
    public CopyDTO arriveCopy(@PathVariable String copyId) {
        Copy copy = arriveCopy.execute(new CopyId(copyId));
        return mapper.toDto(copy);
    }
}