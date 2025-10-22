package com.example.KodikaraGroupBusinessManagementApplication.DTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FairDeliveryItemDTO {
    private String proId;
    private int qtySent;
    private BigDecimal unitPrice;
}