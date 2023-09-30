/**
 * 
 */
package com.jmsc.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmsc.app.common.dto.ClientDTO;
import com.jmsc.app.common.rqrs.UpdateClientBasicInfoRequest;
import com.jmsc.app.common.util.ObjectMapperUtil;
import com.jmsc.app.common.util.Strings;
import com.jmsc.app.entity.Client;
import com.jmsc.app.repository.ClientRepository;
import com.jmsc.app.service.jwt.JwtClientDetailsService;
import com.jmsc.app.service.jwt.JwtTokenUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Chandan
 *
 */
@Service
@Slf4j
public class ClientService {
	
	@Autowired
	private ClientRepository repository;
	
	@Autowired
	private EncryptionService encService;
	
	@Autowired
	private JwtClientDetailsService jwtClientService;
	
	
	@Autowired
	private JwtTokenUtil jwtUtil;
	
	public ClientDTO addClient(ClientDTO clientDTO) throws Throwable {
		
		if(Strings.isNullOrEmpty(clientDTO.getName()))
			throw new RuntimeException("Client name can be null");
		
		if(Strings.isNullOrEmpty(clientDTO.getDisplayName()))
			throw new RuntimeException("Client display name can not be null, please provide a small nick name.");
		
		if(Strings.isNullOrEmpty(clientDTO.getPassword()))
			throw new RuntimeException("Password can not be null, this will be required in login");
		
		if(Strings.isNullOrEmpty(clientDTO.getAdminPassword()))
			throw new RuntimeException("Admin Password can not be null, this will be required in authentication of multiple actions");
		
		if(Strings.isNullOrEmpty(clientDTO.getLogonId()))
			throw new RuntimeException("Logon id can not be null, this will be required in login");
		
		
		String encryptedPassword = encService.getEncryptedPassword(clientDTO.getPassword());
		String encryptedAdminPassword = encService.getEncryptedPassword(clientDTO.getAdminPassword());
		
		clientDTO.setPassword(encryptedPassword);
		clientDTO.setAdminPassword(encryptedAdminPassword);
		Client client = ObjectMapperUtil.map(clientDTO, Client.class);
		Client entity = repository.save(client);
		ClientDTO dto = ObjectMapperUtil.map(entity, ClientDTO.class);
		
		dto.clearAllPassword();
		return dto;
	}
	

	
	
	 public ClientDTO getClient(String logonId) {
		 Optional<Client> optionalClient= repository.findByLogonId(logonId);
			if(optionalClient.isPresent()) {
				ClientDTO dto = ObjectMapperUtil.map(optionalClient.get(), ClientDTO.class);
				dto.clearAllPassword();
				return dto;
			} else 
				return null;
	 }
	
	
	
	public ClientDTO updateClient(ClientDTO clientDTO) {
		Client client = ObjectMapperUtil.map(clientDTO, Client.class);
		Client entity = repository.save(client);
		ClientDTO dto = ObjectMapperUtil.map(entity, ClientDTO.class);
		dto.clearAllPassword();
		return dto;
	}
	
	
	/**
	 * 
	 * This service is used in authentication of rest call.
	 * This is also used in update and reset password 
	 * 
	 * @param logonId
	 * @return
	 */
	public ClientDTO getClientForAuth(String logonId) {
		Optional<Client> optionalClient= repository.findByLogonId(logonId);
		if(optionalClient.isPresent()) {
			ClientDTO dto = ObjectMapperUtil.map(optionalClient.get(), ClientDTO.class);
			//Do not ever call below method, it will block the authentication
			//The password is used in authentication
			//dto.removePassword();
			return dto;
		} else 
			return null;
	}
	
	
	/**
	 * This service is used for authorization purpose
	 * Do not use this in other purpose or modify this service.
	 * 
	 * @param clientId
	 * @return
	 */
	public ClientDTO getClientForAuth(Long clientId) {
		Optional<Client> optionalClient= repository.findById(clientId);
		if(optionalClient.isPresent()) {
			ClientDTO dto = ObjectMapperUtil.map(optionalClient.get(), ClientDTO.class);
			//Do not ever call below method, it will block the authentication
			//The password is used in authentication
			//dto.removePassword();
			return dto;
		} else
			return null;
	}
	
	
	public ClientDTO getClientById(Long id) {
		Optional<Client> optionalClient= repository.findById(id);
		if(optionalClient.isPresent()) {
			ClientDTO dto = ObjectMapperUtil.map(optionalClient.get(), ClientDTO.class);
			dto.clearAllPassword();
			return dto;
		} else 
			return null;
	}
	
	
	public String blockClient(String logonId, String authorization) {
		
		String userName = jwtUtil.getUsernameFromToken(authorization.substring(7));
		if(!"Admin".equalsIgnoreCase(userName)) {
			throw new RuntimeException("Insufficient Permission");
		}
		
		if("Admin".equalsIgnoreCase(logonId)) {
			throw new RuntimeException("Sorry, Admin can not be blocked");
		}
		
		Optional<Client> optional= repository.findByLogonId(logonId);
		if(optional.isPresent()) {
			Client client = optional.get();
			client.setStatus("BLOCKED");
			repository.save(client);
			jwtClientService.clearCache(client.getLogonId());
			return "Client has been blocked";
		} else 
			return "Client Not Found";
	}
	
	
	public String unblockClient(String logonId, String authorization) {
		
		String userName = jwtUtil.getUsernameFromToken(authorization.substring(7));
		if(!"Admin".equalsIgnoreCase(userName)) {
			throw new RuntimeException("Insufficient Permission");
		}
		
		Optional<Client> optional= repository.findByLogonId(logonId);
		if(optional.isPresent()) {
			Client client = optional.get();
			client.setStatus("ACTIVE");
			repository.save(client);
			jwtClientService.clearCache(client.getLogonId());
			return "Client has been unblocked";
		} else 
			return "Client Not Found";
	}
	
	
	public ClientDTO updateBasicInfo(UpdateClientBasicInfoRequest request) {
		Optional<Client> optional= repository.findByLogonId(request.getLogonId());
		if(optional.isPresent()) {
			Client client = optional.get();
			if(Strings.isNotNullOrEmpty(request.getName())) {
				client.setName(request.getName());
			}
			if(Strings.isNotNullOrEmpty(request.getDisplayName())) {
				client.setDisplayName(request.getDisplayName());
			}
			if(Strings.isNotNullOrEmpty(request.getRecipients())) {
				client.setRecipients(request.getRecipients());
			}
			Client savedClient = repository.save(client);
			ClientDTO dto = ObjectMapperUtil.map(savedClient, ClientDTO.class);
			return dto;
		}
		return null;
	}
}
