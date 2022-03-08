package com.jmsc.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmsc.app.common.enums.ENotificationType;
import com.jmsc.app.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
	
	public Optional<Notification> findByClientIdAndType(Long clientId, ENotificationType type);
}
