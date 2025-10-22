package com.example.KodikaraGroupBusinessManagementApplication.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleResponseDTO {
    private String saleId;
    private String shopName;
    private String ownerName; // Added
    private String contactNo; // Added
    private String driverName;
    private String vehicleNo;
    private List<SaleItemResponse> items;
    private BigDecimal totalAmount; // Added
    private LocalDate saleDate; // Changed from Object to LocalDate
}
