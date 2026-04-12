package com.example.printemps.users.infrastructure.rest;

import com.example.printemps.users.application.models.CreateUserRequest;
import com.example.printemps.users.application.models.UpdatePolicyRequest;
import com.example.printemps.users.application.models.UpdateUserStatusRequest;
import com.example.printemps.users.application.usecases.*;
import com.example.printemps.users.domain.Category;
import com.example.printemps.users.infrastructure.rest.dto.PolicyDTO;
import com.example.printemps.users.infrastructure.rest.dto.UserDTO;
import com.example.printemps.users.infrastructure.rest.mapper.UserMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
class UserController {

    private final CreateUser createUser;
    private final SearchUserById searchUserById;
    private final SearchUsers searchUsers;
    private final UpdateUserStatus updateUserStatus;
    private final SearchPolicyByCategory searchPolicyByCategory;
    private final UpdatePolicy updatePolicy;
    private final UserMapper userMapper;

    UserController(
            CreateUser createUser,
            SearchUserById searchUserById,
            SearchUsers searchUsers,
            UpdateUserStatus updateUserStatus,
            SearchPolicyByCategory searchPolicyByCategory,
            UpdatePolicy updatePolicy,
            UserMapper userMapper
    ) {
        this.createUser = createUser;
        this.searchUserById = searchUserById;
        this.searchUsers = searchUsers;
        this.updateUserStatus = updateUserStatus;
        this.searchPolicyByCategory = searchPolicyByCategory;
        this.updatePolicy = updatePolicy;
        this.userMapper = userMapper;
    }

    @GetMapping
    ResponseEntity<List<UserDTO>> listUsers() {
        return ResponseEntity.ok(userMapper.toUserDTOList(searchUsers.handle()));
    }

    @GetMapping("/{ssoId}")
    ResponseEntity<UserDTO> getUser(@PathVariable String ssoId) {
        return searchUserById.handle(ssoId)
                .map(userMapper::toUserDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAnyRole('LIBRARIAN', 'ADMIN')")
    @PostMapping
    ResponseEntity<Void> createUser(@Valid @RequestBody CreateUserRequest request) {
        String ssoId = createUser.handle(request);
        return ResponseEntity.created(URI.create("/api/users/" + ssoId)).build();
    }

    @PatchMapping("/{ssoId}/status")
    ResponseEntity<Void> updateUserStatus(
            @PathVariable String ssoId,
            @Valid @RequestBody UpdateUserStatusRequest request
    ) {
        updateUserStatus.handle(ssoId, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/policies/{category}")
    ResponseEntity<PolicyDTO> getPolicy(@PathVariable Category category) {
        return searchPolicyByCategory.handle(category)
                .map(userMapper::toPolicyDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/policies/{category}")
    ResponseEntity<Void> updatePolicy(
            @PathVariable Category category,
            @Valid @RequestBody UpdatePolicyRequest request
    ) {
        updatePolicy.handle(category, request);
        return ResponseEntity.ok().build();
    }
}
