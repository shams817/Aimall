package com.aimall.aimall.service;

import com.aimall.aimall.model.Product;
import com.aimall.aimall.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategory_Id(categoryId);
    }

    public List<Product> searchProducts(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Product> getTopRatedProducts() {
        return productRepository.findByOrderByRatingDesc();
    }

    public List<Product> getInStockProducts() {
        return productRepository.findByStockGreaterThan(0);
    }

    public Product createProduct(Product product) {
        if (product.getAiScore() == null) {
            product.setAiScore(generateAiScore(product));
        }
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product productDetails) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            Product p = product.get();
            p.setName(productDetails.getName());
            p.setDescription(productDetails.getDescription());
            p.setPrice(productDetails.getPrice());
            p.setStock(productDetails.getStock());
            p.setImageUrl(productDetails.getImageUrl());
            p.setCategory(productDetails.getCategory());
            p.setRating(productDetails.getRating());
            return productRepository.save(p);
        }
        return null;
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    private Long generateAiScore(Product product) {
        // Simple AI scoring algorithm
        double score = 0;
        if (product.getRating() != null) {
            score += product.getRating() * 20;
        }
        if (product.getStock() != null && product.getStock() > 0) {
            score += Math.min(product.getStock() * 2, 30);
        }
        score += 20; // Base score
        return Math.round(score);
    }

    public List<Product> getAiRecommendations() {
        // AI recommendation logic - returns top-rated products with good stock
        List<Product> allProducts = productRepository.findByOrderByRatingDesc();
        return allProducts.stream()
                .filter(p -> p.getStock() != null && p.getStock() > 5)
                .limit(10)
                .toList();
    }
}
