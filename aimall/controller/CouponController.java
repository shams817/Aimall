package com.aimall.aimall.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aimall.aimall.model.Coupon;
import com.aimall.aimall.service.CouponService;

@RestController
@RequestMapping("/api/coupons")
@CrossOrigin(origins = "*")
public class CouponController {
    @Autowired
    private CouponService couponService;

    @GetMapping("/active")
    public List<Coupon> getActiveCoupons() {
        return couponService.getActiveCoupons();
    }

    @PostMapping("/validate")
    public Map<String, Object> validateCoupon(@RequestParam String code, @RequestParam Double orderAmount) {
        Map<String, Object> response = new HashMap<>();
        Optional<Coupon> coupon = couponService.validateCoupon(code);
        
        if (coupon.isPresent()) {
            Double discount = couponService.calculateDiscount(code, orderAmount);
            response.put("valid", true);
            response.put("discount", discount);
            response.put("finalAmount", orderAmount - discount);
            response.put("coupon", coupon.get());
        } else {
            response.put("valid", false);
            response.put("message", "Invalid or expired coupon");
        }
        return response;
    }

    @PostMapping("/apply")
    public Map<String, Object> applyCoupon(@RequestParam String code) {
        Map<String, Object> response = new HashMap<>();
        Optional<Coupon> coupon = couponService.validateCoupon(code);
        
        if (coupon.isPresent()) {
            couponService.useCoupon(code);
            response.put("success", true);
            response.put("message", "Coupon applied successfully");
        } else {
            response.put("success", false);
            response.put("message", "Invalid or expired coupon");
        }
        return response;
    }
}
