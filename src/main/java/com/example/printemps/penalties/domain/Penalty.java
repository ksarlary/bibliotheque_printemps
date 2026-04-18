package com.example.printemps.penalties.domain;

import com.example.printemps.penalties.application.models.CreatePenaltyRequest;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "penalty", indexes = {
        @Index(name = "idx_penalty_user_id", columnList = "user_id"),
        @Index(name = "idx_penalty_status", columnList = "status")
})
@Access(AccessType.FIELD)
public class Penalty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long technicalId;

    @Embedded
    private PenaltyId id;

    @Column(nullable = false)
    private String userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PenaltyType type;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PenaltyStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    protected Penalty() {
    }

    private Penalty(
            PenaltyId id,
            String userId,
            PenaltyType type,
            BigDecimal amount,
            String reason,
            PenaltyStatus status,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.amount = amount;
        this.reason = reason;
        this.status = status;
        this.createdAt = createdAt;
    }

    public static Penalty create(PenaltyId id, CreatePenaltyRequest request) {
        return create(
                id,
                request.userId(),
                request.type(),
                request.amount(),
                request.reason()
        );
    }


    public static Penalty create(
            PenaltyId id,
            String userId,
            PenaltyType type,
            BigDecimal amount,
            String reason
    ) {
        return new Penalty(
                id,
                userId,
                type,
                amount,
                reason,
                PenaltyStatus.ACTIVE,
                LocalDateTime.now()
        );
    }


    public void updateStatus(PenaltyStatus newStatus) {
        if (this.status != PenaltyStatus.ACTIVE) {
            throw new IllegalStateException("Only an ACTIVE penalty can be updated");
        }

        if (newStatus != PenaltyStatus.PAID && newStatus != PenaltyStatus.CANCELLED) {
            throw new IllegalStateException("Penalty can only become PAID or CANCELLED");
        }

        this.status = newStatus;
    }


    public Long getTechnicalId() {
        return technicalId;
    }

    public PenaltyId getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public PenaltyType getType() {
        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getReason() {
        return reason;
    }

    public PenaltyStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}