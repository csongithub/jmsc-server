/**
 * 
 */
package com.jmsc.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmsc.app.common.dto.Credentials;
import com.jmsc.app.common.dto.UserDTO;
import com.jmsc.app.common.exception.ResourceNotFoundException;
import com.jmsc.app.common.rqrs.LoginResponse;
import com.jmsc.app.common.rqrs.UpdatePasswordRequest;
import com.jmsc.app.common.rqrs.UpdatePasswordResponse;
import com.jmsc.app.common.util.ObjectMapperUtil;
import com.jmsc.app.common.util.Strings;
import com.jmsc.app.entity.users.User;
import com.jmsc.app.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chandan
 *
 */
@Service
@Slf4j
public class LoginService {
	@Autowired
	UserRepository repository;
	
	public LoginResponse login(Credentials credentials){
		LoginResponse response = null;
		if(Strings.isNullOrEmpty(credentials.getLogonID())
				|| Strings.isNullOrEmpty(credentials.getPassword()))
			throw new RuntimeException("Invalid id/password");
		
		Optional<User> optionalUser = repository.findByLogonID(credentials.getLogonID());
		if(optionalUser.isPresent()) {
			log.debug("User {} found", credentials.getLogonID());
			User user = optionalUser.get();
			if(credentials.getPassword().equals(user.getPassword())) {
				log.debug("This is first login for user {}: " + user.isFirstLogin() , credentials.getLogonID());
				response = new LoginResponse();
				response.setLoginSuccess(true);
				response.setUserDTO(ObjectMapperUtil.map(optionalUser.get(), UserDTO.class));
			}else {
				log.debug("Invalid Password: {}", credentials.getPassword());
				throw new RuntimeException("Wrong Password");
			}
		}else {
			throw new ResourceNotFoundException("User not found");
		}
		return response;
	}
	
	
	public UpdatePasswordResponse updatePassword(UpdatePasswordRequest request) {
		UpdatePasswordResponse response = null;
		if(Strings.isNullOrEmpty(request.getLogonID()) || Strings.isNullOrEmpty(request.getPassword())
				|| Strings.isNullOrEmpty(request.getNewPassword()) || Strings.isNullOrEmpty(request.getReNewPassword()))
			throw new RuntimeException("Invalid input");
		
		if(!request.getNewPassword().equals(request.getReNewPassword()))
			throw new RuntimeException("Password did not match");
		
		Optional<User> optionalUser = repository.findByLogonID(request.getLogonID());
		if(optionalUser.isPresent()) {
			log.debug("User {} found", request.getLogonID());
			User user = optionalUser.get();
			if(request.getPassword().equals(user.getPassword())) {
				response = new UpdatePasswordResponse();
				user.setPassword(request.getNewPassword());
				user.setFirstLogin(false);
				repository.save(user);
				response.setStatus(true);
				response.setMessage("Password updated successfully");
			}else {
				log.debug("Invalid Password: {}", request.getPassword());
				throw new RuntimeException("Wrong Password");
			}
		}else {
			throw new ResourceNotFoundException("User not found");
		}
		return response;
	}
}
