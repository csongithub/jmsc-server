/**
 * 
 */
package com.jmsc.app.service.scheduler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.mail.MessagingException;

import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.jmsc.app.common.dto.ClientDTO;
import com.jmsc.app.common.dto.CreditFacilityDTO;
import com.jmsc.app.common.enums.EFacility;
import com.jmsc.app.common.enums.EFacilityStatus;
import com.jmsc.app.common.enums.ENotificationType;
import com.jmsc.app.common.util.DateUtils;
import com.jmsc.app.common.util.Strings;
import com.jmsc.app.config.jmsc.JmscProperties;
import com.jmsc.app.config.jmsc.ServiceLocator;
import com.jmsc.app.entity.CreditFacility;
import com.jmsc.app.entity.Notification;
import com.jmsc.app.repository.CreditFacilityRepository;
import com.jmsc.app.repository.NotificationRepository;
import com.jmsc.app.service.ClientService;
import com.jmsc.app.service.CreditFacilityService;
import com.jmsc.app.service.email.Body;
import com.jmsc.app.service.email.Envelop;
import com.jmsc.app.service.email.JMSCEMailServices;
import com.jmsc.app.service.resource.JMSCResource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Chandan
 *
 */
@Configuration
@EnableScheduling
@Slf4j
public class CreditFacilityScheduler {
	
	
	private final long CREDIT_FACILITY_EXPIRY_INTERVAL = 30 * 60000; //At Every 30 Minutes
	
	private final long CREDIT_FACILITY_EXPIRY_UPDATE_INTERVAL = 30 * 60000;
	
	private final String CREDIT_FACILITY_EXPIRY_TEMPLATE_RESOURCE = "classpath:cf_expiry_alert_template.html";
	
	@Autowired
	private CreditFacilityService cfService;
	
	@Autowired
	private JMSCEMailServices emailService;
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private NotificationRepository notificationRepo;
	
	@Autowired
	private JMSCResource resource;
	
	private Map<Long, ClientDTO> clientMap = new HashMap<Long, ClientDTO>();
	
	
	//TODO: Uncomment Below @Scheduled annotation line to start sending mail
	//Commented this because google mail stopped working on gsmtp
	
	//@Scheduled(fixedRate = CREDIT_FACILITY_EXPIRY_INTERVAL)
	public void alertCreditFacilityExpiry() {
		JmscProperties properties = ServiceLocator.getService(JmscProperties.class);
		Long alertBeforeDays = properties.getCreditFacilityExpiryAlertDays();
		
		Map<Long, List<CreditFacilityDTO>> expiringCf = cfService.evaluateExpiry(alertBeforeDays);
		Set<Long> clients = expiringCf.keySet();
		for(Long clientId: clients) {
			
			//Check if this has been already alerted today, if yes then do not alert again today
			Optional<Notification> optional =  notificationRepo.findByClientIdAndType(clientId, ENotificationType.CF_EXPRY_ALERT);
			Notification notification = null;
			if(optional.isPresent()) {
				notification  = optional.get();
				Date lastUpdated = notification.getUpdatedTimestamp();
				if(DateUtils.isToday(lastUpdated)) {
					log.debug("Skiping this alert, since it has been already sent today");
					continue;
				}
			}
			
			String recipients = null;
			if(clientMap.containsKey(clientId)) {
				recipients = clientMap.get(clientId).getRecipients();
			}else {
				ClientDTO clientDTO = clientService.getClientById(clientId);
				recipients = clientDTO.getRecipients();
				clientMap.put(clientId, clientDTO);
			}
			
			if(Strings.isNullOrEmpty(recipients)) {
				continue;
			}
			
			List<CreditFacilityDTO> list = expiringCf.get(clientId);
			clientService.getClientById(clientId);
			
			for(CreditFacilityDTO cf: list) {
				
				VelocityContext context = new VelocityContext();
				context.put("ACCOUNT_NUMBER", cf.getAccountNumber());
				context.put("VALUE", cf.getAmount());
				context.put("OPEN_DATE", cf.getOpenDate());
				context.put("MATURITY_DATE", cf.getMaturityDate());
				context.put("TYPE", this.getFacilityType(cf.getFacilityType()));
				context.put("ISSUER", cf.getIssuerName());
				context.put("BRANCH", cf.getIssuerBranch());
				context.put("MESSAGE", cf.getAlertMessage());
				
				String message = resource.readAsString(CREDIT_FACILITY_EXPIRY_TEMPLATE_RESOURCE, context);
				
				Envelop env = new Envelop();
				env.setRecipients(recipients);
				env.setSubject("EXPIRY-ALERT: Credit Facility " + cf.getAccountNumber());
				Body body = new Body();
				body.setMessage(message.toString());
				env.setBody(body);
				try {
					emailService.sendMail(env);
					if(notification == null) {
						notification = new Notification();
						notification.setClientId(clientId);
						notification.setType(ENotificationType.CF_EXPRY_ALERT);
					}
					notification.setUpdatedTimestamp(null);
					notificationRepo.save(notification);
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	
	/**
	 * This schedule marks all expired credit facilities in the table
	 */
	@Scheduled(fixedRate = CREDIT_FACILITY_EXPIRY_UPDATE_INTERVAL)
	public void markFacilityExpired() {
		
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
						log.debug("Credit Facility Expiry Evaluation Already Done For the Day");
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
	
	
	
	private String getFacilityType(EFacility type) {
		if(EFacility.BG.equals(type)) {
			return "Bank Guarantee (BG)";
		} else if (EFacility.FD.equals(type)){
			return "Fix Deposit (FD)";
		}else if (EFacility.NSC.equals(type)){
			return "National Saving Certificate (NSC)";
		}
		return "";
	}
}
