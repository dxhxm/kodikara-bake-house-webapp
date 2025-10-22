package com.example.KodikaraGroupBusinessManagementApplication.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "sale") // 1. Fixed: Table name is lowercase
@NoArgsConstructor
@AllArgsConstructor
public class Sale {

    @Id
    @Column(name = "sale_id", columnDefinition = "CHAR(10)")
    private String saleId;

    @ManyToOne
    @JoinColumn(name= "shop_id")
    private Shop shop;

    @ManyToOne
    @JoinColumn(name ="vehicle_id")
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    // 2. ADDED: This field was missing
    @ManyToOne
    @JoinColumn(name="driver_id")
    private Driver driver;

    // 3. REMOVED @CreationTimestamp. This is a regular date field.
    @Column(name = "sale_date")
    private LocalDate saleDate;

    @Column(name="tot_amount",precision = 10,scale = 2,nullable = false)
    private BigDecimal totalAmount;

    @Column(name = "payment_method", length = 20, nullable = false)
    private String paymentMethod;

    @OneToMany(mappedBy = "sale",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<SaleDetail> saleDetails;

    // 4. REMOVED all the broken/duplicate code and manual getters/setters.
    // Lombok's @Getter and @Setter handle all of this.
}