/**
 * 
 */
package com.jmsc.app.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jmsc.app.common.dto.ClientDTO;
import com.jmsc.app.service.ClientService;

import io.swagger.annotations.Api;

/**
 * @author Chandan
 *
 */
@RestController
@RequestMapping("/v1/client")
@Api(value = "APIs to clients")
public class ClientEndPoint {
	
	@Autowired
	private ClientService service;

	@PostMapping("/addClient")
	public ResponseEntity<ClientDTO> addBankAccount(@RequestBody ClientDTO clientDTO){
		ClientDTO client = service.addClient(clientDTO);
		return ResponseEntity.ok(client);
	}
}
