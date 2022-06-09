/**
 * 
 */
package com.jmsc.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmsc.app.common.dto.CreditFacilityDTO;
import com.jmsc.app.common.dto.LoanDTO;
import com.jmsc.app.common.rqrs.ManageCollateralRequest;
import com.jmsc.app.common.util.Collections;
import com.jmsc.app.common.util.ObjectMapperUtil;
import com.jmsc.app.common.util.Strings;
import com.jmsc.app.config.jmsc.ServiceLocator;
import com.jmsc.app.entity.CreditFacility;
import com.jmsc.app.entity.Loan;
import com.jmsc.app.repository.CreditFacilityRepository;
import com.jmsc.app.repository.LoanRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Chandan
 *
 */
@Slf4j
@Service
public class LoanService {
	
	@Autowired
	private LoanRepository repository;
	
	
	
	public LoanDTO addLoan(LoanDTO dto) {
		if(dto.getClientId() == null) {
			throw new RuntimeException("Insufficient Data");
		}
		
		if(Strings.isNullOrEmpty(dto.getAccountNo())) {
			throw new RuntimeException("Account number not found");
		}
		if(dto.getId() == null) {
			Optional<Loan> optional = repository.findByClientIdAndAccountNo(dto.getClientId(), dto.getAccountNo());
			if(optional.isPresent()) {
				throw new RuntimeException("Account already exist with: " + optional.get().getDisplayName());
			}
		}
		Loan entity =  ObjectMapperUtil.map(dto, Loan.class);
		entity = repository.save(entity);
		dto = ObjectMapperUtil.map(entity, LoanDTO.class);
		return dto;
	}
	
	
	public List<LoanDTO> getAllLoans(Long clientId) {
		List<Loan> loans = repository.findAllByClientId(clientId);
		if(Collections.isNullOrEmpty(loans))
			return new ArrayList<LoanDTO>();
		List<LoanDTO> results = ObjectMapperUtil.mapAll(loans, LoanDTO.class);
		return results;
	}
	
	
	/**
	 * 
	 * This service gives all collateral that are hold against a loan
	 * 
	 * @param clientId
	 * @param loanId
	 * @return
	 */
	public List<CreditFacilityDTO> getCollateralForLoan(Long clientId, Long loanId){
		CreditFacilityService service = ServiceLocator.getService(CreditFacilityService.class);
		return service.getCollateralForLoan(clientId, loanId);
	}
	
	
	/**
	 * This service is use to link/de-link collateral to/from a Loan
	 * 
	 * @param request
	 * @return
	 * @throws Throwable
	 */
	public LoanDTO manageCollateral(ManageCollateralRequest request) throws Throwable{
		
		if(Collections.isNullOrEmpty(request.getCollateral())) {
			log.debug("Insufficient Data");
			throw new Exception("No Collateral Found");
		}
		
		if(request.getLoan() == null || request.getLoan().getId() == null || request.getLoan().getClientId() == null) {
			log.debug("Insufficient Data");
			throw new Exception("Invalid BG Group");
		}
		
		if(request.getLink() == null) {
			log.debug("Insufficient Data");
			throw new Exception("Linking information is not there");
		}
		
		for(CreditFacilityDTO collateral: request.getCollateral()) {
			Optional<CreditFacility> optional = ServiceLocator.getService(CreditFacilityRepository.class).findAllByClientIdAndAccountNumber(request.getLoan().getClientId(), collateral.getAccountNumber());
			if(optional.isPresent()) {
				CreditFacility cf = optional.get();
				if(request.getLink()) {
					//Link deposit
					cf.setLoanId(request.getLoan().getId());
				} else{
					//de-link deposit
					cf.setLoanId(null);
				}
				ServiceLocator.getService(CreditFacilityRepository.class).save(cf);
			}
		}
		return request.getLoan();
	}
}
