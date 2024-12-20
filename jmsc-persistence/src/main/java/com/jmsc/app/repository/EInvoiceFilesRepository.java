/**
 * 
 */
package com.jmsc.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmsc.app.entity.EInvoiceFiles;

/**
 * @author anuhr
 *
 */
public interface EInvoiceFilesRepository extends JpaRepository<EInvoiceFiles, Long> {
	
	Optional<EInvoiceFiles> findByClientIdAndInvoiceId(Long clientId, Long invoiceId);
}
