/**
 * 
 */
package com.jmsc.app.service;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmsc.app.common.dto.PaymentDraftDTO;
import com.jmsc.app.common.rqrs.GetPaymentsByDateRequest;
import com.jmsc.app.common.rqrs.Range;
import com.jmsc.app.entity.PaymentDraft;
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
		
		if(dto.getClientId() == null) {
			throw new RuntimeException("Insufficient Data");
		}
		
		PaymentDraft draft = new PaymentDraft();
		draft.setId(dto.getId());
		draft.setClientId(dto.getClientId());
		draft.setStatus(dto.getStatus());
		draft.setDraft(dto.getDraftJson().getBytes(StandardCharsets.UTF_8));
		return repository.save(draft).getStatus();
	}
	
	
	public List<PaymentDraftDTO> getAllDrafts(Long clientId){
		List<PaymentDraft> list = repository.findAllByClientId(clientId);
		List<PaymentDraftDTO> dtoList = new ArrayList<PaymentDraftDTO>();
		for(PaymentDraft draft: list) {
			if("CREATED".equalsIgnoreCase(draft.getStatus()))
			{
				PaymentDraftDTO dto = new PaymentDraftDTO();
				dto.setId(draft.getId());
				dto.setClientId(draft.getClientId());
				dto.setStatus(draft.getStatus());
				dto.setDraftJson(new String(draft.getDraft(), StandardCharsets.UTF_8));
				
				dtoList.add(dto);
			}
		}
		return dtoList;
	}
	
	
	
	public int markPrinted(List<Long> payments) {
		for(Long id: payments) {
			Optional<PaymentDraft> optional = repository.findById(id);
			if(optional.isPresent()) {
				PaymentDraft draft = optional.get();
				draft.setStatus("PRINTED");
				repository.save(draft);
			}
		}
		return 0;
	}
	
	
	
	public List<PaymentDraftDTO> getAllPrinted(Long clientId){
		List<PaymentDraft> list = repository.findAllByClientId(clientId);
		List<PaymentDraftDTO> dtoList = new ArrayList<PaymentDraftDTO>();
		for(PaymentDraft draft: list) {
			if("PRINTED".equalsIgnoreCase(draft.getStatus()))
			{
				PaymentDraftDTO dto = new PaymentDraftDTO();
				dto.setId(draft.getId());
				dto.setClientId(draft.getClientId());
				dto.setStatus(draft.getStatus());
				dto.setDraftJson(new String(draft.getDraft(), StandardCharsets.UTF_8));
				
				dtoList.add(dto);
			}
		}
		return dtoList;
	}	
	
	
	
	
	public List<PaymentDraftDTO> getAllPrintedByRange(Long clientId, GetPaymentsByDateRequest req) {
		Range range = req.getRange();
		Date fromDate = null;
		Date toDate = null;
		try {
			fromDate =new SimpleDateFormat("yyyy/MM/dd").parse(range.getFrom());
			Calendar cal = Calendar.getInstance();  
			cal.setTime(fromDate);
			cal.set(Calendar.HOUR_OF_DAY, 0);  
		    cal.set(Calendar.MINUTE, 0);  
		    cal.set(Calendar.SECOND, 0);  
		    cal.set(Calendar.MILLISECOND, 0);
		    fromDate = cal.getTime();
		    
			toDate = new SimpleDateFormat("yyyy/MM/dd").parse(range.getTo());
			cal = Calendar.getInstance();
			cal.setTime(toDate);
			cal.set(Calendar.HOUR_OF_DAY, 23);  
		    cal.set(Calendar.MINUTE, 59);  
		    cal.set(Calendar.SECOND, 59);  
		    cal.set(Calendar.MILLISECOND, 59);
		    toDate = cal.getTime();
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<PaymentDraft> list = repository.findAllByClientIdAndUpdatedTimestampBetween(clientId, fromDate, toDate);
		List<PaymentDraftDTO> dtoList = new ArrayList<PaymentDraftDTO>();
		for(PaymentDraft draft: list) {
			if("PRINTED".equalsIgnoreCase(draft.getStatus()))
			{
				PaymentDraftDTO dto = new PaymentDraftDTO();
				dto.setId(draft.getId());
				dto.setClientId(draft.getClientId());
				dto.setStatus(draft.getStatus());
				dto.setDraftJson(new String(draft.getDraft(), StandardCharsets.UTF_8));
				dtoList.add(dto);
			}
		}
		return dtoList;
	}
	
	
	
	
	public int discardDraft(Long id) {
		Optional<PaymentDraft> optional = repository.findById(id);
		if(optional.isPresent())
			repository.deleteById(id);
		return 0;
	}
}
