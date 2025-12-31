/**
 * 
 */
package com.jmsc.app.repository.accounting;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmsc.app.entity.accounting.StockTransaction;

/**
 * @author anuhr
 *
 */
public interface StockTransactionRepository extends JpaRepository<StockTransaction, Long> {

}
