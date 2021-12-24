/**
 * 
 */
package com.jmsc.app.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jmsc.app.common.dto.PaymentDraftDTO;
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
	public ResponseEntity<List<PaymentDraftDTO>> getAllDrafts(){
		List<PaymentDraftDTO> list = service.getAllDrafts();
		return ResponseEntity.ok(list);
	}
	
	
	
	
	public ResponseEntity<Integer> discardDraft(Long id){
		Integer value = service.discardDraft(id);
		return ResponseEntity.ok(value);
	}
}
