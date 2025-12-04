/**
 * 
 */
package com.jmsc.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmsc.app.entity.accounting.CreditorPaymentLinkage;

/**
 * @author anuhr
 *
 */
public interface CreditorPaymentLinkageRepository extends JpaRepository<CreditorPaymentLinkage, Long> {
	
	
	public List<CreditorPaymentLinkage> findByClientIdAndPartyIdAndCreditorIdAndStatus(Long clientId, Long partyId, Long creditorId, String status);
	
	public CreditorPaymentLinkage findByClientIdAndId(Long clientId, Long id);
	
	

}
