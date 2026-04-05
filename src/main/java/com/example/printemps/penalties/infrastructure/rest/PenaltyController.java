package com.example.printemps.penalties.infrastructure.rest;

import com.example.printemps.penalties.application.models.CreatePenaltyRequest;
import com.example.printemps.penalties.application.models.UpdatePenaltyStatusRequest;
import com.example.printemps.penalties.application.usecases.CreatePenalty;
import com.example.printemps.penalties.application.usecases.SearchPenaltiesByUser;
import com.example.printemps.penalties.application.usecases.SearchPenaltyById;
import com.example.printemps.penalties.application.usecases.UpdatePenaltyStatus;
import com.example.printemps.penalties.domain.PenaltyId;
import com.example.printemps.penalties.infrastructure.rest.dto.PenaltyDTO;
import com.example.printemps.penalties.infrastructure.rest.mapper.PenaltyMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/penalties")
class PenaltyController {

    private final CreatePenalty createPenalty;
    private final SearchPenaltyById searchPenaltyById;
    private final SearchPenaltiesByUser searchPenaltiesByUser;
    private final UpdatePenaltyStatus updatePenaltyStatus;
    private final PenaltyMapper penaltyMapper;


    PenaltyController(CreatePenalty createPenalty, SearchPenaltyById searchPenaltyById, SearchPenaltiesByUser searchPenaltiesByUser, UpdatePenaltyStatus updatePenaltyStatus, PenaltyMapper penaltyMapper) {
        this.createPenalty = createPenalty;
        this.searchPenaltyById = searchPenaltyById;
        this.searchPenaltiesByUser = searchPenaltiesByUser;
        this.updatePenaltyStatus = updatePenaltyStatus;
        this.penaltyMapper = penaltyMapper;
    }

    @PostMapping
    ResponseEntity<Void> createPenalty(@Valid @RequestBody CreatePenaltyRequest request) {
        PenaltyId penaltyId = createPenalty.handle(request);
        return ResponseEntity.created(URI.create("/api/penalties/" + penaltyId.value())).build();

    }

    @GetMapping("/{penaltyId}")
    ResponseEntity<PenaltyDTO> getPenalty(@PathVariable String penaltyId) {
        return searchPenaltyById.handle(new PenaltyId(penaltyId))
                .map(penaltyMapper::toPenaltyDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/users/{userId}")
    ResponseEntity<List<PenaltyDTO>> getPenaltiesByUser(@PathVariable String userId) {
        return ResponseEntity.ok(
                penaltyMapper.toPenaltyDTOList(searchPenaltiesByUser.handle(userId))
        );
    }

    @PatchMapping("/{penaltyId}/status")
    ResponseEntity<Void> updatePenaltyStatus(
            @PathVariable String penaltyId,
            @Valid @RequestBody UpdatePenaltyStatusRequest request
    ) {
        updatePenaltyStatus.handle(new PenaltyId(penaltyId), request);
        return ResponseEntity.noContent().build();
    }

}
