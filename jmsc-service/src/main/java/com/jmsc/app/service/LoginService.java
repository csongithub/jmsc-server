/**
 * 
 */
package com.jmsc.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmsc.app.common.dto.ClientDTO;
import com.jmsc.app.common.rqrs.LoginRequest;
import com.jmsc.app.common.rqrs.LoginResponse;
import com.jmsc.app.common.rqrs.UpdatePasswordRequest;
import com.jmsc.app.common.rqrs.UpdatePasswordResponse;
import com.jmsc.app.common.util.ObjectMapperUtil;
import com.jmsc.app.common.util.Strings;
import com.jmsc.app.entity.users.Client;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Chandan
 *
 */
@Service
@Slf4j
public class LoginService {
	
	@Autowired
	private ClientService service;
	
	
	@Autowired
	private EncryptionService encService;
	
	
	public LoginResponse login(LoginRequest request) {
		
		if(Strings.isNullOrEmpty(request.getLogonId()) || Strings.isNullOrEmpty(request.getPassword())){
			LoginResponse response = new LoginResponse();
			response.setLoginSuccess(false);
			response.setMessage("Insufficient Data");
			return response;
		}
		
		ClientDTO client = service.getClientByLogonId(request.getLogonId());
		if(client != null) {
			LoginResponse response = new LoginResponse();
			String encryptedPassword = encService.getEncryptedPassword(request.getPassword());
			if(encryptedPassword.equals(client.getPassword())) {
				client.removePassword();
				response.setLoginSuccess(true);
				response.setClientDTO(client);
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
			ClientDTO dto = service.updateClient(clientDTO);
			response.setUpdateSuccess(true);
			response.setMessage("Password Updated Successfully");
		} else {
			response.setUpdateSuccess(false);
			response.setMessage(loginResponse.getMessage());
		}
		return response;
	}

}
