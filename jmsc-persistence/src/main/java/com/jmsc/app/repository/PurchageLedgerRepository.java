/**
 * 
 */
package com.jmsc.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jmsc.app.entity.PurchageLedger;

/**
 * @author anuhr
 *
 */
public interface PurchageLedgerRepository extends JpaRepository<PurchageLedger, Long> {
	
	@Query("SELECT COUNT(e) > 0 FROM PurchageLedger e WHERE e.clientId = :clientId AND e.supplierId = :supplierId")
	boolean  findFirstRecords(@Param("clientId")Long clientId, @Param("supplierId")Long supplierId);
}
