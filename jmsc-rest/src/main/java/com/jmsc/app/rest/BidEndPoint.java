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

import com.jmsc.app.common.dto.BidCostDTO;
import com.jmsc.app.common.dto.BidDTO;
import com.jmsc.app.common.enums.EBidStatus;
import com.jmsc.app.common.wrapper.FeeDetails;
import com.jmsc.app.common.wrapper.OtherBidCost;
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
	public ResponseEntity<BidDTO> createBid(@RequestBody BidDTO bidDTO){
		BidDTO dto = service.createBid(bidDTO);
		return ResponseEntity.ok(dto);
	}
	
	@DeleteMapping("/discard_bid/{client_id}/{bid_id}")
	public ResponseEntity<Boolean> discardBid(@PathVariable("client_id") Long clientId, @PathVariable("bid_id") Long bidId){
		Boolean status = service.discardBid(clientId, bidId);
		return ResponseEntity.ok(status);
	}
	
	
	@GetMapping("/bids_by_status/{client_id}/{status}")
	public ResponseEntity<List<BidDTO>> getAllBids(@PathVariable("client_id") Long clientId, @PathVariable("status") EBidStatus status){
		List<BidDTO> bids = service.getBidsByStatus(clientId,status);
		return ResponseEntity.ok(bids);
	}
	
	
	@GetMapping("/all_accepted_bids/{client_id}")
	public ResponseEntity<List<BidDTO>> getAcceptedBids(@PathVariable("client_id") Long clientId){
		List<BidDTO> bids = service.getAllAcceptedBids(clientId);
		return ResponseEntity.ok(bids);
	}
	
	
	@GetMapping("/all_rejected_bids/{client_id}")
	public ResponseEntity<List<BidDTO>> getAllRejectedBids(@PathVariable("client_id") Long clientId){
		List<BidDTO> bids = service.getAllRejectedBids(clientId);
		return ResponseEntity.ok(bids);
	}
	
	
	
	@PostMapping("/save_bid_cost")
	public ResponseEntity<BidCostDTO> saveBiddingCostLinkage(@RequestBody BidCostDTO bidCostDTO){
		BidCostDTO dto = service.saveBidCost(bidCostDTO);
		return ResponseEntity.ok(dto);
	}
	
	
	@GetMapping("/bid_cost/{bid_id}")
	public ResponseEntity<BidCostDTO> getBidCost(@PathVariable("bid_id") Long bidId){
		BidCostDTO bidCost = service.getBidCost(bidId);
		return ResponseEntity.ok(bidCost);
	}
	
	
	@PostMapping("/clear_emd/{bid_id}")
	public ResponseEntity<Boolean> clearEMD(@PathVariable("bid_id") Long bidId){
		Boolean status = service.clearEMD(bidId);
		return ResponseEntity.ok(status);
	}
	
	
	@PostMapping("/mark_emd_returned/{bid_id}")
	public ResponseEntity<Boolean> markEMDReturned(@PathVariable("bid_id") Long bidId){
		Boolean status = service.markEMDReturned(bidId);
		return ResponseEntity.ok(status);
	}
	
	
	@PostMapping("/save_bid_fee/{client_id}/{bid_id}")
	public ResponseEntity<FeeDetails> saveBidFee(@RequestBody FeeDetails feeDetails, @PathVariable("client_id")Long clientId, @PathVariable("bid_id")Long bidId){
		FeeDetails fee = service.saveBidFee(feeDetails, clientId, bidId);
		return ResponseEntity.ok(fee);
	}
	
	
	
	@PostMapping("/clear_fee/{bid_id}")
	public ResponseEntity<Boolean> clearFee(@PathVariable("bid_id") Long bidId){
		Boolean status = service.clearFee(bidId);
		return ResponseEntity.ok(status);
	}
	
	
	@PostMapping("/save_other_cost")
	public ResponseEntity<Boolean> saveOtherCost(@RequestBody OtherBidCost otherCost){
		Boolean status = service.saveOtherCost(otherCost);
		return ResponseEntity.ok(status);
	}
	
	
	@GetMapping("/bid_details/{client_id}/{bid_id}")
	public ResponseEntity<BidDTO> getBidDetails(@PathVariable("client_id") Long clientId, @PathVariable("bid_id") Long bidId){
		BidDTO bid = service.getBidDetails(clientId, bidId);
		return ResponseEntity.ok(bid);
	}
}
