package com.rvs.backend.repository;

import com.rvs.backend.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByClientId(Long clientId);
}