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

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmsc.app.common.dto.CreditFacilityDTO;
import com.jmsc.app.common.enums.EFacility;
import com.jmsc.app.common.enums.EFacilityIssuerType;
import com.jmsc.app.common.util.Collections;
import com.jmsc.app.common.util.ObjectMapperUtil;
import com.jmsc.app.config.jmsc.JmscProperties;
import com.jmsc.app.entity.BankAccount;
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
