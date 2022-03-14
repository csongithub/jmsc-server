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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jmsc.app.common.dto.BidDTO;
import com.jmsc.app.service.BidService;

import io.swagger.annotations.Api;

/**
 * @author Chandan
 *
 */
@RestController
@RequestMapping("/v1/bid")
@Api(value = "APIs to handle bids opersations")
public class BidEndPoint {
	
	@Autowired
	private BidService service;
	
	@PostMapping("/create_bid")
	public ResponseEntity<BidDTO> addClient(@RequestBody BidDTO bidDTO){
		BidDTO dto = service.createBid(bidDTO);
		return ResponseEntity.ok(dto);
	}
	
	
	@GetMapping("/get_all/{client_id}")
	public ResponseEntity<List<BidDTO>> getAllBids(@PathVariable("client_id") Long clientId){
		List<BidDTO> dto = service.getAllBids(clientId);
		return ResponseEntity.ok(dto);
	}
}
