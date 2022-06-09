/**
 * 
 */
package com.jmsc.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmsc.app.common.enums.EFacility;
import com.jmsc.app.entity.CreditFacility;

/**
 * @author Chandan
 *
 */
public interface CreditFacilityRepository extends JpaRepository<CreditFacility, Long> {

	List<CreditFacility> findAllByClientId(Long clientId);
	
	Optional<CreditFacility> findAllByClientIdAndAccountNumber(Long clientId, String accountNumber);
	
	List<CreditFacility> findAllByClientIdAndFacilityType(Long clientId, EFacility facilityType);
	
	List<CreditFacility> findAllByClientIdAndBgGroupId(Long clientId, Long bgGroupId);
	
	List<CreditFacility> findAllByClientIdAndLoanId(Long clientId, Long loanId);
}
