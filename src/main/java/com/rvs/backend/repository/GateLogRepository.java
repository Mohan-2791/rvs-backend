package com.rvs.backend.repository;

import com.rvs.backend.model.GateLog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GateLogRepository extends JpaRepository<GateLog, Long> {
    List<GateLog> findByClientId(Long clientId);
}