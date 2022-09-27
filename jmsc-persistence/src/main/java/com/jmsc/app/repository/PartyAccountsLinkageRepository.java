/**
 * 
 */
package com.jmsc.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmsc.app.entity.PartyAccountsLinkage;

/**
 * @author Chandan
 *
 */
public interface PartyAccountsLinkageRepository extends JpaRepository<PartyAccountsLinkage, Long> {
	
	public Optional<PartyAccountsLinkage> findByClientIdAndPartyIdAndAccountId(Long clientId, Long partyId, Long accountId);
	
	public List<PartyAccountsLinkage> findByClientIdAndPartyId(Long clientId, Long partyId);
}
