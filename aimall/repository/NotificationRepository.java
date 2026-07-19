package com.aimall.aimall.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aimall.aimall.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserId(Long userId);
    List<Notification> findByUserIdAndRead(Long userId, Boolean read);
    Long countByUserIdAndRead(Long userId, Boolean read);
}
