/**
 * 
 */
package com.jmsc.app.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jmsc.app.common.dto.DirectAccessCardDTO;
import com.jmsc.app.service.HomeService;

import io.swagger.annotations.Api;

/**
 * @author Chandan
 *
 */
@RestController
@RequestMapping("/v1/home")
@Api(value = "Home Apis")
public class JMSCHomeEndPoint {
	
	
	@Autowired
	private HomeService service;
	
	@GetMapping("/access_cards")
	public  ResponseEntity<List<List<DirectAccessCardDTO>>> directAccess() throws Throwable{
		
		return ResponseEntity.ok(service.directAccess());
		
	}
}
