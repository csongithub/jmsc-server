/**
 * 
 */
package com.jmsc.app.service.accounting;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmsc.app.common.dto.PaymentSummaryDTO;
import com.jmsc.app.common.dto.accounting.CapitalAccountDTO;
import com.jmsc.app.common.dto.accounting.CreditorDTO;
import com.jmsc.app.common.dto.accounting.GetLedgerEntryRequest;
import com.jmsc.app.common.dto.accounting.Item;
import com.jmsc.app.common.dto.accounting.LedgerDTO;
import com.jmsc.app.common.dto.accounting.LedgerEntryDTO;
import com.jmsc.app.common.dto.accounting.ListDTO;
import com.jmsc.app.common.dto.accounting.VoucherDTO;
import com.jmsc.app.common.enums.EEntryType;
import com.jmsc.app.common.util.Collections;
import com.jmsc.app.common.util.DateUtils;
import com.jmsc.app.common.util.ObjectMapperUtil;
import com.jmsc.app.common.util.Strings;
import com.jmsc.app.config.jmsc.ServiceLocator;
import com.jmsc.app.entity.accounting.CapitalAccount;
import com.jmsc.app.entity.accounting.CapitalAccountEntry;
import com.jmsc.app.entity.accounting.Creditor;
import com.jmsc.app.entity.accounting.CreditorPaymentLinkage;
import com.jmsc.app.entity.accounting.Ledger;
import com.jmsc.app.entity.accounting.LedgerEntry;
import com.jmsc.app.entity.accounting.Voucher;
import com.jmsc.app.repository.accounting.CapitalAccountEntryRepository;
import com.jmsc.app.repository.accounting.CapitalAccountRepository;
import com.jmsc.app.repository.accounting.CreditorPaymentLinkageRepository;
import com.jmsc.app.repository.accounting.CreditorRepository;
import com.jmsc.app.repository.accounting.LedgerEntryRepository;
import com.jmsc.app.repository.accounting.LedgerRepository;
import com.jmsc.app.repository.accounting.VoucherRepository;
import com.jmsc.app.service.AbstractService;
import com.jmsc.app.service.PaymentService2;

/**
 * @author anuhr
 *
 */
@Service
public class AccountingService extends AbstractService{
	
	
	@Autowired
	private CreditorRepository creditorRepository;
	
	@Autowired
	private LedgerRepository ledgerRepository;
	
	@Autowired
	private LedgerEntryRepository entryRepository;
	
	@Autowired
	private CreditorPaymentLinkageRepository paymentLinkageRepository;
	
	@Autowired
	private CapitalAccountRepository capitalAccountRepository;
	
	@Autowired
	private CapitalAccountEntryRepository capitalAccountEntryRepository;
	
	@Autowired
	private VoucherRepository voucherRepository;
	
	
	
	public LedgerDTO createOrUpdate(LedgerDTO dto) {
		if(isNull(dto.getClientId()) || isNull(dto.getCode()) || isNull(dto.getCreditorId())
				|| isNull(dto.getName()) || isNull(dto.getOpeningBalance()) || isNull(dto.getStartDate())) {
			throw new RuntimeException("Invalid Request");
		}
		
		Ledger entity= ObjectMapperUtil.map(dto, Ledger.class);
		entity = ledgerRepository.save(entity);
		
		LedgerDTO savedLedger= ObjectMapperUtil.map(entity, LedgerDTO.class);
		
		return savedLedger;
	}
	
	
	public List<LedgerDTO> getLedgers(Long clientId, Long creditorId){
		
		if(isNull(clientId) || isNull(creditorId)) {
			throw new RuntimeException("Invalid Request");
		}
		List<Ledger> ledgers =  ledgerRepository.findByClientIdAndCreditorId(clientId, creditorId);
		
		if(Collections.isEmpty(ledgers))
			return new ArrayList<LedgerDTO>();
		
		List<LedgerDTO> allLedgers = ObjectMapperUtil.mapAll(ledgers, LedgerDTO.class);
		
		return allLedgers;
	}
	
	
	
	public LedgerDTO getLedger(Long clientId, Long creditorId, Long ledgerId) {
		if(isNull(clientId) || isNull(ledgerId)) {
			throw new RuntimeException("Invalid Request");
		}
		
		Optional<Ledger> optional = ledgerRepository.findByClientIdAndCreditorIdAndId(clientId, creditorId, ledgerId);
		
		if(!optional.isPresent()) {
			throw new RuntimeException("Ledger Not Found");
		}
		
		LedgerDTO ledger= ObjectMapperUtil.map(optional.get(), LedgerDTO.class);
		
		return ledger;
	}
	
	
	public CreditorDTO createOrUpdate(CreditorDTO dto) {
		if(isNull(dto.getClientId()) || isNull(dto.getName()) || isNull(dto.getAddress())
				|| isNull(dto.getPartyId())) {
			throw new RuntimeException("Insufficient Data");
		}
		
		dto.toUpperCase();
		
		Creditor entity= ObjectMapperUtil.map(dto, Creditor.class);
		
		entity = creditorRepository.save(entity);
		
		CreditorDTO savedCreditor = ObjectMapperUtil.map(entity, CreditorDTO.class);
		
		return savedCreditor;
	}
	
	
	public CreditorDTO getById(Long clientId, Long id) {
		if(isNull(clientId) || isNull(id)) {
			throw new RuntimeException("Invalid Request");
		}
		
		Optional<Creditor> optional =  creditorRepository.findByClientIdAndId(clientId, id);
		if(!optional.isPresent()) {
			throw new RuntimeException("Creditor Not Found");
		}
		
		CreditorDTO creditor = ObjectMapperUtil.map(optional.get(), CreditorDTO.class);
		return creditor;
	}
	
	
	public ListDTO getAllCreditors(Long clientId) {
		
		ListDTO list = new ListDTO();
		list.setListName("Creditors");
		
		if(isNull(clientId)) {
			throw new RuntimeException("Invalid Request");
		}
		
		List<Creditor> creditors  = creditorRepository.findByClientId(clientId);
		
		if(Collections.isEmpty(creditors))
			return list;
		
		creditors.forEach(c ->  {
			Item item  = new Item();
			item.setLabel(c.getName());
			item.setValue(c.getId());
			list.getList().add(item);
		});
		
		ListDTO.sortByLevel(list);
		
		return list;
	}
	
	
	public String getMaterials(Long clientId, Long creditorId) {
		if(isNull(clientId) || isNull(creditorId)) {
			throw new RuntimeException("Invalid Request");
		}
		
		Optional<Creditor> optional =  creditorRepository.findByClientIdAndId(clientId, creditorId);
		if(!optional.isPresent()) {
			throw new RuntimeException("Creditor Not Found");
		}
		
		return optional.get().getItems();
	}
	
	
	
	public List<LedgerEntryDTO> getEntries(GetLedgerEntryRequest req){
		/**
		 * Check if dates are not provided with with the request.
		 * And if not the evaluate for the current month from 1st to today's date.
		 */
		if(req.getFrom() == null || req.getTo() == null) {
			 LocalDate today = LocalDate.now();
			 int firstDate = 1;
			 int date = today.getDayOfMonth();
			 int month = today.getMonthValue();
			 int year  = today.getYear();
			 
			 
			 String from = String.valueOf(firstDate) + "-"+String.valueOf(month)+"-"+String.valueOf(year);
			 String to = String.valueOf(date) + "-"+String.valueOf(month)+"-"+String.valueOf(year);
			 
			 req.setFrom(from);
			 req.setTo(to);
		}
		
		
		Date fromDate = DateUtils.getDate(req.getFrom());
		Date toDate = DateUtils.getDate(req.getTo());
		
		
		List<LedgerEntry> entries = null;
		
		if(EEntryType.ALL.equals(req.getEntryType())) {
			entries = entryRepository.findAllByClientIdAndCreditorIdAndLedgerIdAndDateBetween(req.getClientId(), 
					  req.getCreditorId(), 
					  req.getLedgerId(),
					  fromDate, 
					  toDate);
		} else {
			entries = entryRepository.findAllByClientIdAndCreditorIdAndLedgerIdAndEntryTypeAndDateBetween(req.getClientId(), 
																										  req.getCreditorId(), 
																										  req.getLedgerId(), 
																										  req.getEntryType(),
																										  fromDate, 
																										  toDate);
		}
		
		if(Collections.isNullOrEmpty(entries))
			return new ArrayList<LedgerEntryDTO>();
		
		List<LedgerEntryDTO> all = ObjectMapperUtil.mapAll(entries, LedgerEntryDTO.class);		
		
		
		//Update narration only in case of ALL Records
		if(EEntryType.ALL.equals(req.getEntryType())) {
			updateNarration(all); 
		}
		
		java.util.Collections.sort(all, new Comparator<LedgerEntryDTO>() {
	        public int compare(LedgerEntryDTO entry1, LedgerEntryDTO entry2) {
	            return entry1.getDate().compareTo(entry2.getDate());
	        }
	    });
		
		if(EEntryType.CREDIT.equals(req.getEntryType())) {
			updateTotalBalanceForCredit(all);
		} else if(EEntryType.DEBIT.equals(req.getEntryType())) {
			updateTotalBalanceForDebit(all);
		}else if(EEntryType.ALL.equals(req.getEntryType())) {
			updateTotalBalanceForAll(req, all);
		}
		
		return all;
	}
	
	
	
	public Boolean deleteEntry(Long clientId, Long creditorId, Long ledgerId, Long id) {
		Optional<LedgerEntry> optional =  entryRepository.findByClientIdAndCreditorIdAndLedgerIdAndId(clientId, creditorId, ledgerId, id);
		
		if(!optional.isPresent())
			throw new RuntimeException("Entry Not Found");
		
		entryRepository.delete(optional.get());
		
		return Boolean.TRUE;
	}
	
	
	public void updateTotalBalanceForAll(GetLedgerEntryRequest req, List<LedgerEntryDTO> entries) {
		
		Optional<Ledger> optional = ledgerRepository.findByClientIdAndCreditorIdAndId(req.getClientId(), req.getCreditorId(), req.getLedgerId());
		
		if(!optional.isPresent())
			throw new RuntimeException("Ledger Not Found");
		
		Double openingBalance = 0.0;
		
		if(DateUtils.isSameDay(optional.get().getStartDate(), DateUtils.getDate(req.getFrom()))) {
			openingBalance = optional.get().getOpeningBalance();
		} else {
			openingBalance = getOpeningBalance(req, optional.get());
		}
		

		//TODO: Set first record as opengin balance
		
		for(int index = 0; index<entries.size(); index++) {
			Double credit = entries.get(index).getCredit() != null ? entries.get(index).getCredit() : 0.0;
			Double debit = entries.get(index).getDebit() != null ? entries.get(index).getDebit() : 0.0;
			Double currentOpeningBalance = (index==0) ? openingBalance : entries.get(index-1).getTotal();
			
			entries.get(index).setTotal(currentOpeningBalance + credit - debit );
		}
	}
	
	
	private Double getOpeningBalance(GetLedgerEntryRequest req, Ledger ledger ) {
		
		
		Double openingBalance = ledger.getOpeningBalance();
		Date ledgerStartDate =  ledger.getStartDate(); 
		
		Date oneDayBeforeFromDate = DateUtils.getNDaysBefore(req.getFrom(), 1);
		
		
		List<LedgerEntry>  entries = entryRepository.findAllByClientIdAndCreditorIdAndLedgerIdAndDateBetween(req.getClientId(), 
				  																							 req.getCreditorId(), 
				  																							 req.getLedgerId(),
				  																							 ledgerStartDate, 
				  																							 oneDayBeforeFromDate);
		for(int index = 0; index  <entries.size(); index++) {
			Double credit = entries.get(index).getCredit() != null ? entries.get(index).getCredit() : 0.0;
			Double debit = entries.get(index).getDebit() != null ? entries.get(index).getDebit() : 0.0;
			openingBalance = openingBalance + credit - debit;
		}
		
		return openingBalance;
	}
	

	
	
	public void updateTotalBalanceForCredit(List<LedgerEntryDTO> entries) {
		for(int index = 0; index<entries.size(); index++) {
			if(index==0) {
				entries.get(0).setTotal(entries.get(0).getCredit());
				continue;
			}
			
			entries.get(index).setTotal(entries.get(index).getCredit() + entries.get(index-1).getTotal());
		}
	}
	
	
	public void updateTotalBalanceForDebit(List<LedgerEntryDTO> entries) {
		for(int index = 0; index<entries.size(); index++) {
			if(index==0) {
				entries.get(0).setTotal(entries.get(0).getDebit());
				continue;
			}
			
			entries.get(index).setTotal(entries.get(index).getDebit() + entries.get(index-1).getDebit());
		}
	}
	
	
	public void updateNarration(List<LedgerEntryDTO> all) {
		
		all.forEach(entry ->{
			if(EEntryType.CREDIT.equals(entry.getEntryType())) {
				
				entry.setNarration("[" + entry.getReceipt() + "]-[" + entry.getVehicle() + "]");
				
			} else if(EEntryType.DEBIT.equals(entry.getEntryType())) { 
				String narration = entry.getPaymentMode() + ((Strings.isNotNullOrEmpty(entry.getPaymentRefNo()) ? "-" + entry.getPaymentRefNo() : ""));
				entry.setItem(narration + "-["+entry.getRemark()+"]");
			}
		});
	}
	
	
	
	public Boolean postCreditEntries(List<LedgerEntryDTO> entries) {
		
		if(Collections.isNullOrEmpty(entries))
			throw new RuntimeException("empty request");
		
		
		for(LedgerEntryDTO entry: entries) {
			
			if(isNull(entry.getClientId()) || isNull(entry.getCreditorId()) || isNull(entry.getLedgerId()) 
							|| isNull(entry.getDate())) {
				throw new RuntimeException("Invalid Request");
			}
			
			
			if(EEntryType.CREDIT.equals(entry.getEntryType())) {
				if(isNull(entry.getProjectId()) ||  Strings.isNullOrEmpty(entry.getReceipt()) 
						|| Strings.isNullOrEmpty(entry.getItem()) || isNull(entry.getRate()) 
						|| isNull(entry.getQuantity()) || entry.getQuantity() == 0.0
						|| isNull(entry.getCredit()) || entry.getCredit() ==0.0 
						|| Strings.isNullOrEmpty(entry.getUnit())) {
					
					throw new RuntimeException("Invalid Post Request");
				}
				
				
				Optional<LedgerEntry>	optional  =  entryRepository.findByDateAndClientIdAndCreditorIdAndReceipt(entry.getDate(), 
																												 entry.getClientId(),
																												 entry.getCreditorId(),
																												 entry.getReceipt());
				if(optional.isPresent()) {
					LedgerEntry old = optional.get();
					if(!entry.getId().equals(old.getId()))
						throw new RuntimeException("Dublicate Entry Found for Receipt: "+ old.getReceipt() + ", Date-" + old.getDate() + ", Item:- " + old.getItem() + ", QTY:- " + old.getQuantity());
				}

			} else if(EEntryType.DEBIT.equals(entry.getEntryType())) {
				if(isNull(entry.getDebit()) || Strings.isNullOrEmpty(entry.getPaymentMode()) 
											|| Strings.isNullOrEmpty(entry.getRemark())) {
					
					throw new RuntimeException("empty request");
				}
			}
		}
		
		List<LedgerEntry> allEntries = ObjectMapperUtil.mapAll(entries, LedgerEntry.class);
		
		try {
			entryRepository.saveAll(allEntries);
		}catch(Exception e) {
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
	
	 
	public LedgerEntryDTO validateByChallan(LedgerEntryDTO request) {
		
		if(isNull(request.getClientId()) || request.getDate() == null || isNull(request.getCreditorId()) || isNull(request.getReceipt()))
			throw new RuntimeException("Invalid Request");
		
		Optional<LedgerEntry>	optional  = entryRepository.findByDateAndClientIdAndCreditorIdAndReceipt(request.getDate(), 
																	request.getClientId(),
																	request.getCreditorId(),
																	request.getReceipt());
		
		LedgerEntryDTO entry = new LedgerEntryDTO();
		if(!optional.isPresent()) {
			List<LedgerEntry> entries =  entryRepository.findByClientIdAndCreditorIdAndReceipt(request.getClientId(),
																								request.getCreditorId(),
																								request.getReceipt());
			if(Collections.isNullOrEmpty(entries))
				return entry ;
			else
				entry = ObjectMapperUtil.map(entries.get(0), LedgerEntryDTO.class);
			
		} else {
			entry = ObjectMapperUtil.map(optional.get(), LedgerEntryDTO.class);
		}
		
		return entry;
	}
	
	
	public List<LedgerEntryDTO> getCreditorPayments(Long clientId, Long creditorId) {
		
		List<LedgerEntryDTO> entries  = new ArrayList<LedgerEntryDTO>();
		
		Optional<Creditor> optional =  creditorRepository.findByClientIdAndId(clientId, creditorId);
		if(!optional.isPresent())
			throw new RuntimeException("Invalid Request, Creditor Not Found");
		
		Long partyId = optional.get().getPartyId();
		List<CreditorPaymentLinkage> paymentsLinkage = 
				paymentLinkageRepository.findByClientIdAndPartyIdAndCreditorIdAndStatus(clientId, 
																						partyId,
																						creditorId, 
																						"CREATED");
		if(Collections.isNullOrEmpty(paymentsLinkage))
			return entries; // return no payments
		
		PaymentService2 paymentService = ServiceLocator.getService(PaymentService2.class);
		
		paymentsLinkage.forEach(linkage ->{
			PaymentSummaryDTO payment =  paymentService.getPaymentSummary(clientId, linkage.getPaymentId());
			LedgerEntryDTO debitEntry = new LedgerEntryDTO();
			debitEntry.setClientId(clientId);
			debitEntry.setCreditorId(creditorId);
			debitEntry.setLedgerId(null); // To be set on UI
			debitEntry.setEntryType(EEntryType.DEBIT);
			debitEntry.setDate(payment.getPaymentDate());
			debitEntry.setPaymentMode(payment.getMode());
			debitEntry.setPaymentRefNo(payment.getTransactionRef());
			debitEntry.setPaymentId(payment.getPaymentId());
			debitEntry.setDebit(Double.valueOf(payment.getAmount()));
			debitEntry.setRemark(payment.getRemark());
			
			debitEntry.setTempId(clientId + "#" + linkage.getId());
			debitEntry.setStatus("CREATED");
			
			entries.add(debitEntry);
		});
		
		return entries;
	}
	
	
	public Boolean updatePaymentEntries(LedgerEntryDTO entry) {
		
		LedgerEntry entity = ObjectMapperUtil.map(entry, LedgerEntry.class);
		if("ACCEPTED".equals(entry.getStatus()))
			entryRepository.save(entity);
		String[] literals = entry.getTempId().split("#");
		
		Long clientId = Long.parseLong(literals[0]);
		Long linkageId = Long.parseLong(literals[1]);
		
		CreditorPaymentLinkage linkage = paymentLinkageRepository.findByClientIdAndId(clientId, linkageId);
		linkage.setStatus(entry.getStatus());
		paymentLinkageRepository.save(linkage);
		
		return Boolean.TRUE;
	}
	
	
	public CapitalAccountDTO createOrUpdate(CapitalAccountDTO account) {
		
		if(isNull(account.getClientId()) || Strings.isNullOrEmpty(account.getAccountName()) 
				|| Strings.isNullOrEmpty(account.getAccountType())) {
			throw new RuntimeException("Invalid Request");
			
		}
		
		if(isNull(account.getLastUpdated()))
			account.setLastUpdated(new Date());
		
		if("CASH".equalsIgnoreCase(account.getAccountType()) && isNull(account.getBalance()))
			throw new RuntimeException("Balance can not be null");
		
		else if("BANK ACCOUNT".equalsIgnoreCase(account.getAccountType()))
			account.setBalance(-1d);
			
		
		CapitalAccount entity = ObjectMapperUtil.map(account, CapitalAccount.class);
		entity = capitalAccountRepository.save(entity);
		
		CapitalAccountDTO savedAccount = ObjectMapperUtil.map(entity, CapitalAccountDTO.class);
		
		if(isNull(account.getId()) && "CASH".equalsIgnoreCase(account.getAccountType()))
			this.updateFirstCapitalAccountEntry(savedAccount);
		
		return savedAccount;
	}
	
	/**
	 * Call this method to make the first entry in the 
	 * CapitalAccountEntry as soon as the new CapitalAccount is created
	 *
	 * @param dto
	 */
	private void updateFirstCapitalAccountEntry(CapitalAccountDTO account) {
		CapitalAccountEntry entry = new CapitalAccountEntry();
		entry.setClientId(account.getClientId());
		entry.setAccountId(account.getId());
		entry.setDate(account.getLastUpdated());
		entry.setNote("New Account Opening Balance");
		entry.setDebit(0d);
		entry.setCredit(account.getBalance());
		entry.setBalance(account.getBalance());
		entry.setEntryType(EEntryType.CREDIT);
		entry.setTransactionRefNo(null);
		
		capitalAccountEntryRepository.save(entry);
	}
	
	
	
	public ListDTO getAllCapitalAccountList(Long clientId){
		if(isNull(clientId))
			throw new RuntimeException("Invalid Request, Client Id is NULL");
		
		ListDTO list = new ListDTO();
		list.setListName("CapitalAccount");
		
		List<CapitalAccount> capitals =  capitalAccountRepository.findAllByClientId(clientId);
		
		if(Collections.isNullOrEmpty(capitals)) 
			new ArrayList<CapitalAccountDTO>();
		
//		List<CapitalAccountDTO> accounts = ObjectMapperUtil.mapAll(dbResult, CapitalAccountDTO.class);
		
		capitals.forEach(c ->  {
			Item item  = new Item();
			item.setLabel(c.getAccountName());
			item.setValue(c.getId());
			list.getList().add(item);
		});
		
		ListDTO.sortByLevel(list);
		
		return list;
	}
	
	
	public List<CapitalAccountDTO> getAllCapitalAccounts(Long clientId){
		if(isNull(clientId))
			throw new RuntimeException("Invalid Request, Client Id is NULL");
		
		List<CapitalAccount> capitals =  capitalAccountRepository.findAllByClientId(clientId);
		
		if(Collections.isNullOrEmpty(capitals)) 
			new ArrayList<CapitalAccountDTO>();
		
		List<CapitalAccountDTO> accounts = ObjectMapperUtil.mapAll(capitals, CapitalAccountDTO.class);
		
		return accounts;
	}
	
	
	public VoucherDTO createVuocher(VoucherDTO dto) {
		
		if(isNull(dto.getClientId()) || Strings.isNullOrEmpty(dto.getVoucherNo())
				|| isNull(dto.getDate()) || Strings.isNullOrEmpty(dto.getItems()) 
				|| isNull(dto.getAmount())
				|| isNull(dto.getProjectId()) || isNull(dto.getCapitalAccountId())) {
			throw new RuntimeException("Invalid Request");
		}
		
		CapitalAccount account =  capitalAccountRepository.findByClientIdAndId(dto.getClientId(), dto.getCapitalAccountId()).orElseThrow(() -> new RuntimeException("invalid request"));
		
		Voucher voucher = ObjectMapperUtil.map(dto, Voucher.class);
		voucher = voucherRepository.save(voucher);
		
		VoucherDTO voucherDTO = ObjectMapperUtil.map(voucher, VoucherDTO.class);
		
		//Block to update capital account
		{
			CapitalAccountEntry entry = new CapitalAccountEntry();
			entry.setClientId(dto.getClientId());
			entry.setAccountId(dto.getCapitalAccountId());
			entry.setDate(dto.getDate());
			entry.setNote(dto.getVoucherNo());
			entry.setDebit(dto.getAmount());
			entry.setCredit(0d);
			entry.setBalance(account.getBalance() - voucher.getAmount());
			entry.setEntryType(EEntryType.DEBIT);
			entry.setTransactionRefNo(voucher.getId());
			
			capitalAccountEntryRepository.save(entry);
		}
		
		
		return voucherDTO;
	}
}
