/**
 * 
 */
package com.jmsc.app.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmsc.app.common.dto.PartyBankAccountDTO;
import com.jmsc.app.common.dto.PartyDTO;
import com.jmsc.app.common.dto.PaymentSummaryDTO;
import com.jmsc.app.common.dto.accounting.Item;
import com.jmsc.app.common.dto.accounting.ListDTO;
import com.jmsc.app.common.enums.EPartyStatus;
import com.jmsc.app.common.exception.ResourceNotFoundException;
import com.jmsc.app.common.util.Collections;
import com.jmsc.app.common.util.ObjectMapperUtil;
import com.jmsc.app.common.util.Strings;
import com.jmsc.app.config.jmsc.ServiceLocator;
import com.jmsc.app.entity.Party;
import com.jmsc.app.entity.PartyAccountsLinkage;
import com.jmsc.app.entity.PartyBankAccount;
import com.jmsc.app.repository.PartyAccountsLinkageRepository;
import com.jmsc.app.repository.PartyBankAccountRepository;
import com.jmsc.app.repository.PartyRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Chandan
 *
 */
@Service
@Slf4j
public class PartyService {
	
	
	@Autowired
	private PartyRepository repository;
	
	@Autowired
	private PartyAccountsLinkageRepository linkageRepository;
	
	
	public PartyDTO createParty(PartyDTO partyDTO) {
		log.debug("Inside createParty method");
		
		if(partyDTO.getClientId() == null) {
			throw new RuntimeException("Insufficient Data");
		}
		
		if(Strings.isNullOrEmpty(partyDTO.getName()) 
				|| Strings.isNullOrEmpty(partyDTO.getNickName())) {
			throw new RuntimeException("Incorrect party name or nick name");
		}
		
		partyDTO.toUppercase();
	
		if(partyDTO.getId() == null) {
			Optional<Party> optional = repository.findByNameOrNickName(partyDTO.getName(), partyDTO.getNickName());
			if(optional.isPresent()) {
				throw new RuntimeException("Party already exists with same Name or Nick Name");
			}
		}
		
		Party party = ObjectMapperUtil.map(partyDTO, Party.class);
		
		Party savedParty = repository.save(party);

		PartyDTO newParty = ObjectMapperUtil.map(savedParty, PartyDTO.class);
		
		return newParty;
	}
	
	
	public List<PartyDTO> getAllParties(Long clientId){
		List<Party> all = repository.findByClientId(clientId);
		
		if(Collections.isNullOrEmpty(all)) {
			return new ArrayList<PartyDTO>();
		}
		
		List<PartyDTO> parties = ObjectMapperUtil.mapAll(all, PartyDTO.class);
		
		
		java.util.Collections.sort(parties, new Comparator<PartyDTO>() {
	        public int compare(PartyDTO party1, PartyDTO party2) {
	            return party1.getNickName().compareTo(party2.getNickName());
	        }
	    });
		
		return parties;
	}
	
	
	public List<PartyDTO> getAllParties(Long clientId, EPartyStatus status){
		List<Party> all = repository.findByClientIdAndStatus(clientId, status);
		
		if(Collections.isNullOrEmpty(all)) {
			return new ArrayList<PartyDTO>();
		}
		
		List<PartyDTO> parties = ObjectMapperUtil.mapAll(all, PartyDTO.class);
		return parties;
	}
	
	
	
	public PartyDTO getPartyById(Long clientId, Long partyId){
		Optional<Party> optional = repository.findByClientIdAndId(clientId, partyId);
		
		if(!optional.isPresent()) {
			throw new RuntimeException("Party dose not exists");
		}
		
		PartyDTO party = ObjectMapperUtil.map(optional.get(), PartyDTO.class);
		return party;
	}
	
	
	public List<PartyBankAccountDTO> getAllLinkedAccounts(Long clientId, Long partyId) {
		List<PartyBankAccountDTO> list = new ArrayList<PartyBankAccountDTO>();
		
		List<PartyAccountsLinkage> linkages = linkageRepository.findByClientIdAndPartyId(clientId, partyId);
		
		if(Collections.isNullOrEmpty(linkages)) {
			return list;
		}
		
		PartyBankAccountRepository partyAccountrepository = ServiceLocator.getService(PartyBankAccountRepository.class);
	
		for(PartyAccountsLinkage linkage: linkages) {
			Optional<PartyBankAccount> optional = partyAccountrepository.findById(linkage.getId().getAccountId());
			if(optional.isPresent()) {
				PartyBankAccountDTO accountDTO = ObjectMapperUtil.map(optional.get(), PartyBankAccountDTO.class);
				list.add(accountDTO);
			}
		}
		
		return list;
	}
	
	public Integer deleteParty(Long clientId, Long partyId) {
		Optional<Party> optional = repository.findByClientIdAndId(clientId, partyId);
		
		if(optional.isPresent()) {
			Party party = optional.get();
			PaymentService2 service =  ServiceLocator.getService(PaymentService2.class);
			
			List<PaymentSummaryDTO> listOfDraft  = service.getPaymentsByPartyId(clientId, partyId);
			if(Collections.isNullOrEmpty(listOfDraft))
				repository.delete(party);
			else
				throw new RuntimeException("One or more payment(s) exists for this party");
			
			return 0;
		} else {
			throw new ResourceNotFoundException("Payment not found");
		}
	}
	
	
	public ListDTO getAllParty(Long clientId) {
	
		ListDTO list = new ListDTO();
		list.setListName("Party");
		
		if(clientId == null) {
			throw new RuntimeException("Invalid Request");
		}
		
		List<Party> parties  = repository.findByClientId(clientId);
		
		
		if(Collections.isEmpty(parties))
			return list;
		
		parties.forEach(c ->  {
			Item item  = new Item();
			item.setLabel(c.getName() + "(" + c.getNickName() + ")");
			item.setValue(c.getId());
			list.getList().add(item);
		});
		
		java.util.Collections.sort(list.getList(), new Comparator<Item>() {
	        public int compare(Item item1, Item item2) {
	            return item1.getLabel().compareTo(item2.getLabel());
	        }
	    });
		
		return list;
		
	}

}
