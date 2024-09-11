/**
 * 
 */
package com.jmsc.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.jmsc.app.common.dto.BankGuaranteeDTO;
import com.jmsc.app.common.exception.ResourceNotFoundException;
import com.jmsc.app.common.rqrs.File;
import com.jmsc.app.common.util.Collections;
import com.jmsc.app.common.util.ObjectMapperUtil;
import com.jmsc.app.common.util.Strings;
import com.jmsc.app.entity.BankGuarantee;
import com.jmsc.app.repository.BankGuaranteeRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author anuhr
 *
 */
@Slf4j
@Service
public class BankGuaranteeService {
	
	@Autowired
	private BankGuaranteeRepository repository;
	
	
	
	public BankGuaranteeDTO createOrUpdate(BankGuaranteeDTO dto) {
		boolean isCreate = dto.getId() == null ? true : false;
		
		if(dto.getClientId() == null) {
			throw new RuntimeException("Insufficient Data");
		}
		
		if(Strings.isNullOrEmpty(dto.getBgNumber())) {
			throw new RuntimeException("BG Account number not found");
		}
		
		if(Strings.isNullOrEmpty(dto.getInFavourOf())) {
			throw new RuntimeException("Beneficiary  number not found");
		}
		
		if(isCreate) {
			Optional<BankGuarantee> optional = repository.findByClientIdAndBgNumber(dto.getClientId(), dto.getBgNumber());
			if(optional.isPresent()) {
				throw new RuntimeException("BG Account already exist with");
			}
		}
		
		BankGuarantee entity= ObjectMapperUtil.map(dto, BankGuarantee.class);
		
		entity = repository.save(entity);
		dto.setId(entity.getId());
		
		return dto;
	}
	
	
	
	public List<BankGuaranteeDTO> getAllBankGuarantee(Long clientId){
		if(clientId == null) {
			throw new RuntimeException("Insufficient Data");
		}
		List<BankGuarantee> list = repository.findByClientId(clientId);
		
		if(Collections.isNullOrEmpty(list))
			return new ArrayList<BankGuaranteeDTO>();
		
		List<BankGuaranteeDTO> reponse = ObjectMapperUtil.mapAll(list, BankGuaranteeDTO.class);
		return reponse;
		
	}
	
	
	
	
	public BankGuaranteeDTO getBankGuarantee(Long clientId, Long id) {
		if(clientId == null) {
			throw new RuntimeException("Insufficient Data");
		}
		
		Optional<BankGuarantee> optional = repository.findByClientIdAndId(clientId, id);
		if(!optional.isPresent())
			throw new ResourceNotFoundException("Bank guarantee does not exist");
		
		BankGuaranteeDTO bg = ObjectMapperUtil.map(optional.get(), BankGuaranteeDTO.class);
		return bg;
	}
	
	
	public BankGuaranteeDTO upload(MultipartFile upcomingFile, Long clientId, Long fileId) throws Exception {
		
		BankGuaranteeDTO bg = null;
		
	    if (upcomingFile.isEmpty())
            throw new IllegalStateException("Cannot upload empty file");
		
	    try {   
			Optional<BankGuarantee> optional = repository.findByClientIdAndId(clientId, fileId);
			if(optional.isPresent()) {

				BankGuarantee bankGuarantee = optional.get();
				bankGuarantee.setFile(upcomingFile.getBytes());
				bankGuarantee.setFileName(StringUtils.cleanPath(upcomingFile.getOriginalFilename()));
				bankGuarantee.setContentType(upcomingFile.getContentType());
				bankGuarantee.setFileAttached(true);
				
				repository.save(bankGuarantee);
				
				bg = ObjectMapperUtil.map(bankGuarantee, BankGuaranteeDTO.class);
				
			} else {
				throw new ResourceNotFoundException("Selected file does not exist in system.");
			}
			
	     } catch (java.io.IOException ex) {
	    	ex.printStackTrace();
	     }
	    return bg;
	 }
	
	
	
	public File download(Long clientId, Long fileId)throws Exception {
		Optional<BankGuarantee> optional = repository.findByClientIdAndId(clientId, fileId);
		if(optional.isPresent()) {

			BankGuarantee bankGuarantee = optional.get();
			
			File file= new File(bankGuarantee.getFile(),
								bankGuarantee.getFileName(),
								bankGuarantee.getContentType());
			
			return file;
		} else {
			throw new ResourceNotFoundException("Selected file does not exist in system.");
		}
	}
}
