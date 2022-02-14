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
import com.jmsc.app.repository.ClientRepository;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;

/**
 * @author Chandan
 *
 */
@Service
@Slf4j
public class LoginService {
	
	@Autowired
	private ClientService service;
	
	
	public LoginResponse login(LoginRequest request) {
		
		if(Strings.isNotNullOrEmpty(request.getLogonId()) || Strings.isNotNullOrEmpty(request.getPassword())){
			LoginResponse response = new LoginResponse();
			response.setLoginSuccess(false);
			response.setMessage("Insufficient Data");
			return response;
		}
		
		ClientDTO client = service.getClientByLogonId(request.getLogonId());
		if(client != null) {
			LoginResponse response = new LoginResponse();
			if(request.getPassword().equals(client.getPassword())) {
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
		if(Strings.isNotNullOrEmpty(request.getLogonId()) || Strings.isNotNullOrEmpty(request.getCurrentPassword()) || Strings.isNotNullOrEmpty(request.getNewPassword())){
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
			client.setPassword(request.getNewPassword());
			ClientDTO clientDTO = ObjectMapperUtil.map(client, ClientDTO.class);
			ClientDTO dto = service.addClient(clientDTO);
			
			response.setUpdateSuccess(true);
			response.setMessage("Password Updated Successfully");
		}
		return response;
	}
	

}
