/**
 * 
 */
package com.jmsc.app.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jmsc.app.common.dto.PartyBankAccountDTO;
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
	public ResponseEntity<List<PartyBankAccountDTO>> getAllAccounts() {
		List<PartyBankAccountDTO> accounts = service.getAllAccounts();
		return ResponseEntity.ok(accounts);
	}
}
