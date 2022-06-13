/**
 * 
 */
package com.jmsc.app.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jmsc.app.common.rqrs.PostgresBackup;
import com.jmsc.app.service.scheduler.PostgresService;

import io.swagger.annotations.Api;

/**
 * @author Chandan
 *
 */
@RestController
@RequestMapping("/v1/postres")
@Api(value = "APIs to handle postgres operation")
public class PostgresEndPoint {
	
	
	@Autowired
	private PostgresService service;

	
	
	@PostMapping("/create_backup")
	public ResponseEntity<PostgresBackup> backup() throws Throwable{
		PostgresBackup response = service.startBackup();
		return ResponseEntity.ok(response);
	}

}
