package com.jmsc.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.jmsc.app.common.dto.PurchageLedgerDTO;
import com.jmsc.app.common.dto.SupplierDTO;
import com.jmsc.app.common.exception.ResourceNotFoundException;
import com.jmsc.app.common.util.ObjectMapperUtil;
import com.jmsc.app.entity.PurchageLedger;
import com.jmsc.app.entity.Supplier;
import com.jmsc.app.repository.PurchageLedgerRepository;
import com.jmsc.app.repository.SupplierRepository;

@Service
public class SupplierService extends AbstractService{
	
	@Autowired
	public SupplierRepository repository;
	
	@Autowired
	public PurchageLedgerRepository ledgerRepository;
	
	
	public SupplierDTO createOrUpdate(SupplierDTO dto) {
		
		if(dto.getClientId() == null || dto.getPartyId() == null) {
			throw new RuntimeException("Invalid Request");
		}
		
		Supplier entity= ObjectMapperUtil.map(dto, Supplier.class);
		Supplier savedEntity = repository.save(entity);
		SupplierDTO savedDTO = ObjectMapperUtil.map(savedEntity, SupplierDTO.class);
		return savedDTO;
	}
	
	
	public List<SupplierDTO> getAllSuuplier(@PathVariable("clientId") Long clientId) {
		if(clientId == null) {
			throw new RuntimeException("Invalid Request");
		}
		
		List<Supplier> all = repository.findAllByClientId(clientId);
		
		List<SupplierDTO> allDto =  ObjectMapperUtil.mapAll(all, SupplierDTO.class);
		
		return allDto;
	}
	
	
	public Integer deleteSupplier(Long clientId, Long id) {
		if(clientId == null) {
			throw new RuntimeException("Insufficient Data");
		}
		
		Optional<Supplier> optional = repository.findByClientIdAndId(clientId, id);
		if(!optional.isPresent())
			throw new ResourceNotFoundException("Bank guarantee does not exist");
		
		repository.delete(optional.get());
		return 0;
	}
	
	
	
	public Boolean isLedgerCreated(Long clientId, Long supplierId) {
		boolean count = ledgerRepository.findFirstRecords(clientId, supplierId);
		
		if(count)
			return Boolean.TRUE;
		else 
			return Boolean.FALSE;
	}
	
	public Boolean createLedger(PurchageLedgerDTO entry) {
		
		PurchageLedger entity = ObjectMapperUtil.map(entry, PurchageLedger.class);
//		entry.setClientId(clientId);
//		entry.setSupplierId(supplierId);
//		entry.setCreditAmount(amount);
//		entry.setRemark("Opening Balance");
		ledgerRepository.save(entity);
		return Boolean.TRUE;
	}
}
