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

import com.jmsc.app.common.dto.PartyBankAccountDTO;
import com.jmsc.app.common.dto.PaymentDTO;
import com.jmsc.app.common.dto.PaymentSummaryDTO;
import com.jmsc.app.common.enums.EPaymentStatus;
import com.jmsc.app.common.rqrs.ApprovePaymentRequest;
import com.jmsc.app.common.rqrs.GetPaymentsByDateRequest;
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
	
	
	
	@PutMapping("/approve_payments")
	public ResponseEntity<Integer> approveAllPayments(@RequestBody ApprovePaymentRequest req) {
		Integer statusCode = service.approvePayments(req);
		return ResponseEntity.ok(statusCode);
	}
	
	
	@PutMapping("/reject/{client_id}/{payment_id}")
	public ResponseEntity<Integer> rejectPayment(@PathVariable("client_id")Long clientId,
												 @PathVariable("payment_id")Long paymentId) {
		Integer statusCode = service.rejectPayment(clientId, paymentId);
		return ResponseEntity.ok(statusCode);
	}
	
	
	@PutMapping("/linke_party_account/{client_id}/{party_id}/{account_id}")
	public ResponseEntity<Integer> linkPartyAccount(@PathVariable("client_id")Long clientId,
												  @PathVariable("party_id")Long partyId,
												  @PathVariable("account_id")Long accountId) {
		Integer statusCode = service.linkPartyAccount(clientId, partyId, accountId);
		return ResponseEntity.ok(statusCode);
	}
	
	
	
	@GetMapping("/party_linked_accounts/{client_id}/{party_id}")
	public ResponseEntity<List<PartyBankAccountDTO>> getAllLinkedAccounts(@PathVariable("client_id")Long clientId , @PathVariable("party_id")Long partyId) throws Throwable{
		List<PartyBankAccountDTO> list = service.getAllLinkedAccounts(clientId, partyId);
		return ResponseEntity.ok(list);
	}
	
	
	
	@PutMapping("/payment_between_dates")
	public ResponseEntity<List<PaymentSummaryDTO>> getPaymentsBetweenDates(@RequestParam("client_id")Long clientId,
																		   @RequestParam("status")EPaymentStatus status,
																		   @RequestBody GetPaymentsByDateRequest req){
		List<PaymentSummaryDTO> list= service.getPaymentsBetweenDates(clientId, status, req);
		return ResponseEntity.ok(list);
	}
}
