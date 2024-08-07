/**
 * 
 */
package com.jmsc.app.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.multipart.MultipartFile;

import com.jmsc.app.common.dto.PartyBankAccountDTO;
import com.jmsc.app.common.enums.EBankAccountStatus;
import com.jmsc.app.service.PartyBankAccountService;

import io.swagger.annotations.Api;

/**
 * @author Chandan
 *
 */

@RestController
@RequestMapping("/v1/party/bankaccount")
@Api(value = "APIs to handle party bank account")
public class PartyBankAccountEndPoint {
	
	@Autowired
	private PartyBankAccountService service;
	
	
	
	 @PostMapping("/uploadFile")
	 public String uploadFile(@RequestParam("file") MultipartFile file)throws Throwable {
		 return service.storeFile(file);
	 }
	 
	 

	@GetMapping("/getFileRecords/**")
	public ResponseEntity<List<PartyBankAccountDTO>> getFileRecords(HttpServletRequest request) throws Throwable{
		String fullUrl = request.getRequestURL().toString();
	    String filePath = fullUrl.split("/getFileRecords/")[1];
	    List<PartyBankAccountDTO> records = service.getAccountRecords(filePath);
		return ResponseEntity.ok(records);
	}
	
	
	
	@PostMapping("/addAccount")
	public ResponseEntity<PartyBankAccountDTO> addAccount(@RequestBody PartyBankAccountDTO accountDTO) {
		PartyBankAccountDTO account = service.addAccount(accountDTO);
		return ResponseEntity.ok(account);
	}
	
	
	
	@GetMapping("/allAccounts")
	public ResponseEntity<List<PartyBankAccountDTO>> getAllAccounts(@RequestParam("clientId") Long clientId) {
		List<PartyBankAccountDTO> accounts = service.getAllAccounts(clientId);
		return ResponseEntity.ok(accounts);
	}
	
	
	
	@GetMapping("/account_by_id/{client_id}/{account_id}")
	public ResponseEntity<PartyBankAccountDTO> getAccount(@PathVariable("client_id")Long clientId, @PathVariable("account_id")Long accountId) {
		PartyBankAccountDTO account  = service.getAccount(clientId, accountId);
		return ResponseEntity.ok(account);
	}
	
	
	
	@PutMapping("/linke_party_account/{client_id}/{party_id}/{account_id}")
	public ResponseEntity<Integer> linkPartyAccount(@PathVariable("client_id")Long clientId,
												  @PathVariable("party_id")Long partyId,
												  @PathVariable("account_id")Long accountId) {
		Integer statusCode = service.linkPartyAccount(clientId, partyId, accountId);
		return ResponseEntity.ok(statusCode);
	}
	
	
	
	@PutMapping("/de_linke_party_account/{client_id}/{party_id}/{account_id}")
	public ResponseEntity<Integer> delink(@PathVariable("client_id")Long clientId,
												  @PathVariable("party_id")Long partyId,
												  @PathVariable("account_id")Long accountId) {
		Integer statusCode = service.delinkPartyAccount(clientId, partyId, accountId);
		return ResponseEntity.ok(statusCode);
	}
	
	
	@GetMapping("/bank_by_ifsc/{ifsc_code}")
	public ResponseEntity<String> delink(@PathVariable("ifsc_code")String ifscCode) {
		String bank = service.getBankByIfsc(ifscCode);
		return ResponseEntity.ok(bank);
	}
	
	
	@PutMapping("/update_status/{client_id}/{account_id}/{status}")
	public ResponseEntity<Integer> updateStatus(@PathVariable("client_id")Long clientId,
												@PathVariable("account_id")Long accountId,
												@PathVariable("status") EBankAccountStatus status) {
		Integer statusCode = service.updateStatus(clientId, accountId, status);
		return ResponseEntity.ok(statusCode);
	}
}
