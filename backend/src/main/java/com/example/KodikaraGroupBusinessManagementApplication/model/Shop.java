package com.example.KodikaraGroupBusinessManagementApplication.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="shop") // ERROR 1: Changed to lowercase
public class Shop {

    @Id
    @Column(name = "shop_id",columnDefinition = "CHAR(7)")
    private String shopId;

    // ERROR 2: Changed length from 100 to 25 to match the database
    @Column(name = "shop_name", length = 25, nullable = false)
    private String shopName;

    @Column(name = "owner_name", length = 100, nullable = false)
    private String ownerName;

    @Column(name = "contact_details", length = 15, nullable = false)
    private String contactNo;

    @Column(name = "location", length = 25, nullable = false)
    private String address;

    // ERROR 4: Added the required no-argument constructor
    public Shop() {
    }

    // ERROR 3: Removed the 'getSaleDetailBySaleId' method.
    // It does not belong in an entity.

    // --- Getters and Setters ---

    public String getShopId() {
        return shopId;
    }
    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
    public String getShopName() {
        return shopName;
    }
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
    public String getOwnerName() {
        return ownerName;
    }
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
    public String getContactNo() {
        return contactNo;
    }
    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
}