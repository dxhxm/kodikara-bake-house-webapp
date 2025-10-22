package com.example.KodikaraGroupBusinessManagementApplication.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "fair_delivery")
public class FairDelivery {
    @Id
    @Column(name = "delivery_id", length = 10, columnDefinition = "CHAR(10)")
    private String deliveryId;
    @Column(name = "vehicle_id", length = 10, columnDefinition = "CHAR(10)")
    private String vehicleId;
    @Column(name = "driver_id", length = 10, columnDefinition = "CHAR(10)")
    private String driverId;
    @Column(name = "fair_name", length = 100)
    private String fairName;
    @Column(name = "delivery_date")
    private LocalDate deliveryDate;
    @Column(name = "dstatus", length = 20)
    private String status;

    public String getDeliveryId() {
        return deliveryId;
    }
    public void setDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId;
    }
    public String getVehicleId() {
        return vehicleId;
    }
    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }
    public String getDriverId() {
        return driverId;
    }
    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }
    public String getFairName() {
        return fairName;
    }
    public void setFairName(String fairName) {
        this.fairName = fairName;
    }
    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }
    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
