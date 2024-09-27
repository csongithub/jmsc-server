/**
 * 
 */
package com.jmsc.app.service;

import org.springframework.stereotype.Service;

import com.jmsc.app.common.dto.EInvoiceDTO;
import com.jmsc.app.repository.EInvoiceRepository;

/**
 * @author anuhr
 *
 */
@Service
public class EInvoiceService {
	
	
	private EInvoiceRepository repository;
	
	
//	public EInvoiceDTO saveOrUpdate(EInvoiceDTO dto) {
//		boolean isCreate = dto.getId() == null ? true : false;
//		
//		if(dto.getClientId() == null) {
//			throw new RuntimeException("Insufficient Data");
//		}
//	}

}
