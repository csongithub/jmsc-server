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

import com.amazonaws.services.rds.model.Option;
import com.fasterxml.jackson.core.type.TypeReference;
import com.jmsc.app.common.dto.AttributeDTO;
import com.jmsc.app.common.dto.WebAccountDTO;
import com.jmsc.app.common.enums.EAttributeType;
import com.jmsc.app.common.util.Collections;
import com.jmsc.app.common.util.ObjectMapperUtil;
import com.jmsc.app.entity.WebAccount;
import com.jmsc.app.repository.WebAccountRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chandan
 *
 */
@Service
@Slf4j
public class WebAccountService {

	@Autowired
	private WebAccountRepository repository;
	
	
	
	public WebAccountDTO createOrUpdate(WebAccountDTO accountDTO){
		
		if(accountDTO.getClientId() == null) {
			throw new RuntimeException("Insufficient Data");
		}
		
		if(accountDTO.getId() == null) {
			Optional<WebAccount> optional = repository.findByClientIdAndName(accountDTO.getClientId(), accountDTO.getName().trim());
			if(optional.isPresent()) {
				throw new RuntimeException("Account exists with same name");
			}
		}
		
		WebAccount account = ObjectMapperUtil.map(accountDTO, WebAccount.class);
		
		//Encrypt attributes
		if(Collections.isNotNullOrEmpty(accountDTO.getAttributes())) {
			accountDTO.getAttributes().forEach(attr -> {
				if(EAttributeType.Encrypt.equals(attr.getType())) {
					attr.setValue(Base64Service.encode(attr.getValue()));
				}
			});
			
			String attributeJson =  ObjectMapperUtil.json(accountDTO.getAttributes());
			account.setAttributesByte(attributeJson.getBytes(StandardCharsets.UTF_8));
		}
		
		WebAccount entity = repository.save(account);
		WebAccountDTO dto = ObjectMapperUtil.map(entity, WebAccountDTO.class);
		return dto;
	}
	
	
	
	public List<WebAccountDTO> getAll(Long clientId){
		
		List<WebAccountDTO> accounts = new ArrayList<WebAccountDTO>();
		
		if(clientId == null) {
			throw new RuntimeException("Insufficient Data");
		}
		
		List<WebAccount> all = repository.findAllByClientId(clientId);
		
		if(Collections.isNullOrEmpty(all))
			return new ArrayList<WebAccountDTO>();
		
		all.forEach(a -> {
			WebAccountDTO account = new WebAccountDTO();
			account.setClientId(a.getClientId());
			account.setId(a.getId());
			account.setName(a.getName());
			account.setUrl(a.getUrl());
			
			if(a.getAttributesByte() != null) {
				//Get json from byte
				String attributeJson = new String(a.getAttributesByte(), StandardCharsets.UTF_8);
				List<AttributeDTO> attributes = ObjectMapperUtil.listOfObjects(attributeJson, new TypeReference<List<AttributeDTO>>() {});
				if(Collections.isNotNullOrEmpty(attributes)) {
					attributes.forEach(attr -> {
						if(EAttributeType.Encrypt.equals(attr.getType())) {
							attr.setValue("**********");
						}
					});
					account.setAttributes(attributes);
				}
			}
			accounts.add(account);
		});
		
		return accounts;
	}
	
	
	public WebAccountDTO showAllAttributes(Long clientId, Long accountId) {
		WebAccountDTO account = new WebAccountDTO();;
		
		if(clientId == null || accountId == null) {
			throw new RuntimeException("Insufficient Data");
		}
		
		
		Optional<WebAccount> optional = repository.findByClientIdAndId(clientId, accountId);
		if(optional.isPresent()) {
			WebAccount accountEntity = optional.get();
			
			account.setClientId(accountEntity.getClientId());
			account.setId(accountEntity.getId());
			account.setName(accountEntity.getName());
			account.setUrl(accountEntity.getUrl());
			
			if(accountEntity.getAttributesByte() != null) {
				//Get json from byte
				String attributeJson = new String(accountEntity.getAttributesByte(), StandardCharsets.UTF_8);
				List<AttributeDTO> attributes = ObjectMapperUtil.listOfObjects(attributeJson, new TypeReference<List<AttributeDTO>>() {});
				if(Collections.isNotNullOrEmpty(attributes)) {
					attributes.forEach(attr -> {
						if(EAttributeType.Encrypt.equals(attr.getType())) {
							attr.setValue(Base64Service.decode(attr.getValue()));
						}
					});
					account.setAttributes(attributes);
				}
			}
		}
		
		return account;
	}
}
