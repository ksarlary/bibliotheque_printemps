package com.example.printemps.catalog.infrastructure.persistance;

import com.example.printemps.catalog.domain.CopyId;
import com.example.printemps.catalog.domain.CopyStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "copy")
@Access(AccessType.FIELD)
public class CopyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long technicalId;

    @Embedded
    private CopyId id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "work_id", nullable = false)
    private WorkEntity work;

    @Column(nullable = false, unique = true)
    private String barcode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CopyStatus status;

    @Column(nullable = false)
    private String location;

    protected CopyEntity() {
        // JPA
    }

    public CopyEntity(
            CopyId id,
            WorkEntity work,
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

    public Long getTechnicalId() {
        return technicalId;
    }

    public CopyId getId() {
        return id;
    }

    public WorkEntity getWork() {
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