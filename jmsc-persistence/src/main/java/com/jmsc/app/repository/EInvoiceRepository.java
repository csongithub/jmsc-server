/**
 * 
 */
package com.jmsc.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmsc.app.common.enums.EFyMonths;
import com.jmsc.app.entity.EInvoice;

/**
 * @author anuhr
 *
 */
public interface EInvoiceRepository extends JpaRepository<EInvoice, Long> {
	
	public Optional<EInvoice> findByClientIdAndId(Long clientId, Long id);
	
	public List<EInvoice> findByClientIdAndFy(Long clientId, String fy);
	
	public List<EInvoice> findByClientIdAndGstStateAndFy(Long clientId, String gstState, String fy);
	
	public List<EInvoice> findByClientIdAndFyAndMonth(Long clientId, String fy, EFyMonths month);
	
	public List<EInvoice> findByClientIdAndGstStateAndFyAndMonth(Long clientId, String gstState, String fy, EFyMonths month);
}
