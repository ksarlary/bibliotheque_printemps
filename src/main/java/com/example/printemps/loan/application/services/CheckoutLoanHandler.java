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
import com.example.printemps.shared.DomainIdGenerator;
import com.example.printemps.shared.error.BusinessException;
import com.example.printemps.users.application.gateways.PolicyRepository;
import com.example.printemps.users.application.gateways.UserRepository;
import com.example.printemps.users.domain.Policy;
import com.example.printemps.users.domain.Status;
import com.example.printemps.users.domain.User;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;


@Service
public class CheckoutLoanHandler implements CheckoutLoan {

    private static final Logger log = LoggerFactory.getLogger(CheckoutLoanHandler.class);
    private final LoanRepository loanRepository;
    private final CopyRepository copyRepository;
    private final UserRepository userRepository;
    private final PolicyRepository policyRepository;
    private final HoldRepository holdRepository;
    private final DomainIdGenerator idGenerator;

    public CheckoutLoanHandler(
            LoanRepository loanRepository,
            CopyRepository copyRepository,
            UserRepository userRepository,
            PolicyRepository policyRepository,
            HoldRepository holdRepository,
            DomainIdGenerator idGenerator
    ) {
        this.loanRepository = loanRepository;
        this.copyRepository = copyRepository;
        this.userRepository = userRepository;
        this.policyRepository = policyRepository;
        this.holdRepository = holdRepository;
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

        validateUserCanCheckout(user);

        // Cherche d'abord un hold READY_FOR_PICKUP sur cet exemplaire précis
        Optional<Hold> readyHoldForUser = holdRepository.findByCopyIdAndUserIdAndStatus(
                copy.getId().value(),
                request.userId(),
                HoldStatus.READY_FOR_PICKUP
        );
        // Sinon, cherche un hold READY_FOR_PICKUP sur l'oeuvre en général
        if (readyHoldForUser.isEmpty()) {
            readyHoldForUser = holdRepository.findByWorkIdAndUserIdAndStatus(
                    copy.getWork().getId().value(),
                    request.userId(),
                    HoldStatus.READY_FOR_PICKUP
            );
        }

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

        log.info("[AUDIT] CHECKOUT loanId={} copyId={} userId={} dueAt={} reservationPickup={}",
                saved.getId().value(), request.copyId(), request.userId(), saved.getDueAt(), isReservationPickup);

        return saved;
    }

    private void validateUserCanCheckout(User user) {
        if (user.getStatus() == Status.BLOCKED || user.getStatus() == Status.SUSPENDED) {
            throw new BusinessException("New loan not allowed: user account is " + user.getStatus());
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