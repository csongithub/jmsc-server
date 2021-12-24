/**
 * 
 */
package com.jmsc.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmsc.app.common.dto.UserDTO;
import com.jmsc.app.common.exception.ResourceNotFoundException;
import com.jmsc.app.common.util.ObjectMapperUtil;
import com.jmsc.app.entity.users.User;
import com.jmsc.app.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chandan
 *
 */
@Service
@Slf4j
public class UserService {
	
	@Autowired
	UserRepository repository;
	
	public UserDTO addUser(UserDTO userDTO){
		User user = ObjectMapperUtil.map(userDTO, User.class);
		User savedUser = repository.save(user);
		UserDTO savedUserDTO = ObjectMapperUtil.map(savedUser, UserDTO.class);
		log.debug("New User Created: " + savedUserDTO.toString());
		return savedUserDTO;
	}
	
	
	public UserDTO getUserByLogonID(String logonID){
		Optional<User> optionalUser = repository.findByLogonID(logonID);
		if(!optionalUser.isPresent()) {
			throw new ResourceNotFoundException("User not found");
		}
		UserDTO fetchedUser = ObjectMapperUtil.map(optionalUser.get(), UserDTO.class);
		return fetchedUser;
	}
	
	
	public List<UserDTO> getAllUsers(){
		List<User> usersEntity = repository.findAll();
		List<UserDTO> users = ObjectMapperUtil.mapAll(usersEntity, UserDTO.class);
		return users;
	}
	
	
	public UserDTO getUserByID(Long userID){
		Optional<User> optionalUser = repository.findById(userID);
		if(!optionalUser.isPresent()) {
			throw new ResourceNotFoundException("User not found");
		}
		UserDTO fetchedUser = ObjectMapperUtil.map(optionalUser.get(), UserDTO.class);
		return fetchedUser;
	}
}
