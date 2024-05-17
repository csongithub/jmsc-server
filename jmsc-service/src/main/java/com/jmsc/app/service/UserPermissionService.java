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
		UserPermissions entity = ObjectMapperUtil.map(dto, UserPermissions.class);
		entity.setPermissionsJson(ObjectMapperUtil.json(dto.getPermissions()));
		repository.save(entity);
		
		return Boolean.TRUE;
	}
	
	
	public UserPermissionsDTO getPermission(Long clientId, Long userId) {
		Optional<UserPermissions> optional = repository.findByClientIdAndUserId(clientId, userId);
		if(optional.isPresent()) {
			UserPermissions entity = optional.get();
			UserPermissionsDTO dto = ObjectMapperUtil.map(entity, UserPermissionsDTO.class);
			dto.setPermissions(ObjectMapperUtil.object(entity.getPermissionsJson(), PermisssionsDTO.class));
			return dto;
		} else {
			throw new ResourceNotFoundException("No Record Exists");
		}
	}
}
