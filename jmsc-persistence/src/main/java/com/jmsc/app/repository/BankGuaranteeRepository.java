/**
 * 
 */
package com.jmsc.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmsc.app.entity.BankGuarantee;

/**
 * @author anuhr
 *
 */
public interface BankGuaranteeRepository extends JpaRepository<BankGuarantee, Long> {

	public List<BankGuarantee> findByClientId(Long clientId);
	
	public Optional<BankGuarantee> findByClientIdAndId(Long clientId, Long id);
	
	public Optional<BankGuarantee> findByClientIdAndBgNumber(Long clientId, String bgNumber);
}
