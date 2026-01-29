/**
 * 
 */
package com.jmsc.app.repository.accounting;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmsc.app.entity.accounting.StockTransaction;

/**
 * @author anuhr
 *
 */
public interface StockTransactionRepository extends JpaRepository<StockTransaction, Long> {
	
	List<StockTransaction> findAllByClientIdAndStockIdAndDateBetween(Long clientId, 
				 													 Long stockId, 
				 													 Date fromDate, 
				 													 Date toDate);

}
