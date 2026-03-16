package com.example.printemps.users.domain;

import jakarta.persistence.*;

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

    protected Policy() {
    }

    private Policy(Category category, int maxLoans, int loanDurationDays, int maxRenewals) {
        this.category = category;
        this.maxLoans = maxLoans;
        this.loanDurationDays = loanDurationDays;
        this.maxRenewals = maxRenewals;
    }

    public static Policy create(Category category, int maxLoans, int loanDurationDays, int maxRenewals) {
        return new Policy(category, maxLoans, loanDurationDays, maxRenewals);
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

    public void update(int maxLoans, int loanDurationDays, int maxRenewals) {
        this.maxLoans = maxLoans;
        this.loanDurationDays = loanDurationDays;
        this.maxRenewals = maxRenewals;
    }
}
