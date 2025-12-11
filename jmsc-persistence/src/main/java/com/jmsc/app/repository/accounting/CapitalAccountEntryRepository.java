/**
 * 
 */
package com.jmsc.app.repository.accounting;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmsc.app.entity.accounting.CapitalAccountEntry;

/**
 * @author anuhr
 *
 */
public interface CapitalAccountEntryRepository extends JpaRepository<CapitalAccountEntry, Long> {
	
	
	public List<CapitalAccountEntry> findAllByClientIdAndAccountId(Long clientId, Long accountId);
	
	
	
}
