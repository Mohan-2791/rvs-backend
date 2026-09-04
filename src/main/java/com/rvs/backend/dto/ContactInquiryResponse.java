package com.rvs.backend.dto;

import com.rvs.backend.enums.InquiryStatus;
import com.rvs.backend.model.ContactInquiry;

import java.time.Instant;

public record ContactInquiryResponse(
        Long id,
        String name,
        String email,
        String phone,
        String subject,
        String message,
        InquiryStatus status,
        Instant createdAt
) {
    public static ContactInquiryResponse from(ContactInquiry inquiry) {
        return new ContactInquiryResponse(
                inquiry.getId(),
                inquiry.getName(),
                inquiry.getEmail(),
                inquiry.getPhone(),
                inquiry.getSubject(),
                inquiry.getMessage(),
                inquiry.getStatus(),
                inquiry.getCreatedAt()
        );
    }
}
