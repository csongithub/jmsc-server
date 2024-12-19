/**
 * 
 */
package com.jmsc.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmsc.app.common.dto.EInvoiceDTO;
import com.jmsc.app.common.enums.EFyMonths;
import com.jmsc.app.common.exception.ResourceNotFoundException;
import com.jmsc.app.common.util.Collections;
import com.jmsc.app.common.util.ObjectMapperUtil;
import com.jmsc.app.common.util.Strings;
import com.jmsc.app.entity.BankGuarantee;
import com.jmsc.app.entity.EInvoice;
import com.jmsc.app.repository.EInvoiceRepository;

/**
 * @author anuhr
 *
 */
@Service
public class EInvoiceService extends AbstractService{
	
	
	@Autowired
	private EInvoiceRepository repository;
	
	
	public EInvoiceDTO saveOrUpdate(EInvoiceDTO dto) {
		if(dto.getClientId() == null || Strings.isNullOrEmpty(dto.getGstState())
				|| Strings.isNullOrEmpty(dto.getFy()) || dto.getMonth() == null) {
			throw new RuntimeException("Insufficient Data");
		}
		
		EInvoice entity= ObjectMapperUtil.map(dto, EInvoice.class);
		
		entity = repository.save(entity);
		dto.setId(entity.getId());
		
		return dto;
	}
	
	
	public List<EInvoiceDTO> getEInvloices(Long clientId, String gstState, String fy, EFyMonths month) {
		
		List<EInvoiceDTO> results = new ArrayList<EInvoiceDTO>();
		
		if(clientId == null || Strings.isNullOrEmpty(gstState) || Strings.isNullOrEmpty(fy) || month == null) {
			throw new RuntimeException("Insufficient Data");
		}
		
		List<EInvoice> list =  repository.findByClientIdAndGstStateAndFyAndMonth(clientId, gstState, fy, month);
		
		if(Collections.isNotNullOrEmpty(list))
			results = ObjectMapperUtil.mapAll(list, EInvoiceDTO.class);
		
		return results;
	}
	
	
	
	public Integer deleteEInvoice(Long clientId, Long id) {
		if(clientId == null) {
			throw new RuntimeException("Insufficient Data");
		}
		
		Optional<EInvoice> optional = repository.findByClientIdAndId(clientId, id);
		if(!optional.isPresent())
			throw new ResourceNotFoundException("Bank guarantee does not exist");
		
		repository.delete(optional.get());
		return 0;
	}
}
