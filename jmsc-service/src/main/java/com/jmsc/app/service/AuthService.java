/**
 * 
 */
package com.jmsc.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmsc.app.common.dto.ClientDTO;
import com.jmsc.app.common.dto.UserDTO;
import com.jmsc.app.common.rqrs.AdminAuthRequest;
import com.jmsc.app.common.rqrs.AdminAuthResponse;
import com.jmsc.app.common.rqrs.LoginRequest;
import com.jmsc.app.common.rqrs.LoginResponse;
import com.jmsc.app.common.rqrs.ResetPasswordRequest;
import com.jmsc.app.common.rqrs.UpdateAdminPasswordRequest;
import com.jmsc.app.common.rqrs.UpdatePasswordRequest;
import com.jmsc.app.common.rqrs.UpdatePasswordResponse;
import com.jmsc.app.common.util.ObjectMapperUtil;
import com.jmsc.app.common.util.Strings;
import com.jmsc.app.config.jmsc.ServiceLocator;
import com.jmsc.app.entity.Client;
import com.jmsc.app.entity.User;
import com.jmsc.app.repository.ClientRepository;
import com.jmsc.app.repository.UserRepository;
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
	
	@Autowired
	private UserRepository userRepository;
	
	public LoginResponse login(LoginRequest request) {
		
		if(Strings.isNullOrEmpty(request.getLogonId()) || Strings.isNullOrEmpty(request.getPassword())){
			LoginResponse response = new LoginResponse();
			response.setLoginSuccess(false);
			response.setMessage("Insufficient Data");
			return response;
		}
		
		if(request.isAdmin()) {
			return clientLogin(request);
		} else {
			return userLogin(request);
		}
	}
	
	
	private LoginResponse clientLogin(LoginRequest request) {
		ClientDTO client = service.getClientForAuth(request.getLogonId());
		if(client != null) {
			
			if(!"ACTIVE".equalsIgnoreCase(client.getStatus())) {
				throw new RuntimeException("Use has been blocked, Kindly contact admin");
			}
			
			LoginResponse response = new LoginResponse();
			String encryptedPassword = encService.getEncryptedPassword(request.getPassword());
			if(encryptedPassword.equals(client.getPassword())) {
				client.clearAllPassword();
				response.setLoginSuccess(true);
				response.setClientDTO(client);
				
				final String token = jwtProvider.generateToken(client.getLogonId());
				log.debug("Issued token: {} for logon request by: {}",token,request.getLogonId());
				
				response.setToken(token);
				response.setAdmin(true);
				response.setMessage("Login Successful");
			} else {
				response.setLoginSuccess(false);
				response.setMessage("Incorrect Password, Please Try Again");
			}
			return response;
		}else {
			LoginResponse response = new LoginResponse();
			response.setLoginSuccess(false);
			response.setMessage("Invalid Credentials");
			return response;
		}
	}
	
	
	private LoginResponse userLogin(LoginRequest request) {
		
		Optional<User> optionalUser = userRepository.findByLogonId(request.getLogonId());
		
		if(!optionalUser.isPresent()) {
			LoginResponse response = new LoginResponse();
			response.setLoginSuccess(false);
			response.setMessage("User not found");
			return response;
		}
		
		User user = optionalUser.get();
		
		ClientDTO client = service.getClientById(user.getClientId());
		if(client == null) {
			LoginResponse response = new LoginResponse();
			response.setLoginSuccess(false);
			response.setMessage("No client found for this user");
			return response;
		}
		
		if(!"ACTIVE".equalsIgnoreCase(user.getStatus())) {
			throw new RuntimeException("User has been blocked, Kindly contact admin");
		}
		
		LoginResponse response = new LoginResponse();
		if(user != null) {
			if(!"ACTIVE".equalsIgnoreCase(user.getStatus())) {
				throw new RuntimeException("User has been blocked, Kindly contact admin");
			}
			String encryptedPassword = encService.getEncryptedPassword(request.getPassword());
			if(encryptedPassword.equals(user.getPassword())) {
				UserDTO userDTO = ObjectMapperUtil.map(user, UserDTO.class);
				userDTO.clearPassword();
				response.setLoginSuccess(true);
				response.setClientDTO(client);
				response.setUserDTO(userDTO);
				
				final String token = jwtProvider.generateToken(client.getLogonId());
				log.debug("Issued token: {} for logon request by: {}",token,request.getLogonId());
				
				response.setToken(token);
				response.setAdmin(false);
				response.setMessage("Login Successful");
			} else {
				response.setLoginSuccess(false);
				response.setMessage("Incorrect Password, Please Try Again");
			}
		}
		return response;
	}
	
	
	
	
	public UpdatePasswordResponse updatePassword(UpdatePasswordRequest request) {
		if(Strings.isNullOrEmpty(request.getLogonId()) || Strings.isNullOrEmpty(request.getCurrentPassword()) || Strings.isNullOrEmpty(request.getNewPassword())){
			UpdatePasswordResponse response = new UpdatePasswordResponse();
			response.setUpdateSuccess(false);
			response.setMessage("Insufficient Data");
			return response;
		}
		
		if(request.isAdmin()) {
			return updateAdminPassword(request);
		} else {
			return updateUserPassword(request);
		}
	}
	
	
	private UpdatePasswordResponse updateAdminPassword(UpdatePasswordRequest request) {
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setLogonId(request.getLogonId());
		loginRequest.setPassword(request.getCurrentPassword());
		loginRequest.setAdmin(true);
		
		LoginResponse loginResponse = this.login(loginRequest);
		UpdatePasswordResponse response = new UpdatePasswordResponse();
		
		if(loginResponse.isLoginSuccess()) {
			
			Optional<Client> optional =  ServiceLocator.getService(ClientRepository.class).findByLogonId(request.getLogonId());
			Client client = null;
			if(optional.isPresent()) {
				client = optional.get();
			}
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
	
	
	private UpdatePasswordResponse updateUserPassword(UpdatePasswordRequest request) {
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setLogonId(request.getLogonId());
		loginRequest.setPassword(request.getCurrentPassword());
		loginRequest.setAdmin(false);
		
		LoginResponse loginResponse = this.login(loginRequest);
		UpdatePasswordResponse response = new UpdatePasswordResponse();
		
		if(loginResponse.isLoginSuccess()) {
			
			Optional<User> optional =  userRepository.findByLogonId(request.getLogonId());
			User user = null;
			if(optional.isPresent()) {
				user = optional.get();
			}
			String encryptedPassword = encService.getEncryptedPassword(request.getNewPassword());
			user.setPassword(encryptedPassword);
			userRepository.save(user);
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
		ClientDTO clientDTO = service.getClientForAuth(request.getLogonId());
		Client client = ObjectMapperUtil.map(clientDTO, Client.class);
		String encryptedPassword = encService.getEncryptedPassword(request.getNewPassword());
		
		client.setPassword(encryptedPassword);
		clientDTO = ObjectMapperUtil.map(client, ClientDTO.class);
		service.updateClient(clientDTO);
		response.setUpdateSuccess(true);
		response.setMessage("Password Reset Successfully");
		
		return response;
	}
	
	
	/**
	 * This service is useful in authorizing a client with Admin access.
	 * For example lets say that if someone who has logged in to the application
	 * and want to delete or modify some credit facility, so such type of actions
	 * would be required to validate with admin password. Once the user will provide 
	 * the correct admin password then only he will be able to perform such kind of 
	 * authorized action.
	 * @param req
	 * @return
	 */
	public AdminAuthResponse authorizeAdminAccess(AdminAuthRequest req) throws Throwable{
		
		if(req.getClientId() == null || Strings.isNullOrEmpty(req.getAdminPassword())){
			throw new RuntimeException("Insufficient Data");
		}
		
		ClientDTO client = service.getClientForAuth(req.getClientId());
		if(client != null) {
			
			if(!"ACTIVE".equalsIgnoreCase(client.getStatus())) {
				throw new RuntimeException("Use has been blocked, Kindly contact admin");
			}
			
			AdminAuthResponse response = new AdminAuthResponse();
			String encryptedAdminPassword = encService.getEncryptedPassword(req.getAdminPassword());
			
			if(encryptedAdminPassword.equals(client.getAdminPassword())) {
				response.setAuthorized(true);
				response.setMessage("Authorization Successful");
			} else {
				response.setAuthorized(false);
				response.setMessage("Authorization Failed");
			}
			return response;
		}else {
			AdminAuthResponse response = new AdminAuthResponse();
			response.setAuthorized(false);
			response.setMessage("Client not found");
			return response;
		}
	}
	
	
	
	
	
	public UpdatePasswordResponse updateAdminPassword(UpdateAdminPasswordRequest request) {
		
//		if(Strings.isNullOrEmpty(request.getLogonId()) || Strings.isNullOrEmpty(request.getPassword()) 
//				|| Strings.isNullOrEmpty(request.getCurrentAdminPassword()) ||  Strings.isNullOrEmpty(request.getNewAdminPassword())){
//			UpdatePasswordResponse response = new UpdatePasswordResponse();
//			response.setUpdateSuccess(false);
//			response.setMessage("Insufficient Data");
//			return response;
//		}
//		
//		LoginRequest loginRequest = new LoginRequest();
//		loginRequest.setLogonId(request.getLogonId());
//		loginRequest.setPassword(request.getPassword());
//		
//		// First validate the user with login
//		LoginResponse loginResponse = this.login(loginRequest);
		
		UpdatePasswordResponse response = new UpdatePasswordResponse();
		
		/*
		 * Once login is successful then update the admin password
		 */
		if(request.getClientId() != null) {
			/*
			 * In the client present in the login response password info wont be there
			 * So, need to fetch the client from database  to get the password info.
			 * 
			 * Get the client id from login response data and fetch the client from the database
			 */
			ClientRepository clientRepository = ServiceLocator.getService(ClientRepository.class);
			
			Optional<Client> optional =  clientRepository.findById(request.getClientId());
			
			Client client = null;
			
			if(optional.isPresent())
				client = optional.get();
			else
				throw new RuntimeException("Somethting went wrong, please try again later");
			/*
			 *Check if the Admin password is present in the client object.
			 *If it is present then go for Admin password updation, otherwise 
			 *throw error, because password update can be done only if old 
			 *password ispresent.
			 *
			 */
			if(Strings.isNullOrEmpty(client.getAdminPassword()))
				throw new RuntimeException("Somethgin went wrong, pleas try later");
		
			
			String currentAdminPwdEnc = encService.getEncryptedPassword(request.getCurrentAdminPassword());
			
			/*
			 * If admin password is present then first match it with the current admin password in the request.
			 * If the current admin password matches with the client admin password, then update the new admin password
			 */
			if(client.getAdminPassword().equals(currentAdminPwdEnc)) {
				String newAdminPwdEnc = encService.getEncryptedPassword(request.getNewAdminPassword());
				
				client.setAdminPassword(newAdminPwdEnc);
				clientRepository.save(client);
				
				response.setUpdateSuccess(true);
				response.setMessage("Password Updated Successfully");
			} else {
				response.setUpdateSuccess(false);
				response.setMessage("Incorrect admin password");
			}
			
			
			
		} else {
			response.setUpdateSuccess(false);
			response.setMessage("Insufficient Data");
		}
		return response;
	}
	

}
