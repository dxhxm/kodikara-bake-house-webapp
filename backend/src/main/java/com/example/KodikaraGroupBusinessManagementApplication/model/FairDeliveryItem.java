package com.example.KodikaraGroupBusinessManagementApplication.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "fair_delivery_item")
public class FairDeliveryItem {
    @Id
    private String itemId;
    private String deliveryId;
    private String proId;
    private Integer qtySent;
    private BigDecimal unitPrice;
    private Integer qtyRemaining;

    public String getItemId() {
        return itemId;
    }
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
    public String getDeliveryId() {
        return deliveryId;
    }
    public void setDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId;
    }
    public String getProId() {
        return proId;
    }
    public void setProId(String proId) {
        this.proId = proId;
    }
    public Integer getQtySent() {
        return qtySent;
    }
    public void setQtySent(Integer qtySent) {
        this.qtySent = qtySent;
    }
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
    public Integer getQtyRemaining() {
        return qtyRemaining;
    }
    public void setQtyRemaining(Integer qtyRemaining) {
        this.qtyRemaining = qtyRemaining;
    }
}

