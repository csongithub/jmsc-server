/**
 * 
 */
package com.jmsc.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmsc.app.common.enums.EFacility;
import com.jmsc.app.entity.users.CreditFacility;

/**
 * @author Chandan
 *
 */
public interface CreditFacilityRepository extends JpaRepository<CreditFacility, Long> {

	List<CreditFacility> findAllByClientId(Long clientId);
	
	List<CreditFacility> findAllByClientIdAndFacilityType(Long clientId, EFacility facilityType);
}
