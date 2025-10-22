package com.example.KodikaraGroupBusinessManagementApplication.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "driver")
public class Driver {

    @Id
    // REMOVED @GeneratedValue. This ID is assigned manually (e.g., "DRV001").
    @Column(name = "driver_id", columnDefinition = "CHAR(10)")
    private String driverId;

    @Column(name = "dname", nullable = false, length = 50)
    private String name;

    // ADDED the missing 'contact' field to match the database table
    @Column(name = "contact", length = 15)
    private String contact;

    // --- Getters and Setters ---

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}