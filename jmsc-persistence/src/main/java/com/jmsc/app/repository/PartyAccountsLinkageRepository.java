/**
 * 
 */
package com.jmsc.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmsc.app.entity.PartyAccountsLinkage;

/**
 * @author Chandan
 *
 */
public interface PartyAccountsLinkageRepository extends JpaRepository<PartyAccountsLinkage, Long> {
	
	public List<PartyAccountsLinkage> findByPartyId(Long partyId);

}
