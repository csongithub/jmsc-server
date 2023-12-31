/**
 * 
 */
package com.jmsc.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jmsc.app.entity.PartyBankAccount;

/**
 * @author Chandan
 *
 */
public interface PartyBankAccountRepository extends JpaRepository<PartyBankAccount, Long> {
	
	public List<PartyBankAccount> findByClientId(Long clientId);
	
	@Query(value = "SELECT pba from PartyBankAccount pba WHERE pba.clientId=:clientId AND pba.accountNumber=:accountNumber")
	public Optional<PartyBankAccount> findByClientIdAndAccountNumber(@Param("clientId") Long clientId, @Param("accountNumber")String accountNumber);
	
	public Optional<PartyBankAccount> findByClientIdAndId(Long clientId, Long id);
}
