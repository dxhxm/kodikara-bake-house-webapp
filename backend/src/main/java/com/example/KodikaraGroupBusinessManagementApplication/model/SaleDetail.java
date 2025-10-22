package com.example.KodikaraGroupBusinessManagementApplication.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "sale_detail")
@NoArgsConstructor
@AllArgsConstructor
public class SaleDetail {

    @Id
    @Column(name="sdetail_id", columnDefinition = "CHAR(10)")
    private String sdetailId;

    @ManyToOne
    // CORRECTED: Removed 'nullable = false' to match the database schema
    @JoinColumn(name = "sale_id")
    private Sale sale;

    @ManyToOne
    // CORRECTED: Removed 'nullable = false' to match the database schema
    @JoinColumn(name="pro_id")
    private Product product;

    @Column(name="qty", nullable = false)
    private int qty;

    @Column(name = "unit_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "subtot", precision = 10, scale = 2, nullable = false)
    private BigDecimal subTot;

    // --- Getters and Setters ---

    // CORRECTED: Method name was 'getSaleId'
    public String getSdetailId() {
        return sdetailId;
    }

    // CORRECTED: Method name was 'setSaleId'
    public void setSdetailId(String sdetailId) {
        this.sdetailId = sdetailId;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getSubTot() {
        return subTot;
    }

    public void setSubTot(BigDecimal subTot) {
        this.subTot = subTot;
    }
}