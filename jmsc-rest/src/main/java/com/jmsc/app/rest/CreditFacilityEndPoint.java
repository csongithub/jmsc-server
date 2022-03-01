/**
 * 
 */
package com.jmsc.app.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jmsc.app.common.dto.CreditFacilityDTO;
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
	
	
	@PostMapping("/add")
	ResponseEntity<CreditFacilityDTO> addCreditFacility(@RequestBody CreditFacilityDTO cfDTO){
		CreditFacilityDTO response = service.addCreditFacility(cfDTO);
		return ResponseEntity.ok(response);
	}
	
	
	@GetMapping("/get_all/{client_id}")
	ResponseEntity<List<CreditFacilityDTO>> getAll(@PathVariable("client_id") Long clientId){
		List<CreditFacilityDTO> list = service.getAllCrditFacility(clientId);
		return ResponseEntity.ok(list);
	}
}