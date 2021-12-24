/**
 * 
 */
package com.jmsc.app.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jmsc.app.common.dto.Credentials;
import com.jmsc.app.common.exception.ExceptionResponse;
import com.jmsc.app.common.rqrs.LoginResponse;
import com.jmsc.app.common.rqrs.UpdatePasswordRequest;
import com.jmsc.app.common.rqrs.UpdatePasswordResponse;
import com.jmsc.app.service.LoginService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author chandan
 *
 */
@RestController
@RequestMapping("/v1/login")
@Api(value = "APIs to handle user login")
public class LoginEndPoint {
	
	@Autowired
	private LoginService loginServcie;
	
	@PutMapping
	@ApiOperation(value = "User Login API")
	@ApiResponses(value = {
	          @ApiResponse(code = 200, message = "Successful!", response = LoginResponse.class),
	          @ApiResponse(code = 401, message = "You are not authorized to use this resource", response = ExceptionResponse.class),
	          @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden", response = ExceptionResponse.class),
	          @ApiResponse(code = 404, message = "The resource you were trying to reach is available", response = ExceptionResponse.class)
	})
	public ResponseEntity<LoginResponse> userLogin(@RequestBody Credentials credentials){
		LoginResponse response = loginServcie.login(credentials);
		return ResponseEntity.ok(response);
	}

	
	@PutMapping("/updatepassword")
	@ApiOperation(value = "Password update API")
	@ApiResponses(value = {
	          @ApiResponse(code = 200, message = "Successful!", response = LoginResponse.class),
	          @ApiResponse(code = 401, message = "You are not authorized to use this resource", response = ExceptionResponse.class),
	          @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden", response = ExceptionResponse.class),
	          @ApiResponse(code = 404, message = "The resource you were trying to reach is available", response = ExceptionResponse.class)
	})
	public ResponseEntity<UpdatePasswordResponse> updatePassword(@RequestBody UpdatePasswordRequest rerquest){
		UpdatePasswordResponse response = loginServcie.updatePassword(rerquest);
		return ResponseEntity.ok(response);
	}

}
