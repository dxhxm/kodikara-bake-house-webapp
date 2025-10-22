package com.example.KodikaraGroupBusinessManagementApplication.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="vehicle")
public class Vehicle {

    @Id
    @Column(name = "vehicle_id", columnDefinition = "CHAR(10)")
    private String vehicleId;

    // ERROR 1 & 2: Fixed length (20->10) and removed 'unique = true'
    @Column(name = "vehicle_no", length = 10, nullable = false)
    private String vehicleNo;

    @Column(name = "driver_name", length = 100, nullable = false)
    private String driverName;

    // ERROR 4: Added 'nullable = false' to match the database
    @Column(name = "vehicle_type", length = 50, nullable = false)
    private String vehicleType;

    // ERROR 5: Added the required no-argument constructor
    public Vehicle() {
    }

    // --- Getters and Setters ---

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    // ERROR 6: Added the missing getter
    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }
}