/**
 * 
 */
package com.jmsc.app.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

	
	
	@PostMapping("/backup")
	public ResponseEntity<PostgresBackup> backup() throws Throwable{
		PostgresBackup response = service.startBackup("auto");
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/start_data_backup")
	public ResponseEntity<Boolean> startDataBackup() throws Throwable{
		Boolean response = service.doBackup();
		return ResponseEntity.ok(response);
	}
	
	
	@GetMapping("/backup_status")
	public ResponseEntity<Boolean> backupStatus() throws Throwable{
		Boolean response = service.checkBackupStatus();
		return ResponseEntity.ok(response);
	}
}
