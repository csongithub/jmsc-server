/**
 * 
 */
package com.jmsc.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmsc.app.common.dto.PermisssionsDTO;
import com.jmsc.app.common.dto.UserPermissionsDTO;
import com.jmsc.app.common.exception.ResourceNotFoundException;
import com.jmsc.app.common.util.ObjectMapperUtil;
import com.jmsc.app.entity.User;
import com.jmsc.app.entity.UserPermissions;
import com.jmsc.app.repository.UserPermissionsRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author anuhr
 *
 */
@Slf4j
@Service
public class UserPermissionService {

	@Autowired
	private UserPermissionsRepository repository;
	
	
	public Boolean createOrUpdate(UserPermissionsDTO dto) {
		if(dto.getUserId() == null) {
			throw new RuntimeException("User Id cam not be null");
		}
		
		if(dto.getClientId() == null) {
			throw new RuntimeException("Client id can be null");
		}
		
		Optional<UserPermissions> optional = repository.findByClientIdAndUserId(dto.getClientId(), dto.getUserId());
		if(optional.isPresent()) {
			dto.setId(optional.get().getId());
		}
		UserPermissions entity = ObjectMapperUtil.map(dto, UserPermissions.class);
		entity.setPermissionsJson(ObjectMapperUtil.json(dto.getPermissions()));
		repository.save(entity);
		
		return Boolean.TRUE;
	}
	
	
	public PermisssionsDTO getPermission(Long clientId, Long userId) {
		Optional<UserPermissions> optional = repository.findByClientIdAndUserId(clientId, userId);
		if(optional.isPresent()) {
			UserPermissions entity = optional.get();
		
			PermisssionsDTO permissions =ObjectMapperUtil.object(entity.getPermissionsJson(), PermisssionsDTO.class);
			return permissions;
		} else {
			return new PermisssionsDTO();
		}
	}
	
	
	
	public Integer deleteUserPermissions(Long clientId, Long userId) {
		Optional<UserPermissions> optional = repository.findByClientIdAndUserId(clientId, userId);
		
		if(optional.isPresent()) {
			UserPermissions permissions = optional.get();
			repository.delete(permissions);
			return 0;
		} else {
			throw new ResourceNotFoundException("User Permissions not found");
		}
	}
}
