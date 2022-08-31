/**
 * 
 */
package com.jmsc.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmsc.app.common.dto.PaymentDTO;
import com.jmsc.app.common.dto.PaymentSummaryDTO;
import com.jmsc.app.common.enums.EPaymentStatus;
import com.jmsc.app.common.exception.ResourceNotFoundException;
import com.jmsc.app.common.util.ObjectMapperUtil;
import com.jmsc.app.entity.Payment;
import com.jmsc.app.repository.PartyAccountsLinkageRepository;
import com.jmsc.app.repository.PaymentRepository;

/**
 * @author Chandan
 *
 */
@Service
public class PaymentService2 {

	@Autowired
	private PartyAccountsLinkageRepository repository;
	
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
}
