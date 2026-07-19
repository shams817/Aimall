package com.aimall.aimall.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_locations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    // Location coordinates
    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    // Location address
    @Column(nullable = false)
    private String address;

    // Current city
    private String city;

    // Location status
    @Column(columnDefinition = "VARCHAR(50) DEFAULT 'IN_TRANSIT'")
    private String status;

    // Distance remaining in km
    private Double distanceRemaining;

    // Estimated delivery time
    private LocalDateTime estimatedDeliveryTime;

    // Timestamp of this location update
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    // Description of location (checkpoint)
    private String description;

    @PrePersist
    protected void onCreate() {
        updatedAt = LocalDateTime.now();
        if (status == null) {
            status = "IN_TRANSIT";
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
