package com.rvs.backend.dto;

import com.rvs.backend.enums.InquiryStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InquiryStatusUpdateRequest {

    @NotNull(message = "Status is required")
    private InquiryStatus status;
}
