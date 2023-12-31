/**
 * 
 */
package com.jmsc.app.service;


import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.jmsc.app.common.dto.PartyBankAccountDTO;
import com.jmsc.app.common.util.ObjectMapperUtil;
import com.jmsc.app.entity.PartyAccountsLinkage;
import com.jmsc.app.entity.PartyAccountsLinkageKey;
import com.jmsc.app.entity.PartyBankAccount;
import com.jmsc.app.repository.PartyAccountsLinkageRepository;
import com.jmsc.app.repository.PartyBankAccountRepository;

import lombok.extern.slf4j.Slf4j; 

/**
 * @author chandan
 *
 */
@Service
@Slf4j
public class PartyBankAccountService {
	
	@Autowired
	private PartyBankAccountRepository repository;
	
	@Autowired
	private PartyAccountsLinkageRepository linkageRepository;
	
	
	private Map<String, String> branchCache = new ConcurrentHashMap<String, String>();
	
	
	@PostConstruct
	public void onActivate() {
		this.loadBanks();
	}
	
	
	private void loadBanks() {
		List<PartyBankAccount> all = repository.findAll();
		all.forEach(account -> {
			updateBankCache(account);
		});
		log.debug("All banks loaded");
	
	}
	
	
	private void updateBankCache(PartyBankAccount account) {
		String key = account.getIfscCode().trim().toUpperCase();
		if(branchCache.containsKey(key))
			return;
		else{
			String bank = account.getBankName();
			String branch = account.getBranchName();
			String branchCode = account.getBranchCode();
			branchCache.put(key, bank+","+branch+","+branchCode);
		}
	}
	
	
	public String getBankByIfsc(String ifscCode) {
		if(branchCache.containsKey(ifscCode)) {
			return branchCache.get(ifscCode);
		} else {
			return "-1";
		}
	}
	
	
	 public String storeFile(MultipartFile file) throws Exception {
		 String fileName = StringUtils.cleanPath(file.getOriginalFilename());
	     if(fileName.contains(" ")){
	    	 throw new RuntimeException("File name contains empty space.");
	     }
		 try {
	        Path fileStorageLocation = Paths.get("C:\\Users\\Chandan\\Downloads\\").toAbsolutePath().normalize();
	        Path targetLocation =	fileStorageLocation.resolve(new Timestamp(System.currentTimeMillis()).getTime() + "_" + fileName);
	        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
	        return targetLocation.toString();
	     } catch (java.io.IOException ex) {
	    	 throw new Exception("Could not store file " + fileName + ". Please try again!", ex);
	     }finally {
			
		}
	 }
	
	
	public List<PartyBankAccountDTO> getAccountRecords(String filePath) throws Throwable
	{  
		List<PartyBankAccountDTO> partyAccountList = new ArrayList<PartyBankAccountDTO>();
		try{  
			File file = new File(filePath);   
			FileInputStream fis = new FileInputStream(file);   
			
			XSSFWorkbook wb = new XSSFWorkbook(fis);   
			XSSFSheet sheet = wb.getSheetAt(0);       
			Iterator<Row> itr = sheet.iterator();  
			while (itr.hasNext()){  
				Row row = itr.next();  
				Iterator<Cell> cellIterator = row.cellIterator();
				String strValue = null;
				
				int key = 1;
				HashMap<Integer, String> values = new HashMap<Integer, String>(); 
				
				while (cellIterator.hasNext()){
					
					Cell cell = cellIterator.next();
					switch(cell.getCellType()){
						case STRING:
							strValue = cell.getStringCellValue();
							
							break;
						case NUMERIC:
							Double doubleValue = cell.getNumericCellValue();
							strValue = doubleValue.toString();
							break;
					default:
						break;
					}
					values.put(key, strValue);
					key++;
				}  
				PartyBankAccountDTO dto = buildPartyBankAccountDTO(values);
				dto.toUppercase();
				if(dto.getPartyName() != null && dto.getAccountHolder() != null
						&& dto.getAccountNumber() != null && dto.getIfscCode() != null)
					partyAccountList.add(dto);
			}
		}  
		catch(Exception e){  
			e.printStackTrace();  
		}
		return partyAccountList;  
	}
	
	private PartyBankAccountDTO buildPartyBankAccountDTO(Map<Integer, String> values){
		PartyBankAccountDTO dto = new PartyBankAccountDTO();
		dto.setPartyName(values.get(2));
		dto.setAccountHolder(values.get(3));
		dto.setAccountNumber(values.get(4));
		dto.setIfscCode(values.get(5));
		dto.setBankName(values.get(6));
		dto.setBranchName(values.get(7));
		return dto;
	}
	
	
	
	public PartyBankAccountDTO addAccount(PartyBankAccountDTO bankAccountDTO){
		
		if(bankAccountDTO.getClientId() == null) {
			throw new RuntimeException("Insufficient Data");
		}
		
		if(bankAccountDTO.getId() == null) {
			Optional<PartyBankAccount> optional = repository.findByClientIdAndAccountNumber(bankAccountDTO.getClientId(), bankAccountDTO.getAccountNumber().toUpperCase());
			if(optional.isPresent()) {
				throw new RuntimeException("Part Account Already Exists with Name: " + optional.get().getPartyName());
			}
		}
		
		PartyBankAccount bankAccount = ObjectMapperUtil.map(bankAccountDTO.toUppercase(), PartyBankAccount.class);
		PartyBankAccount savedAccount = repository.save(bankAccount);
		PartyBankAccountDTO savedAccountDTO = ObjectMapperUtil.map(savedAccount, PartyBankAccountDTO.class);
		log.debug("New Bank Account Added: " + savedAccountDTO.toString());
		
		this.updateBankCache(bankAccount);
		
		return savedAccountDTO;
	}
	
	
	
	
	public List<PartyBankAccountDTO> getAllAccounts(Long clientId){
		List<PartyBankAccount> accounts = repository.findByClientId(clientId);
		List<PartyBankAccountDTO> accountsDTO = ObjectMapperUtil.mapAll(accounts, PartyBankAccountDTO.class);
		return accountsDTO;
	}
	
	
	
	public PartyBankAccountDTO getAccount(Long clientId, Long accountId) {
		Optional<PartyBankAccount> optional = repository.findByClientIdAndId(clientId, accountId);
		if(optional.isPresent()) {
			PartyBankAccountDTO dto = ObjectMapperUtil.map(optional.get(), PartyBankAccountDTO.class);
			return dto;
		} else {
			throw new RuntimeException("Account dose not exist");
		}
	}
	
	public Integer linkPartyAccount(Long clientId, Long partyId, Long accountId) {
		Optional<PartyAccountsLinkage> optional =  linkageRepository.findByClientIdAndPartyIdAndAccountId(clientId, partyId, accountId);
		if(optional.isPresent())
			return -1;
		else {
			PartyAccountsLinkage linkage = new PartyAccountsLinkage();
			PartyAccountsLinkageKey id = new PartyAccountsLinkageKey(clientId, partyId, accountId);
			linkage.setId(id);
			linkageRepository.save(linkage);
			return 0;
		}
	}
	
	
	public Integer delinkPartyAccount(Long clientId, Long partyId, Long accountId) {
		Optional<PartyAccountsLinkage> optional =  linkageRepository.findByClientIdAndPartyIdAndAccountId(clientId, partyId, accountId);
		if(optional.isPresent()) {
			linkageRepository.delete(optional.get());
			return 0;
		} else {
			throw new RuntimeException("Error while removing account");
		}
	}
}
