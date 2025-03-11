/**
 * 
 */
package com.jmsc.app.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.jmsc.app.common.dto.BankGuaranteeDTO;
import com.jmsc.app.common.enums.EBankGuaranteeStatus;
import com.jmsc.app.common.enums.ENotificationType;
import com.jmsc.app.common.exception.ResourceNotFoundException;
import com.jmsc.app.common.rqrs.File;
import com.jmsc.app.common.util.Collections;
import com.jmsc.app.common.util.DateUtils;
import com.jmsc.app.common.util.ObjectMapperUtil;
import com.jmsc.app.common.util.Strings;
import com.jmsc.app.config.jmsc.ServiceLocator;
import com.jmsc.app.entity.BankGuarantee;
import com.jmsc.app.entity.BankGuaranteeInterface;
import com.jmsc.app.entity.Client;
import com.jmsc.app.entity.Notification;
import com.jmsc.app.repository.BankGuaranteeRepository;
import com.jmsc.app.repository.ClientRepository;
import com.jmsc.app.repository.NotificationRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author anuhr
 *
 */
@Slf4j
@Service
public class BankGuaranteeService extends AbstractService{
	
	@Autowired
	private BankGuaranteeRepository repository;
	
	
	public BankGuaranteeDTO createOrUpdate(BankGuaranteeDTO dto) {
		
		if(dto.getClientId() == null) {
			throw new RuntimeException("Insufficient Data");
		}
		
		if(Strings.isNullOrEmpty(dto.getBgNumber())) {
			throw new RuntimeException("BG Account number not found");
		}
		
		if(Strings.isNullOrEmpty(dto.getInFavourOf())) {
			throw new RuntimeException("Beneficiary  number not found");
		}
		
		
		if(this.isCreate(dto.getId())) {
			Optional<BankGuarantee> optional = repository.findByClientIdAndBgNumber(dto.getClientId(), dto.getBgNumber());
			if(optional.isPresent()) {
				throw new RuntimeException("BG Account already exist with");
			}
		}
		
		
		BankGuarantee entity= ObjectMapperUtil.map(dto, BankGuarantee.class);
		
		if(DateUtils.isBeforeDay(entity.getValidTo(), new Date()))
			entity.setStatus(EBankGuaranteeStatus.EXPIRED);
		
	
		if(this.isUpdate(dto.getId())) {
			Optional<BankGuarantee> optional = repository.findByClientIdAndId(dto.getClientId(), dto.getId());
			if(optional.isPresent()) {
				entity.setFile(optional.get().getFile());
			}
		}
		
		entity = repository.save(entity);
		dto.setId(entity.getId());
		
		return dto;
	}
	
	
	
	public List<BankGuaranteeDTO> getAllBankGuarantee(Long clientId){
		if(clientId == null) {
			throw new RuntimeException("Insufficient Data");
		}
		List<BankGuaranteeInterface> list = repository.findByClientId(clientId);
		
		if(Collections.isNullOrEmpty(list))
			return new ArrayList<BankGuaranteeDTO>();
		
		List<BankGuaranteeDTO> reponse = ObjectMapperUtil.mapAll(list, BankGuaranteeDTO.class);
		
		java.util.Collections.sort(reponse, new Comparator<BankGuaranteeDTO>() {
	        public int compare(BankGuaranteeDTO bg1, BankGuaranteeDTO bg2) {
	            return bg2.getCreatedTimestamp().compareTo(bg1.getCreatedTimestamp());
	        }
	    });
		return reponse;
		
	}
	
	
	
	
	public BankGuaranteeDTO getBankGuarantee(Long clientId, Long id) {
		if(clientId == null) {
			throw new RuntimeException("Insufficient Data");
		}
		
		Optional<BankGuarantee> optional = repository.findByClientIdAndId(clientId, id);
		if(!optional.isPresent())
			throw new ResourceNotFoundException("Bank guarantee does not exist");
		
		BankGuaranteeDTO bg = ObjectMapperUtil.map(optional.get(), BankGuaranteeDTO.class);
		return bg;
	}
	
	
	public Integer deleteBankGuarantee(Long clientId, Long id) {
		if(clientId == null) {
			throw new RuntimeException("Insufficient Data");
		}
		
		Optional<BankGuarantee> optional = repository.findByClientIdAndId(clientId, id);
		if(!optional.isPresent())
			throw new ResourceNotFoundException("Bank guarantee does not exist");
		
		repository.delete(optional.get());
		return 0;
	}
	
	
	public BankGuaranteeDTO upload(MultipartFile upcomingFile, Long clientId, Long fileId) throws Exception {
		
		BankGuaranteeDTO bg = null;
		
	    if (upcomingFile.isEmpty())
            throw new IllegalStateException("Cannot upload empty file");
		
	    try {   
			Optional<BankGuarantee> optional = repository.findByClientIdAndId(clientId, fileId);
			if(optional.isPresent()) {

				BankGuarantee bankGuarantee = optional.get();
				bankGuarantee.setFile(upcomingFile.getBytes());
				bankGuarantee.setFileName(StringUtils.cleanPath(upcomingFile.getOriginalFilename()));
				bankGuarantee.setContentType(upcomingFile.getContentType());
				bankGuarantee.setFileAttached(true);
				
				repository.save(bankGuarantee);
				
				bg = ObjectMapperUtil.map(bankGuarantee, BankGuaranteeDTO.class);
				
			} else {
				throw new ResourceNotFoundException("Selected file does not exist in system.");
			}
			
	     } catch (java.io.IOException ex) {
	    	ex.printStackTrace();
	     }
	    return bg;
	 }
	
	
	
	public File download(Long clientId, Long fileId)throws Exception {
		Optional<BankGuarantee> optional = repository.findByClientIdAndId(clientId, fileId);
		if(optional.isPresent()) {

			BankGuarantee bankGuarantee = optional.get();
			
			File file= new File(bankGuarantee.getFile(),
								bankGuarantee.getFileName(),
								bankGuarantee.getContentType());
			
			return file;
		} else {
			throw new ResourceNotFoundException("Selected file does not exist in system.");
		}
	}
	
	
	public void markBGExpired() {
		NotificationRepository notificationRepo =  ServiceLocator.getService(NotificationRepository.class);
		
		ClientRepository clientRepository =  ServiceLocator.getService(ClientRepository.class);
		
		List<Client> clients = clientRepository.findAll();
		
		if(!Collections.isNullOrEmpty(clients)) {
			for(Client client: clients) {
				List<BankGuaranteeInterface> list = repository.findByClientId(client.getId());
				if(Collections.isNullOrEmpty(list))
					continue;
				Optional<Notification> optional =  notificationRepo.findByClientIdAndType(client.getId(), ENotificationType.BANK_GUARANTEE);
				Notification notification = null;
				if(optional.isPresent()) {
					notification  = optional.get();
					Date lastUpdated = notification.getUpdatedTimestamp();
					if(DateUtils.isToday(lastUpdated)) {
						log.debug("Bank Guarantee Expiry evaluation already done for the day for the client {}", client.getId());
						continue;
					} else {
						log.debug("Starting Bank Guarantee Expiry Evaluation");
					}
				}
				
				for(BankGuaranteeInterface bgi: list) {
					Optional<BankGuarantee> bg = repository.findByClientIdAndId(client.getId(), bgi.getId());
					if(bg.isPresent() && bg.get().getStatus().equals(EBankGuaranteeStatus.ACTIVE)) {
						Date validToDate = bg.get().getValidTo();
						if(DateUtils.isBeforeDay(validToDate, new Date())) {
							bg.get().setStatus(EBankGuaranteeStatus.EXPIRED);
							repository.save(bg.get());
						}
					}
				}

				if(notification == null) {
					notification = new Notification();
					notification.setClientId(client.getId());
					notification.setType(ENotificationType.BANK_GUARANTEE);
				}
				notification.setUpdatedTimestamp(null);
				notificationRepo.save(notification);
				
			}
		}		
	}
}
