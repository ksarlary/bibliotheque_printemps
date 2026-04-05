package com.example.printemps.hold.infrastructure.rest;

import com.example.printemps.hold.application.models.CreateHoldRequest;
import com.example.printemps.hold.application.usecases.CancelHold;
import com.example.printemps.hold.application.usecases.CreateHold;
import com.example.printemps.hold.application.usecases.SearchHoldsByUserId;
import com.example.printemps.hold.infrastructure.rest.dto.HoldDTO;
import com.example.printemps.hold.infrastructure.rest.mapper.HoldMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.jspecify.annotations.NonNull;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/holds")
public class HoldController {

    private final CreateHold createHold;
    private final SearchHoldsByUserId searchHoldsByUserId;
    private final CancelHold cancelHold;
    private final HoldMapper holdMapper;

    public HoldController(
            CreateHold createHold,
            SearchHoldsByUserId searchHoldsByUserId,
            CancelHold cancelHold,
            HoldMapper holdMapper
    ) {
        this.createHold = createHold;
        this.searchHoldsByUserId = searchHoldsByUserId;
        this.cancelHold = cancelHold;
        this.holdMapper = holdMapper;
    }

    @PostMapping
    ResponseEntity<@NonNull Void> createHold(@Valid @RequestBody final CreateHoldRequest request) {
        final var holdId = createHold.handle(request);
        return ResponseEntity.created(URI.create("/api/holds/" + holdId.value())).build();
    }

    @GetMapping("/user/{userId}")
    ResponseEntity<List<HoldDTO>> getHoldsByUser(@PathVariable String userId) {
        return ResponseEntity.ok(holdMapper.toDTOList(searchHoldsByUserId.handle(userId)));
    }

    @PatchMapping("/{holdId}/cancel")
    ResponseEntity<@NonNull Void> cancelHold(@PathVariable String holdId) {
        cancelHold.handle(holdId);
        return ResponseEntity.ok().build();
    }
}
