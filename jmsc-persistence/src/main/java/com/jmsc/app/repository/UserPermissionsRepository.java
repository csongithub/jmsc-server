package com.jmsc.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmsc.app.entity.UserPermissions;

public interface UserPermissionsRepository extends JpaRepository<UserPermissions, Long>{
	
	Optional<UserPermissions> findByClientIdAndUserId(Long clientId, Long userId);
	
	
	
}
