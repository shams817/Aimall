package com.aimall.aimall.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aimall.aimall.model.Order;
import com.aimall.aimall.model.OrderLocation;
import com.aimall.aimall.repository.OrderLocationRepository;
import com.aimall.aimall.repository.OrderRepository;

@Service
public class OrderLocationService {
    @Autowired
    private OrderLocationRepository orderLocationRepository;

    @Autowired
    private OrderRepository orderRepository;

    // Add new location update for an order
    public OrderLocation updateOrderLocation(Long orderId, Double latitude, Double longitude, 
                                            String address, String city, String status,
                                            Double distanceRemaining, LocalDateTime estimatedDelivery,
                                            String description) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (!order.isPresent()) {
            return null;
        }

        OrderLocation location = new OrderLocation();
        location.setOrder(order.get());
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        location.setAddress(address);
        location.setCity(city);
        location.setStatus(status != null ? status : "IN_TRANSIT");
        location.setDistanceRemaining(distanceRemaining);
        location.setEstimatedDeliveryTime(estimatedDelivery);
        location.setDescription(description);

        return orderLocationRepository.save(location);
    }

    // Get complete location history for an order
    public List<OrderLocation> getLocationHistory(Long orderId) {
        return orderLocationRepository.findByOrder_IdOrderByUpdatedAtDesc(orderId);
    }

    // Get current/latest location of an order
    public OrderLocation getCurrentLocation(Long orderId) {
        return orderLocationRepository.findFirstByOrder_IdOrderByUpdatedAtDesc(orderId);
    }

    // Get specific location record
    public OrderLocation getLocationById(Long locationId) {
        return orderLocationRepository.findById(locationId).orElse(null);
    }

    // Calculate distance between two coordinates (Haversine formula)
    public Double calculateDistance(Double lat1, Double lon1, Double lat2, Double lon2) {
        final int R = 6371; // Earth's radius in kilometers

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c; // Distance in km
    }

    // Generate next checkpoint location (for demo)
    public OrderLocation generateNextCheckpoint(Long orderId, String currentCity) {
        OrderLocation currentLocation = getCurrentLocation(orderId);
        if (currentLocation == null) {
            Optional<Order> order = orderRepository.findById(orderId);
            if (!order.isPresent()) {
                return null;
            }
            // Create initial location at warehouse
            return updateOrderLocation(orderId, 28.7041, 77.1025, 
                    "Warehouse, Delhi", "Delhi", "PICKED_UP", 50.0, 
                    LocalDateTime.now().plusHours(24), "Order picked from warehouse");
        }

        // Simulate movement towards destination
        Double newLat = currentLocation.getLatitude() + (Math.random() * 0.01 - 0.005);
        Double newLon = currentLocation.getLongitude() + (Math.random() * 0.01 - 0.005);

        String[] statuses = {"PICKED_UP", "IN_TRANSIT", "OUT_FOR_DELIVERY"};
        String[] checkpoints = {
            "Order picked from warehouse",
            "Package in transit",
            "Out for delivery",
            "Delivery attempted",
            "Ready for pickup"
        };

        String newStatus = statuses[(int)(Math.random() * statuses.length)];
        String newCity = currentCity;
        String newAddress = "Checkpoint near " + currentCity;
        Double newDistance = Math.max(0, currentLocation.getDistanceRemaining() - (Math.random() * 20));
        String description = checkpoints[(int)(Math.random() * checkpoints.length)];

        return updateOrderLocation(orderId, newLat, newLon, newAddress, newCity, 
                newStatus, newDistance, LocalDateTime.now().plusHours((int)(Math.random() * 24) + 1),
                description);
    }

    // Get location summary for order card display
    public LocationSummary getLocationSummary(Long orderId) {
        OrderLocation currentLocation = getCurrentLocation(orderId);
        if (currentLocation == null) {
            return null;
        }

        List<OrderLocation> history = getLocationHistory(orderId);

        LocationSummary summary = new LocationSummary();
        summary.setCurrentLocation(currentLocation);
        summary.setTotalCheckpoints(history.size());
        summary.setAddress(currentLocation.getAddress());
        summary.setCity(currentLocation.getCity());
        summary.setStatus(currentLocation.getStatus());
        summary.setDistanceRemaining(currentLocation.getDistanceRemaining());
        summary.setEstimatedDelivery(currentLocation.getEstimatedDeliveryTime());

        return summary;
    }

    // Inner class for location summary
    public static class LocationSummary {
        public OrderLocation currentLocation;
        public Integer totalCheckpoints;
        public String address;
        public String city;
        public String status;
        public Double distanceRemaining;
        public LocalDateTime estimatedDelivery;

        // Getters and setters
        public OrderLocation getCurrentLocation() { return currentLocation; }
        public void setCurrentLocation(OrderLocation currentLocation) { this.currentLocation = currentLocation; }
        
        public Integer getTotalCheckpoints() { return totalCheckpoints; }
        public void setTotalCheckpoints(Integer totalCheckpoints) { this.totalCheckpoints = totalCheckpoints; }
        
        public String getAddress()  { return address; }
        public void setAddress(String address) { this.address = address; }
        
        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }
        
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        
        public Double getDistanceRemaining() { return distanceRemaining; }
        public void setDistanceRemaining(Double distanceRemaining) { this.distanceRemaining = distanceRemaining; }
        
        public LocalDateTime getEstimatedDelivery() { return estimatedDelivery; }
        public void setEstimatedDelivery(LocalDateTime estimatedDelivery) { this.estimatedDelivery = estimatedDelivery; }
    }
}
