package com.rvs.backend.dto;

import com.rvs.backend.enums.StorageType;
import com.rvs.backend.enums.VehicleType;
import lombok.Data;

@Data
public class ReservationRequestDto {
    private Long clientId;
    private VehicleType vehicleType;
    private StorageType requestedStorageType;
    private String make;
    private String model;
    private String color;
    private String licensePlate;
    private Integer yearOfManufacture;
    private Double approximateValue;
    private Double width;
    private Double height;
    private Double length;
}