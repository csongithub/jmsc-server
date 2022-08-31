/**
 * 
 */
package com.jmsc.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;

import com.jmsc.app.entity.BankAccount;

/**
 * @author Chandan
 *
 */
public interface BankAccountRepository extends JpaRepository<BankAccount, Long>{

	//@Query("select ba from BankAccount ba where ba.status = ?1")
	//public List<BankAccount> findByStatus(String status);
	
	public List<BankAccount> findByClientIdAndStatus(Long clientId, String status);
	
	public List<BankAccount> findByClientId(Long clientId);

	public Optional<BankAccount> findByClientIdAndAccountNumber(Long clientId, String accountNumber);
	
	public Optional<BankAccount> findByClientIdAndId(Long clientId, Long id);
}

