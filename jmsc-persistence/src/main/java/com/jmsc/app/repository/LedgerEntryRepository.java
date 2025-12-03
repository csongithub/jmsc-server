/**
 * 
 */
package com.jmsc.app.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmsc.app.common.enums.LedgerEntryType;
import com.jmsc.app.entity.accounting.LedgerEntry;

/**
 * @author anuhr
 *
 */
public interface LedgerEntryRepository extends JpaRepository<LedgerEntry, Long> {
	
	Optional<LedgerEntry> findByDateAndClientIdAndCreditorIdAndReceipt(Date date, 
																	   Long clientId,
																	   Long creditorId, 
																	   String receipt);
	
	List<LedgerEntry> findByClientIdAndCreditorIdAndReceipt(Long clientId, Long creditorId, String receipt);
	
	
	List<LedgerEntry> findAllByClientIdAndCreditorIdAndLedgerIdAndEntryTypeAndDateBetween(Long clientId, 
																	   Long creditorId, 
																	   Long ledgerId,
																	   LedgerEntryType entryType,
																	   Date paymentDateStart, 
																	   Date paymentDateEnd);
	
	List<LedgerEntry> findAllByClientIdAndCreditorIdAndLedgerIdAndDateBetween(Long clientId, 
			   Long creditorId, 
			   Long ledgerId,
			   Date paymentDateStart, 
			   Date paymentDateEnd);
}
