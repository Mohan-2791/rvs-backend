package com.rvs.backend.model;

import com.rvs.backend.enums.StorageType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "storage_spaces")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StorageSpace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String spaceIdentifier; // e.g., "A-101"

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StorageType storageType; // INDOOR / OUTDOOR

    private Double width;
    private Double height;
    private Double length;

    @Column(nullable = false)
    private boolean isOccupied = false;
}