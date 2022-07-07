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

import com.jmsc.app.common.dto.SiteDTO;
import com.jmsc.app.service.SiteService;

import io.swagger.annotations.Api;

/**
 * @author Chandan
 *
 */
@RestController
@RequestMapping("/v1/site")
@Api(value = "APIs to handle site's data")
public class SiteEndPoint {
	
	
	@Autowired
	private SiteService service;
	
	
	@PostMapping("/create")
	public ResponseEntity<SiteDTO> addSite(@RequestBody SiteDTO siteDTO){
		SiteDTO savedAccount = service.createSite(siteDTO);
		return ResponseEntity.ok(savedAccount);
	}
	
	
	@GetMapping("/all/{client_id}")
	public ResponseEntity<List<SiteDTO>> getAll(@PathVariable("client_id") Long clientId){
		List<SiteDTO> all = service.getAllSites(clientId);
		return ResponseEntity.ok(all);
	}
}
