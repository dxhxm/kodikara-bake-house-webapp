package com.example.KodikaraGroupBusinessManagementApplication.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {

    @Id
    @Column(name="pro_id", columnDefinition = "CHAR(7)")
    private String proId;

    @Column(name="name", length = 15, nullable = false)
    private String name;

    @Column(name="category", length = 20)
    private String category;

    @Column(name="unit_price", precision=10, scale = 2, nullable = false)
    private BigDecimal unitPrice;

    @Column(name="status", length = 15, nullable = false)
    private String status;
}
