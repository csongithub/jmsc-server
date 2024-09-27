/**
 * 
 */
package com.jmsc.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmsc.app.entity.EInvoice;

/**
 * @author anuhr
 *
 */
public interface EInvoiceRepository extends JpaRepository<EInvoice, Long> {
	
	public Optional<EInvoice> findByClientIdAndId(Long clientId, Long id);
	
	public List<EInvoice> findByClientIdAndFyAndMonth(Long clientId, String fy, String month);

}
