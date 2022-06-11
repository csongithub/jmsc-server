/**
 * 
 */
package com.jmsc.app.service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
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
import com.jmsc.app.common.util.ObjectMapperUtil;
import com.jmsc.app.common.util.Strings;
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
		
		/**
		 * Check before submit if the bid has been assigned the emd and cost
		 */
		if(bid.getId() != null && EBidStatus.SUBMITTED.equals(bid.getStatus())) {
			Optional<BidCost> optional = bidCostRepository.findByBidId(bid.getId());
			
			if(optional.isPresent()) {
				//First Release All EMD (Credit Facility
				BidCost bidCost = optional.get();
				String emdDetailsJson = bidCost.getEmdDetails() != null ? new String(bidCost.getEmdDetails(), StandardCharsets.UTF_8) : null;
				if(Strings.isNullOrEmpty(emdDetailsJson)) {
					throw new RuntimeException("EMD not assigned");
				}
				
				String feeDetails = bidCost.getFeeDetails() != null ? new String(bidCost.getFeeDetails(), StandardCharsets.UTF_8) : null;
				if(Strings.isNullOrEmpty(feeDetails)) {
					throw new RuntimeException("Fee not assigned");
				}
			}
		}
		
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
	
	
	public List<BidDTO> getAllAcceptedBids(Long clientId) {
		List<BidDTO> bids = new ArrayList<BidDTO>();
		
		List<Bid> techAcceptedBids = repository.findAllByClientIdAndStatus(clientId, EBidStatus.TECHNICAL_ACCEPTED);
		List<Bid> finAcceptedBids = repository.findAllByClientIdAndStatus(clientId, EBidStatus.FINANCIAL_ACCEPTED);
		
		List<Bid> all = new ArrayList<Bid>();
		
		if(Collections.isNotNullOrEmpty(techAcceptedBids))
			all.addAll(techAcceptedBids);
		
		if(Collections.isNotNullOrEmpty(finAcceptedBids))
			all.addAll(finAcceptedBids);
		
		bids = ObjectMapperUtil.mapAll(all, BidDTO.class);
		
		return bids;
	}
	
	
	public List<BidDTO> getAllRejectedBids(Long clientId) {
		List<BidDTO> bids = new ArrayList<BidDTO>();
		
		List<Bid> techAcceptedBids = repository.findAllByClientIdAndStatus(clientId, EBidStatus.TECHNICAL_REJECTED);
		List<Bid> finAcceptedBids = repository.findAllByClientIdAndStatus(clientId, EBidStatus.FINANCIAL_REJECTED);
		
		List<Bid> all = new ArrayList<Bid>();
		
		if(Collections.isNotNullOrEmpty(techAcceptedBids))
			all.addAll(techAcceptedBids);
		
		if(Collections.isNotNullOrEmpty(finAcceptedBids))
			all.addAll(finAcceptedBids);
		
		bids = ObjectMapperUtil.mapAll(all, BidDTO.class);
		
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
		
		String emdDetsilsJson = ObjectMapperUtil.json(bidCostDTO.getEmdDetails());
		
		bidCost.setEmdDetails(emdDetsilsJson != null ? emdDetsilsJson.getBytes(StandardCharsets.UTF_8) : null);
		
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
					cf.setIsLien(true);
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
			
			releaseEMD(eMDDetails);
			
			//Then Delete BidCost
			bidCost.setEmdDetails(null);
			bidCostRepository.save(bidCost);
		}
		return Boolean.TRUE;
	}
	
	
	
	public boolean markEMDReturned(Long bidId) {
		Optional<BidCost> optional = bidCostRepository.findByBidId(bidId);
		
		if(optional.isPresent()) {
			//First Release All EMD (Credit Facility
			BidCost bidCost = optional.get();
			String emdDetailsJson = new String(bidCost.getEmdDetails(), StandardCharsets.UTF_8);
			EMDDetails eMDDetails = ObjectMapperUtil.object(emdDetailsJson, EMDDetails.class);
			
			this.releaseEMD(eMDDetails);
			
			eMDDetails.setStatus("RETURNED");
			String emdDetsilsJson = ObjectMapperUtil.json(eMDDetails);
			bidCost.setEmdDetails(emdDetsilsJson != null ? emdDetsilsJson.getBytes(StandardCharsets.UTF_8) : null);
			bidCostRepository.save(bidCost);
		}
		return Boolean.TRUE;
	}
	
	
	
	
	private void releaseEMD(EMDDetails eMDDetails) {
		if(eMDDetails != null && Collections.isNotNullOrEmpty(eMDDetails.getEmdList())) {
			for(EMDWrapper emdw: eMDDetails.getEmdList()) {
				CreditFacilityDTO cfDTO = emdw.getEmd();
				Optional<CreditFacility> optionalCF = creditFacilityRespository.findAllByClientIdAndAccountNumber(cfDTO.getClientId(), cfDTO.getAccountNumber());
				if(optionalCF.isPresent()) {
					CreditFacility cf = optionalCF.get();
					cf.setIsPledged(false);
					cf.setPledgedType(null);
					cf.setPledgedId(null);
					cf.setIsLien(false);
					creditFacilityRespository.save(cf);
				}
			}
		}
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
	
	
	public boolean discardBid(Long clientId, Long bidId) {
		Optional<Bid> optional = repository.findByClientIdAndId(clientId, bidId);
		if(optional.isPresent()) {
			Optional<BidCost> optionalCost = bidCostRepository.findByBidId(bidId);
			if(optionalCost.isPresent()) {
				BidCost bidCost = optionalCost.get();
				String emdDetailsJson = bidCost.getEmdDetails() != null ? new String(bidCost.getEmdDetails(), StandardCharsets.UTF_8) : null;
				
				if(Strings.isNotNullOrEmpty(emdDetailsJson)) {
					EMDDetails eMDDetails = ObjectMapperUtil.object(emdDetailsJson, EMDDetails.class);
					
					this.releaseEMD(eMDDetails);
				}
				bidCostRepository.delete(bidCost);
			}
			repository.delete(optional.get());
		}
		return Boolean.TRUE;
	}
	
	
	public BidDTO getBidDetails(Long clientId, Long bidId) {
		BidDTO bidDTO = null;
		Optional<Bid> optional = repository.findByClientIdAndId(clientId, bidId);
		if(optional.isPresent()) {
			bidDTO = ObjectMapperUtil.map(optional.get(), BidDTO.class);
		}
		return bidDTO; 
	}
}
