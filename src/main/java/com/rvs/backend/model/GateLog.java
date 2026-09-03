package com.rvs.backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "gate_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GateLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private ClientProfile client;

    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private String accessMethod; // e.g., "BIOMETRIC_FINGERPRINT" or "VOICE_RECOGNITION"
}