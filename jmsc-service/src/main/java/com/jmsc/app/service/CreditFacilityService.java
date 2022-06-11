/**
 * 
 */
package com.jmsc.app.service;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmsc.app.common.dto.CreditFacilityDTO;
import com.jmsc.app.common.enums.EFacility;
import com.jmsc.app.common.enums.EFacilityIssuerType;
import com.jmsc.app.common.util.Collections;
import com.jmsc.app.common.util.ObjectMapperUtil;
import com.jmsc.app.common.wrapper.CreditFacilityWrapper;
import com.jmsc.app.config.jmsc.JmscProperties;
import com.jmsc.app.entity.Client;
import com.jmsc.app.entity.CreditFacility;
import com.jmsc.app.repository.ClientRepository;
import com.jmsc.app.repository.CreditFacilityRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Chandan
 *
 */
@Slf4j
@Service
public class CreditFacilityService {
	
	@Autowired
	private CreditFacilityRepository repository;
	
	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private JmscProperties properties;
	
	
	public CreditFacilityDTO addCreditFacility(CreditFacilityDTO dto) {
		
		if(dto.getIsPledged()) {
			if(dto.getPledgedId() == null) {
				throw new RuntimeException("The linkage id is not present");
			}else if(dto.getPledgedType() == null) {
				throw new RuntimeException("The linkage type is not present");
			}
		}
		
		if(EFacilityIssuerType.PO.equals(dto.getIssuerType())
				&& !EFacility.NSC.equals(dto.getFacilityType())) {
			throw new RuntimeException("Post Office can not issue: " + dto.getFacilityType().toString());
			
		} else if(EFacilityIssuerType.BANK.equals(dto.getIssuerType()) && EFacility.NSC.equals(dto.getFacilityType())){
			throw new RuntimeException("Bank can not issue: " + dto.getFacilityType().toString());
		}
		
		
		if(dto.getOpenDate().compareTo(dto.getMaturityDate()) >= 0){
			throw new RuntimeException("Invalid Open/Maturity Dates");
			
		}
		
		if(dto.getId() == null) {
			Optional<CreditFacility> optional = repository.findAllByClientIdAndAccountNumber(dto.getClientId(), dto.getAccountNumber());
			if(optional.isPresent()) {
				throw new RuntimeException("Account Number Already Exists");
			}
		}
		
		CreditFacility cf = ObjectMapperUtil.map(dto, CreditFacility.class);
		CreditFacility savedCF = repository.save(cf);
		CreditFacilityDTO savedCFDTO = ObjectMapperUtil.map(savedCF, CreditFacilityDTO.class);
		return savedCFDTO;
	}
	
	
	
	
	public List<CreditFacilityDTO> getAllCrditFacility(Long clientId){
		List<CreditFacility> cfList = repository.findAllByClientId(clientId);
		if(Collections.isNullOrEmpty(cfList)) {
			return new ArrayList<CreditFacilityDTO>();
		}else {
			List<CreditFacilityDTO> cfDTOList =  ObjectMapperUtil.mapAll(cfList, CreditFacilityDTO.class);
			return cfDTOList;
		}
	}
	
	
	
	
	public List<CreditFacilityDTO> getAllByFacilityType(Long clientId, EFacility facilityType){
		if(facilityType == null) {
			throw new RuntimeException("Insufficient Information");
		}
		List<CreditFacility> cfList = repository.findAllByClientIdAndFacilityType(clientId, facilityType);
		if(Collections.isNullOrEmpty(cfList)) {
			return new ArrayList<CreditFacilityDTO>();
		}else {
			List<CreditFacilityDTO> cfDTOList =  ObjectMapperUtil.mapAll(cfList, CreditFacilityDTO.class);
			return cfDTOList;
		}
	}
	
	
	/**
	 * Gives available collateral (Fix Deposit(s), NSC(s) and Bank Guarantees) that can be use as Bid Security in a Bid.
	 * There are three Special Conditions for these deposits.
	 * 1. FD/NSc should not be pledged in any bid as security or agreement
	 * 2. FD/NSC should not be already linked to any other BG Group
	 * 3. FD/NSC should not be already linked to any other Loan
	 * @param clientId
	 * @return
	 */
	public List<CreditFacilityDTO> getCollateralsForBid(Long clientId){
		
		List<CreditFacilityDTO> result = new ArrayList<CreditFacilityDTO>();
		
		List<CreditFacility> all = repository.findAllByClientId(clientId);
		
		if(Collections.isNullOrEmpty(all)) {
			return new ArrayList<CreditFacilityDTO>();
		}
		
		all.removeAll(java.util.Collections.singletonList(null));
		
		if(Collections.isNotNullOrEmpty(all)) {
			//Filter and remove all pledged Fix Deposits from list
			all = all.stream().map(cf -> !cf.getIsPledged() ? cf : null).collect(Collectors.toList());
			all.removeAll(java.util.Collections.singletonList(null));
			
			//filter all deposit that are linked to any loan
			all = all.stream().map(cf -> cf.getLoanId() == null ? cf : null).collect(Collectors.toList());
			all.removeAll(java.util.Collections.singletonList(null));
			
			//Now from the remaining list filter and remove all those Fix Deposits which are already linked to some other or same BG Group
			all = all.stream().map(cf -> cf.getBgGroupId() == null
									? cf : cf.getFacilityType().equals(EFacility.BG) ? cf : null).collect(Collectors.toList());
			all.removeAll(java.util.Collections.singletonList(null));
			
			List<CreditFacilityDTO> depositList = ObjectMapperUtil.mapAll(all, CreditFacilityDTO.class);
			
			if(Collections.isNotNullOrEmpty(depositList))
				result.addAll(depositList);
		}
		return result;
	}
	
	
	/**
	 * Gives available collateral (Fix Deposit(s) & NSCs) that can be used or hold against in a Bank Guarantee Group and Loans.
	 * There are three Special Conditions for these deposits.
	 * 1. FD/NSc should not be pledged in any bid as security or agreement
	 * 2. FD/NSC should not be already linked to any other BG Group
	 * 3. FD/NSC should not be already linked to any other Loan
	 * @param clientId
	 * @return
	 */
	public List<CreditFacilityDTO> getFreeCollateral(Long clientId){
		List<CreditFacility> all = repository.findAllByClientId(clientId);
		
		//Filter for FD, NSC
		all = all.stream().map(cf -> (cf.getFacilityType().equals(EFacility.FD) 
												|| cf.getFacilityType().equals(EFacility.NSC)) ? cf : null).collect(Collectors.toList());
		
		all.removeAll(java.util.Collections.singletonList(null));
		
		if(Collections.isNullOrEmpty(all)) {
			return new ArrayList<CreditFacilityDTO>();
		}else {
			//Filter and remove all pledged Fix Deposits from list
			all = all.stream().map(cf -> !cf.getIsPledged() ? cf : null).collect(Collectors.toList());
			all.removeAll(java.util.Collections.singletonList(null));
			
			//filter all deposit that are linked to any loan
			all = all.stream().map(cf -> cf.getLoanId() == null ? cf : null).collect(Collectors.toList());
			all.removeAll(java.util.Collections.singletonList(null));
			
			//Now from the remaining list filter and remove all those Fix Deposits which are already linked to some other or same BG Group
			all = all.stream().map(cf -> cf.getBgGroupId() == null ? cf : null).collect(Collectors.toList());
			all.removeAll(java.util.Collections.singletonList(null));
			
			List<CreditFacilityDTO> depositList = ObjectMapperUtil.mapAll(all, CreditFacilityDTO.class);
			
			return depositList;
		}
	}
	
	
	/**
	 * Gives available Bank Guarantee(s) list that is issued from a Bank Guarantee Group.
	 * There is only one Special Conditions for these guarantees that the BG should not be 
	 * already linked to any BG Group.
	 * @param clientId
	 * @return
	 */
	public List<CreditFacilityDTO> getApplicableBankGuaranteeBgGroup(Long clientId){
		List<CreditFacility> all = repository.findAllByClientIdAndFacilityType(clientId, EFacility.BG);
		if(Collections.isNullOrEmpty(all)) {
			return new ArrayList<CreditFacilityDTO>();
		}else {
			
			//Filter and remove all those Bank Guarantee which are already linked to some other or same BG Group
			all = all.stream().map(cf -> cf.getBgGroupId() == null ? cf : null).collect(Collectors.toList());
			all.removeAll(java.util.Collections.singletonList(null));
			
			List<CreditFacilityDTO> depositList = ObjectMapperUtil.mapAll(all, CreditFacilityDTO.class);
			
			return depositList;
		}
	}
	
	
	/**
	 * 
	 * This service gives the collateral along with the issued bank guarantees for a bg group
	 * 
	 * All deposits, NSC(s) which are hold against/in a BG Group
	 * All guarantees that are issued in this BG Group
	 * 
	 * @param clientId
	 * @param groupId
	 * @return
	 */
	public CreditFacilityWrapper getCollateralAndBGForBgGroup(Long clientId, Long groupId) {
		
		List<CreditFacility> allEntity = repository.findAllByClientIdAndBgGroupId(clientId, groupId);
		
		List<CreditFacilityDTO> all = ObjectMapperUtil.mapAll(allEntity, CreditFacilityDTO.class);
		
		List<CreditFacilityDTO> deposits = all.stream().map(cf -> (cf.getFacilityType().equals(EFacility.FD)
																					|| cf.getFacilityType().equals(EFacility.NSC) )? cf : null).collect(Collectors.toList());
		deposits.removeAll(java.util.Collections.singletonList(null));
		
		List<CreditFacilityDTO> guarantees = all.stream().map(cf -> cf.getFacilityType().equals(EFacility.BG) ? cf : null).collect(Collectors.toList());
		guarantees.removeAll(java.util.Collections.singletonList(null));
		
		CreditFacilityWrapper wrapper = new CreditFacilityWrapper();
		wrapper.setDepositList(Collections.isNotNullOrEmpty(deposits) ? deposits : new ArrayList<CreditFacilityDTO>());
		wrapper.setGuaranteeList(Collections.isNotNullOrEmpty(guarantees) ? guarantees : new ArrayList<CreditFacilityDTO>());
		
		return wrapper;
	}
	
	
	

	/**
	 * 
	 * This service gives all collateral that are hold against a loan
	 * 
	 * @param clientId
	 * @param loanId
	 * @return
	 */
	public List<CreditFacilityDTO> loanCollateral(Long clientId, Long loanId){
		
		List<CreditFacility> allEntity = repository.findAllByClientIdAndLoanId(clientId, loanId);
		
		List<CreditFacilityDTO> all = ObjectMapperUtil.mapAll(allEntity, CreditFacilityDTO.class);
		
		return all;
	}
	
	
	
	
	public Map<Long, List<CreditFacilityDTO>> evaluateExpiry(){
		log.debug("Evaluating CF Expiry");
		Map<Long, List<CreditFacilityDTO>> clientCfMap = new HashedMap<Long, List<CreditFacilityDTO>>();
		List<Client> clients = clientRepository.findAll();
		
		if(!Collections.isNullOrEmpty(clients)) {
			
			Long cfAlertDays = properties.getCreditFacilityExpiryAlertDays();
			
			for(Client client: clients) {
				List<CreditFacilityDTO> cfList = new ArrayList<CreditFacilityDTO>();
				
				Long clientId = client.getId();
		
				List<CreditFacility> cfs = repository.findAllByClientId(clientId);
				
				if(!Collections.isNullOrEmpty(cfs)) {
					
					for(CreditFacility cf: cfs) {
						Date maturityDate = cf.getMaturityDate();
						
						ZoneId defaultZoneId = ZoneId.systemDefault();
						Date todaysDate = Date.from(java.time.LocalDate.now().atStartOfDay(defaultZoneId).toInstant());
						
					    long diffInMillies = maturityDate.getTime() - todaysDate.getTime();
					    long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
					    
					    if(diff <= cfAlertDays) {
					    	CreditFacilityDTO cfDTO = ObjectMapperUtil.map(cf, CreditFacilityDTO.class);
					    	if(diff < 0) {
					    		cfDTO.setHasExpired(true);
					    		cfDTO.setAlertMessage("This facility has expired " + Math.abs(diff) + " days ago, effectively on " + cfDTO.getMaturityDate() + "." 
					    		+ "<br>Kindly contact with the issuer and get it renewed.");
					    	}else {
					    		cfDTO.setExpiringInDays(diff);
					    		if(diff == 0) {
					    			cfDTO.setAlertMessage("This facility has expired today.<br>Kindly contact with the issuer and get it renewed.");
					    		}else {
					    			cfDTO.setAlertMessage("This facility would be expiring in next " + Math.abs(diff) + " days, effectively on " + cfDTO.getMaturityDate() + ".");
					    		}
					    	}
					    	cfList.add(cfDTO);
					    }
					}
				}
				if(!Collections.isNullOrEmpty(cfList))
					clientCfMap.put(clientId, cfList);
			}
		}
		return clientCfMap;
	}
}
