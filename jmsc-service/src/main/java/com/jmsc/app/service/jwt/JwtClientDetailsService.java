/**
 * 
 */
package com.jmsc.app.service.jwt;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.jmsc.app.entity.Client;
import com.jmsc.app.repository.ClientRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * For every API call this service is called to validate the user.
 * It basically checks if the user is currently active in application database or not.
 * 
 * To avoid the database call for every API validation, It uses a in-memory cache.
 * So on first API call for any user, it loads the user from database, validates the loaded user and also put that user in cache.
 * So next time if the same user makes any API call, the user is loaded from cache and not from database.
 * 
 * 
 * Now there might be some case where a user might have been removed from the application database, but he could be still in cache.
 * So with a valid token this user can still make any API call.
 * To prevent this, we check if the user has logged in today.
 * And if not then again user is looked up in application database.
 * 
 * @author chandan singh
 *
 */
@Component
@Slf4j
public class JwtClientDetailsService implements UserDetailsService {
	
	
	@PostConstruct
	public void init() {
		log.debug("Activated: " + JwtClientDetailsService.class.getName());
	}

	@Autowired
	private ClientRepository clientRepository;
	
	private Map<String, Client> clientCache = new HashMap<String, Client>();
	
	@Override
	public UserDetails loadUserByUsername(String logonId) throws UsernameNotFoundException {
		
		Client client = null;
		
		//To avoid the database call every time, First check if the user is already in cache memory
		//Also if the user in cache has not logged in today then fetch the user from database.
		if(clientCache.containsKey(logonId)) {
			client = clientCache.get(logonId);
		}else {
			//So user is not present in cache memory, So fetch the user from database.
			//Also update the cache. 
			Optional<Client> optional = clientRepository.findByLogonId(logonId);
			
			if(optional.isPresent()) {
				client = optional.get();
				clientCache.put(logonId, client);
			} else {
				throw new UsernameNotFoundException("User Not Found: " + logonId);
			}
		}
		
		if(!"ACTIVE".equalsIgnoreCase(client.getStatus())) {
			throw new UsernameNotFoundException("Cient " +logonId + " is blocked, Kindly contact the admin");
		}
		
		UserDetails userDetails = new User(client.getLogonId(), "" , new ArrayList<>());
		return userDetails;
	}
	
	
	private boolean loggedInToday(Date loggedInDate) {
		
		if(loggedInDate != null) {
			Calendar loggedInCal = Calendar.getInstance();
			loggedInCal.setTime(loggedInDate);
			
			Calendar todaysCal = Calendar.getInstance();
			todaysCal.setTime(new Date());
			
			if(loggedInCal.get(Calendar.YEAR) == todaysCal.get(Calendar.YEAR)
					&& loggedInCal.get(Calendar.MONTH) == todaysCal.get(Calendar.MONTH)
					&& loggedInCal.get(Calendar.DAY_OF_MONTH) == todaysCal.get(Calendar.DAY_OF_MONTH))
				return true;
			else
				return false;
		}else
			return false;
	}
	
	
	public void clearCache(String logonId) {
		if(clientCache.containsKey(logonId)) {
			clientCache.remove(logonId);
		}
	}
}
