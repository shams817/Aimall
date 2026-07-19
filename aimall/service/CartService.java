package com.aimall.aimall.service;

import com.aimall.aimall.model.CartItem;
import com.aimall.aimall.model.Product;
import com.aimall.aimall.model.User;
import com.aimall.aimall.repository.CartItemRepository;
import com.aimall.aimall.repository.ProductRepository;
import com.aimall.aimall.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<CartItem> getCart(Long userId) {
        return cartItemRepository.findByUser_Id(userId);
    }

    public CartItem addToCart(Long userId, Long productId, Integer quantity) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Product> product = productRepository.findById(productId);

        if (user.isPresent() && product.isPresent()) {
            Optional<CartItem> existingItem = cartItemRepository.findByUser_IdAndProduct_Id(userId, productId);
            if (existingItem.isPresent()) {
                CartItem item = existingItem.get();
                item.setQuantity(item.getQuantity() + quantity);
                return cartItemRepository.save(item);
            } else {
                CartItem newItem = new CartItem();
                newItem.setUser(user.get());
                newItem.setProduct(product.get());
                newItem.setQuantity(quantity);
                return cartItemRepository.save(newItem);
            }
        }
        return null;
    }

    public CartItem updateCartItem(Long cartItemId, Integer quantity) {
        Optional<CartItem> item = cartItemRepository.findById(cartItemId);
        if (item.isPresent()) {
            CartItem cartItem = item.get();
            cartItem.setQuantity(quantity);
            return cartItemRepository.save(cartItem);
        }
        return null;
    }

    public void removeFromCart(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    public void clearCart(Long userId) {
        cartItemRepository.deleteByUser_Id(userId);
    }

    public Double getCartTotal(Long userId) {
        List<CartItem> items = cartItemRepository.findByUser_Id(userId);
        return items.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }
}
