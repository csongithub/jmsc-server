/**
 * 
 */
package com.jmsc.app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmsc.app.common.dto.CreditFacilityDTO;
import com.jmsc.app.common.enums.EFacilityIssuerType;
import com.jmsc.app.common.enums.EFacilityType;
import com.jmsc.app.common.util.Collections;
import com.jmsc.app.common.util.ObjectMapperUtil;
import com.jmsc.app.entity.users.CreditFacility;
import com.jmsc.app.repository.CreditFacilityRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Chandan
 *
 */
@Service
@Slf4j
public class CreditFacilityService {
	
	@Autowired
	private CreditFacilityRepository repository;
	
	
	public CreditFacilityDTO addCreditFacility(CreditFacilityDTO dto) {
		
		if(dto.getIsPledged()) {
			if(dto.getPledgedId() == null) {
				throw new RuntimeException("The linkage id is not present");
			}else if(dto.getPledgedType() == null) {
				throw new RuntimeException("The linkage type is not present");
			}
		}
		
		if(EFacilityIssuerType.Post_Office.equals(dto.getIssuerType())
				&& !EFacilityType.NSC.equals(dto.getFacilityType())) {
			throw new RuntimeException("Post Office can not issue: " + dto.getFacilityType().toString());
			
		} else if(EFacilityIssuerType.Bank.equals(dto.getIssuerType()) && EFacilityType.NSC.equals(dto.getFacilityType())){
			throw new RuntimeException("Bank can not issue: " + dto.getFacilityType().toString());
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
}
