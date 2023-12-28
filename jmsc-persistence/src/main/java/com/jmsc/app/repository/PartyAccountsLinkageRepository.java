/**
 * 
 */
package com.jmsc.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jmsc.app.entity.PartyAccountsLinkage;

/**
 * @author Chandan
 *
 */
public interface PartyAccountsLinkageRepository extends JpaRepository<PartyAccountsLinkage, Long> {
	
	@Query(value = "SELECT pal from PartyAccountsLinkage pal WHERE pal.id.clientId = :clientId "
					+ "AND pal.id.partyId = :partyId AND pal.id.accountId = :accountId")
	public Optional<PartyAccountsLinkage> findByClientIdAndPartyIdAndAccountId(@Param("clientId") Long clientId, @Param("partyId") Long partyId, @Param("accountId") Long accountId);
	
	@Query(value = "SELECT pal from PartyAccountsLinkage pal WHERE pal.id.clientId = :clientId AND pal.id.partyId = :partyId")
	public List<PartyAccountsLinkage> findByClientIdAndPartyId(@Param("clientId") Long clientId, @Param("partyId") Long partyId);
}
