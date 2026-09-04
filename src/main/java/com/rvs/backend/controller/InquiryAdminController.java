package com.rvs.backend.controller;

import com.rvs.backend.dto.ContactInquiryResponse;
import com.rvs.backend.dto.InquiryStatusUpdateRequest;
import com.rvs.backend.model.ContactInquiry;
import com.rvs.backend.repository.ContactInquiryRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/inquiries")
public class InquiryAdminController {

    private final ContactInquiryRepository inquiryRepository;

    public InquiryAdminController(ContactInquiryRepository inquiryRepository) {
        this.inquiryRepository = inquiryRepository;
    }

    @GetMapping
    public List<ContactInquiryResponse> list() {
        return inquiryRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(ContactInquiryResponse::from)
                .toList();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody InquiryStatusUpdateRequest request
    ) {
        ContactInquiry inquiry = inquiryRepository.findById(id).orElse(null);
        if (inquiry == null) {
            return ResponseEntity.status(404).body(Map.of("message", "Resource not found"));
        }
        inquiry.setStatus(request.getStatus());
        inquiryRepository.save(inquiry);
        return ResponseEntity.ok(ContactInquiryResponse.from(inquiry));
    }
}
