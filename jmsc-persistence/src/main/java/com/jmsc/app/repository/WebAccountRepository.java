/**
 * 
 */
package com.jmsc.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmsc.app.entity.WebAccount;

/**
 * @author chandan
 *
 */
public interface WebAccountRepository extends JpaRepository<WebAccount, Long> {

	Optional<WebAccount> findByClientIdAndName(Long clientId, String name);
	
	List<WebAccount> findAllByClientId(Long clientId);
	
	Optional<WebAccount> findByClientIdAndId(Long clientId, Long id);
}
