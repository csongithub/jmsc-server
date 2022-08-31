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
import org.springframework.web.bind.annotation.RestController;

import com.jmsc.app.common.dto.PaymentDTO;
import com.jmsc.app.common.dto.PaymentSummaryDTO;
import com.jmsc.app.common.enums.EPaymentStatus;
import com.jmsc.app.service.PaymentService2;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/v2/payment")
@Api(value = "APIs to handle payments")
public class PaymentEndPoint2 {
	
	
	@Autowired
	private PaymentService2 service;
	
	
	@PostMapping("/save")
	public ResponseEntity<PaymentDTO> createDraft(@RequestBody PaymentDTO payment){
		PaymentDTO savedPayment = service.savePayment(payment);
		return ResponseEntity.ok(savedPayment);
	}
	
	
	@GetMapping("/get/{client_id}/{payment_id}")
	public ResponseEntity<PaymentDTO> getPayment(@PathVariable("client_id")Long clientId, @PathVariable("payment_id")Long paymentId){
		PaymentDTO savedPayment = service.getPayment(clientId, paymentId);
		return ResponseEntity.ok(savedPayment);
	}
	
	
	
	@GetMapping("/all_drafts/{client_id}/{status}")
	public ResponseEntity<List<PaymentSummaryDTO>> createDraft(@PathVariable("client_id")Long clientId , @PathVariable("status")EPaymentStatus status) throws Throwable{
		List<PaymentSummaryDTO> drafts = service.getAllDraft(clientId, status);
		return ResponseEntity.ok(drafts);
	}
	
	
	
	@DeleteMapping("/delete/{client_id}/{payment_id}/{status}")
	public ResponseEntity<Integer> deletePayment(@PathVariable("client_id")Long clientId,
												 @PathVariable("payment_id")Long paymentId, 
												 @PathVariable("status")EPaymentStatus status) {
		Integer statusCode = service.deletePayment(clientId, paymentId, status);
		return ResponseEntity.ok(statusCode);
	}
	
	
	
	@PutMapping("/approve/{client_id}/{payment_id}")
	public ResponseEntity<Integer> approvePayment(@PathVariable("client_id")Long clientId,
												 @PathVariable("payment_id")Long paymentId) {
		Integer statusCode = service.approvePayment(clientId, paymentId);
		return ResponseEntity.ok(statusCode);
	}
}
