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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jmsc.app.common.dto.UserDTO;
import com.jmsc.app.common.exception.ExceptionResponse;
import com.jmsc.app.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author chandan
 *
 */
@RestController
@RequestMapping("/v1/user")
@Api(value = "APIs to handle users")
public class UserEndPoint {

	@Autowired
	private UserService userService;
	
	@PostMapping("/add")
	@ApiOperation(value = "Add new user")
	@ApiResponses(value = {
	          @ApiResponse(code = 200, message = "Successful!", response = UserDTO.class),
	          @ApiResponse(code = 401, message = "You are not authorized to use this resource", response = ExceptionResponse.class),
	          @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden", response = ExceptionResponse.class),
	          @ApiResponse(code = 404, message = "The resource you were trying to reach is available", response = ExceptionResponse.class)
	})
	public ResponseEntity<UserDTO> addUser(UserDTO userDTO){
		UserDTO newUser = userService.addUser(userDTO);
		return ResponseEntity.ok(newUser);
	}
	
	
	@GetMapping
	@ApiOperation(value = "Returns user for given logon id")
	@ApiResponses(value = {
	          @ApiResponse(code = 200, message = "Successful!", response = UserDTO.class),
	          @ApiResponse(code = 401, message = "You are not authorized to use this resource", response = ExceptionResponse.class),
	          @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden", response = ExceptionResponse.class),
	          @ApiResponse(code = 404, message = "The resource you were trying to reach is available", response = ExceptionResponse.class)
	})
	public ResponseEntity<UserDTO> getUserByLogonID(@RequestParam("logonid")String logonID){
		UserDTO user = userService.getUserByLogonID(logonID);
		return ResponseEntity.ok(user);
	}
	
	
	@GetMapping("/{userid}")
	@ApiOperation(value = "Returns user for given user id")
	@ApiResponses(value = {
	          @ApiResponse(code = 200, message = "Successful!", response = UserDTO.class),
	          @ApiResponse(code = 401, message = "You are not authorized to use this resource", response = ExceptionResponse.class),
	          @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden", response = ExceptionResponse.class),
	          @ApiResponse(code = 404, message = "The resource you were trying to reach is available", response = ExceptionResponse.class)
	})
	public ResponseEntity<UserDTO> getUserByID(@PathVariable("userid")Long userID){
		UserDTO user = userService.getUserByID(userID);
		return ResponseEntity.ok(user);
	}
	
	
	
	@GetMapping("/all")
	@ApiOperation(value = "Returns all users")
	@ApiResponses(value = {
	          @ApiResponse(code = 200, message = "Successful!", response = UserDTO.class),
	          @ApiResponse(code = 401, message = "You are not authorized to use this resource", response = ExceptionResponse.class),
	          @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden", response = ExceptionResponse.class),
	          @ApiResponse(code = 404, message = "The resource you were trying to reach is available", response = ExceptionResponse.class)
	})
	public ResponseEntity<List<UserDTO>> getUserByD(){
		List<UserDTO> users = userService.getAllUsers();
		return ResponseEntity.ok(users);
	}
	
}
