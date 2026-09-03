package com.rvs.backend.controller;

import com.rvs.backend.model.StorageSpace;
import com.rvs.backend.enums.StorageType;
import com.rvs.backend.repository.StorageSpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/spaces")
public class SpaceManagementController {

    @Autowired
    private StorageSpaceRepository spaceRepository;

    @GetMapping("/available")
    public List<StorageSpace> getAvailableSpaces(@RequestParam StorageType type) {
        return spaceRepository.findByStorageTypeAndIsOccupiedFalse(type);
    }

    @PostMapping("/admin/add")
    public ResponseEntity<?> addSpace(@RequestBody StorageSpace space) {
        spaceRepository.save(space);
        return ResponseEntity.ok("Storage space added successfully.");
    }

    @DeleteMapping("/admin/drop/{id}")
    public ResponseEntity<?> dropSpace(@PathVariable Long id) {
        spaceRepository.deleteById(id);
        return ResponseEntity.ok("Storage space removed.");
    }

    @PutMapping("/admin/reallocate/{spaceId}")
    public ResponseEntity<?> reallocateSpace(@PathVariable Long spaceId, @RequestParam String newIdentifier) {
        StorageSpace space = spaceRepository.findById(spaceId).orElseThrow();
        space.setSpaceIdentifier(newIdentifier);
        spaceRepository.save(space);
        return ResponseEntity.ok("Space location updated successfully.");
    }
}