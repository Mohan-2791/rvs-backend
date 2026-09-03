package com.rvs.backend.controller;

import com.rvs.backend.dto.ReservationRequestDto;
import com.rvs.backend.model.*;
import com.rvs.backend.enums.*;
import com.rvs.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/contracts")
public class ContractController {

    @Autowired
    private ContractRepository contractRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private ClientProfileRepository clientProfileRepository;
    @Autowired
    private StorageSpaceRepository spaceRepository;

    @PostMapping("/request")
    public ResponseEntity<?> createReservationRequest(@RequestBody ReservationRequestDto dto) {
        ClientProfile client = clientProfileRepository.findById(dto.getClientId()).orElseThrow();

        Vehicle vehicle = Vehicle.builder()
                .client(client)
                .vehicleType(dto.getVehicleType())
                .requestedStorageType(dto.getRequestedStorageType())
                .make(dto.getMake())
                .model(dto.getModel())
                .color(dto.getColor())
                .licensePlate(dto.getLicensePlate())
                .yearOfManufacture(dto.getYearOfManufacture())
                .approximateValue(dto.getApproximateValue())
                .width(dto.getWidth())
                .height(dto.getHeight())
                .length(dto.getLength())
                .build();
        vehicleRepository.save(vehicle);

        Contract contract = Contract.builder()
                .client(client)
                .vehicle(vehicle)
                .status(ContractStatus.PENDING)
                .build();
        contractRepository.save(contract);

        return ResponseEntity.ok("Reservation request submitted successfully.");
    }

    @PutMapping("/admin/approve/{contractId}/{spaceId}")
    public ResponseEntity<?> approveContract(@PathVariable Long contractId, @PathVariable Long spaceId, @RequestParam Double monthlyRate) {
        Contract contract = contractRepository.findById(contractId).orElseThrow();
        StorageSpace space = spaceRepository.findById(spaceId).orElseThrow();

        space.setOccupied(true);
        spaceRepository.save(space);

        contract.setAssignedSpace(space);
        contract.setStartDate(LocalDate.now());
        contract.setEndDate(LocalDate.now().plusYears(1)); // Becomes 1-year contract
        contract.setStatus(ContractStatus.ACTIVE);
        contract.setMonthlyRate(monthlyRate);
        contractRepository.save(contract);

        return ResponseEntity.ok("Contract approved and space assigned.");
    }
}