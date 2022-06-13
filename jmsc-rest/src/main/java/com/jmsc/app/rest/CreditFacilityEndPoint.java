
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jmsc.app.common.dto.CreditFacilityDTO;
import com.jmsc.app.common.enums.EFacility;
import com.jmsc.app.common.rqrs.FacilityLinkageDetails;
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
	
	
	@GetMapping("/get_all_active/{client_id}")
	ResponseEntity<List<CreditFacilityDTO>> getAllActive(@PathVariable("client_id") Long clientId){
		List<CreditFacilityDTO> list = service.getAllActiveCrditFacility(clientId);
		return ResponseEntity.ok(list);
	}
	
	
	@GetMapping("/get_all_closed/{client_id}")
	ResponseEntity<List<CreditFacilityDTO>> getAllClosed(@PathVariable("client_id") Long clientId){
		List<CreditFacilityDTO> list = service.getAllClosedCrditFacility(clientId);
		return ResponseEntity.ok(list);
	}
	
	
	
	@GetMapping("/free_facilities/{client_id}")
	ResponseEntity<List<CreditFacilityDTO>> getCollateralsForBid(@PathVariable("client_id") Long clientId){
		List<CreditFacilityDTO> list = service.getCollateralsForBid(clientId);
		return ResponseEntity.ok(list);
	}

	
	
	@GetMapping("/all_active_by_facility_type")
	ResponseEntity<List<CreditFacilityDTO>> getAllActiveByFacilityType(@RequestParam("clientId") Long clientId, @RequestParam("facilityType") EFacility facilityType){
		List<CreditFacilityDTO> list = service.getAllActiveByFacilityType(clientId, facilityType);
		return ResponseEntity.ok(list);
	}
	
	
	
	@GetMapping("/all_closed_by_facility_type")
	ResponseEntity<List<CreditFacilityDTO>> getAllClosedByFacilityType(@RequestParam("clientId") Long clientId, @RequestParam("facilityType") EFacility facilityType){
		List<CreditFacilityDTO> list = service.getAllClosedByFacilityType(clientId, facilityType);
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
	 * 4. FD/NSC should not be closed
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
	
	
	@GetMapping("linkage_details/{client_id}/{facility_id}")
	ResponseEntity<FacilityLinkageDetails> getApplicableBankGuaranteeBgGroup(@PathVariable("client_id") Long clientId, @PathVariable("facility_id") Long facilityId){
		FacilityLinkageDetails list = service.getFacilityLinkageDetails(clientId, facilityId);
		return ResponseEntity.ok(list);
	}
	
	/**
	 * 
	 * This service mark a credit facility close.
	 * A closed facility will not be available to use in any bid, loan, bg group etc.
	 * Also if a facility is already linked to any loan, bid or bg group
	 * then before closing we need to remove this facility from any such linkage.
	 * 
	 * @param clientId
	 * @param facilityId
	 * @return
	 * @throws Throwable
	 */
	@PutMapping("close/{client_id}/{facility_id}")
	ResponseEntity<Boolean> closeFacility(@PathVariable("client_id") Long clientId, @PathVariable("facility_id") Long facilityId) throws Throwable{
		Boolean status = service.closeFacility(clientId, facilityId);
		return ResponseEntity.ok(status);
	}
}
