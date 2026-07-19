package com.aimall.aimall.repository;

import com.aimall.aimall.model.OrderLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderLocationRepository extends JpaRepository<OrderLocation, Long> {
    List<OrderLocation> findByOrder_IdOrderByUpdatedAtDesc(Long orderId);
    OrderLocation findFirstByOrder_IdOrderByUpdatedAtDesc(Long orderId);
}
