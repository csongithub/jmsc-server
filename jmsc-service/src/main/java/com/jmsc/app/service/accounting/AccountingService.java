/**
 * 
 */
package com.jmsc.app.service.accounting;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmsc.app.common.dto.accounting.CreditorDTO;
import com.jmsc.app.common.dto.accounting.Item;
import com.jmsc.app.common.dto.accounting.ListDTO;
import com.jmsc.app.common.util.Collections;
import com.jmsc.app.common.util.ObjectMapperUtil;
import com.jmsc.app.entity.accounting.Creditor;
import com.jmsc.app.repository.accounting.CreditorRepository;
import com.jmsc.app.service.AbstractService;

/**
 * @author anuhr
 *
 */
@Service
public class AccountingService extends AbstractService{
	
	
	@Autowired
	private CreditorRepository creditorRepository;
	
	
	public CreditorDTO createOrUpdate(CreditorDTO dto) {
		if(dto.getClientId() == null || isNull(dto.getName()) || isNull(dto.getAddress())
				|| isNull(dto.getPartyId())) {
			throw new RuntimeException("Insufficient Data");
		}
		
		dto.toUpperCase();
		
		Creditor entity= ObjectMapperUtil.map(dto, Creditor.class);
		
		entity = creditorRepository.save(entity);
		
		CreditorDTO savedCreditor = ObjectMapperUtil.map(entity, CreditorDTO.class);
		
		return savedCreditor;
	}
	
	
	public CreditorDTO getById(Long clientId, Long id) {
		if(isNull(clientId) || isNull(id)) {
			throw new RuntimeException("Invalid Request");
		}
		
		Optional<Creditor> optional =  creditorRepository.findByClientIdAndId(clientId, id);
		if(!optional.isPresent()) {
			throw new RuntimeException("Creditor Not Found");
		}
		
		CreditorDTO creditor = ObjectMapperUtil.map(optional.get(), CreditorDTO.class);
		return creditor;
	}
	
	
	public ListDTO getAllCreditors(Long clientId) {
		
		ListDTO list = new ListDTO();
		list.setListName("Creditors");
		
		if(isNull(clientId)) {
			throw new RuntimeException("Invalid Request");
		}
		
		List<Creditor> creditors  = creditorRepository.findByClientId(clientId);
		
		if(Collections.isEmpty(creditors))
			return list;
		
		creditors.forEach(c ->  {
			Item item  = new Item();
			item.setLabel(c.getName());
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
