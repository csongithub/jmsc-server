/**
 * 
 */
package com.jmsc.app.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jmsc.app.common.dto.UserDTO;
import com.jmsc.app.service.UserService;

import io.swagger.annotations.Api;

/**
 * @author anuhr
 *
 */
@RestController
@RequestMapping("/v1/user")
@Api(value = "APIs to handle site's data")
public class UserEndPoint {
	
	@Autowired
	private UserService service;

	@PostMapping("/create")
	public ResponseEntity<UserDTO> addSite(@RequestBody UserDTO userDTO)throws Throwable{
		UserDTO savedUser = service.createUser(userDTO);
		return ResponseEntity.ok(savedUser);
	}
	
	
	
	@GetMapping("/all/{client_id}")
	public ResponseEntity<List<UserDTO>> getAll(@PathVariable("client_id") Long clientId){
		List<UserDTO> all = service.getAllUsers(clientId);
		return ResponseEntity.ok(all);
	}
	
	
	
	@DeleteMapping("/delete/{client_id}/{user_id}")
	public ResponseEntity<Integer> deletePayment(@PathVariable("client_id")Long clientId,
												 @PathVariable("user_id")Long userId) {
		Integer statusCode = service.deleteUser(clientId, userId);
		return ResponseEntity.ok(statusCode);
	}
}
