/**
 * 
 */
package com.jmsc.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jmsc.app.entity.users.BankAccount;

/**
 * @author Chandan
 *
 */
public interface BankAccountRepository extends JpaRepository<BankAccount, Long>{

	@Query("select ba from BankAccount ba where ba.status = ?1")
	public List<BankAccount> findByStatus(String status);
}

