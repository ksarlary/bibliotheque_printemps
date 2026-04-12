package com.example.printemps.users.application.services;

import com.example.printemps.hold.application.gateways.HoldRepository;
import com.example.printemps.hold.infrastructure.rest.dto.HoldDTO;
import com.example.printemps.hold.infrastructure.rest.mapper.HoldMapper;
import com.example.printemps.loan.application.gateways.LoanRepository;
import com.example.printemps.loan.infrastructure.rest.dto.LoanDTO;
import com.example.printemps.loan.infrastructure.rest.mapper.LoanMapper;
import com.example.printemps.penalties.application.gateways.PenaltyRepository;
import com.example.printemps.penalties.infrastructure.rest.dto.PenaltyDTO;
import com.example.printemps.penalties.infrastructure.rest.mapper.PenaltyMapper;
import com.example.printemps.users.application.gateways.PolicyRepository;
import com.example.printemps.users.application.gateways.UserRepository;
import com.example.printemps.users.application.usecases.SearchUserProfile;
import com.example.printemps.users.domain.Policy;
import com.example.printemps.users.domain.User;
import com.example.printemps.users.infrastructure.rest.dto.PolicyDTO;
import com.example.printemps.users.infrastructure.rest.dto.UserProfileDTO;
import com.example.printemps.users.infrastructure.rest.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SearchUserProfileHandler implements SearchUserProfile {

    private final UserRepository userRepository;
    private final PolicyRepository policyRepository;
    private final LoanRepository loanRepository;
    private final PenaltyRepository penaltyRepository;
    private final HoldRepository holdRepository;
    private final UserMapper userMapper;
    private final LoanMapper loanMapper;
    private final PenaltyMapper penaltyMapper;
    private final HoldMapper holdMapper;

    public SearchUserProfileHandler(
            UserRepository userRepository,
            PolicyRepository policyRepository,
            LoanRepository loanRepository,
            PenaltyRepository penaltyRepository,
            HoldRepository holdRepository,
            UserMapper userMapper,
            LoanMapper loanMapper,
            PenaltyMapper penaltyMapper,
            HoldMapper holdMapper
    ) {
        this.userRepository = userRepository;
        this.policyRepository = policyRepository;
        this.loanRepository = loanRepository;
        this.penaltyRepository = penaltyRepository;
        this.holdRepository = holdRepository;
        this.userMapper = userMapper;
        this.loanMapper = loanMapper;
        this.penaltyMapper = penaltyMapper;
        this.holdMapper = holdMapper;
    }

    @Override
    public UserProfileDTO handle(String ssoId) {
        User user = userRepository.findById(ssoId)
                .orElseThrow(() -> new NoSuchElementException("User not found: " + ssoId));

        Policy policy = policyRepository.findById(user.getCategory())
                .orElseThrow(() -> new NoSuchElementException("Policy not found for category: " + user.getCategory()));

        PolicyDTO policyDTO = userMapper.toPolicyDTO(policy);

        List<LoanDTO> loanHistory = loanMapper.toDTOList(loanRepository.findByUserId(ssoId));
        List<PenaltyDTO> penalties = penaltyMapper.toPenaltyDTOList(penaltyRepository.findByUserId(ssoId));
        List<HoldDTO> holds = holdMapper.toDTOList(holdRepository.findByUserId(ssoId));

        return new UserProfileDTO(
                user.getSsoId(),
                user.getCategory().name(),
                user.getStatus().name(),
                policyDTO,
                loanHistory,
                penalties,
                holds,
                user.isEmailNotificationsEnabled(),
                user.isReminderNotificationsEnabled()
        );
    }
}
