/**
 * 
 */
package com.jmsc.app.service.scheduler;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmsc.app.common.dto.CreditFacilityDTO;
import com.jmsc.app.common.enums.EFacilityStatus;
import com.jmsc.app.common.enums.ENotificationType;
import com.jmsc.app.common.util.DateUtils;
import com.jmsc.app.config.datasource.PostgresConfig;
import com.jmsc.app.config.jmsc.ServiceLocator;
import com.jmsc.app.entity.CreditFacility;
import com.jmsc.app.entity.Notification;
import com.jmsc.app.repository.CreditFacilityRepository;
import com.jmsc.app.repository.NotificationRepository;
import com.jmsc.app.service.BankGuaranteeService;
import com.jmsc.app.service.CreditFacilityService;
import com.jmsc.app.service.MachineService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Chandan
 *
 */
@Service
@Slf4j
public class PostActivationService {
	
	private final long gloab_client_id = 0l;
	
	@Autowired
	private PostgresConfig config;
	
	/**
	 * @throws Throwable 
	 * 
	 */
	public void dataBackup() throws Throwable {
		
		if(!config.isBackupEnabled()) {
			log.debug("Data backup is disabled");
			System.out.println("*************************************************");
			System.out.println("*         Data Backup is Disabled               *");
			System.out.println("*************************************************");
			return;
		}
			
		PostgresService service  = ServiceLocator.getService(PostgresService.class);
		
		NotificationRepository notificationRepo =  ServiceLocator.getService(NotificationRepository.class);
		Optional<Notification> optional =  notificationRepo.findByClientIdAndType(gloab_client_id, ENotificationType.DATA_BACKUP);
		Notification notification = null;
		
		if(optional.isPresent()) {
			notification  = optional.get();
			Date lastUpdated = notification.getUpdatedTimestamp();
			if(DateUtils.isToday(lastUpdated)) {
				log.debug("Data-Backup Already done for the day");
				System.out.println("*************************************************");
				System.out.println("*         Data Backup Already Done              *");
				System.out.println("*************************************************");
				return;
			}
		}
		
		service.startBackup("auto");
		
		if(notification == null) {
			notification = new Notification();
			notification.setClientId(gloab_client_id);
			notification.setType(ENotificationType.DATA_BACKUP);
		}
		notification.setUpdatedTimestamp(null);
		notificationRepo.save(notification);
	}
	
	public void markFacilityExpired() {
		
		CreditFacilityService cfService = ServiceLocator.getService(CreditFacilityService.class);
		NotificationRepository notificationRepo =  ServiceLocator.getService(NotificationRepository.class);
		
		Map<Long, List<CreditFacilityDTO>> expiringCf = cfService.evaluateExpiry(null);
		
		if(expiringCf != null && expiringCf.size() > 0) {
			Set<Long> clients = expiringCf.keySet();
			for(Long clientId: clients) {
				
				//Check if this has been already alerted today, if yes then do not alert again today
				Optional<Notification> optional =  notificationRepo.findByClientIdAndType(clientId, ENotificationType.CF_EXPRY_UPDATE);
				Notification notification = null;
				if(optional.isPresent()) {
					notification  = optional.get();
					Date lastUpdated = notification.getUpdatedTimestamp();
					if(DateUtils.isToday(lastUpdated)) {
						log.debug("Credit-Facility Expiry evaluation already done for the day for the client {}", clientId);
						continue;
					} else {
						log.debug("Starting Credit Facility Expiry Evaluation");
					}
				}
				
				
				List<CreditFacilityDTO> list = expiringCf.get(clientId);
				for(CreditFacilityDTO cfDTO: list) {
					
					CreditFacilityRepository cfRepository = ServiceLocator.getService(CreditFacilityRepository.class);
					Optional<CreditFacility> optionalCF = cfRepository.findByIdAndClientId(cfDTO.getId(), clientId);
					if(optionalCF.isPresent()) {
						CreditFacility cf = optionalCF.get();
						cf.setStatus(EFacilityStatus.EXPIRED);
						cfRepository.save(cf);
					}
				}
				if(notification == null) {
					notification = new Notification();
					notification.setClientId(clientId);
					notification.setType(ENotificationType.CF_EXPRY_UPDATE);
				}
				notification.setUpdatedTimestamp(null);
				notificationRepo.save(notification);
			}
		}
	}
	
	
	
	public void evaluateMachineExpiry() {
		ServiceLocator.getService(MachineService.class).evaluateExpiry();
	}
	
	
	public void markBGExpired() {
		ServiceLocator.getService(BankGuaranteeService.class).markBGExpired();
	}

}
