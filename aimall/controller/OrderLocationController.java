package com.aimall.aimall.controller;

import com.aimall.aimall.model.OrderLocation;
import com.aimall.aimall.service.OrderLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/locations")
@CrossOrigin(origins = "*")
public class OrderLocationController {
    @Autowired
    private OrderLocationService orderLocationService;

    // Update order location (for delivery personnel)
    @PostMapping("/update/{orderId}")
    public ResponseEntity<OrderLocation> updateLocation(@PathVariable Long orderId, 
                                                       @RequestBody Map<String, Object> request) {
        Double latitude = ((Number) request.get("latitude")).doubleValue();
        Double longitude = ((Number) request.get("longitude")).doubleValue();
        String address = (String) request.get("address");
        String city = (String) request.get("city");
        String status = (String) request.get("status");
        Double distanceRemaining = request.get("distanceRemaining") != null ? 
                ((Number) request.get("distanceRemaining")).doubleValue() : null;
        String estimatedDelivery = (String) request.get("estimatedDelivery");
        String description = (String) request.get("description");

        LocalDateTime estimatedDeliveryTime = estimatedDelivery != null ? 
                LocalDateTime.parse(estimatedDelivery) : null;

        OrderLocation location = orderLocationService.updateOrderLocation(orderId, latitude, longitude,
                address, city, status, distanceRemaining, estimatedDeliveryTime, description);

        if (location != null) {
            return ResponseEntity.ok(location);
        }
        return ResponseEntity.badRequest().build();
    }

    // Get current location of an order
    @GetMapping("/current/{orderId}")
    public ResponseEntity<OrderLocation> getCurrentLocation(@PathVariable Long orderId) {
        OrderLocation location = orderLocationService.getCurrentLocation(orderId);
        if (location != null) {
            return ResponseEntity.ok(location);
        }
        return ResponseEntity.notFound().build();
    }

    // Get full location history of an order
    @GetMapping("/history/{orderId}")
    public ResponseEntity<List<OrderLocation>> getLocationHistory(@PathVariable Long orderId) {
        List<OrderLocation> history = orderLocationService.getLocationHistory(orderId);
        return ResponseEntity.ok(history);
    }

    // Get location summary for quick display
    @GetMapping("/summary/{orderId}")
    public ResponseEntity<OrderLocationService.LocationSummary> getLocationSummary(@PathVariable Long orderId) {
        OrderLocationService.LocationSummary summary = orderLocationService.getLocationSummary(orderId);
        if (summary != null) {
            return ResponseEntity.ok(summary);
        }
        return ResponseEntity.notFound().build();
    }

    // Generate next checkpoint (for demo/testing)
    @PostMapping("/simulate/{orderId}")
    public ResponseEntity<OrderLocation> simulateNextCheckpoint(@PathVariable Long orderId,
                                                               @RequestBody(required = false) Map<String, String> request) {
        String city = request != null && request.get("city") != null ? 
                request.get("city") : "Delhi";
        
        OrderLocation location = orderLocationService.generateNextCheckpoint(orderId, city);
        if (location != null) {
            return ResponseEntity.ok(location);
        }
        return ResponseEntity.badRequest().build();
    }

    // Get specific location record
    @GetMapping("/{locationId}")
    public ResponseEntity<OrderLocation> getLocation(@PathVariable Long locationId) {
        OrderLocation location = orderLocationService.getLocationById(locationId);
        if (location != null) {
            return ResponseEntity.ok(location);
        }
        return ResponseEntity.notFound().build();
    }

    // Calculate distance between two coordinates
    @PostMapping("/distance")
    public ResponseEntity<Map<String, Double>> calculateDistance(@RequestBody Map<String, Object> request) {
        Double lat1 = ((Number) request.get("lat1")).doubleValue();
        Double lon1 = ((Number) request.get("lon1")).doubleValue();
        Double lat2 = ((Number) request.get("lat2")).doubleValue();
        Double lon2 = ((Number) request.get("lon2")).doubleValue();

        Double distance = orderLocationService.calculateDistance(lat1, lon1, lat2, lon2);
        return ResponseEntity.ok(Map.of("distance", distance));
    }
}
