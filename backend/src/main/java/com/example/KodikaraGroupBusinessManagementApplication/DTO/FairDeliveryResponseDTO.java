package com.example.KodikaraGroupBusinessManagementApplication.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FairDeliveryResponseDTO {
    private String deliveryId;
    private String vehicleId;
    private String driverId;
    private String fairName;
    private LocalDate deliveryDate;
    private String status;
}