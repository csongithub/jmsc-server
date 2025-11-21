/**
 * 
 */
package com.jmsc.app.rest.accouting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jmsc.app.common.dto.accounting.CreditorDTO;
import com.jmsc.app.common.dto.accounting.ListDTO;
import com.jmsc.app.service.accounting.AccountingService;

import io.swagger.annotations.Api;

/**
 * @author anuhr
 *
 */
@RestController
@RequestMapping("/v1/accounting")
@Api(value = "Single APIs to handle eInvoice opersations")
public class AccountingEndPoint {

	@Autowired
	private AccountingService accountingService;
	
	
	@PostMapping("/creditor/create_or_update")
	ResponseEntity<CreditorDTO> createOrUpdate(@RequestBody CreditorDTO creditorsDTO){
		CreditorDTO response = accountingService.createOrUpdate(creditorsDTO);
		return ResponseEntity.ok(response);
	}
	
	
	@GetMapping("/creditor/{clientId}/{id}")
	public ResponseEntity<CreditorDTO> getById(@PathVariable("clientId") Long clientId, 
												@PathVariable("id") Long id){
		
		CreditorDTO response = accountingService.getById(clientId, id);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/creditors/{clientId}")
	public ResponseEntity<ListDTO> getAllCreditors(@PathVariable("clientId")Long clientId) {
		ListDTO list = accountingService.getAllCreditors(clientId);
		return ResponseEntity.ok(list);
	}
}
