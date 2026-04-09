package com.example.printemps.loan.application.services;

import com.example.printemps.catalog.application.gateways.CopyRepository;
import com.example.printemps.catalog.domain.CopyId;
import com.example.printemps.catalog.domain.CopyStatus;
import com.example.printemps.catalog.domain.Copy;
import com.example.printemps.hold.application.gateways.HoldRepository;
import com.example.printemps.hold.domain.Hold;
import com.example.printemps.hold.domain.HoldStatus;
import com.example.printemps.loan.application.gateways.LoanRepository;
import com.example.printemps.loan.application.models.CheckoutLoanRequest;
import com.example.printemps.loan.application.usecases.CheckoutLoan;
import com.example.printemps.loan.domain.Loan;
import com.example.printemps.loan.domain.LoanId;
import com.example.printemps.penalties.application.gateways.PenaltyRepository;
import com.example.printemps.shared.DomainIdGenerator;
import com.example.printemps.shared.error.BusinessException;
import com.example.printemps.users.application.gateways.PolicyRepository;
import com.example.printemps.users.application.gateways.UserRepository;
import com.example.printemps.users.domain.Policy;
import com.example.printemps.users.domain.Status;
import com.example.printemps.users.domain.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;


@Service
public class CheckoutLoanHandler implements CheckoutLoan {

    private final LoanRepository loanRepository;
    private final CopyRepository copyRepository;
    private final UserRepository userRepository;
    private final PolicyRepository policyRepository;
    private final HoldRepository holdRepository;
    private final PenaltyRepository penaltyRepository;
    private final DomainIdGenerator idGenerator;

    public CheckoutLoanHandler(
            LoanRepository loanRepository,
            CopyRepository copyRepository,
            UserRepository userRepository,
            PolicyRepository policyRepository,
            HoldRepository holdRepository,
            PenaltyRepository penaltyRepository,
            DomainIdGenerator idGenerator
    ) {
        this.loanRepository = loanRepository;
        this.copyRepository = copyRepository;
        this.userRepository = userRepository;
        this.policyRepository = policyRepository;
        this.holdRepository = holdRepository;
        this.penaltyRepository = penaltyRepository;
        this.idGenerator = idGenerator;
    }

    @Override
    @Transactional
    public Loan execute(CheckoutLoanRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new NoSuchElementException("User not found: " + request.userId()));

        Policy policy = policyRepository.findById(user.getCategory())
                .orElseThrow(() -> new NoSuchElementException("Policy not found for category: " + user.getCategory()));

        Copy copy = copyRepository.findById(new CopyId(request.copyId()))
                .orElseThrow(() -> new NoSuchElementException("Copy not found: " + request.copyId()));

        validateUserCanCheckout(user, request.userId());

        Optional<Hold> readyHoldForUser = holdRepository.findByWorkIdAndUserIdAndStatus(
                copy.getWork().getId().value(),
                request.userId(),
                HoldStatus.READY_FOR_PICKUP
        );

        boolean isReservationPickup = isReservationPickup(copy, readyHoldForUser);
        validateCopyCanBeCheckedOut(copy, request.copyId(), isReservationPickup);

        long activeLoans = loanRepository.findActiveByUserId(request.userId()).size();
        if (activeLoans >= policy.getMaxLoans()) {
            throw new BusinessException("User has reached their loan quota of " + policy.getMaxLoans());
        }

        Loan loan = Loan.create(
                new LoanId(idGenerator.generate()),
                request.copyId(),
                request.userId(),
                policy.getLoanDurationDays()
        );
        Loan saved = loanRepository.save(loan);

        if (isReservationPickup) {
            Hold hold = readyHoldForUser.orElseThrow(
                    () -> new IllegalStateException("Expected a READY_FOR_PICKUP hold for this user")
            );
            hold.markPickedUp();
            holdRepository.save(hold);
        }

        copy.updateStatus(CopyStatus.ON_LOAN);
        copyRepository.save(copy);

        return saved;
    }

    private void validateUserCanCheckout(User user, String userId) {
        if (user.getStatus() == Status.BLOCKED || user.getStatus() == Status.SUSPENDED) {
            throw new BusinessException("New loan not allowed: user account is " + user.getStatus());
        }

        if (!penaltyRepository.findActiveByUserId(userId).isEmpty()) {
            throw new BusinessException("New loan not allowed: user has active penalties");
        }
    }

    private boolean isReservationPickup(Copy copy, Optional<Hold> readyHoldForUser) {
        return copy.getStatus() == CopyStatus.RESERVED && readyHoldForUser.isPresent();
    }

    private void validateCopyCanBeCheckedOut(Copy copy, String copyId, boolean isReservationPickup) {
        boolean isNormalCheckout = copy.getStatus() == CopyStatus.AVAILABLE;

        if (!isNormalCheckout && !isReservationPickup) {
            if (copy.getStatus() == CopyStatus.RESERVED) {
                throw new BusinessException("Copy is reserved for another user or not ready for pickup");
            }
            throw new BusinessException("Copy is not available for checkout: " + copyId);
        }
    }

}