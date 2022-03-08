/**
 * 
 */
package com.jmsc.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmsc.app.common.dto.ClientDTO;
import com.jmsc.app.common.rqrs.LoginRequest;
import com.jmsc.app.common.rqrs.LoginResponse;
import com.jmsc.app.common.rqrs.ResetPasswordRequest;
import com.jmsc.app.common.rqrs.UpdatePasswordRequest;
import com.jmsc.app.common.rqrs.UpdatePasswordResponse;
import com.jmsc.app.common.util.ObjectMapperUtil;
import com.jmsc.app.common.util.Strings;
import com.jmsc.app.entity.Client;
import com.jmsc.app.service.jwt.JwtProvider;
import com.jmsc.app.service.jwt.JwtTokenUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Chandan
 *
 */
@Service
@Slf4j
public class AuthService {
	
	@Autowired
	private ClientService service;
	
	@Autowired
	private EncryptionService encService;
	
	@Autowired
	private JwtProvider jwtProvider;
	
	@Autowired
	private JwtTokenUtil jwtUtil;
	
	public LoginResponse login(LoginRequest request) {
		
		if(Strings.isNullOrEmpty(request.getLogonId()) || Strings.isNullOrEmpty(request.getPassword())){
			LoginResponse response = new LoginResponse();
			response.setLoginSuccess(false);
			response.setMessage("Insufficient Data");
			return response;
		}
		
		ClientDTO client = service.getClientByLogonId(request.getLogonId());
		if(client != null) {
			
			if(!"ACTIVE".equalsIgnoreCase(client.getStatus())) {
				throw new RuntimeException("Use has been blocked, Kindly contact admin");
			}
			
			LoginResponse response = new LoginResponse();
			String encryptedPassword = encService.getEncryptedPassword(request.getPassword());
			if(encryptedPassword.equals(client.getPassword())) {
				client.removePassword();
				response.setLoginSuccess(true);
				response.setClientDTO(client);
				
				final String token = jwtProvider.generateToken(client.getLogonId());
				log.debug("Issued token: {} for logon request by: {}",token,request.getLogonId());
				
				response.setToken(token);
				response.setMessage("Login Successful");
			} else {
				response.setLoginSuccess(false);
				response.setMessage("Incorrect Password, Please Try Again");
			}
			return response;
		}else {
			LoginResponse response = new LoginResponse();
			response.setLoginSuccess(false);
			response.setMessage("Client not found");
			return response;
		}
	}
	
	
	
	public UpdatePasswordResponse updatePassword(UpdatePasswordRequest request) {
		if(Strings.isNullOrEmpty(request.getLogonId()) || Strings.isNullOrEmpty(request.getCurrentPassword()) || Strings.isNullOrEmpty(request.getNewPassword())){
			UpdatePasswordResponse response = new UpdatePasswordResponse();
			response.setUpdateSuccess(false);
			response.setMessage("Insufficient Data");
			return response;
		}
		
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setLogonId(request.getLogonId());
		loginRequest.setPassword(request.getCurrentPassword());
		
		LoginResponse loginResponse = this.login(loginRequest);
		UpdatePasswordResponse response = new UpdatePasswordResponse();
		
		if(loginResponse.isLoginSuccess()) {
			Client client = ObjectMapperUtil.map(loginResponse.getClientDTO(), Client.class);
			String encryptedPassword = encService.getEncryptedPassword(request.getNewPassword());
			client.setPassword(encryptedPassword);
			ClientDTO clientDTO = ObjectMapperUtil.map(client, ClientDTO.class);
			service.updateClient(clientDTO);
			response.setUpdateSuccess(true);
			response.setMessage("Password Updated Successfully");
		} else {
			response.setUpdateSuccess(false);
			response.setMessage(loginResponse.getMessage());
		}
		return response;
	}
	
	
	
	public UpdatePasswordResponse resetPassword(ResetPasswordRequest request, String authorization) {
		
		if(Strings.isNullOrEmpty(request.getLogonId()) || Strings.isNullOrEmpty(request.getNewPassword())){
			UpdatePasswordResponse response = new UpdatePasswordResponse();
			response.setUpdateSuccess(false);
			response.setMessage("Insufficient Data");
			return response;
		}
		
		String userName = jwtUtil.getUsernameFromToken(authorization.substring(7));
		if(!"Admin".equalsIgnoreCase(userName)) {
			throw new RuntimeException("Insufficient Permission");
		}
		
		UpdatePasswordResponse response = new UpdatePasswordResponse();
		ClientDTO clientDTO = service.getClientByLogonId(request.getLogonId());
		Client client = ObjectMapperUtil.map(clientDTO, Client.class);
		String encryptedPassword = encService.getEncryptedPassword(request.getNewPassword());
		
		client.setPassword(encryptedPassword);
		clientDTO = ObjectMapperUtil.map(client, ClientDTO.class);
		service.updateClient(clientDTO);
		response.setUpdateSuccess(true);
		response.setMessage("Password Reset Successfully");
		
		return response;
	}

}
