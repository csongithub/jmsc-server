/**
 * 
 */
package com.jmsc.app.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmsc.app.common.dto.PartyBankAccountDTO;
import com.jmsc.app.common.dto.PaymentDTO;
import com.jmsc.app.common.dto.PaymentSummaryDTO;
import com.jmsc.app.common.enums.EPaymentStatus;
import com.jmsc.app.common.exception.ResourceNotFoundException;
import com.jmsc.app.common.rqrs.GetPaymentsByDateRequest;
import com.jmsc.app.common.rqrs.Range;
import com.jmsc.app.common.util.Collections;
import com.jmsc.app.common.util.ObjectMapperUtil;
import com.jmsc.app.config.jmsc.ServiceLocator;
import com.jmsc.app.entity.PartyAccountsLinkage;
import com.jmsc.app.entity.PartyBankAccount;
import com.jmsc.app.entity.Payment;
import com.jmsc.app.repository.PartyAccountsLinkageRepository;
import com.jmsc.app.repository.PartyBankAccountRepository;
import com.jmsc.app.repository.PaymentRepository;

/**
 * @author Chandan
 *
 */
@Service
public class PaymentService2 {

	@Autowired
	private PartyAccountsLinkageRepository linkageRepository;
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	
	public PaymentDTO savePayment(PaymentDTO dto) {
		Payment entity = ObjectMapperUtil.map(dto, Payment.class);
		Payment savedPayment = paymentRepository.save(entity);
		PaymentDTO savedPaymentDTO = ObjectMapperUtil.map(savedPayment, PaymentDTO.class);
		return savedPaymentDTO;
	}
	
	
	
	public PaymentDTO getPayment(Long clientId, Long paymentId) {
		Optional<Payment> optional = paymentRepository.findAllByClientIdAndId(clientId, paymentId);
		PaymentDTO savedPaymentDTO = ObjectMapperUtil.map(optional.get(), PaymentDTO.class);
		return savedPaymentDTO;
	}
	
	
	public List<PaymentSummaryDTO> getAllDraft(Long clientId, EPaymentStatus status) throws Throwable{
		
		List<PaymentSummaryDTO> listOfDraft = new ArrayList<PaymentSummaryDTO>();
		
		List<Payment> drafts = paymentRepository.findAllByClientIdAndStatus(clientId, status);
		
		for(Payment draft: drafts) {
			PaymentSummaryDTO summary = ObjectMapperUtil.object(draft.getPaymentSummary(), PaymentSummaryDTO.class);
			
			summary.setPaymentId(draft.getId());
			summary.setPaymentDate(draft.getPaymentDate());
			listOfDraft.add(summary);
		}
		
		return listOfDraft;
	}
	
	
	
	
	public Integer deletePayment(Long clientId, Long paymentId, EPaymentStatus status) {
		Optional<Payment> optional  = paymentRepository.findAllByClientIdAndId(clientId, paymentId);
		
		if(optional.isPresent()) {
			Payment payment = optional.get();
			if(status.equals(payment.getStatus())) {
				paymentRepository.delete(payment);
				return 0;
			} else {
				throw new ResourceNotFoundException("Payment not found");
			}
		} else {
			throw new ResourceNotFoundException("Payment not found");
		}
	}
	
	
	
	
	public Integer approvePayment(Long clientId, Long paymentId) {
		Optional<Payment> optional  = paymentRepository.findAllByClientIdAndId(clientId, paymentId);
		
		if(optional.isPresent()) {
			Payment payment = optional.get();
			payment.setStatus(EPaymentStatus.APPROVED);
			paymentRepository.save(payment);
			
			//TODO: Create Account linkage entry
			
			return 0;
		} else {
			throw new ResourceNotFoundException("Payment not found");
		}
	}
	
	
	public Integer linkPartyAccount(Long clientId, Long partyId, Long accountId) {
		
		Optional<PartyAccountsLinkage> optional =  linkageRepository.findByClientIdAndPartyIdAndAccountId(clientId, partyId, accountId);
		if(optional.isPresent())
			return 0;
		else {
			PartyAccountsLinkage linkage = new PartyAccountsLinkage();
			linkage.setClientId(clientId);
			linkage.setPartyId(partyId);
			linkage.setAccountId(accountId);
			linkageRepository.save(linkage);
			return 0;
		}
	}
	
	
	
	public List<PartyBankAccountDTO> getAllLinkedAccounts(Long clientId, Long partyId) {
		List<PartyBankAccountDTO> list = new ArrayList<PartyBankAccountDTO>();
		
		List<PartyAccountsLinkage> linkages = linkageRepository.findByClientIdAndPartyId(clientId, partyId);
		
		if(Collections.isNullOrEmpty(linkages)) {
			return list;
		}
		
		PartyBankAccountRepository partyAccountrepository = ServiceLocator.getService(PartyBankAccountRepository.class);
	
		for(PartyAccountsLinkage linkage: linkages) {
			Optional<PartyBankAccount> optional = partyAccountrepository.findById(linkage.getAccountId());
			if(optional.isPresent()) {
				PartyBankAccountDTO accountDTO = ObjectMapperUtil.map(optional.get(), PartyBankAccountDTO.class);
				list.add(accountDTO);
			}
		}
		
		return list;
	}
	
	
	
	public List<PaymentSummaryDTO> getPaymentsBetweenDates(Long clientId, EPaymentStatus status, GetPaymentsByDateRequest req) {
		List<PaymentSummaryDTO> listOfDraft = new ArrayList<PaymentSummaryDTO>();
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
		List<Payment> drafts = paymentRepository.findAllByClientIdAndStatusAndPaymentDateBetween(clientId, status, fromDate, toDate);
		
		for(Payment draft: drafts) {
			PaymentSummaryDTO summary = ObjectMapperUtil.object(draft.getPaymentSummary(), PaymentSummaryDTO.class);
			
			summary.setPaymentId(draft.getId());
			summary.setPaymentDate(draft.getPaymentDate());
			listOfDraft.add(summary);
		}
		
		return listOfDraft;
	}
}
