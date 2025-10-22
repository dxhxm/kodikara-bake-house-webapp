package com.example.KodikaraGroupBusinessManagementApplication.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "fair_delivery_item")
public class FairDeliveryItem {

    @Id
    @Column(name = "fitem_id", length = 10, columnDefinition = "CHAR(10)")
    private String itemId;

    @Column(name = "delivery_id", length = 10, columnDefinition = "CHAR(10)")
    private String deliveryId;

    @Column(name = "pro_id", length = 7, columnDefinition = "CHAR(7)")
    private String proId;

    @Column(name = "qty_sent")
    private Integer qtySent;

    @Column(name = "unit_price", precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "qty_remaining")
    private Integer qtyRemaining;

    public FairDeliveryItem() {
    }

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

