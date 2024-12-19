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

import com.jmsc.app.common.dto.EInvoiceDTO;
import com.jmsc.app.common.enums.EFyMonths;
import com.jmsc.app.service.EInvoiceService;

import io.swagger.annotations.Api;

/**
 * @author anuhr
 *
 */
@RestController
@RequestMapping("/v1/einvoice")
@Api(value = "APIs to handle eInvoice opersations")
public class EInvoiceEndPoint {
	
	@Autowired
	private EInvoiceService service;
	
	
	@PostMapping("/create_or_update")
	ResponseEntity<EInvoiceDTO> addOrUpdateInvoice(@RequestBody EInvoiceDTO invoiceDTO){
		EInvoiceDTO response = service.saveOrUpdate(invoiceDTO);
		return ResponseEntity.ok(response);
	}
	
	
	
	@GetMapping("/{clientId}/{state}/{fy}/{month}")
	public ResponseEntity<List<EInvoiceDTO>> getAllBids(@PathVariable("clientId") Long clientId, 
														@PathVariable("state") String gstState,
														@PathVariable("fy") String fy,
														@PathVariable("month") EFyMonths month){
		
		
		
		List<EInvoiceDTO> invoices = service.getEInvloices(clientId, gstState, fy, month);
		return ResponseEntity.ok(invoices);
	}
	
	
	@DeleteMapping("/delete/{client_id}/{invoice_id}")
	public ResponseEntity<Integer> deleteEInvoice(@PathVariable("client_id")Long clientId,
												 @PathVariable("invoice_id")Long id) {
		Integer statusCode = service.deleteEInvoice(clientId, id);
		return ResponseEntity.ok(statusCode);
	}

}
