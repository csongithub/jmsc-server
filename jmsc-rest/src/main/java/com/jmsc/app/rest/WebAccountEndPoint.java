package com.jmsc.app.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jmsc.app.common.dto.WebAccountDTO;
import com.jmsc.app.service.WebAccountService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/v1/web_account")
@Api(value = "Application Status")
public class WebAccountEndPoint {
	
	@Autowired
	private WebAccountService service;
	
	
	@PostMapping("/create_or_update")
	public ResponseEntity<WebAccountDTO> save(@RequestBody WebAccountDTO account){
		WebAccountDTO response = service.createOrUpdate(account);
		return ResponseEntity.ok(response);
	}
	
	
	
	@GetMapping("/all/{client_id}")
	public ResponseEntity<List<WebAccountDTO>> allAccounts(@PathVariable("client_id")Long clientId){
		List<WebAccountDTO> response = service.getAll(clientId);
		return ResponseEntity.ok(response);
	}
	
	
	
	@GetMapping("/get_account_attributes/{client_id}/{account_id}")
	public ResponseEntity<WebAccountDTO> showAllAttributes(@PathVariable("client_id")Long clientId, @PathVariable("account_id")Long accountId){
		WebAccountDTO response = service.showAllAttributes(clientId, accountId);
		return ResponseEntity.ok(response);
	}
	
	
	
	@DeleteMapping("/delete_account/{client_id}/{account_id}")
	public ResponseEntity<Boolean> deleteAccount(@PathVariable("client_id")Long clientId, @PathVariable("account_id")Long accountId)throws Exception{
		Boolean response = service.deleteAccount(clientId, accountId);
		return ResponseEntity.ok(response);
	}
}
