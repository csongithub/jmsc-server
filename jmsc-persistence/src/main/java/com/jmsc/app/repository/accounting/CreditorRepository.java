/**
 * 
 */
package com.jmsc.app.repository.accounting;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmsc.app.entity.accounting.Creditor;

/**
 * @author anuhr
 *
 */
public interface CreditorRepository extends JpaRepository<Creditor, Long> {
	
	public Optional<Creditor> findByClientIdAndId(Long clientId, Long id);
	
	
	public List<Creditor> findByClientId(Long clientId);
}
