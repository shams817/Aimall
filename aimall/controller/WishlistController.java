package com.aimall.aimall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aimall.aimall.model.Wishlist;
import com.aimall.aimall.service.WishlistService;

@RestController
@RequestMapping("/api/wishlist")
@CrossOrigin(origins = "*")
public class WishlistController {
    @Autowired
    private WishlistService wishlistService;

    @PostMapping("/{userId}/add/{productId}")
    public Wishlist addToWishlist(@PathVariable Long userId, @PathVariable Long productId) {
        return wishlistService.addToWishlist(userId, productId);
    }

    @DeleteMapping("/{userId}/remove/{productId}")
    public void removeFromWishlist(@PathVariable Long userId, @PathVariable Long productId) {
        wishlistService.removeFromWishlist(userId, productId);
    }

    @GetMapping("/{userId}")
    public List<Wishlist> getUserWishlist(@PathVariable Long userId) {
        return wishlistService.getUserWishlist(userId);
    }

    @GetMapping("/{userId}/check/{productId}")
    public Boolean isInWishlist(@PathVariable Long userId, @PathVariable Long productId) {
        return wishlistService.isInWishlist(userId, productId);
    }
}
