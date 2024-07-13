/**
 * 
 */
package com.jmsc.app.service;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmsc.app.common.dto.MachineDTO;
import com.jmsc.app.common.dto.NotificationDTO;
import com.jmsc.app.common.enums.ENotificationEntityType;
import com.jmsc.app.common.exception.ResourceNotFoundException;
import com.jmsc.app.common.util.Collections;
import com.jmsc.app.common.util.ObjectMapperUtil;
import com.jmsc.app.common.util.Strings;
import com.jmsc.app.config.jmsc.JmscProperties;
import com.jmsc.app.config.jmsc.ServiceLocator;
import com.jmsc.app.entity.Client;
import com.jmsc.app.entity.Machine;
import com.jmsc.app.repository.ClientRepository;
import com.jmsc.app.repository.MachineRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Chandan
 *
 */
@Slf4j
@Service
public class MachineService {
	
	@Autowired
	private MachineRepository repository;
	
	
	public MachineDTO addMachine(MachineDTO machineDTO) {
		log.debug("Inside addMachine method");
		
		if(machineDTO.getClientId() == null) {
			throw new RuntimeException("Insufficient Data");
		}
		
		if(Strings.isNullOrEmpty(machineDTO.getName())) {
			throw new RuntimeException("Invalid machine name");
		}
		
		machineDTO.toUppercase();
	
		if(machineDTO.getId() == null) {
			Optional<Machine> optional = repository.findByName(machineDTO.getName());
			if(optional.isPresent()) {
				throw new RuntimeException("Another machine already exists with same Name");
			}
		}
		
		Machine machine = ObjectMapperUtil.map(machineDTO, Machine.class);
		
		Machine savedMachine = repository.save(machine);

		MachineDTO newMachine = ObjectMapperUtil.map(savedMachine, MachineDTO.class);
		
		return newMachine;
	}
	
	
	public List<MachineDTO> getAllMachines(Long clientId){
		List<Machine> all = repository.findByClientId(clientId);
		
		if(Collections.isNullOrEmpty(all)) {
			return new ArrayList<MachineDTO>();
		}
		
		List<MachineDTO> machines = ObjectMapperUtil.mapAll(all, MachineDTO.class);
		
		boolean mask = false;
		if(mask) {
			machines.stream().forEach(m -> {
				m.setChasisNo(Strings.mask(m.getChasisNo(), 4));
				m.setEngineNo(Strings.mask(m.getEngineNo(), 4));
			});
		}
		return machines;
	}
	
	
	public Integer deleteMachine(Long clientId, Long machineId) {
		Optional<Machine> optional = repository.findByClientIdAndId(clientId, machineId);
		
		if(optional.isPresent()) {
			repository.delete(optional.get());
			return 0;
		} else {
			throw new ResourceNotFoundException("Machine not found");
		}
		
	}
	
	public Map<Long, List<NotificationDTO>> evaluateExpiry(){

		Map<Long, List<NotificationDTO>> machineMap = new HashedMap<Long, List<NotificationDTO>>();
		List<Client> clients = ServiceLocator.getService(ClientRepository.class).findAll();
		
		if(!Collections.isNullOrEmpty(clients)) {
			
			for(Client client: clients) {
				List<NotificationDTO> machineList = evaluateExpiryForClient(client.getId());
				if(machineList != null)
					machineMap.put(client.getId(), machineList);
			}
		}
		return machineMap;
	}
	
	
	
	public List<NotificationDTO> evaluateExpiryForClient(Long clientId) {
		
		List<NotificationDTO> machineList = new ArrayList<NotificationDTO>();

		List<Machine> machines = repository.findByClientId(clientId);
		
		if(!Collections.isNullOrEmpty(machines)) {
			for(Machine machine: machines) {
				if(machine.getInsuranceValitUpTo() != null) {
					NotificationDTO insuranceNotification = getNotification(clientId,
																			machine,
																			machine.getInsuranceValitUpTo(),
																			"Insurance");
					if(insuranceNotification != null)
						machineList.add(insuranceNotification);
				}
				
				
				if(machine.getTaxValidUpTo() != null) {
					NotificationDTO insuranceNotification = getNotification(clientId, 
																			machine,
																			machine.getTaxValidUpTo(),
																			"Tax");
					if(insuranceNotification != null)
						machineList.add(insuranceNotification);
				}
				
				
				if(machine.getPermitValidUpTo() != null) {
					NotificationDTO insuranceNotification = getNotification(clientId, 
																			machine,
																			machine.getPermitValidUpTo(),
																			"Permit");
					if(insuranceNotification != null)
						machineList.add(insuranceNotification);
				}
				
				
				if(machine.getPollutionValidUpTo() != null) {
					NotificationDTO insuranceNotification = getNotification(clientId,
																			machine,
																			machine.getPollutionValidUpTo(),
																			"Pollution");
					if(insuranceNotification != null)
						machineList.add(insuranceNotification);
				}
				
				
				if(machine.getFitnessValidUpTo() != null) {
					NotificationDTO fitnessNotification = getNotification(clientId, 
																		  machine,
																		  machine.getFitnessValidUpTo(),
																		  "Fitness");
					if(fitnessNotification != null)
						machineList.add(fitnessNotification);
				}	
			}
		}
		if(!Collections.isNullOrEmpty(machineList)) {
			NotificationService.pushNotifications(clientId, machineList);
			return machineList;
		}
		return machineList;
	}
	
	
	
	private NotificationDTO getNotification(Long clientId, Machine machine, Date expiryDate, String prefix) {		
		Long alertBeforeDays = ServiceLocator.getService(JmscProperties.class).getCreditFacilityExpiryAlertDays();
		
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date todaysDate = Date.from(java.time.LocalDate.now().atStartOfDay(defaultZoneId).toInstant());
		
	    long diffInMillies = expiryDate.getTime() - todaysDate.getTime();
	    long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
	    NotificationDTO notification= null;
	    if(diff <= alertBeforeDays) {
	    	notification = new NotificationDTO();    	
	    	notification.setClientId(clientId);
	    	notification.setEntityId(machine.getId());
	    	notification.setMachine(ObjectMapperUtil.map(machine, MachineDTO.class));
	    	notification.setEntityType(ENotificationEntityType.MACHINE);
	    	
	    	if(diff < 0) {
	    		String message = prefix + " has been expired "  + Math.abs(diff) + " days ago, effectively on " + expiryDate.toString() + ".";
	    		notification.setNotificationMessage(message);
	    	}else {
	    		if(diff == 0) {
	    			String message = prefix + " is expiring today";
	    			notification.setNotificationMessage(message);
	    		}else {
	    			String message = prefix + " would be expiring in next " + Math.abs(diff) + " days, effectively on " + expiryDate.toString() + ".";
	    			notification.setNotificationMessage(message);
	    		}
	    	}
	    }
	    return notification;
	}
}
