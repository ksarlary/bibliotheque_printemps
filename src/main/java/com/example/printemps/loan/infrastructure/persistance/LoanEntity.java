package com.example.printemps.loan.infrastructure.persistance;

import com.example.printemps.loan.domain.LoanId;
import com.example.printemps.loan.domain.LoanStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "loan")
@Access(AccessType.FIELD)
public class LoanEntity {

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

    protected LoanEntity() {
    }

    public LoanEntity(
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