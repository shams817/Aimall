package com.aimall.aimall.service;

import com.aimall.aimall.model.*;
import com.aimall.aimall.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public Order createOrderFromCart(Long userId, String deliveryAddress) {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            return null;
        }

        List<CartItem> cartItems = cartItemRepository.findByUser_Id(userId);
        if (cartItems.isEmpty()) {
            return null;
        }

        Order order = new Order();
        order.setUser(user.get());
        order.setDeliveryAddress(deliveryAddress);
        order.setStatus("PENDING");

        double totalAmount = 0;
        for (CartItem cartItem : cartItems) {
            totalAmount += cartItem.getProduct().getPrice() * cartItem.getQuantity();
        }
        order.setTotalAmount(totalAmount);

        // AI-based suggestions
        order.setAiSuggestions(generateAiSuggestions(cartItems));

        Order savedOrder = orderRepository.save(order);

        // Create order items
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getPrice());
            orderItemRepository.save(orderItem);

            // Reduce stock
            Product product = cartItem.getProduct();
            product.setStock(product.getStock() - cartItem.getQuantity());
            productRepository.save(product);
        }

        // Clear cart
        cartItemRepository.deleteByUser_Id(userId);

        return savedOrder;
    }

    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    public List<Order> getUserOrders(Long userId) {
        return orderRepository.findByUser_Id(userId);
    }

    public Order updateOrderStatus(Long orderId, String status) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            Order o = order.get();
            o.setStatus(status);
            if ("SHIPPED".equals(status)) {
                o.setShippedAt(LocalDateTime.now());
            } else if ("DELIVERED".equals(status)) {
                o.setDeliveredAt(LocalDateTime.now());
            }
            return orderRepository.save(o);
        }
        return null;
    }

    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    private String generateAiSuggestions(List<CartItem> cartItems) {
        StringBuilder suggestions = new StringBuilder();
        suggestions.append("AI Suggestions: ");

        int totalItems = cartItems.size();
        double totalPrice = cartItems.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();

        if (totalItems > 5) {
            suggestions.append("Great bundle! You're buying many items. ");
        }
        if (totalPrice > 500) {
            suggestions.append("Consider bulk discounts for future orders. ");
        }

        boolean hasElectronics = cartItems.stream()
                .anyMatch(item -> item.getProduct().getCategory() != null && 
                        item.getProduct().getCategory().getName().toLowerCase().contains("electronics"));
        if (hasElectronics) {
            suggestions.append("Don't forget warranty protection! ");
        }

        return suggestions.toString();
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
