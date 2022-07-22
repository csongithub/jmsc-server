/**
 * 
 */
package com.jmsc.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmsc.app.common.dto.MachineDTO;
import com.jmsc.app.common.util.Collections;
import com.jmsc.app.common.util.ObjectMapperUtil;
import com.jmsc.app.common.util.Strings;
import com.jmsc.app.entity.Machine;
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
	
}
