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

import com.jmsc.app.common.dto.BankGuaranteeDTO;
import com.jmsc.app.service.BankGuaranteeService;

import io.swagger.annotations.Api;

/**
 * @author anuhr
 *
 */
@RestController
@RequestMapping("/v1/bank_guarantee")
@Api(value = "APIs to handle login services")
public class BankGuaranteeEndPoint {
	
	@Autowired
	private BankGuaranteeService service;
	
	
	@PostMapping("/create_or_update")
	ResponseEntity<BankGuaranteeDTO> addCreditFacility(@RequestBody BankGuaranteeDTO bgDTO){
		BankGuaranteeDTO response = service.createOrUpdate(bgDTO);
		return ResponseEntity.ok(response);
	}
	
	
	
	
	@GetMapping("/{clientId}")
	ResponseEntity<List<BankGuaranteeDTO>> getAllBankGuarantee(@PathVariable("clientId") Long clientId){
		List<BankGuaranteeDTO> response = service.getAllBankGuarantee(clientId);
		return ResponseEntity.ok(response);
	}
	
	
	
	
	@GetMapping("/{clientId}/{id}")
	ResponseEntity<BankGuaranteeDTO> getAllBankGuarantee(@PathVariable("clientId") Long clientId, @PathVariable("id") Long id){
		BankGuaranteeDTO response = service.getBankGuarantee(clientId, id);
		return ResponseEntity.ok(response);
	}
}
