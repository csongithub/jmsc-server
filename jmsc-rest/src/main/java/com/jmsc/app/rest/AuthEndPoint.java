/**
 * 
 */
package com.jmsc.app.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jmsc.app.common.rqrs.AdminAuthRequest;
import com.jmsc.app.common.rqrs.AdminAuthResponse;
import com.jmsc.app.common.rqrs.LoginRequest;
import com.jmsc.app.common.rqrs.LoginResponse;
import com.jmsc.app.common.rqrs.ResetPasswordRequest;
import com.jmsc.app.common.rqrs.UpdateAdminPasswordRequest;
import com.jmsc.app.common.rqrs.UpdatePasswordRequest;
import com.jmsc.app.common.rqrs.UpdatePasswordResponse;
import com.jmsc.app.service.AuthService;

import io.swagger.annotations.Api;

/**
 * @author Chandan
 *
 */
@RestController
@RequestMapping("/v1/auth")
@Api(value = "APIs to handle login services")
public class AuthEndPoint {
	
	@Autowired
	private AuthService service;
	
	

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {

        String refreshToken = request.get("refreshToken");
        String clientId = request.get("clientId");
        String user = request.get("user");

        Map<String, String> tokens = service.refreshToken(refreshToken,clientId, user);

        return ResponseEntity.ok(tokens);
    }

    
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
	
	
	@PostMapping("/reset_password")
	public ResponseEntity<UpdatePasswordResponse> resetPassword(@RequestBody ResetPasswordRequest request, @RequestHeader("Authorization") String authorization)throws Throwable {
		UpdatePasswordResponse response = service.resetPassword(request, authorization);
		return ResponseEntity.ok(response);
	}
	
	
	@PostMapping("/authorize_admin_access")
	public ResponseEntity<AdminAuthResponse> authorizeAdminAccess(@RequestBody AdminAuthRequest req) throws Throwable{
		AdminAuthResponse response = service.authorizeAdminAccess(req);
		return ResponseEntity.ok(response);
	}
	
	
	@PostMapping("/update_admin_password")
	public ResponseEntity<UpdatePasswordResponse> updateAdminPassword(@RequestBody UpdateAdminPasswordRequest req) throws Throwable{
		UpdatePasswordResponse response = service.updateAdminPassword(req);
		return ResponseEntity.ok(response);
	}
}
