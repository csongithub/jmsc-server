/**
 * 
 */
package com.jmsc.app.rest.accouting;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jmsc.app.common.dto.accounting.CreditorDTO;
import com.jmsc.app.common.dto.accounting.LedgerDTO;
import com.jmsc.app.common.dto.accounting.LedgerEntryDTO;
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
	
	
	@GetMapping("/creditor/materials/{clientId}/{creditorId}")
	public ResponseEntity<String> materials(@PathVariable("clientId") Long clientId, 
												@PathVariable("creditorId") Long creditorId){
		
		String response = accountingService.getMaterials(clientId, creditorId);
		return ResponseEntity.ok(response);
	}
	
	
	
	@PostMapping("/ledger/create_or_update")
	ResponseEntity<LedgerDTO> createOrUpdate(@RequestBody LedgerDTO ledgerDTO){
		LedgerDTO response = accountingService.createOrUpdate(ledgerDTO);
		return ResponseEntity.ok(response);
	}
	
	
	@GetMapping("/ledger/{clientId}/{creditorId}")
	public ResponseEntity<List<LedgerDTO>> getLedgers(@PathVariable("clientId")Long clientId, @PathVariable("creditorId")Long creditorId) {
		List<LedgerDTO> list = accountingService.getLedgers(clientId, creditorId);
		return ResponseEntity.ok(list);
	}
	
	
	@GetMapping("/ledger/{clientId}/{creditorId}/{ledgerId}")
	public ResponseEntity<LedgerDTO> getLedger(@PathVariable("clientId")Long clientId, @PathVariable("creditorId")Long creditorId, @PathVariable("ledgerId")Long ledgerId) {
		LedgerDTO list = accountingService.getLedger(clientId, creditorId, ledgerId);
		return ResponseEntity.ok(list);
	}
	
	
	@PostMapping("/ledger/entries/post")
	ResponseEntity<Boolean> postEntries(@RequestBody List<LedgerEntryDTO> entries){
		Boolean status = accountingService.postCreditEntries(entries);
		return ResponseEntity.ok(status);
	}
	
	
	@PostMapping("/ledger/entries/validate_by_date_and_challan")
	ResponseEntity<LedgerEntryDTO> validateByChallan(@RequestBody LedgerEntryDTO req){
		LedgerEntryDTO entry = accountingService.validateByChallan(req);
		return ResponseEntity.ok(entry);
	}
}
