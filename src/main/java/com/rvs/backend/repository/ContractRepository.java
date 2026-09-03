package com.rvs.backend.repository;

import com.rvs.backend.model.Contract;
import com.rvs.backend.enums.ContractStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, Long> {
    List<Contract> findByStatus(ContractStatus status);
    List<Contract> findByClientId(Long clientId);
    
    @Query("SELECT c FROM Contract c WHERE c.endDate BETWEEN :start AND :end AND c.status = 'ACTIVE'")
    List<Contract> findExpiringContracts(@Param("start") LocalDate start, @Param("end") LocalDate end);
}