package com.example.printemps.hold.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "hold", indexes = {
        @Index(name = "idx_hold_work_id", columnList = "work_id"),
        @Index(name = "idx_hold_copy_id", columnList = "copy_id"),
        @Index(name = "idx_hold_user_id", columnList = "user_id"),
        @Index(name = "idx_hold_status", columnList = "status"),
        @Index(name = "idx_hold_pickup_until", columnList = "pickup_until")
})
@Access(AccessType.FIELD)
public class Hold {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long technicalId;

    @Embedded
    private HoldId id;

    @Column(name = "work_id", nullable = false)
    private String workId;

    @Column(name = "copy_id")
    private String copyId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HoldStatus status;

    @Column(name = "queue_position", nullable = false)
    private int queuePosition;

    @Column(name = "pickup_until")
    private LocalDateTime pickupUntil;

    @Column(name = "requested_at", nullable = false)
    private LocalDateTime requestedAt;

    @Column(name = "ready_for_pickup_at")
    private LocalDateTime readyForPickupAt;

    protected Hold() {
    }

    private Hold(HoldId id, String workId, String copyId, String userId, int queuePosition,LocalDateTime requestedAt) {
        this.id = id;
        this.workId = workId;
        this.copyId = copyId;
        this.userId = userId;
        this.queuePosition = queuePosition;
        this.status = HoldStatus.REQUESTED;
        this.requestedAt = requestedAt;
        this.readyForPickupAt = null;
    }

    public static Hold create(HoldId id, String workId, String userId, int queuePosition) {
        return new Hold(id, workId, null, userId, queuePosition, LocalDateTime.now());
    }

    public static Hold createForCopy(HoldId id, String workId, String copyId, String userId, int queuePosition) {
        return new Hold(id, workId, copyId, userId, queuePosition, LocalDateTime.now());
    }

    public HoldId getId() {
        return id;
    }

    public String getWorkId() {
        return workId;
    }

    public String getCopyId() {
        return copyId;
    }

    public boolean isCopySpecific() {
        return copyId != null;
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

    public LocalDateTime getRequestedAt() {
        return requestedAt;
    }

    public LocalDateTime getReadyForPickupAt() {
        return readyForPickupAt;
    }

    public void cancel() {
        if (status == HoldStatus.PICKED_UP || status == HoldStatus.EXPIRED) {
            throw new IllegalStateException("This hold cannot be cancelled");
        }
        this.status = HoldStatus.CANCELLED;
    }

    public void markReadyForPickup(LocalDateTime pickupUntil) {
        if (status != HoldStatus.REQUESTED) {
            throw new IllegalStateException("Only a requested hold can become READY_FOR_PICKUP");
        }
        this.status = HoldStatus.READY_FOR_PICKUP;
        this.pickupUntil = pickupUntil;
        this.readyForPickupAt = LocalDateTime.now();
    }

    public void markPickedUp() {
        if (status != HoldStatus.READY_FOR_PICKUP) {
            throw new IllegalStateException("Only a READY_FOR_PICKUP hold can become PICKED_UP");
        }
        this.status = HoldStatus.PICKED_UP;
        this.pickupUntil = null;
    }

    public void expire() {
        if (status != HoldStatus.READY_FOR_PICKUP) {
            throw new IllegalStateException("Only a READY_FOR_PICKUP hold can expire");
        }
        this.status = HoldStatus.EXPIRED;
    }

    public boolean isWaiting() {
        return status == HoldStatus.REQUESTED;
    }
}