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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jmsc.app.common.dto.PermisssionsDTO;
import com.jmsc.app.common.dto.UserPermissionsDTO;
import com.jmsc.app.service.UserPermissionService;

import io.swagger.annotations.Api;

/**
 * @author anuhr
 *
 */
@RestController
@RequestMapping("/v1/userpermissions")
@Api(value = "APIs to handle site's data")
public class UserPermissionsEndPoint {
	
	@Autowired
	private UserPermissionService service;

	@PostMapping("/create_or_update")
	public ResponseEntity<Boolean> createOrUpdate(@RequestBody UserPermissionsDTO dto)throws Throwable{
		Boolean status = service.createOrUpdate(dto);
		return ResponseEntity.ok(status);
	}
	
	
	@GetMapping("/get/{client_id}/{user_id}")
	public ResponseEntity<PermisssionsDTO> deleteuser(@PathVariable("client_id")Long clientId,
												 @PathVariable("user_id")Long userId) {
		PermisssionsDTO permissions = service.getPermission(clientId, userId);
		return ResponseEntity.ok(permissions);
	}

}
