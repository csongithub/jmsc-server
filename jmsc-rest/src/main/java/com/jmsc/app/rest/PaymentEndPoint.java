/**
 * 
 */
package com.jmsc.app.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jmsc.app.common.dto.PaymentDraftDTO;
import com.jmsc.app.common.rqrs.GetPaymentsByDateRequest;
import com.jmsc.app.service.PyamentService;

import io.swagger.annotations.Api;

/**
 * @author Chandan
 *
 */
@RestController
@RequestMapping("/v1/payment")
@Api(value = "APIs to handle payments")
public class PaymentEndPoint {
	
	@Autowired
	private PyamentService service;
	
	
	@PostMapping("/createDraft")
	public ResponseEntity<String> createDraft(@RequestBody PaymentDraftDTO draftDTO){
		String status = service.createPaymentDraft(draftDTO);
		return ResponseEntity.ok(status);
	}
	
	
	
	@GetMapping("/getAllDrafts")
	public ResponseEntity<List<PaymentDraftDTO>> getAllDrafts(@RequestParam("clientId") Long clientId){
		List<PaymentDraftDTO> list = service.getAllDrafts(clientId);
		return ResponseEntity.ok(list);
	}
	
	
	
	@PutMapping("/markPrinted")
	public ResponseEntity<Integer> markPrinted(@RequestBody List<Long> payments){
		Integer value = service.markPrinted(payments);
		return ResponseEntity.ok(value);
	}
	
	
	
	@GetMapping("/getAllPrinted/{clientId}")
	public ResponseEntity<List<PaymentDraftDTO>> getAllPrinted(@PathVariable("clientId") Long clientId){
		List<PaymentDraftDTO> list= service.getAllPrinted(clientId);
		return ResponseEntity.ok(list);
	}
	
	
	
	
	@PutMapping("/getAllPrintedByRange")
	public ResponseEntity<List<PaymentDraftDTO>> getAllPrintedByRange(@RequestParam("clientId")Long clientId, @RequestBody GetPaymentsByDateRequest req){
		List<PaymentDraftDTO> list= service.getAllPrintedByRange(clientId, req);
		return ResponseEntity.ok(list);
	}
	
		
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Integer> discardDraft(@PathVariable("id") Long id){
		Integer value = service.discardDraft(id);
		return ResponseEntity.ok(value);
	}
}
