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

import com.jmsc.app.common.dto.PaymentDraftDTO;
import com.jmsc.app.entity.users.PaymentDraft;
import com.jmsc.app.repository.PaymentDraftRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Chandan
 *
 */
@Service
@Slf4j
public class PyamentService {
	
	@Autowired
	private PaymentDraftRepository repository;
	
	public String createPaymentDraft(PaymentDraftDTO dto) {
		PaymentDraft draft = new PaymentDraft();
		draft.setId(dto.getId());
		draft.setStatus(dto.getStatus());;
		draft.setDraft(dto.getDraftJson().getBytes(StandardCharsets.UTF_8));
		return repository.save(draft).getStatus();
	}
	
	
	public List<PaymentDraftDTO> getAllDrafts(){
		List<PaymentDraft> list = repository.findAll();
		List<PaymentDraftDTO> dtoList = new ArrayList<PaymentDraftDTO>();
		for(PaymentDraft draft: list) {
			PaymentDraftDTO dto = new PaymentDraftDTO();
			dto.setId(draft.getId());
			dto.setStatus(draft.getStatus());
			dto.setDraftJson(new String(draft.getDraft(), StandardCharsets.UTF_8));
			
			dtoList.add(dto);
		}
		return dtoList;
	}
	
	
	public int discardDraft(Long id) {
		Optional<PaymentDraft> optional = repository.findById(id);
		if(optional.isPresent()) {
			repository.deleteById(id);
			return 0;
		}else {
			return -1;
		}
	}
}
