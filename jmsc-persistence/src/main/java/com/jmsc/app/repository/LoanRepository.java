/**
 * 
 */
package com.jmsc.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmsc.app.entity.Loan;

/**
 * @author Chandan
 *
 */
public interface LoanRepository extends JpaRepository<Loan, Long> {

	List<Loan> findAllByClientId(Long clientId);
	
	Optional<Loan> findByClientIdAndAccountNo(Long clientId, String accountNo);
}
