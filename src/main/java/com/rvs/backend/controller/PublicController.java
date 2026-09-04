package com.rvs.backend.controller;

import com.rvs.backend.dto.AvailabilityResponse;
import com.rvs.backend.dto.ContactRequest;
import com.rvs.backend.enums.InquiryStatus;
import com.rvs.backend.enums.StorageType;
import com.rvs.backend.exception.RateLimitExceededException;
import com.rvs.backend.model.ContactInquiry;
import com.rvs.backend.repository.ContactInquiryRepository;
import com.rvs.backend.repository.StorageSpaceRepository;
import com.rvs.backend.security.ClientIpResolver;
import com.rvs.backend.security.ContactRateLimiter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/public")
public class PublicController {

    private final StorageSpaceRepository spaceRepository;
    private final ContactInquiryRepository inquiryRepository;
    private final ContactRateLimiter contactRateLimiter;
    private final ClientIpResolver clientIpResolver;

    public PublicController(
            StorageSpaceRepository spaceRepository,
            ContactInquiryRepository inquiryRepository,
            ContactRateLimiter contactRateLimiter,
            ClientIpResolver clientIpResolver
    ) {
        this.spaceRepository = spaceRepository;
        this.inquiryRepository = inquiryRepository;
        this.contactRateLimiter = contactRateLimiter;
        this.clientIpResolver = clientIpResolver;
    }

    @GetMapping("/availability")
    public AvailabilityResponse availability() {
        long indoor = spaceRepository.countByStorageTypeAndIsOccupiedFalse(StorageType.INDOOR);
        long outdoor = spaceRepository.countByStorageTypeAndIsOccupiedFalse(StorageType.OUTDOOR);
        return new AvailabilityResponse(indoor, outdoor, indoor + outdoor);
    }

    @PostMapping("/contact")
    public ResponseEntity<Map<String, String>> submitContact(
            @Valid @RequestBody ContactRequest request,
            HttpServletRequest httpRequest
    ) {
        if (!contactRateLimiter.tryConsume(clientIpResolver.resolve(httpRequest))) {
            throw new RateLimitExceededException();
        }

        String phone = request.getPhone() == null || request.getPhone().isBlank()
                ? null
                : request.getPhone().trim();

        ContactInquiry inquiry = ContactInquiry.builder()
                .name(request.getName().trim())
                .email(request.getEmail().trim())
                .phone(phone)
                .subject(request.getSubject().trim())
                .message(request.getMessage().trim())
                .status(InquiryStatus.OPEN)
                .build();
        inquiryRepository.save(inquiry);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "Your message was received."));
    }
}
