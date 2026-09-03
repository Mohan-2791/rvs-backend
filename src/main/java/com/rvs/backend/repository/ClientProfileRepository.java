package com.rvs.backend.repository;

import com.rvs.backend.model.ClientProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ClientProfileRepository extends JpaRepository<ClientProfile, Long> {
    Optional<ClientProfile> findByUserId(Long userId);
}