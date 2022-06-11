/**
 * 
 */
package com.jmsc.app.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jmsc.app.common.dto.CreditFacilityDTO;
import com.jmsc.app.common.dto.LoanDTO;
import com.jmsc.app.common.rqrs.ManageCollateralRequest;
import com.jmsc.app.service.LoanService;

import io.swagger.annotations.Api;

/**
 * @author Chandan
 *
 */
@RestController
@RequestMapping("/v1/loan")
@Api(value = "APIs to loans")
public class LoanEndPoint {

	@Autowired
	private LoanService service;
	
	@PostMapping("/create")
	public ResponseEntity<LoanDTO> createLoan(@RequestBody LoanDTO dto){
		LoanDTO result = service.addLoan(dto);
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/all/{client_id}")
	public ResponseEntity<List<LoanDTO>> all(@PathVariable("client_id") Long clientId){
		List<LoanDTO> list = service.getAllLoans(clientId);
		return ResponseEntity.ok(list);
	}
	
	
	/**
	 * 
	 * This service gives all collateral that are hold against a loan
	 * 
	 * @param clientId
	 * @param loanId
	 * @return
	 */
	@GetMapping("/collateral/{client_id}/{loan_id}")
	ResponseEntity<List<CreditFacilityDTO>> loanCollateral(@PathVariable("client_id") Long clientId, @PathVariable("loan_id")Long loanId){
		List<CreditFacilityDTO> list = service.loanCollateral(clientId, loanId);
		return ResponseEntity.ok(list);
	}
	
	
	@PutMapping("/manage_collateral")
	ResponseEntity<LoanDTO> manageCollateral(@RequestBody ManageCollateralRequest request) throws Throwable {
		LoanDTO loan = service.manageCollateral(request);
		return ResponseEntity.ok(loan);
	}
}
