package com.example.printemps.hold.infrastructure.rest;

import com.example.printemps.hold.application.models.CreateHoldRequest;
import com.example.printemps.hold.application.usecases.CancelHold;
import com.example.printemps.hold.application.usecases.CreateHold;
import com.example.printemps.hold.application.usecases.SearchHoldsByUserId;
import com.example.printemps.hold.infrastructure.rest.dto.HoldDTO;
import com.example.printemps.hold.infrastructure.rest.mapper.HoldMapper;
import com.example.printemps.shared.error.BusinessException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

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

    @PreAuthorize("hasAnyRole('READER', 'LIBRARIAN', 'ADMIN')")
    @PostMapping
    ResponseEntity<Void> createHold(@Valid @RequestBody final CreateHoldRequest request, Authentication authentication) {
        String currentUser = authentication.getName();
        boolean isReader = authentication.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_READER"));

        if (isReader && !currentUser.equals(request.userId())) {
            throw new BusinessException("You cannot create a hold for another user");
        }
        final var holdId = createHold.handle(request);
        return ResponseEntity.created(URI.create("/api/holds/" + holdId.value())).build();
    }

    @GetMapping("/user/{userId}")
    ResponseEntity<List<HoldDTO>> getHoldsByUser(@PathVariable String userId) {
        return ResponseEntity.ok(holdMapper.toDTOList(searchHoldsByUserId.handle(userId)));
    }

    @PatchMapping("/{holdId}/cancel")
    ResponseEntity<Void> cancelHold(@PathVariable String holdId) {
        cancelHold.handle(holdId);
        return ResponseEntity.ok().build();
    }
}
