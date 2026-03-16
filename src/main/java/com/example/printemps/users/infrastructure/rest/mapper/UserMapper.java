package com.example.printemps.users.infrastructure.rest.mapper;

import com.example.printemps.users.domain.Policy;
import com.example.printemps.users.domain.User;
import com.example.printemps.users.infrastructure.rest.dto.PolicyDTO;
import com.example.printemps.users.infrastructure.rest.dto.UserDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {

    public UserDTO toUserDTO(User user) {
        return new UserDTO(
                user.getSsoId(),
                user.getCategory().name(),
                user.getStatus().name()
        );
    }

    public PolicyDTO toPolicyDTO(Policy policy) {
        return new PolicyDTO(
                policy.getCategory().name(),
                policy.getMaxLoans(),
                policy.getLoanDurationDays(),
                policy.getMaxRenewals()
        );
    }

    public List<UserDTO> toUserDTOList(List<User> users) {
        return users.stream().map(this::toUserDTO).toList();
    }
}
