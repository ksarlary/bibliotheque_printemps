package com.example.printemps.loan.infrastructure.rest;

import com.example.printemps.loan.application.models.CheckoutLoanRequest;
import com.example.printemps.loan.application.models.DeclareDamagedRequest;
import com.example.printemps.loan.application.models.DeclareLostRequest;
import com.example.printemps.loan.application.models.RenewLoanRequest;
import com.example.printemps.loan.application.models.ReturnLoanRequest;
import com.example.printemps.loan.application.usecases.CheckoutLoan;
import com.example.printemps.loan.application.usecases.DeclareDamagedLoan;
import com.example.printemps.loan.application.usecases.DeclareLostLoan;
import com.example.printemps.loan.application.usecases.RenewLoan;
import com.example.printemps.loan.application.usecases.ReturnLoan;
import com.example.printemps.loan.application.usecases.SearchLoanById;
import com.example.printemps.loan.application.usecases.SearchLoansByUser;
import com.example.printemps.loan.domain.Loan;
import com.example.printemps.loan.domain.LoanId;
import com.example.printemps.loan.infrastructure.rest.dto.LoanDTO;
import com.example.printemps.loan.infrastructure.rest.mapper.LoanMapper;

import com.example.printemps.shared.error.BusinessException;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final CheckoutLoan checkoutLoan;
    private final ReturnLoan returnLoan;
    private final RenewLoan renewLoan;
    private final SearchLoanById searchLoanById;
    private final SearchLoansByUser searchLoansByUser;
    private final DeclareLostLoan declareLostLoan;
    private final DeclareDamagedLoan declareDamagedLoan;
    private final LoanMapper mapper;

    public LoanController(
            CheckoutLoan checkoutLoan,
            ReturnLoan returnLoan,
            RenewLoan renewLoan,
            SearchLoanById searchLoanById,
            SearchLoansByUser searchLoansByUser,
            DeclareLostLoan declareLostLoan,
            DeclareDamagedLoan declareDamagedLoan,
            LoanMapper mapper
    ) {
        this.checkoutLoan = checkoutLoan;
        this.returnLoan = returnLoan;
        this.renewLoan = renewLoan;
        this.searchLoanById = searchLoanById;
        this.searchLoansByUser = searchLoansByUser;
        this.declareLostLoan = declareLostLoan;
        this.declareDamagedLoan = declareDamagedLoan;
        this.mapper = mapper;
    }

    @PostMapping("/checkout")
    public LoanDTO checkout(@RequestBody CheckoutLoanRequest request, Authentication authentication) {
        String currentUser = authentication.getName();
        boolean isReader = authentication.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_READER"));
        if (isReader && !currentUser.equals(request.userId())) {
            throw new BusinessException("You cannot loan for another user");
        }
        Loan loan = checkoutLoan.execute(request);
        return mapper.toDto(loan);
    }

    @PostMapping("/return")
    public LoanDTO returnLoan(@RequestBody ReturnLoanRequest request) {
        Loan loan = returnLoan.execute(request);
        return mapper.toDto(loan);
    }

    @PostMapping("/renew")
    public LoanDTO renew(@RequestBody RenewLoanRequest request) {
        Loan loan = renewLoan.execute(request);
        return mapper.toDto(loan);
    }

    @GetMapping("/{loanId}")
    public LoanDTO getById(@PathVariable String loanId) {
        Loan loan = searchLoanById.execute(new LoanId(loanId));
        return mapper.toDto(loan);
    }

    @GetMapping("/users/{userId}")
    public List<LoanDTO> getByUser(@PathVariable String userId) {
        return searchLoansByUser.execute(userId)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    @PostMapping("/{loanId}/declare-lost")
    public LoanDTO declareLost(
            @PathVariable String loanId,
            @Valid @RequestBody DeclareLostRequest request
    ) {
        Loan loan = declareLostLoan.execute(loanId, request);
        return mapper.toDto(loan);
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    @PostMapping("/{loanId}/declare-damaged")
    public LoanDTO declareDamaged(
            @PathVariable String loanId,
            @Valid @RequestBody DeclareDamagedRequest request
    ) {
        Loan loan = declareDamagedLoan.execute(loanId, request);
        return mapper.toDto(loan);
    }
}
