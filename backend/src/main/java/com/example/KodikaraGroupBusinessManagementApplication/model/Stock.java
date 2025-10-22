package com.example.KodikaraGroupBusinessManagementApplication.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "stock")
public class Stock {

    @Id
    @Column(name = "stock_id", columnDefinition = "CHAR(7)")
    private String stockId;

    // --- ADDED MISSING FIELDS ---

    @ManyToOne
    @JoinColumn(name = "pro_id") // Maps the pro_id foreign key
    private Product product;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "open_qty")
    private Integer openQty;

    @Column(name = "qty_sold", nullable = false)
    private int qtySold;

    @Column(name = "qty_returned")
    private Integer qtyReturned;

    @Column(name = "closing_qty")
    private Integer closingQty;

    // --- ADDED REQUIRED CONSTRUCTOR ---
    public Stock() {
    }

    // --- ADDED REQUIRED GETTERS/SETTERS ---

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getOpenQty() {
        return openQty;
    }

    public void setOpenQty(Integer openQty) {
        this.openQty = openQty;
    }

    public int getQtySold() {
        return qtySold;
    }

    public void setQtySold(int qtySold) {
        this.qtySold = qtySold;
    }

    public Integer getQtyReturned() {
        return qtyReturned;
    }

    public void setQtyReturned(Integer qtyReturned) {
        this.qtyReturned = qtyReturned;
    }

    public Integer getClosingQty() {
        return closingQty;
    }

    public void setClosingQty(Integer closingQty) {
        this.closingQty = closingQty;
    }
}