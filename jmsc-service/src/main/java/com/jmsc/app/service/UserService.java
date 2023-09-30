/**
 * 
 */
package com.jmsc.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmsc.app.common.dto.UserDTO;
import com.jmsc.app.common.exception.ResourceNotFoundException;
import com.jmsc.app.common.util.Collections;
import com.jmsc.app.common.util.ObjectMapperUtil;
import com.jmsc.app.common.util.Strings;
import com.jmsc.app.entity.User;
import com.jmsc.app.repository.UserRepository;

/**
 * @author anuhr
 *
 */
@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private EncryptionService encService;
	
	
	public UserDTO createUser(UserDTO userDTO) throws Throwable {
		
		if(userDTO.getClientId() == null)
			if(Strings.isNullOrEmpty(userDTO.getName()))
				throw new RuntimeException("Client id can be null");
		
		if(Strings.isNullOrEmpty(userDTO.getName()))
			throw new RuntimeException("User name can be null");
		
		if(Strings.isNullOrEmpty(userDTO.getDisplayName()))
			throw new RuntimeException("User display name can not be null, please provide a small nick name.");
		
		if(Strings.isNullOrEmpty(userDTO.getLogonId()))
			throw new RuntimeException("Logon id can not be null, this will be required in login");
		
		Optional<User> existingUser = repository.findByLogonId(userDTO.getLogonId());
		
		if(userDTO.getId() == null) {
			//New User go here
			if(existingUser.isPresent())
				throw new RuntimeException("Logon Id is not available, please choose a different.");
			
			if(Strings.isNullOrEmpty(userDTO.getPassword()))
				throw new RuntimeException("Password can not be null, this will be required in login");
			else
				userDTO.setPassword(encService.getEncryptedPassword(userDTO.getPassword()));
		} else {
			//Existing user go here
			if(existingUser.isPresent() && !existingUser.get().getId().equals(userDTO.getId()))
				throw new RuntimeException("Logon Id is not available, please choose a different.");
			userDTO.setPassword(repository.getById(userDTO.getId()).getPassword());
		}

		User user = ObjectMapperUtil.map(userDTO, User.class);
		User entity = repository.save(user);
		UserDTO dto = ObjectMapperUtil.map(entity, UserDTO.class);
		dto.clearPassword();
		return dto;
	}
	
	
	
	public List<UserDTO> getAllUsers(Long clientId){
		List<User> all = repository.findByClientId(clientId);
		
		if(Collections.isNullOrEmpty(all)) {
			return new ArrayList<UserDTO>();
		}
		
		List<UserDTO> allUsers = ObjectMapperUtil.mapAll(all, UserDTO.class);
		allUsers.forEach(u-> {
			u.clearPassword();
		});
		
		return allUsers;
	}
	
	
	
	public Integer deleteUser(Long clientId, Long paymentId) {
		Optional<User> optional  = repository.findAllByClientIdAndId(clientId, paymentId);
		
		if(optional.isPresent()) {
			User user = optional.get();
			repository.delete(user);
			return 0;
		} else {
			throw new ResourceNotFoundException("User not found");
		}
	}

}
