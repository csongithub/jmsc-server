/**
 * 
 */
package com.jmsc.app.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jmsc.app.common.dto.ClientDTO;
import com.jmsc.app.common.dto.UserDTO;
import com.jmsc.app.common.rqrs.UpdateClientBasicInfoRequest;
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
	public ResponseEntity<ClientDTO> addClient(@RequestBody ClientDTO clientDTO) throws Throwable {
		ClientDTO client = service.addClient(clientDTO);
		return ResponseEntity.ok(client);
	}
	
	
	@GetMapping("/getClient/{logonId}")
	public ResponseEntity<ClientDTO> getClient(@PathVariable("logonId") String logonId){
		ClientDTO client = service.getClient(logonId);
		return ResponseEntity.ok(client);
	}
	
	
	@PostMapping("/block/{logonId}")
	public ResponseEntity<String> block(@PathVariable("logonId") String logonId, @RequestHeader("Authorization") String authorization){
		service.blockClient(logonId, authorization);
		return ResponseEntity.ok("Client has been blocked");
	}
	
	
	@PostMapping("/unblock/{logonId}")
	public ResponseEntity<String> makeActive(@PathVariable("logonId") String logonId, @RequestHeader("Authorization") String authorization){
		service.unblockClient(logonId, authorization);
		return ResponseEntity.ok("Client has been unblocked");
	}
	
	@PostMapping("/update_basic_info")
	public ResponseEntity<ClientDTO> updateBasicInfo(@RequestBody UpdateClientBasicInfoRequest request){
		return ResponseEntity.ok(service.updateBasicInfo(request));
	}
}
