package com.aimall.aimall.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_variants")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private String type; // "Size" or "Color"

    @Column(name = "variant_value")
    private String value; // e.g., "S", "M", "L" or "Red", "Blue"

    @Column(columnDefinition = "INTEGER DEFAULT 0")
    private Integer stock = 0;

    private Double priceModifier; // Additional price for this variant (optional)
}
