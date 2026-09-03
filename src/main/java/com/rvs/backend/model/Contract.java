package com.rvs.backend.model;

import com.rvs.backend.enums.ContractStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "contracts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private ClientProfile client;

    @OneToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "space_id")
    private StorageSpace assignedSpace;

    private LocalDate startDate;
    private LocalDate endDate; // Typically 1 year term

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContractStatus status;

    private Double monthlyRate;
}