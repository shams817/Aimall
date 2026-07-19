package com.aimall.aimall.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aimall.aimall.model.Product;
import com.aimall.aimall.model.User;
import com.aimall.aimall.model.Wishlist;
import com.aimall.aimall.repository.ProductRepository;
import com.aimall.aimall.repository.UserRepository;
import com.aimall.aimall.repository.WishlistRepository;

@Service
public class WishlistService {
    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public Wishlist addToWishlist(Long userId, Long productId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Product> product = productRepository.findById(productId);

        if (user.isPresent() && product.isPresent()) {
            Optional<Wishlist> existing = wishlistRepository.findByUserIdAndProductId(userId, productId);
            if (existing.isEmpty()) {
                Wishlist wishlist = new Wishlist();
                wishlist.setUser(user.get());
                wishlist.setProduct(product.get());
                return wishlistRepository.save(wishlist);
            }
        }
        return null;
    }

    public void removeFromWishlist(Long userId, Long productId) {
        wishlistRepository.deleteByUserIdAndProductId(userId, productId);
    }

    public List<Wishlist> getUserWishlist(Long userId) {
        return wishlistRepository.findByUserId(userId);
    }

    public Boolean isInWishlist(Long userId, Long productId) {
        return wishlistRepository.findByUserIdAndProductId(userId, productId).isPresent();
    }
}
