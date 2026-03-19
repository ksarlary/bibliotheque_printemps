package com.example.printemps.catalog.domain;

import com.example.printemps.catalog.application.models.AddCopyRequest;
import jakarta.persistence.*;

@Entity
@Table(name = "copy")
@Access(AccessType.FIELD)
public class Copy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long technicalId;

    @Embedded
    private CopyId id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "work", nullable = false)
    private Work work;

    @Column(nullable = false, unique = true)
    private String barcode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CopyStatus status;

    @Column(nullable = false)
    private String location;

    protected Copy() {
    }

    private Copy(
            CopyId id,
            Work work,
            String barcode,
            CopyStatus status,
            String location
    ) {
        this.id = id;
        this.work = work;
        this.barcode = barcode;
        this.status = status;
        this.location = location;
    }

    public static Copy create(CopyId id, Work work, AddCopyRequest request) {
        return new Copy(
                id,
                work,
                request.barcode(),
                CopyStatus.AVAILABLE,
                request.location()
        );
    }

    public void updateStatus(CopyStatus status) {
        this.status = status;
    }

    public Long getTechnicalId() {
        return technicalId;
    }

    public CopyId getId() {
        return id;
    }

    public Work getWork() {
        return work;
    }

    public String getBarcode() {
        return barcode;
    }

    public CopyStatus getStatus() {
        return status;
    }

    public String getLocation() {
        return location;
    }
}