/**
 * 
 */
package com.jmsc.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmsc.app.entity.users.PartyBankAccount;

/**
 * @author Chandan
 *
 */
public interface PartyBankAccountRepository extends JpaRepository<PartyBankAccount, Long> {
	
	public Optional<PartyBankAccount> findByAccountNumber(String accountNumber);
}
