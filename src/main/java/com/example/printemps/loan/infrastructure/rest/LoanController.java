package com.example.printemps.loan.infrastructure.rest;

import com.example.printemps.loan.application.models.CheckoutLoanRequest;
import com.example.printemps.loan.application.models.RenewLoanRequest;
import com.example.printemps.loan.application.models.ReturnLoanRequest;
import com.example.printemps.loan.application.usecases.CheckoutLoan;
import com.example.printemps.loan.application.usecases.RenewLoan;
import com.example.printemps.loan.application.usecases.ReturnLoan;
import com.example.printemps.loan.application.usecases.SearchLoanById;
import com.example.printemps.loan.application.usecases.SearchLoansByUser;
import com.example.printemps.loan.domain.Loan;
import com.example.printemps.loan.domain.LoanId;
import com.example.printemps.loan.infrastructure.rest.dto.LoanDTO;
import com.example.printemps.loan.infrastructure.rest.mapper.LoanMapper;
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
    private final LoanMapper mapper;

    public LoanController(
            CheckoutLoan checkoutLoan,
            ReturnLoan returnLoan,
            RenewLoan renewLoan,
            SearchLoanById searchLoanById,
            SearchLoansByUser searchLoansByUser,
            LoanMapper mapper
    ) {
        this.checkoutLoan = checkoutLoan;
        this.returnLoan = returnLoan;
        this.renewLoan = renewLoan;
        this.searchLoanById = searchLoanById;
        this.searchLoansByUser = searchLoansByUser;
        this.mapper = mapper;
    }

    @PostMapping("/checkout")
    public LoanDTO checkout(@RequestBody CheckoutLoanRequest request) {
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
}
