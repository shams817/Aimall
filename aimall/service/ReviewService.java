package com.aimall.aimall.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aimall.aimall.model.Product;
import com.aimall.aimall.model.Review;
import com.aimall.aimall.model.User;
import com.aimall.aimall.repository.ProductRepository;
import com.aimall.aimall.repository.ReviewRepository;
import com.aimall.aimall.repository.UserRepository;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public Review addReview(Long productId, Long userId, Integer rating, String title, String comment) {
        Optional<Product> product = productRepository.findById(productId);
        Optional<User> user = userRepository.findById(userId);

        if (product.isPresent() && user.isPresent()) {
            Review review = new Review();
            review.setProduct(product.get());
            review.setUser(user.get());
            review.setRating(Math.min(Math.max(rating, 1), 5));
            review.setTitle(title);
            review.setComment(comment);
            return reviewRepository.save(review);
        }
        return null;
    }

    public List<Review> getProductReviews(Long productId) {
        return reviewRepository.findByProductId(productId);
    }

    public List<Review> getUserReviews(Long userId) {
        return reviewRepository.findByUserId(userId);
    }

    public Double getAverageRating(Long productId) {
        Double avg = reviewRepository.getAverageRatingForProduct(productId);
        return avg != null ? avg : 0.0;
    }

    public void markHelpful(Long reviewId) {
        Optional<Review> review = reviewRepository.findById(reviewId);
        if (review.isPresent()) {
            Integer count = review.get().getHelpfulCount() != null ? review.get().getHelpfulCount() : Integer.valueOf(0);
            review.get().setHelpfulCount(count + 1);
            reviewRepository.save(review.get());
        }
    }
}
