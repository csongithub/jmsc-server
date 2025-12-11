/**
 * 
 */
package com.jmsc.app.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmsc.app.common.dto.PaymentDTO;
import com.jmsc.app.common.dto.PaymentFilterCriteria;
import com.jmsc.app.common.dto.PaymentSummaryDTO;
import com.jmsc.app.common.enums.EPaymentStatus;
import com.jmsc.app.common.exception.ResourceNotFoundException;
import com.jmsc.app.common.rqrs.ApprovePaymentRequest;
import com.jmsc.app.common.rqrs.GetPaymentsByDateRequest;
import com.jmsc.app.common.rqrs.Range;
import com.jmsc.app.common.util.Collections;
import com.jmsc.app.common.util.ObjectMapperUtil;
import com.jmsc.app.common.util.Strings;
import com.jmsc.app.config.jmsc.ServiceLocator;
import com.jmsc.app.entity.PartyBankAccount;
import com.jmsc.app.entity.Payment;
import com.jmsc.app.entity.accounting.Creditor;
import com.jmsc.app.entity.accounting.CreditorPaymentLinkage;
import com.jmsc.app.repository.PartyBankAccountRepository;
import com.jmsc.app.repository.PaymentRepository;
import com.jmsc.app.repository.accounting.CreditorPaymentLinkageRepository;
import com.jmsc.app.repository.accounting.CreditorRepository;

/**
 * @author Chandan
 *
 */
@Service

public class PaymentService2 {

	
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private PartyBankAccountService partyAccountService;
	
	@Autowired
	private PartyBankAccountRepository partyBankAccountRepository;
	
	
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
		
		listOfDraft = this.updateOtherDetails(clientId, drafts);
		
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
	
	
	public PaymentSummaryDTO getPaymentSummary(Long clientId, Long paymentId) {
		Optional<Payment> optional  = paymentRepository.findAllByClientIdAndId(clientId, paymentId);
		
		if(!optional.isPresent())
			return null;
		PaymentSummaryDTO paymentSummary = ObjectMapperUtil.object(optional.get().getPaymentSummary(), PaymentSummaryDTO.class);
		paymentSummary.setPaymentDate(optional.get().getPaymentDate());
		return paymentSummary;
	}
	
	
	public Integer approvePayments(ApprovePaymentRequest req) {
		
		if(Collections.isNullOrEmpty(req.getPayments()))
			throw new ResourceNotFoundException("Invalid Request");
		
		for(Long paymentId: req.getPayments()) {
			Optional<Payment> optional  = paymentRepository.findAllByClientIdAndId(req.getClientId(), paymentId);
			
			if(optional.isPresent()) {
				Payment payment = optional.get();
				payment.setStatus(EPaymentStatus.APPROVED);
				paymentRepository.save(payment);
				PaymentSummaryDTO paymentSummary = ObjectMapperUtil.object(payment.getPaymentSummary(), PaymentSummaryDTO.class);
				
				if(paymentSummary.getToAccountId() != null)
					partyAccountService.linkPartyAccount(req.getClientId(), paymentSummary.getPartyId(), paymentSummary.getToAccountId());
				
				
				//TODO: Post approval, check if the party for which this payment is made for exists as a creditor.
				//If YES, then stamp this payment in creditor payment linkage table
				this.linkCreditorPayment(req.getClientId(), paymentSummary.getPartyId(), payment.getId());
			}
		}
		return 0;
	}
	
	
	private void linkCreditorPayment(Long clientId, Long partyId, Long paymentId) {
		CreditorRepository repository = ServiceLocator.getService(CreditorRepository.class);
		Optional<Creditor> optional  = repository.findByClientIdAndPartyId(clientId, partyId);
		
		if(!optional.isPresent())
			return;
		
		CreditorPaymentLinkage paymentLinkage = new CreditorPaymentLinkage();
		paymentLinkage.setClientId(clientId);
		paymentLinkage.setPartyId(partyId);
		paymentLinkage.setPaymentId(paymentId);
		paymentLinkage.setCreditorId(optional.get().getId());
		paymentLinkage.setStatus("CREATED");
		
		ServiceLocator.getService(CreditorPaymentLinkageRepository.class).save(paymentLinkage);
		
	}
	
	
	public Integer rejectPayment(Long clientId, Long paymentId) {
		Optional<Payment> optional  = paymentRepository.findAllByClientIdAndId(clientId, paymentId);
		if(optional.isPresent()) {
			Payment payment = optional.get();
			payment.setStatus(EPaymentStatus.DRAFT);
			paymentRepository.save(payment);
			
			//TODO: Create Account linkage entry
			
			return 0;
		} else {
			throw new ResourceNotFoundException("Payment not found");
		}
	}
	
	
	
	public List<PaymentSummaryDTO> getPaymentsBetweenDates(Long clientId, EPaymentStatus status, GetPaymentsByDateRequest req) {
		List<PaymentSummaryDTO> listOfDraft = new ArrayList<PaymentSummaryDTO>();
		Range range = req.getRange();
		Date fromDate = null;
		Date toDate = null;
		try {
			fromDate =new SimpleDateFormat("yyyy/MM/dd").parse(range.getFrom());
		    
			toDate = new SimpleDateFormat("yyyy/MM/dd").parse(range.getTo());

		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<Payment> drafts = paymentRepository.findAllByClientIdAndStatusAndPaymentDateBetween(clientId, status, fromDate, toDate);
		
		listOfDraft = this.updateOtherDetails(clientId, drafts);
		
		return listOfDraft;
	}
	
	private List<PaymentSummaryDTO> updateOtherDetails(Long clientId, List<Payment> drafts) {
		List<PaymentSummaryDTO> listOfDraft = new ArrayList<PaymentSummaryDTO>();
		for(Payment draft: drafts) {
			PaymentSummaryDTO summary = ObjectMapperUtil.object(draft.getPaymentSummary(), PaymentSummaryDTO.class);
			Optional<PartyBankAccount> partyBankAccount = partyBankAccountRepository.findByClientIdAndId(clientId, summary.getToAccountId());
			
			if(partyBankAccount.isPresent()) {
				summary.setAccountHolder(partyBankAccount.get().getAccountHolder());
				summary.setAccountNumber(partyBankAccount.get().getAccountNumber());
			}
			
			summary.setPaymentId(draft.getId());
			summary.setPaymentDate(draft.getPaymentDate());
			listOfDraft.add(summary);
		}
		
		java.util.Collections.sort(listOfDraft, new Comparator<PaymentSummaryDTO>() {
	        public int compare(PaymentSummaryDTO emp1, PaymentSummaryDTO emp2) {
	            return emp2.getPaymentDate().compareTo(emp1.getPaymentDate());
	        }
	    });
		
		return listOfDraft;
		
	}
	
	
	@PostConstruct
	public void getAllPaymentAccountNumber() {
//		List<Payment> drafts = paymentRepository.findByFromAccountNumber(1L, "6208430945", EPaymentStatus.APPROVED.toString());
//		List<Payment> drafts = paymentRepository.findByParty(1L, "JMSC", EPaymentStatus.APPROVED.toString());
//		System.out.println(drafts.size());
	}
	
	
	public List<PaymentSummaryDTO> getPaymentsByPartyId(Long clientId, Long partyId){
		List<PaymentSummaryDTO> listOfDraft = new ArrayList<PaymentSummaryDTO>();
		List<Payment> searchResult = null;
		
		searchResult = paymentRepository.findAllByClientIdAndPartyId(clientId, partyId );
		listOfDraft = this.updateOtherDetails(clientId, searchResult);

		return listOfDraft;
	}
	
	
	public List<PaymentSummaryDTO> getPaymentsByCriteria(Long clientId, EPaymentStatus status, PaymentFilterCriteria criteria){
		List<PaymentSummaryDTO> listOfDraft = new ArrayList<PaymentSummaryDTO>();
		
		List<Payment> searchResult = null;
		
		if(isValid(criteria.getFromAccount()) && isValid(criteria.getToAccount()) && isValid(criteria.getPartyName())) {
			searchResult = paymentRepository.findByPartyAndFromAccountAndToAccount(clientId,
																				   criteria.getPartyName(),
																				   criteria.getFromAccount(),
																				   criteria.getToAccount(),
																				   status.toString());
		}else if(isValid(criteria.getFromAccount()) && isValid(criteria.getToAccount()) && !isValid(criteria.getPartyName())) {
			searchResult = paymentRepository.findByFromAndToAccount(clientId,
																	criteria.getFromAccount(),
																	criteria.getToAccount(),
																	status.toString());
		}else if(isValid(criteria.getFromAccount()) &&isValid(criteria.getPartyName()) && !isValid(criteria.getToAccount())) {
			searchResult = paymentRepository.findByFromAccountAndPartyName(clientId,
																		   criteria.getFromAccount(),
																		   criteria.getPartyName(),
																		   status.toString());
		}else if(isValid(criteria.getToAccount()) &&isValid(criteria.getPartyName()) && !isValid(criteria.getFromAccount())) {
			searchResult = paymentRepository.findByToAccountAndPartyName(clientId,
																		 criteria.getToAccount(),
																		 criteria.getPartyName(),
																		 status.toString());
		} else if(isValid(criteria.getPartyName()) && !isValid(criteria.getFromAccount()) && !isValid(criteria.getToAccount())) {
			searchResult = paymentRepository.findByParty(clientId,
														 criteria.getPartyName(),
														 status.toString());
		}else if(isValid(criteria.getFromAccount()) && !isValid(criteria.getToAccount()) && !isValid(criteria.getPartyName())) {
			searchResult = paymentRepository.findByFromAccountNumber(clientId,
																	 criteria.getFromAccount(),
																	 status.toString());
		} else if(isValid(criteria.getToAccount()) && !isValid(criteria.getFromAccount()) && !isValid(criteria.getPartyName())) {
			searchResult = paymentRepository.findByToAccountNumber(clientId,
																   criteria.getToAccount(),
																   status.toString());
		}
		
		
		listOfDraft = this.updateOtherDetails(clientId, searchResult);
		
		return listOfDraft;
	}
	
	
//	private List<PaymentSummaryDTO> buildPaymentsDTOj(List<Payment> searchResult, List<PaymentSummaryDTO> listOfDraft){
//		if(Collections.isNotNullOrEmpty(searchResult)) {
//			this.buildPaymentSummaryDTO(searchResult, listOfDraft);
//			
//			java.util.Collections.sort(listOfDraft, new Comparator<PaymentSummaryDTO>() {
//		        public int compare(PaymentSummaryDTO emp1, PaymentSummaryDTO emp2) {
//		            return emp2.getPaymentDate().compareTo(emp1.getPaymentDate());
//		        }
//		    });
//		}
//	
//		return listOfDraft;
//	}
		
	
	private boolean isValid(String val) {
		return Strings.isNotNullOrEmpty(val);
	}
	
	
	
//	private void buildPaymentSummaryDTO(List<Payment> drafts, List<PaymentSummaryDTO> listOfDraft) {
//		for(Payment draft: drafts) {
//			PaymentSummaryDTO summary = ObjectMapperUtil.object(draft.getPaymentSummary(), PaymentSummaryDTO.class);
//			summary.setPaymentId(draft.getId());
//			summary.setPaymentDate(draft.getPaymentDate());
//			listOfDraft.add(summary);
//		}
//	}
}
