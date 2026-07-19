package com.aimall.aimall.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aimall.aimall.model.ProductVariant;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {
    List<ProductVariant> findByProductId(Long productId);
    List<ProductVariant> findByProductIdAndType(Long productId, String type);
}
