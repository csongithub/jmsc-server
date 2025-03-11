package com.jmsc.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.jmsc.app.common.dto.SupplierDTO;
import com.jmsc.app.common.util.ObjectMapperUtil;
import com.jmsc.app.entity.Supplier;
import com.jmsc.app.repository.SupplierRepository;

@Service
public class SupplierService extends AbstractService{
	
	@Autowired
	public SupplierRepository repository;
	
	
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
}
