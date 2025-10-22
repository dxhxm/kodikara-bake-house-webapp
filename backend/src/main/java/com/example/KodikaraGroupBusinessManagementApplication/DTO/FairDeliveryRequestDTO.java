package com.example.KodikaraGroupBusinessManagementApplication.DTO;

import java.math.BigDecimal;
import java.util.List;
import java.math.BigDecimal;

public class FairDeliveryRequestDTO {
    public String deliveryId;
    public String vehicleId;
    public String driverId;
    public String fairName;
    public String deliveryDate;
    public List<FairItemDTO> items;

    public static class FairItemDTO {
        public String proId;
        public Integer qtySent;
        public BigDecimal unitPrice;
    }
}

