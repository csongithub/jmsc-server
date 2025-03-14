/**
 * 
 */
package com.jmsc.app.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.jmsc.app.common.dto.EInvoiceDTO;
import com.jmsc.app.common.dto.TurnOverDTO;
import com.jmsc.app.common.enums.EFyMonths;
import com.jmsc.app.common.exception.ResourceNotFoundException;
import com.jmsc.app.common.rqrs.File;
import com.jmsc.app.common.util.Collections;
import com.jmsc.app.common.util.ObjectMapperUtil;
import com.jmsc.app.common.util.Strings;
import com.jmsc.app.config.jmsc.JmscGeneralConfig;
import com.jmsc.app.entity.EInvoice;
import com.jmsc.app.entity.EInvoiceFiles;
import com.jmsc.app.repository.EInvoiceFilesRepository;
import com.jmsc.app.repository.EInvoiceRepository;

/**
 * @author anuhr
 *
 */
@Service
public class EInvoiceService extends AbstractService{
	
	
	@Autowired
	private EInvoiceRepository repository;
	
	@Autowired
	private EInvoiceFilesRepository filesRepository;
		
	@Autowired
	private JmscGeneralConfig config;
	
	
	public EInvoiceDTO saveOrUpdate(EInvoiceDTO dto) {
		if(dto.getClientId() == null || Strings.isNullOrEmpty(dto.getGstState())
				|| Strings.isNullOrEmpty(dto.getFy()) || dto.getMonth() == null) {
			throw new RuntimeException("Insufficient Data");
		}
		
		EInvoice entity= ObjectMapperUtil.map(dto, EInvoice.class);
		
		entity = repository.save(entity);
		dto.setId(entity.getId());
		
		return dto;
	}
	
	
	public List<EInvoiceDTO> getEInvloices(Long clientId, String gstState, String fy, EFyMonths month) {
		
		List<EInvoiceDTO> results = new ArrayList<EInvoiceDTO>();
		
		if(clientId == null || Strings.isNullOrEmpty(gstState) || Strings.isNullOrEmpty(fy) || month == null) {
			throw new RuntimeException("Insufficient Data");
		}
		
		List<EInvoice> list =  repository.findByClientIdAndGstStateAndFyAndMonth(clientId, gstState, fy, month);
		
		
		
		if(Collections.isNotNullOrEmpty(list)) {
			results = ObjectMapperUtil.mapAll(list, EInvoiceDTO.class);
			
			updateWithFiles(clientId, results);
		}
		
		return results;
	}
	
	
	
	public List<TurnOverDTO> getTurnOver(Long clientId){
		List<TurnOverDTO> turnovers = new ArrayList<TurnOverDTO>();
		List<String> fys = UtilityService.getFyForEInvoice(config.getEinvoiceStartYear());
		fys.forEach(fy -> {
			List<EInvoiceDTO> payments = getEInvoiceByFy(clientId, fy);
			if(Collections.isNotNullOrEmpty(payments)) {
				Double turnover = 0d;
				for(EInvoiceDTO payment: payments){
					turnover = turnover + payment.getGrossAmount();
				}
				String to = UtilityService.getCurrencyFormat(turnover);
				TurnOverDTO turnoverDTO = new TurnOverDTO();
				turnoverDTO.setYear(fy);
				turnoverDTO.setLabel(fy);
				turnoverDTO.setTurnover(to);
				turnoverDTO.setValue(to);
				turnovers.add(turnoverDTO);
			}
		});
		
		return turnovers;
	}
	
	
	
	public String getcurrentFYTurnover(Long clientId){
		String currentFy = UtilityService.getCurrentFinancialYear();
		List<EInvoiceDTO> payments = getEInvoiceByFy(clientId, currentFy);
		Double turnover = 0d;
		if(Collections.isNotNullOrEmpty(payments)) {
			for(EInvoiceDTO payment: payments){
				turnover = turnover + payment.getGrossAmount();
			}
		}
		String to = UtilityService.getCurrencyFormat(turnover);
		return to;
	}
	
	
	
	
	public List<EInvoiceDTO> getEInvoiceByFy(Long clientId,String fy) {
		
		List<EInvoiceDTO> results = new ArrayList<EInvoiceDTO>();
		
		if(clientId == null || Strings.isNullOrEmpty(fy) ) {
			throw new RuntimeException("Insufficient Data");
		}
		
		List<EInvoice> list =  repository.findByClientIdAndFy(clientId, fy);
		
		if(Collections.isNotNullOrEmpty(list)) {
			results = ObjectMapperUtil.mapAll(list, EInvoiceDTO.class);
			
			updateWithFiles(clientId, results);
		}
		
		return results;
	}
	
	
	public List<EInvoiceDTO> getEInvoiceByGstAndFy(Long clientId,String gst, String fy) {
		
		List<EInvoiceDTO> results = new ArrayList<EInvoiceDTO>();
		
		if(clientId == null || Strings.isNullOrEmpty(fy) ) {
			throw new RuntimeException("Insufficient Data");
		}
		
		List<EInvoice> list =  repository.findByClientIdAndGstStateAndFy(clientId, gst, fy);
		
		if(Collections.isNotNullOrEmpty(list)) {
			results = ObjectMapperUtil.mapAll(list, EInvoiceDTO.class);
			
			updateWithFiles(clientId, results);
		}
		
		return results;
	}
	
	
	public List<EInvoiceDTO> getEInvoiceByFyAndMonth(Long clientId, String fy, EFyMonths month) {
		
		List<EInvoiceDTO> results = new ArrayList<EInvoiceDTO>();
		
		if(clientId == null || Strings.isNullOrEmpty(fy) ) {
			throw new RuntimeException("Insufficient Data");
		}
		
		List<EInvoice> list =  repository.findByClientIdAndFyAndMonth(clientId, fy, month);
		
		if(Collections.isNotNullOrEmpty(list)) {
			results = ObjectMapperUtil.mapAll(list, EInvoiceDTO.class);
			updateWithFiles(clientId, results);
		}
		
		return results;
	}
	
	
	private void updateWithFiles(Long clientId, List<EInvoiceDTO> results){
		results.forEach(r-> {
				Optional<EInvoiceFiles> optional = filesRepository.findByClientIdAndInvoiceId(clientId, r.getId());
				if(optional.isPresent()) {
					EInvoiceFiles invoice = optional.get();
					r.setMemoAttached(invoice.isMemoAttached());
					r.setMemoFileName(invoice.getMemoFileName());
					r.setInvoiceAttached(invoice.isInvoiceAttached());
					r.setInvoiceFileName(invoice.getInvoiceFileName());
					
				} else {
					r.setMemoAttached(false);
					r.setInvoiceAttached(false);
				}
			});
		
		
		java.util.Collections.sort(results, new Comparator<EInvoiceDTO>() {
	        public int compare(EInvoiceDTO emp1, EInvoiceDTO emp2) {
	            return emp1.getPaymentDate().compareTo(emp2.getPaymentDate());
	        }
	    });
		
	}
	
	
	
	public Integer deleteEInvoice(Long clientId, Long invoiceId) {
		if(clientId == null) {
			throw new RuntimeException("Insufficient Data");
		}
		
		Optional<EInvoice> optional = repository.findByClientIdAndId(clientId, invoiceId);
		if(!optional.isPresent())
			throw new ResourceNotFoundException("Bank guarantee does not exist");
		
		Optional<EInvoiceFiles> optionalFile = filesRepository.findByClientIdAndInvoiceId(clientId, invoiceId);
		
		if(optionalFile.isPresent()) {
			filesRepository.delete(optionalFile.get());
		}
		
		repository.delete(optional.get());
		return 0;
	}
	
	
	public boolean upload(MultipartFile upcomingFile, Long clientId, Long invoiceId, String whicFile) throws Exception {
		

	    if (upcomingFile.isEmpty())
            throw new IllegalStateException("Cannot upload empty file");
		
	    try {   
	    	Optional<EInvoiceFiles> optional = filesRepository.findByClientIdAndInvoiceId(clientId, invoiceId);
	    	EInvoiceFiles invoiceFile = null;
	    	if(optional.isPresent()) {
	    		invoiceFile = optional.get();		
			} else {
				invoiceFile = new EInvoiceFiles();
				invoiceFile.setClientId(clientId);
				invoiceFile.setInvoiceId(invoiceId);
			}
	    	
	    	if(whicFile.equals("memo")) {
    			invoiceFile.setMemo(upcomingFile.getBytes());
    			invoiceFile.setMemoFileName(StringUtils.cleanPath(upcomingFile.getOriginalFilename()));
    			invoiceFile.setMemoContentType(upcomingFile.getContentType());
    			invoiceFile.setMemoAttached(true);
    		} else if(whicFile.equals("invoice")) {
    			invoiceFile.setInvoice(upcomingFile.getBytes());
    			invoiceFile.setInvoiceFileName(StringUtils.cleanPath(upcomingFile.getOriginalFilename()));
    			invoiceFile.setInvoiceContentType(upcomingFile.getContentType());
    			invoiceFile.setInvoiceAttached(true);
    		}
    		filesRepository.save(invoiceFile);	
			
	     } catch (java.io.IOException ex) {
	    	ex.printStackTrace();
	     }
	    return true;
	 }
	
	
	public File download(Long clientId, Long invoiceId, String whicFile)throws Exception {
		Optional<EInvoiceFiles> optional = filesRepository.findByClientIdAndInvoiceId(clientId, invoiceId);
		if(optional.isPresent()) {
			File file = null;
			EInvoiceFiles invoice = optional.get();
			
			if(whicFile.equals("memo")) {
				file= new File(invoice.getMemo(),
						invoice.getMemoFileName(),
						invoice.getMemoContentType());
			} else if(whicFile.equals("invoice")) {
				file= new File(invoice.getInvoice(),
						invoice.getInvoiceFileName(),
						invoice.getInvoiceContentType());
			}
			return file;
		} else {
			throw new ResourceNotFoundException("No attachements found for this invoice");
		}
	}
	
	
	public boolean deleteFile(Long clientId, Long invoiceId, String whicFile) {
		Optional<EInvoiceFiles> optional = filesRepository.findByClientIdAndInvoiceId(clientId, invoiceId);
		
		if(optional.isPresent()) {
			EInvoiceFiles invoice = optional.get();
			
			if(whicFile.equals("memo")) {
				invoice.setMemo(null);
				invoice.setMemoAttached(false);
				invoice.setMemoFileName(null);
				invoice.setMemoContentType(null);
				
			} else if(whicFile.equals("invoice")) {
				invoice.setInvoice(null);
				invoice.setInvoiceAttached(false);
				invoice.setInvoiceFileName(null);
				invoice.setInvoiceContentType(null);
			}
			filesRepository.save(invoice);
		} else {
			throw new ResourceNotFoundException("No attachements found for this invoice");
		}
		return Boolean.TRUE;
	}
	
	
}
