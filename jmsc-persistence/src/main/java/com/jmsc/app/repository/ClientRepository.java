/**
 * 
 */
package com.jmsc.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmsc.app.entity.Client;

/**
 * @author Chandan
 *
 */
public interface ClientRepository extends JpaRepository<Client, Long> {

	public Optional<Client> findByLogonId(String logonId);
	
	public Optional<Client> findById(Long id);
}
