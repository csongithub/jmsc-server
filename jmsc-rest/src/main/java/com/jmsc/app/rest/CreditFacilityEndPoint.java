
/**
 * 
 */
package com.jmsc.app.rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jmsc.app.common.dto.CreditFacilityDTO;
import com.jmsc.app.common.enums.EFacility;
import com.jmsc.app.service.CreditFacilityService;

import io.swagger.annotations.Api;

/**
 * @author Chandan
 *
 */
@RestController
@RequestMapping("/v1/credit_facility")
@Api(value = "APIs to handle login services")
public class CreditFacilityEndPoint {

	@Autowired
	private CreditFacilityService service;
	
	
	@PostMapping("/add_facility")
	ResponseEntity<CreditFacilityDTO> addCreditFacility(@RequestBody CreditFacilityDTO cfDTO){
		CreditFacilityDTO response = service.addCreditFacility(cfDTO);
		return ResponseEntity.ok(response);
	}
	
	
	@GetMapping("/get_all/{client_id}")
	ResponseEntity<List<CreditFacilityDTO>> getAll(@PathVariable("client_id") Long clientId){
		List<CreditFacilityDTO> list = service.getAllCrditFacility(clientId);
		return ResponseEntity.ok(list);
	}
	
	
	
	@GetMapping("/free_facilities/{client_id}")
	ResponseEntity<List<CreditFacilityDTO>> getCollateralsForBid(@PathVariable("client_id") Long clientId){
		List<CreditFacilityDTO> list = service.getCollateralsForBid(clientId);
		return ResponseEntity.ok(list);
	}

	
	
	@GetMapping("/all_by_facility_type")
	ResponseEntity<List<CreditFacilityDTO>> getAllByFacilityType(@RequestParam("clientId") Long clientId, @RequestParam("facilityType") EFacility facilityType){
		List<CreditFacilityDTO> list = service.getAllByFacilityType(clientId, facilityType);
		return ResponseEntity.ok(list);
	}
	
	
	
	@GetMapping("/expiry")
	ResponseEntity<Map<Long, List<CreditFacilityDTO>>> expiry(){
		return ResponseEntity.ok(service.evaluateExpiry());
	}
	
	
	/**
	 * Gives available collateral (Fix Deposit(s) & NSCs) that can be used or hold against in a Bank Guarantee Group and Loans.
	 * There are three Special Conditions for these deposits.
	 * 1. FD/NSc should not be pledged in any bid as security or agreement
	 * 2. FD/NSC should not be already linked to any other BG Group
	 * 3. FD/NSC should not be already linked to any other Loan
	 * @param clientId
	 * @return
	 */
	@GetMapping("/free_collateral/{client_id}")
	ResponseEntity<List<CreditFacilityDTO>> getFreeCollateral(@PathVariable("client_id") Long clientId){
		List<CreditFacilityDTO> list = service.getFreeCollateral(clientId);
		return ResponseEntity.ok(list);
	}
	
	
	/**
	 * Gives available Bank Guarantee(s) list that is issued from a Bank Guarantee Group.
	 * There is only one Special Conditions for these guarantees that the BG should not be 
	 * already linked to any BG Group.
	 * @param clientId
	 * @return
	 */
	@GetMapping("/guarantees_for_bg_group/{client_id}")
	ResponseEntity<List<CreditFacilityDTO>> getApplicableBankGuaranteeBgGroup(@PathVariable("client_id") Long clientId){
		List<CreditFacilityDTO> list = service.getApplicableBankGuaranteeBgGroup(clientId);
		return ResponseEntity.ok(list);
	}
}
