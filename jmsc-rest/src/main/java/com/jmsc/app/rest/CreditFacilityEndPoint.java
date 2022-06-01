
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
import com.jmsc.app.common.wrapper.CreditFacilityWrapper;
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
	ResponseEntity<List<CreditFacilityDTO>> getFreeFacilities(@PathVariable("client_id") Long clientId){
		List<CreditFacilityDTO> list = service.getFreeFacilities(clientId);
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
	
	
	
	@GetMapping("/deposits_for_bg_group/{client_id}")
	ResponseEntity<List<CreditFacilityDTO>> getApplicableDepositForBgGroup(@PathVariable("client_id") Long clientId){
		List<CreditFacilityDTO> list = service.getApplicableDepositForBgGroup(clientId);
		return ResponseEntity.ok(list);
	}
	
	
	
	@GetMapping("/guarantees_for_bg_group/{client_id}")
	ResponseEntity<List<CreditFacilityDTO>> getApplicableBankGuaranteeBgGroup(@PathVariable("client_id") Long clientId){
		List<CreditFacilityDTO> list = service.getApplicableBankGuaranteeBgGroup(clientId);
		return ResponseEntity.ok(list);
	}
	
	
	/**
	 * This method is used to get the list of deposits & guarantees that are linked to a specific bank 
	 * guarantee group.
	 * 
	 * All deposits which are hold against/in a BG Group
	 * All guarantees that are issued in this BG Group
	 * 
	 * @param clientId
	 * @param groupId
	 * @return
	 */
	@GetMapping("/linked_facilities_for_bg_group/{client_id}/{group_id}")
	ResponseEntity<CreditFacilityWrapper> getApplicableBankGuaranteeBgGroup(@PathVariable("client_id") Long clientId, @PathVariable("group_id")Long groupId){
		CreditFacilityWrapper response = service.getLinkedFacilitiesForBankGuaranteeGroup(clientId, groupId);
		return ResponseEntity.ok(response);
	}
}
