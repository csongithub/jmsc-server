/**
 * 
 */
package com.jmsc.app.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jmsc.app.common.dto.BgGroupDTO;
import com.jmsc.app.common.rqrs.ManageBankGuaranteeRequest;
import com.jmsc.app.common.rqrs.ManagerBGGroupDepositRequest;
import com.jmsc.app.common.wrapper.BgGroupWrapper;
import com.jmsc.app.service.BgGroupService;

import io.swagger.annotations.Api;

/**
 * @author Chandan
 *
 */
@RestController
@RequestMapping("/v1/bg_group")
@Api(value = "APIs to handle BG Group")
public class BgGroupEndPoint {
	
	
	@Autowired
	private BgGroupService service;

	@PostMapping("/create")
	 public ResponseEntity<BgGroupDTO> create(@RequestBody BgGroupDTO request)throws Throwable {
		BgGroupDTO response = service.createOrUpdateBgGroup(request);
		return ResponseEntity.ok(response);
	 }
	
	
	
	@GetMapping("/all/{clientId}")
	 public ResponseEntity<List<BgGroupDTO>> all(@PathVariable("clientId") Long clientId)throws Throwable {
		List<BgGroupDTO> response = service.getAllGroups(clientId);
		return ResponseEntity.ok(response);
	 }
	
	
	
	@GetMapping("/group_details/{client_id}/{group_id}")
	 public ResponseEntity<BgGroupWrapper> groupDetails(@PathVariable("client_id") Long clientId, @PathVariable("group_id") Long groupId)throws Throwable {
		BgGroupWrapper response = service.getGroupDetails(clientId, groupId);
		return ResponseEntity.ok(response);
	 }
	
	
	
	@PostMapping("/manage_fix_deposits_in_bg")
	 public ResponseEntity<BgGroupDTO> manageDeposits(@RequestBody ManagerBGGroupDepositRequest request)throws Throwable {
		BgGroupDTO response = service.manageDeposits(request);
		return ResponseEntity.ok(response);
	 }
	
	
	
	@PostMapping("/manage_bank_guarantees_in_bg")
	 public ResponseEntity<BgGroupDTO> manageBankGuarantees(@RequestBody ManageBankGuaranteeRequest request)throws Throwable {
		BgGroupDTO response = service.manageBankGuarantees(request);
		return ResponseEntity.ok(response);
	 }
}
