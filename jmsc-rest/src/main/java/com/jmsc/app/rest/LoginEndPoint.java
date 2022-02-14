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

import com.jmsc.app.common.rqrs.LoginRequest;
import com.jmsc.app.common.rqrs.LoginResponse;
import com.jmsc.app.common.rqrs.UpdatePasswordRequest;
import com.jmsc.app.common.rqrs.UpdatePasswordResponse;
import com.jmsc.app.service.LoginService;

import io.swagger.annotations.Api;

/**
 * @author Chandan
 *
 */
@RestController
@RequestMapping("/v1/login")
@Api(value = "APIs to handle login services")
public class LoginEndPoint {
	
	@Autowired
	private LoginService service;

	@PostMapping("/login")
	 public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request)throws Throwable {
		LoginResponse response = service.login(request);
		return ResponseEntity.ok(response);
	 }
	
	
	
	@PostMapping("/update_password")
	 public ResponseEntity<UpdatePasswordResponse> updatePassword(@RequestBody UpdatePasswordRequest request)throws Throwable {
		UpdatePasswordResponse response = service.updatePassword(request);
		return ResponseEntity.ok(response);
	 }
}
