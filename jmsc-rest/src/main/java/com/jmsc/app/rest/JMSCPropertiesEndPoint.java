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

import com.jmsc.app.service.JMSCPropertiesService;

import io.swagger.annotations.Api;

/**
 * @author Chandan
 *
 */

@RestController
@RequestMapping("/v1/jmsc_properties")
@Api(value = "APIs to handle jmsc properties")
public class JMSCPropertiesEndPoint {
	
	@Autowired
	private JMSCPropertiesService service;
	
	@GetMapping("/banks")
	ResponseEntity<List<String>> getBanks(){
		List<String> banks = service.getBanks();
		return ResponseEntity.ok(banks);
	}
	
	
	@GetMapping("/branches")
	ResponseEntity<List<String>> getBranches(){
		List<String> branches = service.getBanksBranch();
		return ResponseEntity.ok(branches);
	}
	
	@GetMapping("/post_office")
	ResponseEntity<List<String>> getPostOffice(){
		List<String> pos = service.getPostOffice();
		return ResponseEntity.ok(pos);
	}
}
