/**
 * 
 */
package com.jmsc.app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmsc.app.common.dto.DirectAccessCardDTO;
import com.jmsc.app.config.jmsc.ServiceLocator;
import com.jmsc.app.service.resource.JMSCResource;

/**
 * @author Chandan
 *
 */
@Service
public class HomeService {
	
	private final String DIRECT_ACCESS_CARDS_RESOURCE = "classpath:direct_access_cards.json";
	
	public  List<List<DirectAccessCardDTO>> directAccess() throws Throwable{
		
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
		return listOfList;
		
	}

}
