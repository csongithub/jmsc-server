/**
 * 
 */
package com.jmsc.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmsc.app.entity.accounting.Ledger;

/**
 * @author anuhr
 *
 */
public interface LedgerRepository extends JpaRepository<Ledger, Long> {
	
	
	public List<Ledger> findByClientIdAndCreditorId(Long clientId, Long creditorId);
	
	
	public Optional<Ledger> findByClientIdAndCreditorIdAndId(Long clientId, Long creditorId, Long id);

}
