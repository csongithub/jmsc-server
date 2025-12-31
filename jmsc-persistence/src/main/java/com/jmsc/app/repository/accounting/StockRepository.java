/**
 * 
 */
package com.jmsc.app.repository.accounting;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmsc.app.entity.accounting.Stock;

/**
 * @author anuhr
 *
 */
public interface StockRepository extends JpaRepository<Stock, Long> {
	
	List<Stock> findAllByClientId(Long clientId);

}
