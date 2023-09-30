/**
 * 
 */
package com.jmsc.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmsc.app.entity.User;

/**
 * @author anuhr
 *
 */
public interface UserRepository extends JpaRepository<User, Long> {

	public Optional<User> findByLogonId(String logonId);
	
	public Optional<User> findById(Long id);
	
	public Optional<User> findByClientId(Long clientId);
}
