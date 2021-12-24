/**
 * 
 */
package com.jmsc.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmsc.app.common.dto.BankAccountDTO;
import com.jmsc.app.common.util.ObjectMapperUtil;
import com.jmsc.app.entity.users.BankAccount;
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
		bankAccountDTO.toUppercase();
		BankAccount bankAccount = ObjectMapperUtil.map(bankAccountDTO, BankAccount.class);
		BankAccount entity = repository.save(bankAccount);
		BankAccountDTO dto = ObjectMapperUtil.map(entity, BankAccountDTO.class);
		return dto;
	}
	
	
	public List<BankAccountDTO> getAllAccounts(){
		List<BankAccount> bankAccouns = repository.findAll();
		List<BankAccountDTO> dtos = ObjectMapperUtil.mapAll(bankAccouns, BankAccountDTO.class);
		return dtos;
	}
	
	
	public List<BankAccountDTO> getActiveAccounts(){
		List<BankAccount> bankAccouns = repository.findByStatus("ACTIVE");
		List<BankAccountDTO> dtos = ObjectMapperUtil.mapAll(bankAccouns, BankAccountDTO.class);
		dtos.stream().forEach(account -> {
			account.setDisplayName(account.getAccountNumber()+"-"+account.getAccountHolder()+"-"+account.getBankName());
		});
		return dtos;
	}
	
	

}
