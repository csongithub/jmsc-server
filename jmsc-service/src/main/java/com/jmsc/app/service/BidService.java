/**
 * 
 */
package com.jmsc.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmsc.app.common.dto.BidDTO;
import com.jmsc.app.common.util.ObjectMapperUtil;
import com.jmsc.app.entity.BankAccount;
import com.jmsc.app.entity.Bid;
import com.jmsc.app.repository.BidRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Chandan
 *
 */
@Service
@Slf4j
public class BidService {
	
	@Autowired
	private BidRepository repository;
	
	public BidDTO createBid(BidDTO bidDto) {
		
		Bid bid = ObjectMapperUtil.map(bidDto, Bid.class);
		bid = repository.save(bid);
		BidDTO bisavedBid = ObjectMapperUtil.map(bid, BidDTO.class);
		return bisavedBid;
	}
	
	
	public List<BidDTO> getAllBids(Long clientId) {
		List<Bid> bidList = repository.findAllByClientId(clientId);
		List<BidDTO> bidsDto = ObjectMapperUtil.mapAll(bidList, BidDTO.class);
		return bidsDto;
	}
}
