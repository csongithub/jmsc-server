/**
 * 
 */
package com.jmsc.app.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jmsc.app.common.dto.PartyDTO;
import com.jmsc.app.service.PartyService;

import io.swagger.annotations.Api;

/**
 * @author Chandan
 *
 */
@RestController
@RequestMapping("/v1/party")
@Api(value = "APIs to handle party's data")
public class PartyEndPoint {
	
	@Autowired
	private PartyService service;
	
	@PostMapping("/create")
	public ResponseEntity<PartyDTO> addParty(@RequestBody PartyDTO partyDTO){
		PartyDTO party = service.createParty(partyDTO);
		return ResponseEntity.ok(party);
	}
	
	
	@GetMapping("/all/{client_id}")
	public ResponseEntity<List<PartyDTO>> getAll(@PathVariable("client_id") Long clientId){
		List<PartyDTO> all = service.getAllParties(clientId);
		return ResponseEntity.ok(all);
	}
	
	
	@GetMapping("/{client_id}/{party_id}")
	public ResponseEntity<PartyDTO> getParty(@PathVariable("client_id") Long clientId, @PathVariable("party_id")Long partyId){
		PartyDTO party = service.getPartyById(clientId, partyId);
		return ResponseEntity.ok(party);
	}

}
