/**
 * 
 */
package com.jmsc.app.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmsc.app.common.dto.DirectAccessCardDTO;
import com.jmsc.app.config.jmsc.ServiceLocator;
import com.jmsc.app.service.resource.JMSCResource;

import io.swagger.annotations.Api;

/**
 * @author Chandan
 *
 */
@RestController
@RequestMapping("/v1/home")
@Api(value = "Home Apis")
public class HomeEndPoint {
	
	private final String DIRECT_ACCESS_CARDS_RESOURCE = "classpath:direct_access_cards.json";
	
	@GetMapping("/access_cards")
	public  ResponseEntity<List<List<DirectAccessCardDTO>>> directAccess() throws Throwable{
		
		List<List<DirectAccessCardDTO>> listOfList = new ArrayList<List<DirectAccessCardDTO>>();
		
		JMSCResource resource = ServiceLocator.getService(JMSCResource.class);
		
		String jsonCards = resource.readAsString(DIRECT_ACCESS_CARDS_RESOURCE, null);
		
		List<DirectAccessCardDTO> list = new ObjectMapper().readValue(jsonCards, new TypeReference<List<DirectAccessCardDTO>>(){});
		
		int index = 0;
		int count = 1;
		List<DirectAccessCardDTO> l = new ArrayList<DirectAccessCardDTO>();
		while(index < list.size()) {
			if(count <= 3) {
				l.add(list.get(index++));
				count++;
			}else {
				listOfList.add(l);
				count = 1;
				l = new ArrayList<DirectAccessCardDTO>();
			}
		}
		if(l.size() > 0) {
			listOfList.add(l);
		}
		return ResponseEntity.ok(listOfList);
		
	}
}
