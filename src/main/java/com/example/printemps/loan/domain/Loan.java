package com.example.printemps.loan.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "loan")
@Access(AccessType.FIELD)
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long technicalId;

    @Embedded
    private LoanId id;

    @Column(nullable = false)
    private String copyId;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private LocalDateTime startAt;

    @Column(nullable = false)
    private LocalDateTime dueAt;

    @Column
    private LocalDateTime returnedAt;

    @Column(nullable = false)
    private Integer renewCount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LoanStatus status;

    protected Loan() {
    }

    private Loan(
            LoanId id,
            String copyId,
            String userId,
            LocalDateTime startAt,
            LocalDateTime dueAt,
            LocalDateTime returnedAt,
            Integer renewCount,
            LoanStatus status
    ) {
        this.id = id;
        this.copyId = copyId;
        this.userId = userId;
        this.startAt = startAt;
        this.dueAt = dueAt;
        this.returnedAt = returnedAt;
        this.renewCount = renewCount;
        this.status = status;
    }

    public static Loan create(LoanId id, String copyId, String userId, int loanDurationDays) {
        LocalDateTime now = LocalDateTime.now();

        return new Loan(
                id,
                copyId,
                userId,
                now,
                now.plusDays(loanDurationDays),
                null,
                0,
                LoanStatus.ACTIVE
        );
    }

    public static Loan restore(
            LoanId id,
            String copyId,
            String userId,
            LocalDateTime startAt,
            LocalDateTime dueAt,
            LocalDateTime returnedAt,
            Integer renewCount,
            LoanStatus status
    ) {
        return new Loan(id, copyId, userId, startAt, dueAt, returnedAt, renewCount, status);
    }

    public void markAsReturned() {
        if (this.status == LoanStatus.RETURNED) {
            throw new IllegalStateException("Loan already returned");
        }

        this.returnedAt = LocalDateTime.now();
        this.status = LoanStatus.RETURNED;
    }

    public void renew(int extraDays) {
        if (this.status != LoanStatus.ACTIVE) {
            throw new IllegalStateException("Only active loans can be renewed");
        }

        this.dueAt = this.dueAt.plusDays(extraDays);
        this.renewCount++;
    }

    public void markAsOverdue() {
        if (this.status == LoanStatus.ACTIVE && LocalDateTime.now().isAfter(this.dueAt)) {
            this.status = LoanStatus.OVERDUE;
        }
    }

    public void markAsLost() {
        this.status = LoanStatus.LOST_DECLARED;
    }


    public Long getTechnicalId() {
        return technicalId;
    }

    public LoanId getId() {
        return id;
    }

    public String getCopyId() {
        return copyId;
    }

    public String getUserId() {
        return userId;
    }

    public LocalDateTime getStartAt() {
        return startAt;
    }

    public LocalDateTime getDueAt() {
        return dueAt;
    }

    public LocalDateTime getReturnedAt() {
        return returnedAt;
    }

    public Integer getRenewCount() {
        return renewCount;
    }

    public LoanStatus getStatus() {
        return status;
    }
}