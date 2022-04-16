/**
 * 
 */
package com.jmsc.app.service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.jmsc.app.common.dto.BidCostDTO;
import com.jmsc.app.common.dto.BidDTO;
import com.jmsc.app.common.dto.CreditFacilityDTO;
import com.jmsc.app.common.enums.EBidStatus;
import com.jmsc.app.common.enums.EPledgedType;
import com.jmsc.app.common.util.Collections;
import com.jmsc.app.common.util.DateUtils;
import com.jmsc.app.common.util.ObjectMapperUtil;
import com.jmsc.app.common.wrapper.EMDDetails;
import com.jmsc.app.common.wrapper.EMDWrapper;
import com.jmsc.app.common.wrapper.FeeDetails;
import com.jmsc.app.common.wrapper.OtherBidCost;
import com.jmsc.app.entity.Bid;
import com.jmsc.app.entity.BidCost;
import com.jmsc.app.entity.CreditFacility;
import com.jmsc.app.repository.BidCostRepository;
import com.jmsc.app.repository.BidRepository;
import com.jmsc.app.repository.CreditFacilityRepository;

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
	
	@Autowired
	private BidCostRepository bidCostRepository;
	
	@Autowired
	private CreditFacilityRepository creditFacilityRespository;
	
	
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
	
	
	
	public List<BidDTO> getBidsByStatus(Long clientId, EBidStatus status) {
		
		List<BidDTO> bids = new ArrayList<BidDTO>();
		
		List<Bid> bidList = repository.findAllByClientIdAndStatus(clientId, status);
		bids = ObjectMapperUtil.mapAll(bidList, BidDTO.class);
		
		return bids;
	}
	
	
	
	public BidCostDTO saveBidCost(BidCostDTO bidCostDTO) {
		BidCost bidCost = new BidCost();
		
		Optional<BidCost> optional = bidCostRepository.findByBidId(bidCostDTO.getBidId());
		
		if(optional.isPresent()) {
			bidCost  = optional.get();
		} else {
			bidCost.setBidId(bidCostDTO.getBidId());
			bidCost.setClientId(bidCostDTO.getClientId());
		}
		
		if(bidCostDTO.getEmdDetails() != null && "online".equalsIgnoreCase(bidCostDTO.getEmdDetails().getEmdMode()))
			bidCostDTO.getEmdDetails().setEmdList(null);
		
		if(bidCostDTO.getEmdDetails() != null && "offline".equalsIgnoreCase(bidCostDTO.getEmdDetails().getEmdMode()))
			bidCostDTO.getEmdDetails().setOnlineDetails(null);
		
		String feeDetailsJson = ObjectMapperUtil.json(bidCostDTO.getFeeDetails());
		String emdDetsilsJson = ObjectMapperUtil.json(bidCostDTO.getEmdDetails());
		String otherBiddingCost = bidCostDTO.getOtherCost();
		
		bidCost.setFeeDetails(feeDetailsJson != null ? feeDetailsJson.getBytes(StandardCharsets.UTF_8) : null);
		bidCost.setEmdDetails(emdDetsilsJson != null ? emdDetsilsJson.getBytes(StandardCharsets.UTF_8) : null);
		bidCost.setOtherBiddingCost(otherBiddingCost != null ? otherBiddingCost.getBytes(StandardCharsets.UTF_8) : null);
		
		bidCost = bidCostRepository.save(bidCost);
		
		bidCostDTO.setId(bidCost.getId());
		
		//TODO: Mark EMD as pleased
		if(bidCostDTO.getEmdDetails() != null && "offline".equalsIgnoreCase(bidCostDTO.getEmdDetails().getEmdMode()))
			markEMDSubmitted(bidCostDTO.getEmdDetails(), EPledgedType.BID_SECURITY, bidCostDTO.getBidId());
			
		return bidCostDTO;
	}
	
	
	private void markEMDSubmitted(EMDDetails emdDetails, EPledgedType type, Long bidId) {
		if("offline".equalsIgnoreCase(emdDetails.getEmdMode())) {
			List<EMDWrapper> emdList = emdDetails.getEmdList();
			emdList.forEach(emd -> {
				CreditFacilityDTO cfDTO = emd.getEmd();
				Optional<CreditFacility> optional = creditFacilityRespository.findAllByClientIdAndAccountNumber(cfDTO.getClientId(), cfDTO.getAccountNumber());
				if(optional.isPresent()) {
					CreditFacility cf = optional.get();
					cf.setIsPledged(true);
					cf.setPledgedType(type);
					cf.setPledgedId(bidId);
					creditFacilityRespository.save(cf);
				}
			});
		}
	}
	
	
	public boolean clearEMD(Long bidId) {
		Optional<BidCost> optional = bidCostRepository.findByBidId(bidId);
		
		if(optional.isPresent()) {
			//First Release All EMD (Credit Facility
			BidCost bidCost = optional.get();
			String emdDetailsJson = new String(bidCost.getEmdDetails(), StandardCharsets.UTF_8);
			EMDDetails eMDDetails = ObjectMapperUtil.object(emdDetailsJson, EMDDetails.class);
			if(eMDDetails != null && Collections.isNotNullOrEmpty(eMDDetails.getEmdList())) {
				for(EMDWrapper emdw: eMDDetails.getEmdList()) {
					CreditFacilityDTO cfDTO = emdw.getEmd();
					Optional<CreditFacility> optionalCF = creditFacilityRespository.findAllByClientIdAndAccountNumber(cfDTO.getClientId(), cfDTO.getAccountNumber());
					if(optionalCF.isPresent()) {
						CreditFacility cf = optionalCF.get();
						cf.setIsPledged(false);
						cf.setPledgedType(null);
						cf.setPledgedId(null);
						creditFacilityRespository.save(cf);
					}
				}
			}
			
			//Then Delete BidCost
			bidCost.setEmdDetails(null);
			bidCostRepository.save(bidCost);
		}
		return Boolean.TRUE;
	}

	
	
	
	public BidCostDTO getBidCost(Long bidId) {
		BidCostDTO bidCostDTO = new BidCostDTO();
		Optional<BidCost> bidCostOptional = bidCostRepository.findByBidId(bidId);
		if(bidCostOptional.isPresent()) {
			BidCost bidCost  = bidCostOptional.get();
	
			bidCostDTO.setId(bidCost.getId());
			bidCostDTO.setClientId(bidCost.getClientId());
			bidCostDTO.setBidId(bidCost.getBidId());
			
			if(bidCost.getFeeDetails() != null){
				String feeDetailsJson = new String(bidCost.getFeeDetails(), StandardCharsets.UTF_8);
				FeeDetails feeDetails = ObjectMapperUtil.object(feeDetailsJson, FeeDetails.class);
				bidCostDTO.setFeeDetails(feeDetails);
			}
			
			if(bidCost.getEmdDetails() != null) {
				String emdDetailsJson = new String(bidCost.getEmdDetails(), StandardCharsets.UTF_8);
				EMDDetails eMDDetails = ObjectMapperUtil.object(emdDetailsJson, EMDDetails.class);
				bidCostDTO.setEmdDetails(eMDDetails);
			}
			
			if(bidCost.getOtherBiddingCost() != null) {
				String otherCost = new String(bidCost.getOtherBiddingCost(), StandardCharsets.UTF_8);
				bidCostDTO.setOtherCost(otherCost);
			}
		}
		return bidCostDTO;
	}
	
	
	
	public FeeDetails saveBidFee(@RequestBody FeeDetails  feeDetails, Long clientId, Long bidId) {

		BidCost bidCost = new BidCost();
		
		Optional<BidCost> optional = bidCostRepository.findByBidId(bidId);
		
		if(optional.isPresent()) {
			bidCost  = optional.get();
		} else {
			bidCost.setBidId(bidId);
			bidCost.setClientId(clientId);
		}
		
		if(feeDetails != null && "online".equalsIgnoreCase(feeDetails.getFeeMode()))
			feeDetails.setOfflineFeeDetails(null);
		
		if(feeDetails != null && "offline".equalsIgnoreCase(feeDetails.getFeeMode())) {
			feeDetails.setOnlineFeeDetails(null);
		}
			
		
		String feeDetailsJson = ObjectMapperUtil.json(feeDetails);
		
		bidCost.setFeeDetails(feeDetailsJson != null ? feeDetailsJson.getBytes(StandardCharsets.UTF_8) : null);
		
		bidCost = bidCostRepository.save(bidCost);
		
		return feeDetails;
	}
	
	public boolean clearFee(Long bidId) {
		Optional<BidCost> optional = bidCostRepository.findByBidId(bidId);
		
		if(optional.isPresent()) {
			//First Release All EMD (Credit Facility
			BidCost bidCost = optional.get();
			bidCost.setFeeDetails(null);
			bidCostRepository.save(bidCost);
		}
		return Boolean.TRUE;
	}
	
	public boolean saveOtherCost(OtherBidCost otherCost) {
		BidCost bidCost = new BidCost();
		
		Optional<BidCost> optional = bidCostRepository.findByBidId(otherCost.getBidId());
		
		if(optional.isPresent()) {
			bidCost  = optional.get();
		} else {
			bidCost.setBidId(otherCost.getBidId());
			bidCost.setClientId(otherCost.getClientId());
		}
		
		String otherCostJson = otherCost.getOtherCostJson();
		
		bidCost.setOtherBiddingCost(otherCostJson != null ? otherCostJson.getBytes(StandardCharsets.UTF_8) : null);
		bidCost = bidCostRepository.save(bidCost);
		return Boolean.TRUE;
	}
}
