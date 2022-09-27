/**
 * 
 */
package com.jmsc.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmsc.app.common.dto.BankAccountDTO;
import com.jmsc.app.common.util.ObjectMapperUtil;
import com.jmsc.app.entity.BankAccount;
import com.jmsc.app.repository.BankAccountRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Chandan
 *
 */
@Service
@Slf4j
public class BankAccountService {
	
	@Autowired
	private BankAccountRepository repository;
	
	
	public BankAccountDTO addBankAccount(BankAccountDTO bankAccountDTO) {
		
		if(bankAccountDTO.getClientId() == null) {
			throw new RuntimeException("Insufficient Data");
		}
		
		if(bankAccountDTO.getId() == null) {
			Optional<BankAccount> optional = repository.findByClientIdAndAccountNumber(bankAccountDTO.getClientId(), bankAccountDTO.getAccountNumber().trim());
			if(optional.isPresent()) {
				throw new RuntimeException("Account number already exists");
			}
		}
		
		bankAccountDTO.toUppercase();
		BankAccount bankAccount = ObjectMapperUtil.map(bankAccountDTO, BankAccount.class);
		BankAccount entity = repository.save(bankAccount);
		BankAccountDTO dto = ObjectMapperUtil.map(entity, BankAccountDTO.class);
		return dto;
	}
	
	
	public List<BankAccountDTO> getAllAccounts(Long clientId){
		List<BankAccount> bankAccouns = repository.findByClientId(clientId);
		List<BankAccountDTO> dtos = ObjectMapperUtil.mapAll(bankAccouns, BankAccountDTO.class);
		return dtos;
	}
	
	
	public List<BankAccountDTO> getActiveAccounts(Long clientId){
		List<BankAccount> bankAccouns = repository.findByClientIdAndStatus(clientId, "ACTIVE");
		List<BankAccountDTO> dtos = ObjectMapperUtil.mapAll(bankAccouns, BankAccountDTO.class);
//		dtos.stream().forEach(account -> {
//			account.setDisplayName(account.getAccountNumber()+"-"+account.getAccountHolder()+"-"+account.getBankName());
//		});
		return dtos;
	}
	
	
	public BankAccountDTO getAccount(Long clientId, Long accountId) {
		Optional<BankAccount> optional = repository.findByClientIdAndId(clientId, accountId);
		if(optional.isPresent()) {
			BankAccountDTO dto = ObjectMapperUtil.map(optional.get(), BankAccountDTO.class);
			return dto;
		} else {
			throw new RuntimeException("Account dose not exist");
		}
	}
}
