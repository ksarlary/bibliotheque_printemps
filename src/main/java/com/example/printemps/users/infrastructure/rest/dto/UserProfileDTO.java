package com.example.printemps.users.infrastructure.rest.dto;

import com.example.printemps.hold.infrastructure.rest.dto.HoldDTO;
import com.example.printemps.loan.infrastructure.rest.dto.LoanDTO;
import com.example.printemps.penalties.infrastructure.rest.dto.PenaltyDTO;

import java.util.List;

public record UserProfileDTO(
        String ssoId,
        String category,
        String status,
        PolicyDTO policy,
        List<LoanDTO> loanHistory,
        List<PenaltyDTO> penalties,
        List<HoldDTO> holds,
        boolean emailNotificationsEnabled,
        boolean reminderNotificationsEnabled
) {
}
