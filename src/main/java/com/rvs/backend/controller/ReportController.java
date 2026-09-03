package com.rvs.backend.controller;

import com.rvs.backend.model.Contract;
import com.rvs.backend.enums.ContractStatus;
import com.rvs.backend.repository.ContractRepository;
import com.rvs.backend.repository.StorageSpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ContractRepository contractRepository;
    @Autowired
    private StorageSpaceRepository spaceRepository;

    @GetMapping("/admin/summary")
    public Map<String, Object> getManagementSummary() {
        Map<String, Object> report = new HashMap<>();

        // Pending reservations
        List<Contract> pending = contractRepository.findByStatus(ContractStatus.PENDING);
        report.put("pendingReservationsCount", pending.size());

        // 90-day contract renewals
        LocalDate now = LocalDate.now();
        List<Contract> renewalsDue = contractRepository.findExpiringContracts(now, now.plusDays(90));
        report.put("contractsDueForRenewalIn90Days", renewalsDue.size());

        // Space availability stats
        long totalSpaces = spaceRepository.count();
        long occupiedSpaces = spaceRepository.findAll().stream().filter(s -> s.isOccupied()).count();
        report.put("totalSpaces", totalSpaces);
        report.put("occupiedSpaces", occupiedSpaces);
        report.put("availableSpaces", totalSpaces - occupiedSpaces);

        // Monthly estimated revenue from active contracts
        double monthlyRevenue = contractRepository.findByStatus(ContractStatus.ACTIVE)
                .stream()
                .mapToDouble(c -> c.getMonthlyRate() != null ? c.getMonthlyRate() : 0.0)
                .sum();
        report.put("totalMonthlyRevenue", monthlyRevenue);

        return report;
    }
}