package com.aimall.aimall.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "coupons")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String code;

    private String description;

    private Double discountPercentage; // e.g., 10 for 10% off

    private Double discountAmount; // Fixed discount amount

    private Integer maxUses;

    @Column(columnDefinition = "INTEGER DEFAULT 0")
    private Integer usedCount = 0;

    private Double minimumOrderValue;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean active = true;

    public Boolean isValid() {
        LocalDateTime now = LocalDateTime.now();
        return active 
            && (usedCount < maxUses || maxUses == 0)
            && (startDate == null || now.isAfter(startDate))
            && (endDate == null || now.isBefore(endDate));
    }
}
