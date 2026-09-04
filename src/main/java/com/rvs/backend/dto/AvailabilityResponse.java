package com.rvs.backend.dto;

public record AvailabilityResponse(long indoorAvailable, long outdoorAvailable, long totalAvailable) {
}
