package com.rvs.backend.controller;

import com.rvs.backend.model.GateLog;
import com.rvs.backend.model.ClientProfile;
import com.rvs.backend.repository.GateLogRepository;
import com.rvs.backend.repository.ClientProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/gate")
public class GateLogController {

    @Autowired
    private GateLogRepository gateLogRepository;
    @Autowired
    private ClientProfileRepository clientProfileRepository;

    @PostMapping("/entry")
    public ResponseEntity<?> logEntry(@RequestParam Long clientId, @RequestParam String accessMethod) {
        ClientProfile client = clientProfileRepository.findById(clientId).orElseThrow();
        GateLog log = GateLog.builder()
                .client(client)
                .entryTime(LocalDateTime.now())
                .accessMethod(accessMethod)
                .build();
        gateLogRepository.save(log);
        return ResponseEntity.ok("Gate opened. Entry recorded.");
    }

    @PutMapping("/exit/{logId}")
    public ResponseEntity<?> logExit(@PathVariable Long logId) {
        GateLog log = gateLogRepository.findById(logId).orElseThrow();
        log.setExitTime(LocalDateTime.now());
        gateLogRepository.save(log);
        return ResponseEntity.ok("Gate opened. Exit recorded.");
    }
}