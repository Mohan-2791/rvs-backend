package com.rvs.backend.repository;

import com.rvs.backend.model.StorageSpace;
import com.rvs.backend.enums.StorageType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface StorageSpaceRepository extends JpaRepository<StorageSpace, Long> {
    Optional<StorageSpace> findBySpaceIdentifier(String spaceIdentifier);
    List<StorageSpace> findByStorageTypeAndIsOccupiedFalse(StorageType storageType);
    long countByStorageTypeAndIsOccupiedFalse(StorageType storageType);
}