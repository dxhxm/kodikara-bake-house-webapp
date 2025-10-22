package com.example.KodikaraGroupBusinessManagementApplication.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name="daily_report")
@NoArgsConstructor
@AllArgsConstructor
public class DailyReport {

    @Id
    @Column(name="dreport_id", columnDefinition = "CHAR(10)")
    private String dreportId;

    // ERROR 1: Removed precision and scale. Those are for DECIMAL, not DATE.
    @Column(name = "dreport_date", nullable = false)
    private LocalDate dreportDate;

    // ERROR 2: Added correct precision/scale (12,2) and removed incorrect 'unique = true'.
    @Column(name = "dtotal_sales", nullable = false, precision = 12, scale = 2)
    private BigDecimal dtotalSales;

    @Column(name = "dtotal_transac", nullable = false)
    private int dtotalTransac;

    // This is correct
    @Column(name = "generated_on", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime generatedOn;

}
