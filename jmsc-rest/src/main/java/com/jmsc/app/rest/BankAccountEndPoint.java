package com.jmsc.app.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jmsc.app.common.dto.BankAccountDTO;
import com.jmsc.app.service.BankAccountService;

import io.swagger.annotations.Api;

/**
 * @author Chandan
 *
 */
@RestController
@RequestMapping("/v1/bankaccount")
@Api(value = "APIs to handle party bank account")
public class BankAccountEndPoint {
	
	@Autowired
	private BankAccountService service;
	
	
	@PostMapping("/addBankAccount")
	public ResponseEntity<BankAccountDTO> addBankAccount(@RequestBody BankAccountDTO bankAccount){
		BankAccountDTO savedAccount = service.addBankAccount(bankAccount);
		return ResponseEntity.ok(savedAccount);
	}
	
	
	
	@GetMapping("/getAllAccounts")
	public ResponseEntity<List<BankAccountDTO>> getAllAccounts(){
		List<BankAccountDTO> accounts = service.getAllAccounts();
		return ResponseEntity.ok(accounts);
	}
	
	
	
	@GetMapping("/getActiveAccounts")
	public ResponseEntity<List<BankAccountDTO>> getActiveAccounts(){
		List<BankAccountDTO> accounts = service.getActiveAccounts();
		return ResponseEntity.ok(accounts);
	}
}
