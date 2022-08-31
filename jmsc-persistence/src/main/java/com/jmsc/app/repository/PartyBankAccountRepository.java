/**
 * 
 */
package com.jmsc.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmsc.app.entity.PartyBankAccount;

/**
 * @author Chandan
 *
 */
public interface PartyBankAccountRepository extends JpaRepository<PartyBankAccount, Long> {
	
	public List<PartyBankAccount> findByClientId(Long clientId);
	
	public Optional<PartyBankAccount> findByClientIdAndAccountNumber(Long clientId, String accountNumber);
	
	public Optional<PartyBankAccount> findByClientIdAndId(Long clientId, Long id);
}
