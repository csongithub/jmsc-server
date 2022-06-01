/**
 * 
 */
package com.jmsc.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmsc.app.common.dto.BgGroupDTO;
import com.jmsc.app.common.dto.CreditFacilityDTO;
import com.jmsc.app.common.util.Collections;
import com.jmsc.app.common.util.ObjectMapperUtil;
import com.jmsc.app.common.util.Strings;
import com.jmsc.app.common.wrapper.BgGroupWrapper;
import com.jmsc.app.common.wrapper.CreditFacilityWrapper;
import com.jmsc.app.common.wrapper.LinkBankGuaranteeRequest;
import com.jmsc.app.common.wrapper.LinkFixDepositRequest;
import com.jmsc.app.config.jmsc.ServiceLocator;
import com.jmsc.app.entity.BgGroup;
import com.jmsc.app.entity.CreditFacility;
import com.jmsc.app.repository.BgGroupRepository;
import com.jmsc.app.repository.CreditFacilityRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Chandan
 *
 */
@Service
@Slf4j
public class BgGroupService {
	
	@Autowired
	private BgGroupRepository repository;
	
	public BgGroupDTO createOrUpdateBgGroup(BgGroupDTO dto) throws Throwable {
		if(dto.getClientId() == null)
			throw new Exception("Client ID can not be empty");
		
		if(Strings.isNullOrEmpty(dto.getGroupName()))
			throw new Exception("Group Info can not be empty");
		
		if(Strings.isNullOrEmpty(dto.getGroupInfo()))
			throw new Exception("Name can not be empty");
		
		if(dto.getId() == null) {
			Optional<BgGroup> optional = repository.findByClientIdAndGroupName(dto.getClientId(), dto.getGroupName().trim());
			if(optional.isPresent()) {
				log.debug("BG Group Name Already Exist: {}", dto);
				throw new Exception("BG Group Name Already Exist");
			}
		}
		
		BgGroup newGroup = new BgGroup();
		newGroup.setId(dto.getId());
		newGroup.setGroupName(dto.getGroupName().trim());
		newGroup.setGroupInfo(dto.getGroupInfo());
		newGroup.setClientId(dto.getClientId());
		newGroup.setGroupLimit(dto.getGroupLimit());	
		
		newGroup = repository.save(newGroup);
		
		if(Collections.isNotNullOrEmpty(dto.getLinkedBgList())) {
			for(CreditFacilityDTO cfDTO: dto.getLinkedBgList()) {
				Optional<CreditFacility> optional = ServiceLocator.getService(CreditFacilityRepository.class).findAllByClientIdAndAccountNumber(cfDTO.getClientId(), cfDTO.getAccountNumber());
				if(optional.isPresent()) {
					CreditFacility cf = optional.get();
					cf.setBgGroupId(newGroup.getId());
					ServiceLocator.getService(CreditFacilityRepository.class).save(cf);
				}
			}
		}
		
		if(Collections.isNotNullOrEmpty(dto.getLinkedFdList())) {
			for(CreditFacilityDTO cfDTO: dto.getLinkedFdList()) {
				Optional<CreditFacility> optional = ServiceLocator.getService(CreditFacilityRepository.class).findAllByClientIdAndAccountNumber(cfDTO.getClientId(), cfDTO.getAccountNumber());
				if(optional.isPresent()) {
					CreditFacility cf = optional.get();
					cf.setBgGroupId(newGroup.getId());
					ServiceLocator.getService(CreditFacilityRepository.class).save(cf);
				}
			}
		}
		dto.setId(newGroup.getId());
		return dto;
	}
	
	
	
	public List<BgGroupDTO> getAllGroups(Long clientId){
		List<BgGroup> groups = repository.findAllByClientId(clientId);
		List<BgGroupDTO> dtos = ObjectMapperUtil.mapAll(groups, BgGroupDTO.class);
		return dtos;
	}
	
	
	
	public BgGroupDTO linkFixDeposit(LinkFixDepositRequest request) throws Throwable {
		if(!Collections.isNullOrEmpty(request.getFixDeposits())) {
			log.debug("Insufficient Data");
			throw new Exception("No Fix Deposit(s) Found");
		}
		
		if(request.getBgGroupDTO() == null || request.getBgGroupDTO().getId() == null || request.getBgGroupDTO().getClientId() == null) {
			log.debug("Insufficient Data");
			throw new Exception("Invalid BG Group");
		}
		
		for(CreditFacilityDTO fixDeposit: request.getFixDeposits()) {
			Optional<CreditFacility> optional = ServiceLocator.getService(CreditFacilityRepository.class).findAllByClientIdAndAccountNumber(request.getBgGroupDTO().getClientId(), fixDeposit.getAccountNumber());
			if(optional.isPresent()) {
				CreditFacility cf = optional.get();
				cf.setBgGroupId(request.getBgGroupDTO().getId());
				ServiceLocator.getService(CreditFacilityRepository.class).save(cf);
			}
		}
		return request.getBgGroupDTO();
	}
	
	
	
	
	public BgGroupDTO linkBankGuarantee(LinkBankGuaranteeRequest request) throws Throwable {
		if(!Collections.isNullOrEmpty(request.getBankGuarantees())) {
			log.debug("Insufficient Data");
			throw new Exception("No Bank Guarantee(s) Found");
		}
		
		if(request.getBgGroupDTO() == null || request.getBgGroupDTO().getId() == null || request.getBgGroupDTO().getClientId() == null) {
			log.debug("Insufficient Data");
			throw new Exception("Invalid BG Group");
		}
		
		for(CreditFacilityDTO fixDeposit: request.getBankGuarantees()) {
			Optional<CreditFacility> optional = ServiceLocator.getService(CreditFacilityRepository.class).findAllByClientIdAndAccountNumber(request.getBgGroupDTO().getClientId(), fixDeposit.getAccountNumber());
			if(optional.isPresent()) {
				CreditFacility cf = optional.get();
				cf.setBgGroupId(request.getBgGroupDTO().getId());
				ServiceLocator.getService(CreditFacilityRepository.class).save(cf);
			}
		}
		return request.getBgGroupDTO();
	}
	

	
	
	public BgGroupWrapper getGroupDetails(Long clientId, Long groupId) {
		BgGroupWrapper response = new BgGroupWrapper();
		
		Optional<BgGroup> optional = repository.findByClientIdAndId(clientId, groupId);
		if(optional.isPresent()) {
			BgGroup group = optional.get();
			
			CreditFacilityService cfService = ServiceLocator.getService(CreditFacilityService.class);
			CreditFacilityWrapper facilityWrapper = cfService.getLinkedFacilitiesForBankGuaranteeGroup(clientId, groupId);
			
			BgGroupDTO groupDTO = ObjectMapperUtil.map(group, BgGroupDTO.class);
			
			response.setBgGroup(groupDTO);
			response.setFacilityWrapper(facilityWrapper);
		}
		return response;
	}
}
