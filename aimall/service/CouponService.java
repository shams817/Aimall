package com.aimall.aimall.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aimall.aimall.model.Coupon;
import com.aimall.aimall.repository.CouponRepository;

@Service
public class CouponService {
    @Autowired
    private CouponRepository couponRepository;

    public Coupon createCoupon(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    public Optional<Coupon> validateCoupon(String code) {
        Optional<Coupon> coupon = couponRepository.findByCode(code);
        if (coupon.isPresent() && coupon.get().isValid()) {
            return coupon;
        }
        return Optional.empty();
    }

    public Double calculateDiscount(String couponCode, Double orderAmount) {
        Optional<Coupon> coupon = validateCoupon(couponCode);
        if (coupon.isEmpty()) {
            return 0.0;
        }

        Coupon c = coupon.get();
        if (c.getMinimumOrderValue() != null && orderAmount < c.getMinimumOrderValue()) {
            return 0.0;
        }

        double discount = 0;
        if (c.getDiscountPercentage() != null) {
            discount = (orderAmount * c.getDiscountPercentage()) / 100;
        } else if (c.getDiscountAmount() != null) {
            discount = c.getDiscountAmount();
        }

        return Math.min(discount, orderAmount);
    }

    public void useCoupon(String code) {
        Optional<Coupon> coupon = couponRepository.findByCode(code);
        if (coupon.isPresent()) {
            coupon.get().setUsedCount(coupon.get().getUsedCount() + 1);
            couponRepository.save(coupon.get());
        }
    }

    public List<Coupon> getActiveCoupons() {
        return couponRepository.findByActive(true);
    }
}
