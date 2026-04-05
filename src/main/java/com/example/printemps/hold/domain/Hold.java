package com.example.printemps.hold.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "hold")
@Access(AccessType.FIELD)
public class Hold {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long technicalId;

    @Embedded
    private HoldId id;

    @Column(name = "work_id", nullable = false)
    private String workId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private HoldStatus status;

    @Column(name = "queue_position", nullable = false)
    private int queuePosition;

    @Column(name = "pickup_until")
    private LocalDateTime pickupUntil;

    protected Hold() {
    }

    private Hold(HoldId id, String workId, String userId, int queuePosition) {
        this.id = id;
        this.workId = workId;
        this.userId = userId;
        this.status = HoldStatus.REQUESTED;
        this.queuePosition = queuePosition;
        this.pickupUntil = null;
    }

    public static Hold create(HoldId id, String workId, String userId, int queuePosition) {
        return new Hold(id, workId, userId, queuePosition);
    }

    public HoldId getId() {
        return id;
    }

    public String getWorkId() {
        return workId;
    }

    public String getUserId() {
        return userId;
    }

    public HoldStatus getStatus() {
        return status;
    }

    public int getQueuePosition() {
        return queuePosition;
    }

    public LocalDateTime getPickupUntil() {
        return pickupUntil;
    }

    public void cancel() {
        if (status == HoldStatus.PICKED_UP || status == HoldStatus.EXPIRED) {
            throw new IllegalStateException("This hold cannot be cancelled");
        }
        this.status = HoldStatus.CANCELLED;
    }

    public void readyForPickup(LocalDateTime pickupUntil) {
        this.status = HoldStatus.READY_FOR_PICKUP;
        this.pickupUntil = pickupUntil;
    }

    public void markPickedUp() {
        this.status = HoldStatus.PICKED_UP;
    }

    public void expire() {
        this.status = HoldStatus.EXPIRED;
    }
}