package com.example.printemps.users.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "policy")
@Access(AccessType.FIELD)
public class Policy {

    @Id
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private int maxLoans;

    @Column(nullable = false)
    private int loanDurationDays;

    @Column(nullable = false)
    private int maxRenewals;

    @Column(nullable = false)
    private int blockAfterDaysLate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LateFeeMode lateFeeMode;

    @Column(nullable = false)
    private BigDecimal lateFeeAmount;

    protected Policy() {
    }

    private Policy(Category category, int maxLoans, int loanDurationDays, int maxRenewals, int blockAfterDaysLate, LateFeeMode lateFeeMode, BigDecimal lateFeeAmount) {
        this.category = category;
        this.maxLoans = maxLoans;
        this.loanDurationDays = loanDurationDays;
        this.maxRenewals = maxRenewals;
        this.blockAfterDaysLate = blockAfterDaysLate;
        this.lateFeeMode = lateFeeMode;
        this.lateFeeAmount = lateFeeAmount;

    }

    public static Policy create(Category category, int maxLoans, int loanDurationDays, int maxRenewals, int blockAfterDaysLate, LateFeeMode lateFeeMode, BigDecimal lateFeeAmount) {
        return new Policy(category, maxLoans, loanDurationDays, maxRenewals, blockAfterDaysLate, lateFeeMode, lateFeeAmount);
    }

    public Category getCategory() {
        return category;
    }

    public int getMaxLoans() {
        return maxLoans;
    }

    public int getLoanDurationDays() {
        return loanDurationDays;
    }

    public int getMaxRenewals() {
        return maxRenewals;
    }

    public int getBlockAfterDaysLate() { return blockAfterDaysLate;}

    public LateFeeMode getLateFeeMode() { return lateFeeMode;}

    public BigDecimal getLateFeeAmount() {
        return lateFeeAmount;
    }

    public void update(int maxLoans, int loanDurationDays, int maxRenewals, int blockAfterDaysLate, LateFeeMode lateFeeMode, BigDecimal lateFeeAmount) {
        this.maxLoans = maxLoans;
        this.loanDurationDays = loanDurationDays;
        this.maxRenewals = maxRenewals;
        this.blockAfterDaysLate = blockAfterDaysLate;
        this.lateFeeMode = lateFeeMode;
        this.lateFeeAmount = lateFeeAmount;
    }
}
