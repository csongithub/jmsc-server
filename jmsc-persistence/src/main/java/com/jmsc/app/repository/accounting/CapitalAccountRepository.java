/**
 * 
 */
package com.jmsc.app.repository.accounting;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmsc.app.entity.accounting.CapitalAccount;

/**
 * @author anuhr
 *
 */
public interface CapitalAccountRepository extends JpaRepository<CapitalAccount, Long> {

	
	List<CapitalAccount> findAllByClientId(Long clientId);
	
	Optional<CapitalAccount> findByClientIdAndId(Long client, Long id);
}
