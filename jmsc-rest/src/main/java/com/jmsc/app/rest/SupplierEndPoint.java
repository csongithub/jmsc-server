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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jmsc.app.common.dto.PurchageLedgerDTO;
import com.jmsc.app.common.dto.SupplierDTO;
import com.jmsc.app.service.SupplierService;

import io.swagger.annotations.Api;

/**
 * @author anuhr
 *
 */
@RestController
@RequestMapping("/v1/supplier")
@Api(value = "APIs to handle eInvoice opersation")
public class SupplierEndPoint {
	
	
	@Autowired
	private SupplierService service;
	
	
	@PostMapping("/create_or_update")
	ResponseEntity<SupplierDTO> post(@RequestBody SupplierDTO supplierDTO){
		SupplierDTO response = service.createOrUpdate(supplierDTO);
		return ResponseEntity.ok(response);
	}
	
	
	
	@GetMapping("/{clientId}")
	ResponseEntity<List<SupplierDTO>> getAllByClient(@PathVariable("clientId") Long clientId){
		List<SupplierDTO> response = service.getAllSuuplier(clientId);
		return ResponseEntity.ok(response);
	}
	
	
	
	@DeleteMapping("/delete/{client_id}/{id}")
	public ResponseEntity<Integer> deleteBankGuarantee(@PathVariable("client_id")Long clientId,
												 	   @PathVariable("id")Long id) {
		Integer statusCode = service.deleteSupplier(clientId, id);
		return ResponseEntity.ok(statusCode);
	}
	
	
	@GetMapping("/isledgerexist/{clientId}/{supplierId}")
	ResponseEntity<Boolean> getAllByClient(@PathVariable("clientId") Long clientId, @PathVariable("supplierId")Long supplierId){
		Boolean response = service.isLedgerCreated(clientId, supplierId);
		return ResponseEntity.ok(response);
	}
	
	
	@PostMapping("/createledger")
	ResponseEntity<Boolean> getAllByClient(@RequestBody PurchageLedgerDTO entry){
		Boolean response = service.createLedger(entry);
		return ResponseEntity.ok(response);
	}
}
