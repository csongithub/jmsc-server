/**
 * 
 */
package com.jmsc.app.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jmsc.app.common.dto.MachineDTO;
import com.jmsc.app.service.MachineService;

import io.swagger.annotations.Api;

/**
 * @author Chandan
 *
 */
@RestController
@RequestMapping("/v1/machine")
@Api(value = "APIs to handle machine data")
public class MachineEndPoint {
	
	@Autowired
	private MachineService service;
	
	@PostMapping("/add")
	public ResponseEntity<MachineDTO> addParty(@RequestBody MachineDTO machineDTO){
		MachineDTO machine = service.addMachine(machineDTO);
		return ResponseEntity.ok(machine);
	}
	
	
	@GetMapping("/all/{client_id}")
	public ResponseEntity<List<MachineDTO>> getAll(@PathVariable("client_id") Long clientId){
		List<MachineDTO> all = service.getAllMachines(clientId);
		return ResponseEntity.ok(all);
	}
	
	
	@DeleteMapping("/delete/{client_id}/{machine_id}")
	public ResponseEntity<Integer> deleteMachine(@PathVariable("client_id")Long clientId,
												 @PathVariable("machine_id")Long machineId
												) {
		Integer statusCode = service.deleteMachine(clientId, machineId);
		return ResponseEntity.ok(statusCode);
	}
}
