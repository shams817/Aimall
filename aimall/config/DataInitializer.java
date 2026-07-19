package com.aimall.aimall.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.aimall.aimall.model.Category;
import com.aimall.aimall.model.Product;
import com.aimall.aimall.repository.CategoryRepository;
import com.aimall.aimall.repository.ProductRepository;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void run(String... args) {
        initializeData();
    }

    private void initializeData() {
        // Create categories
        Category electronics = createCategory("Electronics", "Electronic devices and gadgets");
        Category fashion = createCategory("Fashion", "Clothing and fashion items");
        Category books = createCategory("Books", "Books and educational materials");
        Category gaming = createCategory("Gaming", "Gaming devices and accessories");

        // Create products for Electronics
        createProduct("iPhone 14 Pro", "Latest Apple smartphone with advanced camera", 999.99, 50, electronics, 4.8, "Premium flagship phone", "https://images.unsplash.com/photo-1511707267537-b85faf00021e?w=300&q=50");
        createProduct("MacBook Pro", "Powerful laptop for professionals", 1999.99, 30, electronics, 4.9, "Best for developers", "https://images.unsplash.com/photo-1517336714731-489689fd1ca8?w=300&q=50");
        createProduct("AirPods Pro", "Wireless earbuds with noise cancellation", 249.99, 100, electronics, 4.7, "Great audio quality", "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=300&q=50");

        // Create products for Fashion
        createProduct("Nike Air Max", "Comfortable running shoes", 129.99, 75, fashion, 4.6, "Perfect for athletes", "https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=300&q=50");
        createProduct("Designer Jeans", "Premium denim pants", 89.99, 60, fashion, 4.5, "Stylish and durable", "https://images.unsplash.com/photo-1604695573706-e8ac120e35e9?w=300&q=50");
        createProduct("Summer T-Shirt", "Lightweight cotton shirt", 29.99, 150, fashion, 4.4, "Perfect for summer", "https://images.unsplash.com/photo-1505209346881-18e1d038f3fc?w=300&q=50");

        // Create products for Books
        createProduct("Clean Code", "Programming best practices guide", 39.99, 45, books, 4.8, "Must read for developers", "https://images.unsplash.com/photo-1507842217343-583f20270319?w=300&q=50");
        createProduct("Design Patterns", "Software design patterns book", 49.99, 35, books, 4.7, "Classic reference book", "https://images.unsplash.com/photo-1524995997946-a1c2e315a42f?w=300&q=50");
        createProduct("Python Crash Course", "Beginner-friendly Python guide", 34.99, 55, books, 4.6, "Great for beginners", "https://images.unsplash.com/photo-1517694712202-14dd9538aa97?w=300&q=50");

        // Create products for Gaming
        createProduct("PS5 Console", "Sony PlayStation 5", 499.99, 20, gaming, 4.9, "Latest gaming console", "https://images.unsplash.com/photo-1486401899868-0e435ed85562?w=300&q=50");
        createProduct("Gaming Monitor", "144Hz ultra-responsive display", 349.99, 40, gaming, 4.8, "Perfect for competitive gaming", "https://images.unsplash.com/photo-1527814050087-3793815479db?w=300&q=50");
        createProduct("Gaming Headset", "High-quality 7.1 surround sound", 149.99, 60, gaming, 4.7, "Professional grade audio", "https://images.unsplash.com/photo-1487215078519-e21cc028cb29?w=300&q=50");
    }

    private Category createCategory(String name, String description) {
        Category category = new Category();
        category.setName(name);
        category.setDescription(description);
        return categoryRepository.save(category);
    }

    private Product createProduct(String name, String description, Double price, Integer stock, 
                                 Category category, Double rating, String aiRecommendation, String imageUrl) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setStock(stock);
        product.setCategory(category);
        product.setRating(rating);
        product.setAiRecommendation(aiRecommendation);
        product.setImageUrl(imageUrl);
        
        // Calculate AI score
        double score = 0;
        if (rating != null) {
            score += rating * 20;
        }
        if (stock > 0) {
            score += Math.min(stock * 2, 30);
        }
        score += 20;
        product.setAiScore(Math.round(score));
        
        return productRepository.save(product);
    }
}
