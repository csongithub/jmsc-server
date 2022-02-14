/**
 * 
 */
package com.jmsc.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmsc.app.common.dto.ClientDTO;
import com.jmsc.app.common.util.ObjectMapperUtil;
import com.jmsc.app.entity.users.Client;
import com.jmsc.app.repository.ClientRepository;

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
	
	public ClientDTO addClient(ClientDTO clientDTO) {
		String encryptedPassword = encService.getEncryptedPassword(clientDTO.getPassword());
		clientDTO.setPassword(encryptedPassword);
		Client client = ObjectMapperUtil.map(clientDTO, Client.class);
		Client entity = repository.save(client);
		ClientDTO dto = ObjectMapperUtil.map(entity, ClientDTO.class);
		dto.removePassword();
		return dto;
	}
	
	
	
	public ClientDTO updateClient(ClientDTO clientDTO) {
		Client client = ObjectMapperUtil.map(clientDTO, Client.class);
		Client entity = repository.save(client);
		ClientDTO dto = ObjectMapperUtil.map(entity, ClientDTO.class);
		dto.removePassword();
		return dto;
	}
	
	
	public ClientDTO getClientByLogonId(String logonId) {
		Optional<Client> optionalClient= repository.findByLogonId(logonId);
		if(optionalClient.isPresent()) {
			ClientDTO dto = ObjectMapperUtil.map(optionalClient.get(), ClientDTO.class);
			return dto;
		} else 
			return null;
	}
}
